package cdx.opencdx.adr.service.impl;

import cdx.opencdx.adr.dto.ConceptFocus;
import cdx.opencdx.adr.dto.Formula;
import cdx.opencdx.adr.dto.NumericalOperation;
import cdx.opencdx.adr.model.*;
import cdx.opencdx.adr.repository.CalculatedConceptRepository;
import cdx.opencdx.adr.repository.TinkarConceptRepository;
import cdx.opencdx.adr.service.ConceptService;
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
    private final ConceptService conceptService;

    public FormulaServiceImpl(TinkarConceptRepository tinkarConceptRepository, ConversionService conversionService, CalculatedConceptRepository calculatedConceptRepository, ConceptService conceptService) {
        this.tinkarConceptRepository = tinkarConceptRepository;
        this.conversionService = conversionService;
        this.calculatedConceptRepository = calculatedConceptRepository;
        this.conceptService = conceptService;
    }

    @Override
    public List<AnfStatementModel> evaluateFormula(Formula formula) {
        // Get the participant ID from the formula
        return this.tinkarConceptRepository.findAllByConceptIdIn(getConceptIds(formula))
                .stream().flatMap(concept -> concept.getAnfStatements().stream())
                .map(anf -> anf.getSubjectOfRecord().getPartId())
                .distinct()
                .map(id -> this.applyFormula(formula, id))
                .filter(Objects::nonNull)
                .toList();
    }

    private List<UUID> getConceptIds(Formula formula) {
        List<UUID> conceptIds = new ArrayList<>();

        if (formula.getLeftOperand() != null) {
            conceptIds.addAll(this.conceptService.getFocusConcepts(formula.getLeftOperand()));
        }
        if (formula.getRightOperand() != null) {
            conceptIds.addAll(this.conceptService.getFocusConcepts(formula.getRightOperand()));
        }
        if (formula.getLeftOperandFormula() != null) {
            conceptIds.addAll(getConceptIds(formula.getLeftOperandFormula()));
        }
        if (formula.getRightOperandFormula() != null) {
            conceptIds.addAll(getConceptIds(formula.getRightOperandFormula()));
        }

        return conceptIds.stream().distinct().toList();
    }

    private AnfStatementModel applyFormula(Formula formula, UUID participantId) {
        log.debug("Applying formula {} for participant {}", formula.getName(), participantId);
        Double value = this.evaluateFormula(formula, participantId);

        if (value != null) {
            log.debug("Formula {} evaluated to {}", formula.getName(), value);
            CalculatedConcept temp = new CalculatedConcept();
            temp.setConceptName(formula.getName());
            temp.setParticipantId(participantId);
            temp.setThreadName(Thread.currentThread().getName());
            temp.setValue(value);

            this.calculatedConceptRepository.save(temp);

            AnfStatementModel anf = new AnfStatementModel();
            ParticipantModel participantModel = new ParticipantModel();
            MeasureModel timing = new MeasureModel();
            MeasureModel result = new MeasureModel();
            TinkarConceptModel semantic = new TinkarConceptModel();
            PerformanceCircumstanceModel performanceCircumstance = new PerformanceCircumstanceModel();

            semantic.setConceptId(UUID.fromString(OpenCDXIKMServiceImpl.UNIT_CALENDAR_TIME));
            timing.setSemantic(semantic);
            timing.setResolution(1.0);
            timing.setLowerBound((double) Instant.now().getEpochSecond());
            timing.setUpperBound(timing.getLowerBound());
            timing.setIncludeLowerBound(true);
            timing.setIncludeUpperBound(true);

            result.setUpperBound(value);
            result.setLowerBound(value);
            result.setIncludeLowerBound(true);
            result.setIncludeUpperBound(true);

            performanceCircumstance.setTiming(timing);
            performanceCircumstance.setResult(result);

            participantModel.setPartId(participantId);
            anf.setSubjectOfRecord(participantModel);
            anf.setTime(timing);
            anf.setPerformanceCircumstance(performanceCircumstance);

            return anf;
        }

        return null;
    }

    private Double evaluateFormula(Formula formula, UUID participantId) {
        Double leftOperandValue = formula.getLeftOperandValue();
        Double rightOperandValue = formula.getRightOperandValue();

        log.debug("Evaluating formula {} for participant {}", formula.getName(), participantId);
        log.debug("Left operand value: {} Right operand value: {}", leftOperandValue, rightOperandValue);

        if (formula.getLeftOperandFormula() != null) {
            leftOperandValue = this.evaluateFormula(formula.getLeftOperandFormula(), participantId);
        } else if (formula.getLeftOperand() != null) {
            leftOperandValue = this.getMeasureValue(participantId, formula.getLeftOperand(), formula.getLeftOperandUnit().getConceptId());
        }

        log.debug("Left operand value: {} Right operand value: {}", leftOperandValue, rightOperandValue);

        if (formula.getRightOperandFormula() != null) {
            rightOperandValue = this.evaluateFormula(formula.getRightOperandFormula(), participantId);
        } else if (formula.getRightOperand() != null) {
            rightOperandValue = this.getMeasureValue(participantId, formula.getRightOperand(), formula.getRightOperandUnit().getConceptId());
        }

        log.debug("Left operand value: {} Right operand value: {}", leftOperandValue, rightOperandValue);

        if (leftOperandValue == null || rightOperandValue == null) {
            log.warn("Unable to evaluate formula {} for participant {}", formula.getName(), participantId);
            return null;
        }

        return this.performCalculation(leftOperandValue, formula.getOperation(), rightOperandValue);
    }

    private Double getMeasureValue(UUID participantId, TinkarConceptModel concept, UUID unitId) {
        List<UUID> focusConcepts = this.conceptService.getFocusConcepts(concept);

        List<TinkarConceptModel> concepts = this.tinkarConceptRepository.findAllByConceptIdIn(focusConcepts);

        if (concepts.isEmpty()) {
            log.warn("Concept {} not found", concept.getConceptId());
            return null;
        }

        Optional<AnfStatementModel> selected = concepts.stream().map(TinkarConceptModel::getAnfStatements).flatMap(List::stream).filter(anf -> anf.getSubjectOfRecord().getPartId().equals(participantId)).min((anf1, anf2) -> anf2.getTime().getUpperBound().compareTo(anf1.getTime().getUpperBound()));

        if (selected.isEmpty()) {
            log.warn("Participant {} not found in concept {}", participantId, concept.getConceptId());
            return null;
        }

        if (selected.get().getPerformanceCircumstance() != null && selected.get().getPerformanceCircumstance().getResult() != null) {
            log.debug("Performance circumstance found");
            return this.conversionService.convert(unitId, ConceptFocus.DATE.equals(concept.getFocus()) ? selected.get().getPerformanceCircumstance().getTiming() : selected.get().getPerformanceCircumstance().getResult()).getUpperBound();
        } else if (selected.get().getRequestCircumstance() != null) {
            log.debug("Request circumstance found");
            return this.conversionService.convert(unitId, ConceptFocus.DATE.equals(concept.getFocus()) ? selected.get().getRequestCircumstance().getTiming() : selected.get().getRequestCircumstance().getRequestedResult()).getUpperBound();
        } else if (selected.get().getNarrativeCircumstance() != null && ConceptFocus.DATE.equals(concept.getFocus())) {
            log.debug("Narrative circumstance found");
            return this.conversionService.convert(unitId, selected.get().getRequestCircumstance().getTiming()).getUpperBound();
        }
        log.debug("No performance or request circumstance found");
        return null;
    }


    private Double performCalculation(Double left, NumericalOperation operation, Double right) {
        log.debug("Performing calculation: {} {} {}", left, operation, right);
        return switch (operation) {
            case ADD -> left + right;
            case SUBTRACT -> left - right;
            case MULTIPLY -> left * right;
            case DIVIDE -> left / right;
            case MODULO -> left % right;
            case POWER -> Math.pow(left, right);
        };
    }
}
