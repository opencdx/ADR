package cdx.opencdx.adr.dto;

/**
 * The NumericalOperation enum represents different numerical operations that can be performed on numerical values.
 *
 * The operations include multiply, divide, add, subtract, modulo, and power.
 *
 * This enum is used as a symbol to perform the corresponding operation between two numerical values.
 *
 * Example usage:
 * <pre>
 *     NumericalOperation operation = NumericalOperation.ADD;
 *     double result = operand1 operation operand2;
 * </pre>
 *
 * @see NumericalOperation
 */
public enum NumericalOperation {
    /**
     * This is an enumeration constant representing the "multiply" operation.
     * It is used as a symbol to perform multiplication between two numerical values.
     *
     * Example usage:
     * double result = a * b;
     *
     * @see NumericalOperation
     */
    MULTIPLY,
    /**
     * Represents the divide operation.
     */
    DIVIDE,
    /**
     * Represents the addition operation.
     */
    ADD,
    /**
     * Represents the subtraction operation.
     *
     * Usage:
     * <pre>
     *     int result = operand1 - operand2;
     * </pre>
     */
    SUBTRACT,
    /**
     * Represents a numerical operation to calculate the remainder of a division operation.
     *
     * @see NumericalOperation
     */
    MODULO,
    /**
     * Represents the power operation in a numerical calculation.
     */
    POWER
}
