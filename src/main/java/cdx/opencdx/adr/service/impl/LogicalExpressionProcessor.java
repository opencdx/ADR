package cdx.opencdx.adr.service.impl;

import cdx.opencdx.adr.model.*;
import cdx.opencdx.adr.repository.ANFRepo;
import cdx.opencdx.adr.service.OpenCDXANFProcessor;
import cdx.opencdx.adr.service.OpenCDXIKMService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * This class is responsible for processing logical expressions in the context
 * of the OpenCDX system.
 */
@Slf4j
@Component
public class LogicalExpressionProcessor implements OpenCDXANFProcessor {
    /**
     * This variable represents an instance of ANFRepo.
     * ANFRepo is a class that is responsible for managing ANF (Algebraic Normal Form) expressions.
     * It provides methods for creating, querying, and manipulating ANF expressions.
     * <p>
     * The variable is declared as private final, which means it cannot be accessed or modified from outside the class,
     * and its value cannot be changed once assigned. This ensures the integrity and encapsulation of the ANFRepo instance.
     *
     * @see ANFRepo
     */
    private final ANFRepo anfRepo;

    /**
     * The ikmService variable represents an instance of the OpenCDXIKMService class.
     */
    private final OpenCDXIKMService ikmService;

    /**
     * Creates a new instance of LogicalExpressionProcessor.
     *
     * @param ikmService the OpenCDXIKMService used for processing logical expressions
     * @param anfRepo    the ANFRepo used for accessing ANF data
     */
    public LogicalExpressionProcessor(OpenCDXIKMService ikmService,
                                      ANFRepo anfRepo) {
        this.ikmService = ikmService;
        this.anfRepo = anfRepo;
    }

    /**
     * Process the given AnfStatementModel by updating the associated TinkarConceptModel objects.
     *
     * @param anfStatement The AnfStatementModel to process.
     */
    @Override
    public void processAnfStatement(AnfStatementModel anfStatement) {


        this.updateTinkarConceptModel(anfStatement.getAssociatedStatements().stream().map(AssociatedStatementModel::getSemantic).filter(Objects::nonNull).toList(), anfStatement);
        this.updateTinkarConceptModel(anfStatement.getSubjectOfInformation(), anfStatement);
        this.updateTinkarConceptModel(anfStatement.getTopic(), anfStatement);
        this.updateTinkarConceptModel(anfStatement.getType(), anfStatement);
        this.updateTinkarConceptModel(anfStatement.getSubjectOfRecord().getCode(), anfStatement);
        this.updateTinkarConceptModel(anfStatement.getAuthors().stream().map(PractitionerModel::getCode).filter(Objects::nonNull).toList(), anfStatement);
        if (anfStatement.getPerformanceCircumstance() != null) {
            this.updateTinkarConceptModel(anfStatement.getPerformanceCircumstance().getStatus(), anfStatement);
            if (anfStatement.getPerformanceCircumstance().getHealthRisk() != null) {
                this.updateTinkarConceptModel(anfStatement.getPerformanceCircumstance().getHealthRisk(), anfStatement);
            }
            this.updateTinkarConceptModel(anfStatement.getPerformanceCircumstance().getPurposes(), anfStatement);
        } else if (anfStatement.getRequestCircumstance() != null) {
            this.updateTinkarConceptModel(anfStatement.getRequestCircumstance().getConditionalTrigger().stream().map(AssociatedStatementModel::getSemantic).filter(Objects::nonNull).toList(), anfStatement);
            this.updateTinkarConceptModel(anfStatement.getRequestCircumstance().getPriority(), anfStatement);
            this.updateTinkarConceptModel(anfStatement.getRequestCircumstance().getPurposes(), anfStatement);
        } else if (anfStatement.getNarrativeCircumstance() != null) {
            this.updateTinkarConceptModel(anfStatement.getNarrativeCircumstance().getPurposes(), anfStatement);
        }
    }

    /**
     * Updates the Tinkar Concept Model for each LogicalExpressionModel in the given list using the provided AnfStatementModel.
     *
     * @param models       the list of LogicalExpressionModels to update
     * @param anfStatement the AnfStatementModel to use for updating the Tinkar Concept Model
     * @throws NullPointerException if the models parameter is null
     */
    private void updateTinkarConceptModel(List<LogicalExpressionModel> models, AnfStatementModel anfStatement) {
        if (models == null) {
            return;
        }
        models.forEach(model -> this.updateTinkarConceptModel(model, anfStatement));
    }

    /**
     * Updates the Tinkar concept model in the logical expression model.
     *
     * @param model        The logical expression model to update.
     * @param anfStatement The ANF statement model to add to the Tinkar concept model.
     */
    private void updateTinkarConceptModel(LogicalExpressionModel model, AnfStatementModel anfStatement) {
        if (model == null) {
            return;
        }

        TinkarConceptModel work = this.ikmService.getInkarConceptModel(model);

        if (this.anfRepo.getTinkarConceptRepository().existsByConceptId(work.getConceptId())) {
            work = this.anfRepo.getTinkarConceptRepository().findByConceptId(work.getConceptId());
        } else {
            work = this.anfRepo.getTinkarConceptRepository().save(work);
        }

        Optional<AnfStatementModel> first = work.getAnfStatements().stream().filter(Objects::nonNull).filter(anf -> anf.getId().equals(anfStatement.getId())).findFirst();
        if (first.isEmpty()) {
            work.getAnfStatements().add(anfStatement);
            this.anfRepo.getTinkarConceptRepository().save(work);
        }

        model.setTinkarConcept(work);
        this.anfRepo.getLogicalExpressionRepository().save(model);
    }
}
