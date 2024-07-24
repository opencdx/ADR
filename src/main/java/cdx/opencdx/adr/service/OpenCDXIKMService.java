package cdx.opencdx.adr.service;

import cdx.opencdx.adr.model.LogicalExpressionModel;
import cdx.opencdx.adr.model.TinkarConceptModel;

/**
 * The OpenCDXIKMService interface provides methods for retrieving TinkarConceptModel
 * associated with LogicalExpressionModel.
 */
public interface OpenCDXIKMService {
    /**
     * Retrieves the TinkarConceptModel associated with the given LogicalExpressionModel.
     *
     * @param logicalExpression The LogicalExpressionModel for which to retrieve the TinkarConceptModel.
     * @return The TinkarConceptModel associated with the given LogicalExpressionModel.
     */
    TinkarConceptModel getInkarConceptModel(LogicalExpressionModel logicalExpression);


}
