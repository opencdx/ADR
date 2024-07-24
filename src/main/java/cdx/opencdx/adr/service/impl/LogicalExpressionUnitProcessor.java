package cdx.opencdx.adr.service.impl;

import cdx.opencdx.adr.model.AnfStatementModel;
import cdx.opencdx.adr.model.MeasureModel;
import cdx.opencdx.adr.model.TinkarConceptModel;
import cdx.opencdx.adr.repository.MeasureRepository;
import cdx.opencdx.adr.repository.TinkarConceptRepository;
import cdx.opencdx.adr.service.OpenCDXANFProcessor;
import cdx.opencdx.adr.service.OpenCDXIKMService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class is responsible for processing logical expression units in ANF statements.
 */
@Slf4j
@Component
public class LogicalExpressionUnitProcessor implements OpenCDXANFProcessor {
    /**
     * Private final variable representing the TinkarConceptRepository.
     */
    private final TinkarConceptRepository tinkarConceptRepository;

    /**
     * The OpenCDXIKMService used for retrieving TinkarConceptModel.
     */
    private final OpenCDXIKMService ikmService;
    /**
     * The measureRepository variable is a private final instance of the MeasureRepository interface.
     * It is used to store and retrieve MeasureModel objects, which represent measures in the ANFStatementModel class.
     * It provides methods for saving, finding, and checking the existence of measure objects in the database.
     */
    private final MeasureRepository measureRepository;

    /**
     * This class is responsible for processing logical expression units in ANF statements.
     */
    public LogicalExpressionUnitProcessor(OpenCDXIKMService ikmService,
                                          TinkarConceptRepository tinkarConceptRepository, MeasureRepository measureRepository) {
        this.ikmService = ikmService;
        this.tinkarConceptRepository = tinkarConceptRepository;
        this.measureRepository = measureRepository;
    }

    /**
     * Processes the ANF statement by extracting relevant measures and saving them in the database.
     *
     * @param anfStatement the ANF statement to process
     */
    @Override
    public void processAnfStatement(AnfStatementModel anfStatement) {
        List<MeasureModel> list = new ArrayList<>();

        list.add(anfStatement.getTime());
        if (anfStatement.getPerformanceCircumstance() != null) {
            list.add(anfStatement.getPerformanceCircumstance().getResult());
            if (anfStatement.getPerformanceCircumstance().getNormalRange() != null) {
                list.add(anfStatement.getPerformanceCircumstance().getNormalRange());
            }

            list.add(anfStatement.getPerformanceCircumstance().getTiming());
        } else if (anfStatement.getRequestCircumstance() != null) {
            list.add(anfStatement.getRequestCircumstance().getTiming());
            list.add(anfStatement.getRequestCircumstance().getRequestedResult());
            list.add(anfStatement.getRequestCircumstance().getRepetition().getEventDuration());
            list.add(anfStatement.getRequestCircumstance().getRepetition().getEventSeparation());
            list.add(anfStatement.getRequestCircumstance().getRepetition().getEventFrequency());
            list.add(anfStatement.getRequestCircumstance().getRepetition().getPeriodStart());
            list.add(anfStatement.getRequestCircumstance().getRepetition().getPeriodDuration());
        } else if (anfStatement.getNarrativeCircumstance() != null) {
            list.add(anfStatement.getNarrativeCircumstance().getTiming());
        }

        list.stream().filter(Objects::nonNull).forEach(measure -> {
            TinkarConceptModel tinkar = this.ikmService.getInkarConceptModel(measure.getSemantic());

            if (tinkar != null) {
                if (!this.tinkarConceptRepository.existsByConceptId(tinkar.getConceptId())) {
                    tinkar = this.tinkarConceptRepository.save(tinkar);
                } else {
                    tinkar = this.tinkarConceptRepository.findByConceptId(tinkar.getConceptId());
                }

                measure.setUnit(tinkar);

                this.measureRepository.save(measure);
            }

        });
    }
}
