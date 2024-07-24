package cdx.opencdx.adr.service.impl;

import cdx.opencdx.adr.model.AnfStatementModel;
import cdx.opencdx.adr.model.MeasureModel;
import cdx.opencdx.adr.model.ParticipantModel;
import cdx.opencdx.adr.repository.ANFRepo;
import cdx.opencdx.adr.service.CsvService;
import cdx.opencdx.adr.utils.CsvBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class CsvServiceImpl implements CsvService {

    private final ANFRepo anfRepo;

    public CsvServiceImpl(ANFRepo anfRepo) {
        this.anfRepo = anfRepo;
    }


    @Override
    public CsvBuilder buildCsvDto(List<UUID> list, List<AnfStatementModel> results) {
        CsvBuilder csvDto = new CsvBuilder();
        AtomicInteger row = new AtomicInteger();
        list.stream().forEach(uuid -> {
            csvDto.setCell( row.get(), "Participant ID", uuid.toString());

            Optional<AnfStatementModel> recent = results.stream().filter(item -> item.getSubjectOfRecord().getPartId().equals(uuid)).sorted(Comparator.comparing(item -> item.getTime().getLowerBound())).findFirst();

            if(recent.isPresent()) {
                csvDto.setCell(row.get(), "Reported", new Date(recent.get().getTime().getLowerBound().longValue()).toString());
            }

            ArrayList<AnfStatementModel> anfs = this.anfRepo.getParticipantRepository().findAllByPartId(uuid).stream().map(ParticipantModel::getDimanfstatements).collect(ArrayList::new, List::addAll, List::addAll);

            anfs.forEach(anf -> {
                if(anf.getTopic() != null) {
                    if(anf.getPerformanceCircumstance() != null && anf.getPerformanceCircumstance().getResult() != null) {
                        if(anf.getPerformanceCircumstance().getTiming() != null) {
                            csvDto.setCell(row.get(), anf.getTopic().getTinkarConcept().getConceptName() + " Reported", new Date(anf.getPerformanceCircumstance().getTiming().getLowerBound().longValue()).toString());
                        }
                        csvDto.setCell(row.get(), anf.getTopic().getTinkarConcept().getConceptName(),  this.buildValue(anf.getPerformanceCircumstance().getResult()) );
                    } else if(anf.getRequestCircumstance() != null) {
                        if(anf.getRequestCircumstance().getTiming() != null) {
                            csvDto.setCell(row.get(), anf.getTopic().getTinkarConcept().getConceptName() + " Reported", new Date(anf.getRequestCircumstance().getTiming().getLowerBound().longValue()).toString());
                        }
                        csvDto.setCell(row.get(), anf.getTopic().getTinkarConcept().getConceptName(),  this.buildValue(anf.getRequestCircumstance().getRequestedResult()));
                    } else if(anf.getNarrativeCircumstance() != null && anf.getNarrativeCircumstance().getText() != null) {
                        if(anf.getNarrativeCircumstance().getTiming() != null) {
                            csvDto.setCell(row.get(), anf.getTopic().getTinkarConcept().getConceptName() + " Reported", new Date(anf.getNarrativeCircumstance().getTiming().getLowerBound().longValue()).toString());
                        }
                        csvDto.setCell(row.get(),anf.getTopic().getTinkarConcept().getConceptName(), anf.getNarrativeCircumstance().getText());
                    }
                }
            });
            row.getAndIncrement();
        });

        return csvDto;
    }

    private String buildValue(MeasureModel measure)  {
        if(measure == null) {
            return "\"\"";
        }
        StringBuilder sb = new StringBuilder();

        if(Boolean.TRUE.equals(measure.getIncludeLowerBound()) && Boolean.TRUE.equals(measure.getIncludeUpperBound())
                && measure.getLowerBound() != null && measure.getUpperBound() != null && measure.getUpperBound().equals(measure.getLowerBound())) {
            sb.append(measure.getLowerBound());

            if (measure.getUnit() != null) {
                sb.append(" ").append(measure.getUnit().getConceptName());
            }
        } else {
            if (Boolean.TRUE.equals(measure.getIncludeLowerBound())) {
                if (measure.getLowerBound() == Double.NEGATIVE_INFINITY) {
                    sb.append("INF");
                } else {
                    sb.append(measure.getLowerBound());
                }
            }
            if (measure.getIncludeLowerBound() && measure.getIncludeUpperBound()) {
                sb.append(" - ");
            }
            if (Boolean.TRUE.equals(measure.getIncludeUpperBound())) {
                if (measure.getUpperBound() == Double.POSITIVE_INFINITY) {
                    sb.append("INF");
                } else {
                    sb.append(measure.getUpperBound());
                }
            }

            if (measure.getUnit() != null) {
                sb.append(" ").append(measure.getUnit().getConceptName());
            }
        }

        return sb.toString();
    }
}
