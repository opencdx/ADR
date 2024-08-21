package cdx.opencdx.adr.service;

import cdx.opencdx.adr.dto.ComparisonOperation;
import cdx.opencdx.adr.model.MeasureModel;
import cdx.opencdx.adr.model.TinkarConceptModel;

/**
 * The MeasureOperationService interface represents a service that measures an operation using a MeasureModel.
 */
public interface MeasureOperationService {
    /**
     * Measures an operation using a MeasureModel.
     *
     * @param operation     The operation to be measured. It should be one of the comparison operations defined in the Operation enum.
     * @param operationValue    The value of the operation to be measured.
     * @param operationUnit     The unit of measurement for the operation.
     * @param measure       The MeasureModel used to calculate and store the measurement result.
     *
     * @return true if the operation was successfully measured and the measurement result was stored in the MeasureModel, false otherwise.
     */
    boolean measureOperation(ComparisonOperation operation, Double operationValue, TinkarConceptModel operationUnit, MeasureModel measure);
}
