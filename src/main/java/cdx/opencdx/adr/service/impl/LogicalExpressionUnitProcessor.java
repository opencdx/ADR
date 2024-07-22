package cdx.opencdx.adr.service.impl;

import cdx.opencdx.adr.model.*;
import cdx.opencdx.adr.repository.MeasureRepository;
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
public class LogicalExpressionUnitProcessor implements OpenCDXANFProcessor {
    private final TinkarConceptRepository tinkarConceptRepository;

    private final OpenCDXIKMService ikmService;
    private final MeasureRepository measureRepository;

    public LogicalExpressionUnitProcessor(OpenCDXIKMService ikmService,
                                          TinkarConceptRepository tinkarConceptRepository, MeasureRepository measureRepository) {
        this.ikmService = ikmService;
        this.tinkarConceptRepository = tinkarConceptRepository;
        this.measureRepository = measureRepository;
    }

    /**
     * Process the ANF Statement
     *
     * @param anfStatement
     */
    @Override
    public void processAnfStatement(AnfStatementModel anfStatement) {
        List<MeasureModel> list = new ArrayList<>();

        list.add(anfStatement.getTime());
        if(anfStatement.getPerformanceCircumstance() != null) {
            list.add(anfStatement.getPerformanceCircumstance().getResult());
            if(anfStatement.getPerformanceCircumstance().getNormalRange() != null) {
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
        } else if(anfStatement.getNarrativeCircumstance() != null) {
            list.add(anfStatement.getNarrativeCircumstance().getTiming());
        }

        list.stream().filter(Objects::nonNull).forEach(measure -> {
            TinkarConceptModel tinkar = this.ikmService.getInkarConceptModel(measure.getSemantic());

            if(tinkar != null) {
                if(!this.tinkarConceptRepository.existsByConceptId(tinkar.getConceptId())) {
                   tinkar =  this.tinkarConceptRepository.save(tinkar);
                } else {
                    tinkar = this.tinkarConceptRepository.findByConceptId(tinkar.getConceptId());
                }

                measure.setUnit(tinkar);

                this.measureRepository.save(measure);
            }

        });
    }
}
