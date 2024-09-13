package cdx.opencdx.adr.service.impl;

import cdx.opencdx.adr.dto.ComparisonOperation;
import cdx.opencdx.adr.service.TextOperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * The TextOperationServiceImpl class implements the TextOperationService interface.
 * It provides a method for performing text operations based on a specified operation.
 */
@Slf4j
@Service
public class TextOperationServiceImpl implements TextOperationService {
    /**
     * Performs a text operation on the given text based on the specified operation.
     *
     * @param operation the operation to perform on the text
     * @param text      the text to perform the operation on
     * @return true if the operation is successful, false otherwise
     */
    @Override
    public boolean textOperation(ComparisonOperation operation, String operationValue, String text) {
        return switch (operation) {
            case GREATER_THAN -> text.compareTo(operationValue) > 0;
            case LESS_THAN -> text.compareTo(operationValue) < 0;
            case GREATER_THAN_OR_EQUAL -> text.compareTo(operationValue) >= 0;
            case LESS_THAN_OR_EQUAL -> text.compareTo(operationValue) <= 0;
            case EQUAL -> text.equals(operationValue);
            case NOT_EQUAL -> !text.equals(operationValue);
        };
    }
}
