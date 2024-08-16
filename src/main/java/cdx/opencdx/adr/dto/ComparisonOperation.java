package cdx.opencdx.adr.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * The Operation enum represents comparison operations for querying.
 * It provides constants for various comparison operations such as greater than, less than, equal to, etc.
 */
public enum ComparisonOperation {
    /**
     * Represents the "greater than" operation.
     */
    GREATER_THAN,
    /**
     * Represents a comparison operation Less Than.
     *
     * This operation is used to determine if one value is less than another value.
     * It is typically used in conditional statements and comparison operations.
     */
    LESS_THAN,
    /**
     * Represents the operation Greater Than Or Equal.
     *
     * This operation checks if one value is greater than or equal to another value.
     *
     * Example usage:
     * int a = 5;
     * int b = 3;
     *
     * if (a >= b) {
     *     System.out.println("a is greater than or equal to b");
     * } else {
     *     System.out.println("a is less than b");
     * }
     */
    GREATER_THAN_OR_EQUAL,
    /**
     * Represents the "less than or equal" operation.
     */
    LESS_THAN_OR_EQUAL,
    /**
     * Represents the "equal to" operation.
     */
    EQUAL,
    /**
     * Represents the "not equal" operation.
     *
     * This operation is used to compare two values and determine if they are not equal.
     * It returns true if the values are not equal, and false otherwise.
     *
     * Example Usage:
     *
     * int a = 5;
     * int b = 10;
     *
     * boolean result = (a != b); // result is true
     *
     */
    NOT_EQUAL
}
