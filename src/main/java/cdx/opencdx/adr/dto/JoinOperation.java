package cdx.opencdx.adr.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * The JoinOperation enum represents logical join operations for querying.
 * It provides two constants: OR and AND.
 */
@Schema(description = "Logical join operations for querying.")
public enum JoinOperation {
    /**
     * Represents a logical OR operator.
     */
    @Schema(description = "Logical OR operation.")
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
    @Schema(description = "Logical AND operation.")
    AND
}
