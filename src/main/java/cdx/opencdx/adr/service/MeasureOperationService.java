package cdx.opencdx.adr.service;

import cdx.opencdx.adr.dto.Operation;
import cdx.opencdx.adr.model.MeasureModel;

/**
 * The MeasureOperationService interface represents a service that measures an operation using a MeasureModel.
 */
public interface MeasureOperationService {
    /**
     * Measures an operation using a MeasureModel.
     *
     * @param operation     The operation to be measured.
     * @param operationValue    The value of the operation to be measured.
     * @param measure The MeasureModel used for measurement.
     * @return true if the operation is successfully measured, false otherwise.
     */
    boolean measureOperation(Operation operation, Double operationValue,  MeasureModel measure);
}
