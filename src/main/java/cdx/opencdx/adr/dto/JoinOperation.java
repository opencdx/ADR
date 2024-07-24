package cdx.opencdx.adr.dto;

/**
 * The JoinOperation enum represents logical join operations for querying.
 * It provides two constants: OR and AND.
 */
public enum JoinOperation {
    /**
     * Represents a logical OR operator.
     */
    OR,
    /**
     * Represents a logical AND operation.
     *
     * <p>
     * The AND operation takes two boolean values as operands and
     * returns true if both operands are true. Otherwise, it returns false.
     * </p>
     *
     * <p>
     * This class is an enum constant defined in the {@link JoinOperation} enum.
     * It is used to perform logical AND operation in conjunction with other values
     * defined in the enum.
     * </p>
     */
    AND
}
