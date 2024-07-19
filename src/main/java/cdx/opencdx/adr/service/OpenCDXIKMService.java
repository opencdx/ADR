package cdx.opencdx.adr.service;

import cdx.opencdx.adr.model.LogicalExpressionModel;
import cdx.opencdx.adr.model.TinkarConceptModel;

public interface OpenCDXIKMService {
    TinkarConceptModel getInkarConceptModel(LogicalExpressionModel logicalExpression);
}
