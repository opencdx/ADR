package cdx.opencdx.adr.service.impl;

import cdx.opencdx.adr.model.*;
import cdx.opencdx.adr.repository.ANFRepo;
import cdx.opencdx.adr.service.CsvService;
import cdx.opencdx.adr.utils.CsvBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class provides an implementation of the CsvService interface.
 * It is responsible for building a CsvBuilder object based on a list of UUIDs and a list of AnfStatementModel objects.
 * The CsvBuilder object represents a CSV file with data extracted from the given lists.
 */
@Slf4j
@Service
public class CsvServiceImpl implements CsvService {
    /**
     * Represents the concept of negative infinity.
     * <p>
     * This constant is used to represent a value that is smaller than any finite number.
     * </p>
     */
    private static final String NEGATIVE_INFINITY = "INF";
    /**
     * Represents the positive infinity value.
     * The value is represented as a string with the value "INF".
     */
    private static final String POSITIVE_INFINITY = "INF";
    /**
     * This constant variable represents the separator used to separate elements in a string.
     * The value of this variable is "-".
     */
    private static final String SEPARATOR = " - ";

    /**
     * The ANFRepo class is used to access and manipulate ANF (Algebraic Normal Form) data.
     */
    private final ANFRepo anfRepo;

    /**
     * Creates a new instance of CsvServiceImpl with the given ANFRepo.
     *
     * @param anfRepo the ANFRepo to be used by CsvServiceImpl
     */
    public CsvServiceImpl(ANFRepo anfRepo) {
        this.anfRepo = anfRepo;
    }


    /**
     * Builds a CsvBuilder object based on the given list of UUIDs and list of AnfStatementModels.
     *
     * @param list    the list of UUIDs
     * @param results the list of AnfStatementModels
     * @return the CsvBuilder object containing the built CSV data
     */
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

    /**
     * Processes the performance circumstance for a given row in the CSV builder.
     *
     * @param csvDto                  - the CSV builder object to update the cells
     * @param row                     - the row number to update
     * @param conceptName             - the name of the performance circumstance concept
     * @param performanceCircumstance - the performance circumstance model containing the timing and result information
     */
    private void processPerformanceCircumstance(CsvBuilder csvDto, int row, String conceptName, PerformanceCircumstanceModel performanceCircumstance) {
        if (performanceCircumstance.getTiming() != null) {
            csvDto.setCell(row, conceptName + " Reported", new Date(performanceCircumstance.getTiming().getLowerBound().longValue()).toString());
        }
        csvDto.setCell(row, conceptName, formatMeasure(performanceCircumstance.getResult()));
    }

    /**
     * Process the request circumstance and populate the given CsvBuilder object.
     *
     * @param csvDto              The CsvBuilder object to populate.
     * @param row                 The row number in the CsvBuilder to populate.
     * @param conceptName         The name of the concept.
     * @param requestCircumstance The RequestCircumstanceModel object containing the request circumstance data.
     */
    private void processRequestCircumstance(CsvBuilder csvDto, int row, String conceptName, RequestCircumstanceModel requestCircumstance) {
        if (requestCircumstance.getTiming() != null) {
            csvDto.setCell(row, conceptName + " Reported", new Date(requestCircumstance.getTiming().getLowerBound().longValue()).toString());
        }
        csvDto.setCell(row, conceptName, formatMeasure(requestCircumstance.getRequestedResult()));
    }

    /**
     * Processes the narrative circumstance and updates the CSV builder with the necessary information.
     *
     * @param csvDto                The CSV builder object used to store the data.
     * @param row                   The index of the row to update in the CSV builder.
     * @param conceptName           The name of the concept associated with the narrative circumstance.
     * @param narrativeCircumstance The narrative circumstance model to process.
     */
    private void processNarrativeCircumstance(CsvBuilder csvDto, int row, String conceptName, NarrativeCircumstanceModel narrativeCircumstance) {
        if (narrativeCircumstance.getTiming() != null) {
            csvDto.setCell(row, conceptName + " Reported", new Date(narrativeCircumstance.getTiming().getLowerBound().longValue()).toString());
        }
        csvDto.setCell(row, conceptName, narrativeCircumstance.getText());
    }

    /**
     * Finds the most recent statement result for a given UUID.
     *
     * @param results the list of AnfStatementModel objects to search through
     * @param uuid    the UUID to find the recent statement result for
     * @return an Optional object containing the Date of the most recent statement result, or an empty Optional if no result is found
     * @throws NullPointerException if either results or uuid is null
     */
    private Optional<Date> findRecentStatementResult(List<AnfStatementModel> results, UUID uuid) {
        return results.stream()
                .filter(item -> item.getSubjectOfRecord().getPartId().equals(uuid))
                .sorted(Comparator.comparing(item -> item.getTime().getLowerBound()))
                .map(item -> new Date(item.getTime().getLowerBound().longValue()))
                .findFirst();
    }


    /**
     * Formats a MeasureModel object into a string representation.
     *
     * @param measure the MeasureModel object to format
     * @return the string representation of the MeasureModel object
     */
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

    /**
     * Determines if the lower bound and upper bound of a MeasureModel object are both equal and included,
     * based on the provided MeasureModel object.
     *
     * @param measure the MeasureModel object to check the bounds for
     * @return true if the lower bound and upper bound are both equal and included, false otherwise
     */
    private boolean boundsAreEqualAndIncluded(MeasureModel measure) {
        return Boolean.TRUE.equals(measure.getIncludeLowerBound())
                && Boolean.TRUE.equals(measure.getIncludeUpperBound())
                && measure.getLowerBound() != null
                && measure.getUpperBound() != null
                && measure.getUpperBound().equals(measure.getLowerBound());
    }

    /**
     * Appends the unit of the given MeasureModel to the StringBuilder if it is present.
     *
     * @param measure The MeasureModel object.
     * @param sb      The StringBuilder object to append the unit to.
     */
    private void appendUnitIfPresent(MeasureModel measure, StringBuilder sb) {
        if (measure.getUnit() != null) {
            sb.append(" ").append(measure.getUnit().getConceptName());
        }
    }

    /**
     * Appends the lower bound to the StringBuilder if it is included.
     *
     * @param measure The MeasureModel object containing the lower bound information.
     * @param sb      The StringBuilder object to which the lower bound will be appended.
     */
    private void appendLowerBoundIfIncluded(MeasureModel measure, StringBuilder sb) {
        if (Boolean.TRUE.equals(measure.getIncludeLowerBound())) {
            if (measure.getLowerBound() == Double.NEGATIVE_INFINITY) {
                sb.append(NEGATIVE_INFINITY);
            } else {
                sb.append(measure.getLowerBound());
            }
        }
    }

    /**
     * Appends a separator to the StringBuilder if both the lower bound and upper bound of the MeasureModel are included.
     *
     * @param measure The MeasureModel object representing the measure.
     * @param sb      The StringBuilder object to which the separator will be appended.
     */
    private void appendSeparatorIfBoundsIncluded(MeasureModel measure, StringBuilder sb) {
        if (measure.getIncludeLowerBound() && measure.getIncludeUpperBound()) {
            sb.append(SEPARATOR);
        }
    }

    /**
     * Appends the upper bound of a MeasureModel object to a StringBuilder if it is included.
     *
     * @param measure the MeasureModel object containing the upper bound
     * @param sb      the StringBuilder to append the upper bound to
     */
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
