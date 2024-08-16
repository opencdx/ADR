package cdx.opencdx.adr.service.impl;

import cdx.opencdx.adr.dto.ComparisonOperation;
import cdx.opencdx.adr.model.MeasureModel;
import cdx.opencdx.adr.service.ConversionService;
import cdx.opencdx.adr.service.MeasureOperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * The MeasureOperationServiceImpl class implements the MeasureOperationService interface.
 * It provides methods to measure an operation using a MeasureModel.
 */
@Slf4j
@Service
public class MeasureOperationServiceImpl implements MeasureOperationService {


    /**
     * This variable represents the ConversionService used for converting data types.
     */
    private final ConversionService conversionService;

    /**
     * Constructs a new MeasureOperationServiceImpl instance.
     *
     * @param conversionService the ConversionService used for performing unit conversions
     */
    public MeasureOperationServiceImpl(ConversionService conversionService) {
        this.conversionService = conversionService;
    }

    /**
     * Measures the given operation using the MeasureModel.
     *
     * @param operation The Operation to be measured.
     * @param measure   The MeasureModel used to measure the operation.
     * @return True if the operation measurement is successful, false otherwise.
     */
    @Override
    public boolean measureOperation(ComparisonOperation operation, Double operationValue, UUID operationUnit, MeasureModel measure) {
        return switch (operation) {
            case GREATER_THAN -> greaterThan(operationValue, getMeasureValue(operationUnit, measure));
            case LESS_THAN -> lessThan(operationValue, getMeasureValue(operationUnit, measure));
            case GREATER_THAN_OR_EQUAL -> greatherThanOrEqual(operationValue, getMeasureValue(operationUnit, measure));
            case LESS_THAN_OR_EQUAL -> lessThanOrEqual(operationValue, getMeasureValue(operationUnit, measure));
            case EQUAL -> equalValue(operationValue, getMeasureValue(operationUnit, measure));
            case NOT_EQUAL -> notEqualValue(operationValue, getMeasureValue(operationUnit, measure));
        };
    }

    /**
     * Determines if the given operation value is less than or equal to the upper value of the MeasureValue.
     *
     * @param operationValue The operation value to be compared.
     * @param measureValue   The MeasureValue object containing the upper value.
     * @return True if the operation value is less than or equal to the upper value, otherwise false.
     */
    boolean lessThanOrEqual(Double operationValue, MeasureValue measureValue) {
        return  measureValue.lower <= operationValue;
    }

    /**
     * Checks if the operation value is greater than or equal to the upper bound of the measure value.
     *
     * @param operationValue The value of the operation to be compared.
     * @param measureValue   The measure value specifying the upper bound.
     * @return true if the operation value is greater than or equal to the upper bound, false otherwise.
     */
    boolean greatherThanOrEqual(Double operationValue, MeasureValue measureValue) {
        return measureValue.upper >= operationValue;
    }

    /**
     * Checks if the given operation value is within the range defined by the measure value.
     *
     * @param operationValue The value of the operation to be checked.
     * @param measureValue   The range of values defined by the measure.
     * @return true if the operation value is within the range, false otherwise.
     */
    boolean equalValue(Double operationValue, MeasureValue measureValue) {
        return  measureValue.lower >= operationValue &&  measureValue.upper <= operationValue;
    }

    /**
     * Determines if the operation value is not equal to the lower or upper bound of the measure value, or if it is outside the range defined by the lower and upper bounds.
     *
     * @param operationValue The value of the operation to be compared.
     * @param measureValue   The measure value defining the range.
     * @return True if the operation value is not equal to the lower or upper bound and is outside the range, false otherwise.
     */
    boolean notEqualValue(Double operationValue, MeasureValue measureValue) {
        return !equalValue(operationValue, measureValue);
    }

    /**
     * Checks if the operation value is greater than the upper limit of a MeasureValue.
     *
     * @param operationValue The value of the operation.
     * @param measureValue   The MeasureValue containing the upper limit.
     * @return True if the operation value is greater than the upper limit, false otherwise.
     */
    boolean greaterThan(Double operationValue, MeasureValue measureValue) {
        return measureValue.upper > operationValue;
    }

    /**
     * Determines if the operation value is less than the lower limit of the measure value.
     *
     * @param operationValue The value of the operation to be compared.
     * @param measureValue   The lower and upper limits of the measure value.
     * @return true if the operation value is less than the lower limit, false otherwise.
     */
    boolean lessThan(Double operationValue, MeasureValue measureValue) {
        return measureValue.lower < operationValue;
    }

    /**
     * Retrieves a MeasureValue object based on the given MeasureModel.
     *
     * @param measure The MeasureModel used to retrieve the MeasureValue.
     * @return The MeasureValue object retrieved based on the MeasureModel.
     * @throws IllegalArgumentException if the measure bounds are invalid.
     */
    MeasureValue getMeasureValue(UUID operationUnit, MeasureModel measure) {

        MeasureModel workingMeasure = this.conversionService.convert(operationUnit, measure);
        if (workingMeasure.getIncludeLowerBound() && workingMeasure.getIncludeUpperBound()) {
            return new MeasureValue(workingMeasure.getLowerBound(), workingMeasure.getUpperBound());
        } else if (workingMeasure.getIncludeLowerBound()) {
            return new MeasureValue(workingMeasure.getLowerBound(), workingMeasure.getLowerBound());
        } else if (workingMeasure.getIncludeUpperBound()) {
            return new MeasureValue(workingMeasure.getUpperBound(), workingMeasure.getUpperBound());
        } else {
            throw new IllegalArgumentException("Invalid measure bounds");
        }
    }

    /**
     * Represents a measure value range with lower and upper bounds.
     */
    public record MeasureValue(Double lower, Double upper) {
    }
}
