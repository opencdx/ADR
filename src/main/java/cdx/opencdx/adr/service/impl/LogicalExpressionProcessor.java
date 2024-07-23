package cdx.opencdx.adr.service.impl;

import cdx.opencdx.adr.model.*;
import cdx.opencdx.adr.repository.ANFRepo;
import cdx.opencdx.adr.repository.TinkarConceptRepository;
import cdx.opencdx.adr.service.OpenCDXANFProcessor;
import cdx.opencdx.adr.service.OpenCDXIKMService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
public class LogicalExpressionProcessor implements OpenCDXANFProcessor {
    private final ANFRepo anfRepo;

    private final OpenCDXIKMService ikmService;

    public LogicalExpressionProcessor(OpenCDXIKMService ikmService,
                                      ANFRepo anfRepo) {
        this.ikmService = ikmService;
        this.anfRepo = anfRepo;
    }

    /**
     * Process the ANF Statement
     *
     * @param anfStatement
     */
    @Override
    public void processAnfStatement(AnfStatementModel anfStatement) {
        

        this.updateTinkarConceptModel(anfStatement.getAssociatedStatements().stream().map(AssociatedStatementModel::getSemantic).filter(Objects::nonNull).toList(), anfStatement);
        this.updateTinkarConceptModel(anfStatement.getSubjectOfInformation(), anfStatement);
        this.updateTinkarConceptModel(anfStatement.getTopic(), anfStatement);
        this.updateTinkarConceptModel(anfStatement.getType(), anfStatement);
        this.updateTinkarConceptModel(anfStatement.getSubjectOfRecord().getCode(), anfStatement);
        this.updateTinkarConceptModel(anfStatement.getAuthors().stream().map(PractitionerModel::getCode).filter(Objects::nonNull).toList(), anfStatement);
        if(anfStatement.getPerformanceCircumstance() != null) {
            this.updateTinkarConceptModel(anfStatement.getPerformanceCircumstance().getStatus(), anfStatement);
            if(anfStatement.getPerformanceCircumstance().getHealthRisk() !=  null) {
                this.updateTinkarConceptModel(anfStatement.getPerformanceCircumstance().getHealthRisk(), anfStatement);
            }
            this.updateTinkarConceptModel(anfStatement.getPerformanceCircumstance().getPurposes(), anfStatement);
        } else if (anfStatement.getRequestCircumstance() != null) {
            this.updateTinkarConceptModel(anfStatement.getRequestCircumstance().getConditionalTrigger().stream().map(AssociatedStatementModel::getSemantic).filter(Objects::nonNull).toList(), anfStatement);
            this.updateTinkarConceptModel(anfStatement.getRequestCircumstance().getPriority(), anfStatement);
            this.updateTinkarConceptModel(anfStatement.getRequestCircumstance().getPurposes(), anfStatement);
        } else if(anfStatement.getNarrativeCircumstance() != null) {
            this.updateTinkarConceptModel(anfStatement.getNarrativeCircumstance().getPurposes(), anfStatement);
        };
    }
    private void updateTinkarConceptModel(List<LogicalExpressionModel> models, AnfStatementModel anfStatement) {
        if(models == null) {
            return;
        }
        models.forEach(model -> this.updateTinkarConceptModel(model, anfStatement));
    }
    private void updateTinkarConceptModel(LogicalExpressionModel model, AnfStatementModel anfStatement) {
        if(model == null) {
            return;
        }
        
        TinkarConceptModel work = this.ikmService.getInkarConceptModel(model);
        
        if(this.anfRepo.getTinkarConceptRepository().existsByConceptId(work.getConceptId())) {
            work = this.anfRepo.getTinkarConceptRepository().findByConceptId(work.getConceptId());
        } else {
            work = this.anfRepo.getTinkarConceptRepository().save(work);
        }

        Optional<AnfStatementModel> first = work.getAnfStatements().stream().filter(Objects::nonNull).filter(anf -> anf.getId().equals(anfStatement.getId())).findFirst();
        if(first.isEmpty()) {
            work.getAnfStatements().add(anfStatement);
            this.anfRepo.getTinkarConceptRepository().save(work);
        }

        model.setTinkarConcept(work);
        this.anfRepo.getLogicalExpressionRepository().save(model);
    }
}
