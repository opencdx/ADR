package cdx.opencdx.adr.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * The NumericalOperation enum represents different numerical operations that can be performed on numerical values.
 * <p>
 * The operations include multiply, divide, add, subtract, modulo, and power.
 * <p>
 * This enum is used as a symbol to perform the corresponding operation between two numerical values.
 * <p>
 * Example usage:
 * <pre>
 *     NumericalOperation operation = NumericalOperation.ADD;
 *     double result = operand1 operation operand2;
 * </pre>
 *
 * @see NumericalOperation
 */
@Schema(description = "Numerical operations that can be performed on numerical values.")
public enum NumericalOperation {
    /**
     * This is an enumeration constant representing the "multiply" operation.
     * It is used as a symbol to perform multiplication between two numerical values.
     * <p>
     * Example usage:
     * double result = a * b;
     *
     * @see NumericalOperation
     */
    @Schema(description = "Represents the multiply operation.")
    MULTIPLY,
    /**
     * Represents the divide operation.
     */
    @Schema(description = "Represents the divide operation.")
    DIVIDE,
    /**
     * Represents the addition operation.
     */
    @Schema(description = "Represents the addition operation.")
    ADD,
    /**
     * Represents the subtraction operation.
     * <p>
     * Usage:
     * <pre>
     *     int result = operand1 - operand2;
     * </pre>
     */
    @Schema(description = "Represents the subtraction operation.")
    SUBTRACT,
    /**
     * Represents a numerical operation to calculate the remainder of a division operation.
     *
     * @see NumericalOperation
     */
    @Schema(description = "Represents the modulo operation.")
    MODULO,
    /**
     * Represents the power operation in a numerical calculation.
     */
    @Schema(description = "Represents the power operation.")
    POWER
}
