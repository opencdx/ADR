package cdx.opencdx.adr.service.impl;

import cdx.opencdx.adr.model.*;
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
    private static final String NEGATIVE_INFINITY = "INF";
    private static final String POSITIVE_INFINITY = "INF";
    private static final String SEPARATOR = " - ";

    private final ANFRepo anfRepo;

    public CsvServiceImpl(ANFRepo anfRepo) {
        this.anfRepo = anfRepo;
    }


    @Override
    public CsvBuilder buildCsvDto(List<UUID> list, List<AnfStatementModel> results) {
        CsvBuilder csvDto = new CsvBuilder();
        AtomicInteger row = new AtomicInteger();

        list.forEach(uuid -> {
            int currentRow = row.getAndIncrement();
            csvDto.setCell(currentRow, "Participant ID", uuid.toString());

            Optional<Date> recentDate = findRecentStatementResult(results, uuid);
            recentDate.ifPresent(date -> csvDto.setCell(currentRow, "Reported", date.toString()));

            ArrayList<AnfStatementModel> anfs = this.anfRepo.getParticipantRepository().findAllByPartId(uuid)
                    .stream()
                    .map(ParticipantModel::getDimanfstatements)
                    .collect(ArrayList::new, List::addAll, List::addAll);

            anfs.forEach(anf -> {
                if (anf.getTopic() != null) {
                    var conceptName = anf.getTopic().getTinkarConcept().getConceptName();
                    if (anf.getPerformanceCircumstance() != null && anf.getPerformanceCircumstance().getResult() != null) {
                        processPerformanceCircumstance(csvDto, currentRow, conceptName, anf.getPerformanceCircumstance());
                    } else if (anf.getRequestCircumstance() != null) {
                        processRequestCircumstance(csvDto, currentRow, conceptName, anf.getRequestCircumstance());
                    } else if (anf.getNarrativeCircumstance() != null && anf.getNarrativeCircumstance().getText() != null) {
                        processNarrativeCircumstance(csvDto, currentRow, conceptName, anf.getNarrativeCircumstance());
                    }
                }
            });
        });
        return csvDto;
    }

    private void processPerformanceCircumstance(CsvBuilder csvDto, int row, String conceptName, PerformanceCircumstanceModel performanceCircumstance) {
        if (performanceCircumstance.getTiming() != null) {
            csvDto.setCell(row, conceptName + " Reported", new Date(performanceCircumstance.getTiming().getLowerBound().longValue()).toString());
        }
        csvDto.setCell(row, conceptName, formatMeasure(performanceCircumstance.getResult()));
    }

    private void processRequestCircumstance(CsvBuilder csvDto, int row, String conceptName, RequestCircumstanceModel requestCircumstance) {
        if (requestCircumstance.getTiming() != null) {
            csvDto.setCell(row, conceptName + " Reported", new Date(requestCircumstance.getTiming().getLowerBound().longValue()).toString());
        }
        csvDto.setCell(row, conceptName, formatMeasure(requestCircumstance.getRequestedResult()));
    }

    private void processNarrativeCircumstance(CsvBuilder csvDto, int row, String conceptName, NarrativeCircumstanceModel narrativeCircumstance) {
        if (narrativeCircumstance.getTiming() != null) {
            csvDto.setCell(row, conceptName + " Reported", new Date(narrativeCircumstance.getTiming().getLowerBound().longValue()).toString());
        }
        csvDto.setCell(row, conceptName, narrativeCircumstance.getText());
    }

    private Optional<Date> findRecentStatementResult(List<AnfStatementModel> results, UUID uuid) {
        return results.stream()
                .filter(item -> item.getSubjectOfRecord().getPartId().equals(uuid))
                .sorted(Comparator.comparing(item -> item.getTime().getLowerBound()))
                .map(item -> new Date(item.getTime().getLowerBound().longValue()))
                .findFirst();
    }


    private String formatMeasure(MeasureModel measure) {
        if (measure == null) {
            return "\"\"";
        }
        StringBuilder sb = new StringBuilder();
        if (boundsAreEqualAndIncluded(measure)) {
            sb.append(measure.getLowerBound());
            appendUnitIfPresent(measure, sb);
        } else {
            appendLowerBoundIfIncluded(measure, sb);
            appendSeparatorIfBoundsIncluded(measure, sb);
            appendUpperBoundIfIncluded(measure, sb);
            appendUnitIfPresent(measure, sb);
        }
        return sb.toString();
    }

    private boolean boundsAreEqualAndIncluded(MeasureModel measure) {
        return Boolean.TRUE.equals(measure.getIncludeLowerBound())
                && Boolean.TRUE.equals(measure.getIncludeUpperBound())
                && measure.getLowerBound() != null
                && measure.getUpperBound() != null
                && measure.getUpperBound().equals(measure.getLowerBound());
    }

    private void appendUnitIfPresent(MeasureModel measure, StringBuilder sb) {
        if (measure.getUnit() != null) {
            sb.append(" ").append(measure.getUnit().getConceptName());
        }
    }

    private void appendLowerBoundIfIncluded(MeasureModel measure, StringBuilder sb) {
        if (Boolean.TRUE.equals(measure.getIncludeLowerBound())) {
            if (measure.getLowerBound() == Double.NEGATIVE_INFINITY) {
                sb.append(NEGATIVE_INFINITY);
            } else {
                sb.append(measure.getLowerBound());
            }
        }
    }

    private void appendSeparatorIfBoundsIncluded(MeasureModel measure, StringBuilder sb) {
        if (measure.getIncludeLowerBound() && measure.getIncludeUpperBound()) {
            sb.append(SEPARATOR);
        }
    }

    private void appendUpperBoundIfIncluded(MeasureModel measure, StringBuilder sb) {
        if (Boolean.TRUE.equals(measure.getIncludeUpperBound())) {
            if (measure.getUpperBound() == Double.POSITIVE_INFINITY) {
                sb.append(POSITIVE_INFINITY);
            } else {
                sb.append(measure.getUpperBound());
            }
        }
    }
}
