package cdx.opencdx.adr.service.impl;

import cdx.opencdx.adr.dto.Operation;
import cdx.opencdx.adr.model.MeasureModel;
import cdx.opencdx.adr.service.MeasureOperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * The MeasureOperationServiceImpl class implements the MeasureOperationService interface.
 * It provides methods to measure an operation using a MeasureModel.
 */
@Slf4j
@Service
public class MeasureOperationServiceImpl implements MeasureOperationService {

    /**
     * Measures the given operation using the MeasureModel.
     *
     * @param operation The Operation to be measured.
     * @param measure   The MeasureModel used to measure the operation.
     * @return True if the operation measurement is successful, false otherwise.
     */
    @Override
    public boolean measureOperation(Operation operation, Double operationValue, MeasureModel measure) {
        switch (operation) {
            case GREATER_THAN:
                return greatherThan(operationValue, getMeasureValue(measure));
            case LESS_THAN:
                return lessThan(operationValue, getMeasureValue(measure));
            case GREATER_THAN_OR_EQUAL:
                return greatherThanOrEqual(operationValue, getMeasureValue(measure));
            case LESS_THAN_OR_EQUAL:
                return lessThanOrEqual(operationValue, getMeasureValue(measure));
            case EQUAL:
                return equalValue(operationValue, getMeasureValue(measure));
            case NOT_EQUAL:
                return notEqualValue(operationValue, getMeasureValue(measure));
        }
        return false;
    }

    /**
     * Determines if the given operation value is less than or equal to the upper value of the MeasureValue.
     *
     * @param operationValue The operation value to be compared.
     * @param measureValue   The MeasureValue object containing the upper value.
     * @return True if the operation value is less than or equal to the upper value, otherwise false.
     */
    boolean lessThanOrEqual(Double operationValue, MeasureValue measureValue) {
        return operationValue <= measureValue.upper;
    }

    /**
     * Checks if the operation value is greater than or equal to the upper bound of the measure value.
     *
     * @param operationValue The value of the operation to be compared.
     * @param measureValue   The measure value specifying the upper bound.
     * @return true if the operation value is greater than or equal to the upper bound, false otherwise.
     */
    boolean greatherThanOrEqual(Double operationValue, MeasureValue measureValue) {
        return operationValue >= measureValue.upper;
    }

    /**
     * Checks if the given operation value is within the range defined by the measure value.
     *
     * @param operationValue The value of the operation to be checked.
     * @param measureValue   The range of values defined by the measure.
     * @return true if the operation value is within the range, false otherwise.
     */
    boolean equalValue(Double operationValue, MeasureValue measureValue) {
        return operationValue >= measureValue.lower && operationValue <= measureValue.upper;
    }

    /**
     * Determines if the operation value is not equal to the lower or upper bound of the measure value, or if it is outside the range defined by the lower and upper bounds.
     *
     * @param operationValue The value of the operation to be compared.
     * @param measureValue   The measure value defining the range.
     * @return True if the operation value is not equal to the lower or upper bound and is outside the range, false otherwise.
     */
    boolean notEqualValue(Double operationValue, MeasureValue measureValue) {
        return operationValue != measureValue.lower && operationValue != measureValue.upper
                && (operationValue < measureValue.lower || operationValue > measureValue.upper);
    }

    /**
     * Checks if the operation value is greater than the upper limit of a MeasureValue.
     *
     * @param operationValue The value of the operation.
     * @param measureValue   The MeasureValue containing the upper limit.
     * @return True if the operation value is greater than the upper limit, false otherwise.
     */
    boolean greatherThan(Double operationValue, MeasureValue measureValue) {
        return operationValue > measureValue.upper;
    }

    /**
     * Determines if the operation value is less than the lower limit of the measure value.
     *
     * @param operationValue The value of the operation to be compared.
     * @param measureValue   The lower and upper limits of the measure value.
     * @return true if the operation value is less than the lower limit, false otherwise.
     */
    boolean lessThan(Double operationValue, MeasureValue measureValue) {
        return operationValue < measureValue.lower;
    }

    /**
     * Retrieves a MeasureValue object based on the given MeasureModel.
     *
     * @param measure The MeasureModel used to retrieve the MeasureValue.
     * @return The MeasureValue object retrieved based on the MeasureModel.
     * @throws IllegalArgumentException if the measure bounds are invalid.
     */
    MeasureValue getMeasureValue(MeasureModel measure) {
        if (measure.getIncludeLowerBound() && measure.getIncludeUpperBound()) {
            return new MeasureValue(measure.getLowerBound(), measure.getUpperBound());
        } else if (Boolean.TRUE.equals(measure.getIncludeLowerBound())) {
            return new MeasureValue(measure.getLowerBound(), measure.getLowerBound());
        } else if (Boolean.TRUE.equals(measure.getIncludeUpperBound())) {
            return new MeasureValue(measure.getUpperBound(), measure.getUpperBound());
        } else {
            throw new IllegalArgumentException("Invalid measure bounds");
        }
    }

    public record MeasureValue(Double lower, Double upper) {
    }
}
