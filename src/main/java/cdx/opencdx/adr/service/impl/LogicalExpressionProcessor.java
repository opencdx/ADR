package cdx.opencdx.adr.service.impl;

import cdx.opencdx.adr.model.AnfStatementModel;
import cdx.opencdx.adr.model.AssociatedStatementModel;
import cdx.opencdx.adr.model.LogicalExpressionModel;
import cdx.opencdx.adr.model.PractitionerModel;
import cdx.opencdx.adr.repository.TinkarConceptRepository;
import cdx.opencdx.adr.service.OpenCDXANFProcessor;
import cdx.opencdx.adr.service.OpenCDXIKMService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class LogicalExpressionProcessor implements OpenCDXANFProcessor {
    private final TinkarConceptRepository tinkarConceptRepository;

    private final OpenCDXIKMService ikmService;

    public LogicalExpressionProcessor(OpenCDXIKMService ikmService,
                                      TinkarConceptRepository tinkarConceptRepository) {
        this.ikmService = ikmService;
        this.tinkarConceptRepository = tinkarConceptRepository;
    }

    /**
     * Process the ANF Statement
     *
     * @param anfStatement
     */
    @Override
    public void processAnfStatement(AnfStatementModel anfStatement) {
        List<LogicalExpressionModel> list = new ArrayList<>();

        list.addAll(anfStatement.getAssociatedStatements().stream().map(AssociatedStatementModel::getSemantic).filter(Objects::nonNull).toList());
        list.add(anfStatement.getSubjectOfInformation());
        list.add(anfStatement.getTopic());
        list.add(anfStatement.getType());
        list.add(anfStatement.getSubjectOfRecord().getCode());
        list.addAll(anfStatement.getAuthors().stream().map(PractitionerModel::getCode).filter(Objects::nonNull).toList());
        if(anfStatement.getPerformanceCircumstance() != null) {
            list.add(anfStatement.getPerformanceCircumstance().getStatus());
            list.add(anfStatement.getPerformanceCircumstance().getHealthRisk());
            list.addAll(anfStatement.getPerformanceCircumstance().getPurposes());
        } else if (anfStatement.getRequestCircumstance() != null) {
            list.addAll(anfStatement.getRequestCircumstance().getConditionalTrigger().stream().map(AssociatedStatementModel::getSemantic).filter(Objects::nonNull).toList());
            list.add(anfStatement.getRequestCircumstance().getPriority());
            list.addAll(anfStatement.getRequestCircumstance().getPurposes());
        } else if(anfStatement.getNarrativeCircumstance() != null) {
            list.addAll(anfStatement.getNarrativeCircumstance().getPurposes());
        }

        list.stream().distinct().map(this.ikmService::getInkarConceptModel).filter(Objects::nonNull).forEach(tinkar -> {
            if(!this.tinkarConceptRepository.existsByConceptId(tinkar.getConceptId())) {
                log.info("Saving Tinkar Concept: {}", tinkar);
                this.tinkarConceptRepository.save(tinkar);
            }
        });
    }
}
