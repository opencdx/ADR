package cdx.opencdx.adr.service.impl;

import cdx.opencdx.adr.dto.UnitOutput;
import cdx.opencdx.adr.model.*;
import cdx.opencdx.adr.repository.CalculatedConceptRepository;
import cdx.opencdx.adr.service.ConversionService;
import cdx.opencdx.adr.service.CsvService;
import cdx.opencdx.adr.service.OpenCDXIKMService;
import cdx.opencdx.adr.utils.ANFHelper;
import cdx.opencdx.adr.utils.CsvBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
     * Represents the string representation of negative infinity.
     * The value of this constant is "INF".
     */
    private static final String NEGATIVE_INFINITY = "INF";
    /**
     * Represents the symbol used to denote positive infinity.
     */
    private static final String POSITIVE_INFINITY = "INF";
    /**
     * Represents the separator used to separate different parts of a string.
     * This separator is a constant and is not meant to be modified.
     * <p>
     * The separator is defined as " - ".
     */
    private static final String SEPARATOR = " - ";

    /**
     * Represents an instance of ANFRepo.
     */
    private final ANFHelper anfRepo;

    private final UUID DATETIME_UUID = UUID.fromString(OpenCDXIKMService.UNIT_CALENDAR_TIME);
    private final DecimalFormat decimalFormat = new DecimalFormat("#.00");
    SimpleDateFormat formatter = new SimpleDateFormat("EE MMMM dd yyyy");
    /**
     * Represents a conversion service.
     */
    private final ConversionService conversionService;

    /**
     * The calculatedConceptRepository variable represents an instance of the CalculatedConceptRepository class.
     * It is marked as final to indicate that it cannot be reassigned once initialized.
     * CalculatedConceptRepository is responsible for interacting with the database to perform operations related to calculated concepts.
     * This variable provides access to the methods and functionality provided by the CalculatedConceptRepository class.
     */
    private final CalculatedConceptRepository calculatedConceptRepository;

    /**
     * Constructs a new CsvServiceImpl with the given repositories and services.
     *
     * @param anfRepo                     the ANFHelper repository used for retrieving ANF data
     * @param conversionService           the ConversionService used for performing data conversions
     * @param calculatedConceptRepository the CalculatedConceptRepository used for storing calculated concepts
     */
    public CsvServiceImpl(ANFHelper anfRepo, ConversionService conversionService, CalculatedConceptRepository calculatedConceptRepository) {
        this.anfRepo = anfRepo;
        this.conversionService = conversionService;
        this.calculatedConceptRepository = calculatedConceptRepository;
    }

    /**
     * Builds a CsvBuilder object based on the given parameters.
     *
     * @param list       The list of UUIDs.
     * @param results    The list of AnfStatementModel results.
     * @param unitOutput The UnitOutput object.
     * @return The CsvBuilder object with the constructed CSV data.
     */
    @Override
    public CsvBuilder buildCsvDto(List<UUID> list, List<AnfStatementModel> results, UnitOutput unitOutput) {
        CsvBuilder csvDto = new CsvBuilder();
        AtomicInteger row = new AtomicInteger();

        log.info("Processing {} participants", list.size());

        list.forEach(uuid -> {
            int currentRow = row.getAndIncrement();
            csvDto.setCell(currentRow, "Participant ID", uuid.toString());

            Optional<Date> recentDate = findRecentStatementResult(results, uuid);
            recentDate.ifPresent(date -> csvDto.setCell(currentRow, "Reported", formatter.format(date)));

            ArrayList<AnfStatementModel> anfs = this.anfRepo.getParticipantRepository().findAllByPartId(uuid)
                    .stream()
                    .map(ParticipantModel::getDimanfstatements)
                    .collect(ArrayList::new, List::addAll, List::addAll);

            anfs.forEach(anf -> {
                if (anf.getTopic() != null) {
                    var conceptName = anf.getTopic().getConceptName();
                    if (anf.getPerformanceCircumstance() != null && anf.getPerformanceCircumstance().getResult() != null) {
                        processPerformanceCircumstance(csvDto, currentRow, conceptName, anf.getPerformanceCircumstance(), unitOutput);
                    } else if (anf.getRequestCircumstance() != null) {
                        processRequestCircumstance(csvDto, currentRow, conceptName, anf.getRequestCircumstance(), unitOutput);
                    } else if (anf.getNarrativeCircumstance() != null && anf.getNarrativeCircumstance().getText() != null) {
                        processNarrativeCircumstance(csvDto, currentRow, conceptName, anf.getNarrativeCircumstance(), unitOutput);
                    }
                }
            });

            this.calculatedConceptRepository.findAllByParticipantIdAndThreadName(uuid, Thread.currentThread().getName())
                    .forEach(calculatedConcept -> csvDto.setCell(currentRow, calculatedConcept.getConceptName() + " Calculation", calculatedConcept.getValue().toString()));
        });
        return csvDto;
    }

    /**
     * This method processes the performance circumstance and updates the CsvBuilder object with the result.
     *
     * @param csvDto                  The CsvBuilder object to be updated with the result
     * @param row                     The row index for which the result needs to be updated
     * @param conceptName             The name of the concept for which the result is being processed
     * @param performanceCircumstance The PerformanceCircumstanceModel object containing the performance circumstance details
     * @param unitOutput              The UnitOutput object containing the unit output details
     */
    private void processPerformanceCircumstance(CsvBuilder csvDto, int row, String conceptName, PerformanceCircumstanceModel performanceCircumstance, UnitOutput unitOutput) {
        if (performanceCircumstance.getTiming() != null) {
            csvDto.setCell(row, conceptName + " Reported", this.formatMeasure(performanceCircumstance.getTiming(),unitOutput));
        }
        csvDto.setCell(row, conceptName, formatMeasure(performanceCircumstance.getResult(), unitOutput));
    }

    /**
     * Process the request circumstance and update the CSV builder object.
     *
     * @param csvDto              The CSV builder object that holds the CSV data.
     * @param row                 The row number in the CSV where the data will be updated.
     * @param conceptName         The name of the concept being processed.
     * @param requestCircumstance The request circumstance model.
     * @param unitOutput          The unit output object that provides formatting information for the requested result.
     */
    private void processRequestCircumstance(CsvBuilder csvDto, int row, String conceptName, RequestCircumstanceModel requestCircumstance, UnitOutput unitOutput) {
        if (requestCircumstance.getTiming() != null) {
            csvDto.setCell(row, conceptName + " Reported", this.formatMeasure(requestCircumstance.getTiming(),unitOutput));
        }
        csvDto.setCell(row, conceptName, formatMeasure(requestCircumstance.getRequestedResult(), unitOutput));
    }

    /**
     * Process the narrative circumstance for a given row in the CSV.
     *
     * @param csvDto                The CsvBuilder object representing the CSV file.
     * @param row                   The row index in the CSV.
     * @param conceptName           The name of the concept associated with the narrative circumstance.
     * @param narrativeCircumstance The NarrativeCircumstanceModel object containing the narrative circumstance data.
     */
    private void processNarrativeCircumstance(CsvBuilder csvDto, int row, String conceptName, NarrativeCircumstanceModel narrativeCircumstance, UnitOutput unitOutput) {
        if (narrativeCircumstance.getTiming() != null) {
            csvDto.setCell(row, conceptName + " Reported", this.formatMeasure(narrativeCircumstance.getTiming(),unitOutput));
        }
        csvDto.setCell(row, conceptName, narrativeCircumstance.getText());
    }

    /**
     * Finds the most recent statement result by matching the UUID in the given list of AnfStatementModel.
     *
     * @param results the list of AnfStatementModel containing statement results
     * @param uuid    the UUID to match with the subject partId
     * @return an optional Date representing the most recent statement result time, or an empty Optional if no matching result is found
     */
    private Optional<Date> findRecentStatementResult(List<AnfStatementModel> results, UUID uuid) {
        log.info("Finding recent statement result for participant {}", uuid);
        return results.stream()
                .filter(item -> item.getSubjectOfRecord().getPartId().equals(uuid))
                .sorted(Comparator.comparing(item -> item.getTime().getLowerBound()))
                .map(item -> this.conversionService.convert(this.DATETIME_UUID,item.getTime()))
                .map(item -> new Date(item.getLowerBound().longValue()))
                .findFirst();
    }


    /**
     * Formats a measure based on the provided unit output.
     *
     * @param measure    the measure to be formatted
     * @param unitOutput the desired unit output
     * @return the formatted measure as a string
     */
    private String formatMeasure(MeasureModel measure, UnitOutput unitOutput) {

        MeasureModel convertedMeasure = conversionService.output(unitOutput, measure);

        if (convertedMeasure == null) {
            return "\"\"";
        }
        StringBuilder sb = new StringBuilder();
        String semantic = null;
        if (convertedMeasure.getSemantic() != null) {
            semantic = convertedMeasure.getSemantic().getConceptId().toString();
        }

        switch(semantic) {
            case OpenCDXIKMService.UNIT_BOOLEAN -> {
                if (boundsAreEqualAndIncluded(convertedMeasure)) {
                    return convertedMeasure.getLowerBound() == 1 ? "True" : "False";
                } else {
                    appendLowerBoundIfIncluded(convertedMeasure, sb);
                    appendSeparatorIfBoundsIncluded(convertedMeasure, sb);
                    appendUpperBoundIfIncluded(convertedMeasure, sb);
                    appendUnitIfPresent(convertedMeasure, sb);
                }
            }
            case OpenCDXIKMService.UNIT_POSITIVE -> {
                return "Positive";
            }
            case OpenCDXIKMService.UNIT_PRESUMPTIVE_POSITIVE -> {
                return "Presumptive Positive";
            }
            case OpenCDXIKMService.UNIT_NEGATIVE -> {
                return "Negative";
            }
            case OpenCDXIKMService.UNIT_NOT_DETECTED -> {
                return "Not Detected";
            }
            case OpenCDXIKMService.UNIT_YES -> {
                return "Yes";
            }
            case OpenCDXIKMService.UNIT_NO -> {
                return "No";
            }
            case OpenCDXIKMService.UNIT_CALENDAR_TIME, OpenCDXIKMService.UNIT_DATE_TIME, OpenCDXIKMService.UNIT_DATE -> {
                MeasureModel model = this.conversionService.convert(this.DATETIME_UUID, convertedMeasure);
                return formatter.format(new Date(model.getLowerBound().longValue()));
            }

            default -> {
                if (boundsAreEqualAndIncluded(convertedMeasure)) {
                    sb.append(decimalFormat.format(convertedMeasure.getLowerBound()));
                    appendUnitIfPresent(convertedMeasure, sb);
                } else {
                    appendLowerBoundIfIncluded(convertedMeasure, sb);
                    appendSeparatorIfBoundsIncluded(convertedMeasure, sb);
                    appendUpperBoundIfIncluded(convertedMeasure, sb);
                    appendUnitIfPresent(convertedMeasure, sb);
                }
            }
        }
        return sb.toString();
    }



    /**
     * Checks if the given MeasureModel object has equal and inclusive bounds.
     *
     * @param measure the MeasureModel object to check
     * @return true if the bounds are equal and inclusive, false otherwise
     */
    private boolean boundsAreEqualAndIncluded(MeasureModel measure) {
        return Boolean.TRUE.equals(measure.getIncludeLowerBound())
                && Boolean.TRUE.equals(measure.getIncludeUpperBound())
                && measure.getLowerBound() != null
                && measure.getUpperBound() != null
                && measure.getUpperBound().equals(measure.getLowerBound());
    }

    /**
     * Appends the unit of measure to the given StringBuilder if it is present in the MeasureModel object.
     *
     * @param measure The MeasureModel object containing the unit of measure.
     * @param sb      The StringBuilder object to append the unit of measure.
     */
    private void appendUnitIfPresent(MeasureModel measure, StringBuilder sb) {
        if (measure.getSemantic() != null) {
            sb.append(" ").append(measure.getSemantic().getConceptName());
        }
    }

    /**
     * Appends the lower bound of the MeasureModel to the given StringBuilder if it is included.
     *
     * @param measure The MeasureModel object.
     * @param sb      The StringBuilder object to append the lower bound to.
     */
    private void appendLowerBoundIfIncluded(MeasureModel measure, StringBuilder sb) {
        if (Boolean.TRUE.equals(measure.getIncludeLowerBound())) {
            if (measure.getLowerBound() == Double.NEGATIVE_INFINITY) {
                sb.append(NEGATIVE_INFINITY);
            } else {
                sb.append(this.decimalFormat.format(measure.getLowerBound()));
            }
        }
    }

    /**
     * Appends a separator to the given StringBuilder if both the lower bound and upper bound are included in the measure.
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
     * Appends the upper bound of a MeasureModel to a StringBuilder if it is included.
     *
     * @param measure The MeasureModel object containing the upper bound.
     * @param sb      The StringBuilder object to append the upper bound to.
     */
    private void appendUpperBoundIfIncluded(MeasureModel measure, StringBuilder sb) {
        if (Boolean.TRUE.equals(measure.getIncludeUpperBound())) {
            if (measure.getUpperBound() == Double.POSITIVE_INFINITY) {
                sb.append(POSITIVE_INFINITY);
            } else {
                sb.append(this.decimalFormat.format(measure.getUpperBound()));
            }
        }
    }
}
