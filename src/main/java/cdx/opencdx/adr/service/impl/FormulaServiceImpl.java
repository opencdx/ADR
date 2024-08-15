package cdx.opencdx.adr.service.impl;

import cdx.opencdx.adr.dto.Formula;
import cdx.opencdx.adr.dto.NumericalOperation;
import cdx.opencdx.adr.model.*;
import cdx.opencdx.adr.repository.ANFStatementRepository;
import cdx.opencdx.adr.repository.CalculatedConceptRepository;
import cdx.opencdx.adr.repository.TinkarConceptRepository;
import cdx.opencdx.adr.service.ConversionService;
import cdx.opencdx.adr.service.FormulaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Slf4j
@Service
public class FormulaServiceImpl implements FormulaService {

    private final TinkarConceptRepository tinkarConceptRepository;
    private final ConversionService conversionService;
    private final CalculatedConceptRepository calculatedConceptRepository;

    public FormulaServiceImpl(TinkarConceptRepository tinkarConceptRepository, ConversionService conversionService, CalculatedConceptRepository calculatedConceptRepository) {
        this.tinkarConceptRepository = tinkarConceptRepository;
        this.conversionService = conversionService;
        this.calculatedConceptRepository = calculatedConceptRepository;
    }

    @Override
    public List<AnfStatementModel> evaluateFormula(Formula formula) {
        // Get the participant ID from the formula
        return this.tinkarConceptRepository.findAllByConceptIdIn(getConceptIds(formula))
                .stream().flatMap(concept -> concept.getAnfStatements().stream())
                .map(anf -> anf.getSubjectOfRecord().getPartId())
                .distinct()
                .map(id -> this.applyFormula(formula,id))
                .filter(Objects::nonNull)
                .toList();
    }

    private List<UUID> getConceptIds(Formula formula) {
        List<UUID> conceptIds = new ArrayList<>();

        if(formula.getLeftOperand() != null) {
            conceptIds.add(formula.getLeftOperand());
        }
        if(formula.getRightOperand() != null) {
            conceptIds.add(formula.getRightOperand());
        }
        if(formula.getLeftOperandFormula() != null) {
            conceptIds.addAll(getConceptIds(formula.getLeftOperandFormula()));
        }
        if(formula.getRightOperandFormula() != null) {
            conceptIds.addAll(getConceptIds(formula.getRightOperandFormula()));
        }

        return conceptIds.stream().distinct().toList();
    }

    private AnfStatementModel applyFormula(Formula formula, UUID participantId) {
        log.info("Applying formula {} for participant {}", formula.getName(), participantId);
        Double value = this.evaluateFormula(formula, participantId);

        if(value != null) {
            log.info("Formula {} evaluated to {}", formula.getName(), value);
            CalculatedConcept temp = new CalculatedConcept();
            temp.setConceptName(formula.getName());
            temp.setParticipantId(participantId);
            temp.setThreadName(Thread.currentThread().getName());
            temp.setValue(value);

            this.calculatedConceptRepository.save(temp);

            AnfStatementModel anf = new AnfStatementModel();
            ParticipantModel participantModel = new ParticipantModel();
            MeasureModel timing = new MeasureModel();
            TinkarConceptModel semantic = new TinkarConceptModel();

            semantic.setConceptId(UUID.fromString(OpenCDXIKMServiceImpl.UNIT_SECONDS));
            timing.setSemantic(semantic);
            timing.setResolution(1.0);
            timing.setLowerBound((double) Instant.now().getEpochSecond());
            timing.setUpperBound(timing.getLowerBound());


            participantModel.setPartId(participantId);
            anf.setSubjectOfRecord(participantModel);
            anf.setTime(timing);

            return anf;
        }

        return null;
    }

    private Double evaluateFormula(Formula formula, UUID participantId) {
        Double leftOperandValue = formula.getLeftOperandValue();
        Double rightOperandValue = formula.getRightOperandValue();

        log.info("Evaluating formula {} for participant {}", formula.getName(), participantId);
        log.info("Left operand value: {} Right operand value: {}", leftOperandValue, rightOperandValue);

        if(formula.getLeftOperandFormula() != null) {
            leftOperandValue = this.evaluateFormula(formula.getLeftOperandFormula(), participantId);
        } else if(formula.getLeftOperand() != null) {
            leftOperandValue = this.getMeasureValue(participantId, formula.getLeftOperand(), formula.getLeftOperandUnit());
        }

        log.info("Left operand value: {} Right operand value: {}", leftOperandValue, rightOperandValue);

        if(formula.getRightOperandFormula() != null) {
            rightOperandValue = this.evaluateFormula(formula.getRightOperandFormula(), participantId);
        } else if (formula.getRightOperand() != null) {
            rightOperandValue = this.getMeasureValue(participantId, formula.getRightOperand(), formula.getRightOperandUnit());
        }

        log.info("Left operand value: {} Right operand value: {}", leftOperandValue, rightOperandValue);

        if(leftOperandValue == null || rightOperandValue == null) {
            log.warn("Unable to evaluate formula {} for participant {}", formula.getName(), participantId);
            return null;
        }

        return this.performCalculation(leftOperandValue, formula.getOperation(), rightOperandValue);
    }

    private Double getMeasureValue(UUID participantId, UUID conceptId, UUID unitId) {
        TinkarConceptModel concept = this.tinkarConceptRepository.findByConceptId(conceptId);

        if(concept == null) {
            log.warn("Concept {} not found", conceptId);
            return null;
        }

        Optional<AnfStatementModel> selected = concept.getAnfStatements().stream().filter(anf -> anf.getSubjectOfRecord().getPartId().equals(participantId))
                .sorted((anf1, anf2) -> anf2.getTime().getUpperBound().compareTo(anf1.getTime().getUpperBound())).findFirst();

        if(selected.isEmpty()) {
            log.warn("Participant {} not found in concept {}", participantId, conceptId);
            return null;
        }

        if (selected.get().getPerformanceCircumstance() != null && selected.get().getPerformanceCircumstance().getResult() != null) {
            log.info("Performance circumstance found");
            return this.conversionService.convert(unitId,selected.get().getPerformanceCircumstance().getResult()).getUpperBound();
        } else if (selected.get().getRequestCircumstance() != null) {
            log.info("Request circumstance found");
            return this.conversionService.convert(unitId,selected.get().getRequestCircumstance().getRequestedResult()).getUpperBound();
        }
        log.info("No performance or request circumstance found");
        return null;
    }


    private Double performCalculation(Double left, NumericalOperation operation, Double right) {
        log.info("Performing calculation: {} {} {}", left, operation, right);
        switch (operation) {
            case ADD:
                return left + right;
            case SUBTRACT:
                return left - right;
            case MULTIPLY:
                return left * right;
            case DIVIDE:
                return left / right;
            case MODULO:
                return left % right;
            case POWER:
                return Math.pow(left, right);
            default:
                return null;
        }
    }
}
