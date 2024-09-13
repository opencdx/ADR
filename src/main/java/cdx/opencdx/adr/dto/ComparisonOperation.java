package cdx.opencdx.adr.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * The Operation enum represents comparison operations for querying.
 * It provides constants for various comparison operations such as greater than, less than, equal to, etc.
 */
@Schema(description = "Comparison operations for querying.")
public enum ComparisonOperation {
    /**
     * Represents the "greater than" operation.
     */
    @Schema(description = "Greater than operation.")
    GREATER_THAN,
    /**
     * Represents a comparison operation Less Than.
     * <p>
     * This operation is used to determine if one value is less than another value.
     * It is typically used in conditional statements and comparison operations.
     */
    @Schema(description = "Less than operation.")
    LESS_THAN,
    /**
     * Represents the operation Greater Than Or Equal.
     * <p>
     * This operation checks if one value is greater than or equal to another value.
     * <p>
     * Example usage:
     * int a = 5;
     * int b = 3;
     * <p>
     * if (a >= b) {
     * System.out.println("a is greater than or equal to b");
     * } else {
     * System.out.println("a is less than b");
     * }
     */
    @Schema(description = "Greater than or equal operation.")
    GREATER_THAN_OR_EQUAL,
    /**
     * Represents the "less than or equal" operation.
     */
    @Schema(description = "Less than or equal operation.")
    LESS_THAN_OR_EQUAL,
    /**
     * Represents the "equal to" operation.
     */
    @Schema(description = "Equal to operation.")
    EQUAL,
    /**
     * Represents the "not equal" operation.
     * <p>
     * This operation is used to compare two values and determine if they are not equal.
     * It returns true if the values are not equal, and false otherwise.
     * <p>
     * Example Usage:
     * <p>
     * int a = 5;
     * int b = 10;
     * <p>
     * boolean result = (a != b); // result is true
     */
    @Schema(description = "Not equal operation.")
    NOT_EQUAL
}
