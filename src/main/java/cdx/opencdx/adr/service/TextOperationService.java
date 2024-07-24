package cdx.opencdx.adr.service;

import cdx.opencdx.adr.dto.Operation;

/**
 * The TextOperationService interface represents a service for performing text operations on a given text.
 * It defines a method for performing a text operation based on a specified operation enum.
 */
public interface TextOperationService {
    /**
     * Performs a text operation on the given text based on the specified operation.
     *
     * @param operation the operation to perform on the text
     * @param text the text to perform the operation on
     * @return true if the operation is successful, false otherwise
     */
    boolean textOperation(Operation operation, String operationValue, String text);
}
