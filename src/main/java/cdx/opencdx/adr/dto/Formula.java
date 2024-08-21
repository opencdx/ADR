package cdx.opencdx.adr.dto;

import cdx.opencdx.adr.model.TinkarConceptModel;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

/**
 * The Formula class represents a mathematical formula for performing calculations.
 * Only one of leftOperand, leftOperandValue, or leftOperandFormula should be set.
 * Only one of rightOperand, rightOperandValue, or rightOperandFormula should be set.
 * Operation is required.
 */
@Data
@Schema(description = "This class represents a mathematical formula for performing calculations. Only one of leftOperand, leftOperandValue, or leftOperandFormula should be set. Only one of rightOperand, rightOperandValue, or rightOperandFormula should be set. Operation is required.")
public class Formula {

    /**
     * The name of the formula, this will be used as the CSC Header.
     */
    @Schema(description = "The name of the formula, this will be used as the CSC Header.")
    private String name;

    /**
     * The left operand of the formula. Only one of leftOperand, leftOperandValue, or leftOperandFormula should be set.
     *
     * @param <T> the type of the left operand
     */
    @Schema(description = "The left operand of the formula. Only one of leftOperand, leftOperandValue, or leftOperandFormula should be set.")
    private TinkarConceptModel leftOperand;
    /**
     * The right operand of the formula. Only one of rightOperand, rightOperandValue, or rightOperandFormula should be set.
     */
    @Schema(description = "The right operand of the formula. Only one of rightOperand, rightOperandValue, or rightOperandFormula should be set.")
    private TinkarConceptModel rightOperand;

    /**
     * The value of the left operand in the formula. Only one of leftOperand, leftOperandValue, or leftOperandFormula should be set.
     */
    @Schema(description = "The value of the left operand in the formula. Only one of leftOperand, leftOperandValue, or leftOperandFormula should be set.")
    private Double leftOperandValue;
    /**
     * The value of the right operand in the formula.
     * Only one of rightOperand, rightOperandValue, or rightOperandFormula should be set.
     *
     * @since 1.0.0
     */
    @Schema(description = "The value of the right operand in the formula. Only one of rightOperand, rightOperandValue, or rightOperandFormula should be set.")
    private Double rightOperandValue;

    /**
     * The leftOperandFormula variable represents the formula for the left operand in the formula.
     * Only one of leftOperand, leftOperandValue, or leftOperandFormula should be set.
     * It is an instance of the Formula class.
     *
     * The Formula class represents a mathematical formula for performing calculations.
     * It contains various properties such as name, leftOperand, rightOperand, leftOperandValue, etc.
     * Only one of leftOperand, leftOperandValue, or leftOperandFormula should be set.
     * Only one of rightOperand, rightOperandValue, or rightOperandFormula should be set.
     * Operation is required.
     *
     * The TinkarConceptModel class represents a Tinkar concept stored in the database.
     * It contains various properties of a concept including conceptId, conceptName, conceptDescription, and anfStatements.
     * TinkarConceptModel is used to store and retrieve Tinkar concepts in the database.
     *
     * The NumericalOperation enum represents different numerical operations that can be performed on numerical values.
     * The operations include multiply, divide, add, subtract, modulo, and power.
     * NumericalOperation is used as a symbol to perform the corresponding operation between two numerical values.
     */
    @Schema(description = "The formula for the left operand in the formula. Only one of leftOperand, leftOperandValue, or leftOperandFormula should be set.")
    private Formula leftOperandFormula;
    /**
     * The formula for the right operand in the formula. Only one of rightOperand, rightOperandValue,
     * or rightOperandFormula should be set.
     */
    @Schema(description = "The formula for the right operand in the formula. Only one of rightOperand, rightOperandValue, or rightOperandFormula should be set.")
    private Formula rightOperandFormula;

    /**
     * The leftOperandUnit variable represents the unit of measurement for the left operand in the formula.
     * It is of type TinkarConceptModel.
     *
     * Example usage:
     *
     * Formula formula = new Formula();
     * TinkarConceptModel unit = new TinkarConceptModel(UUID.randomUUID(), "Meter", "Unit of length measurement");
     * formula.setLeftOperandUnit(unit);
     */
    @Schema(description = "The unit of measurement for the left operand in the formula.")
    private TinkarConceptModel leftOperandUnit;
    /**
     * The unit of measurement for the right operand in the formula.
     */
    @Schema(description = "The unit of measurement for the right operand in the formula.")
    private TinkarConceptModel rightOperandUnit;

    /**
     * The operation to be performed in the formula. Required.
     * It represents different numerical operations that can be performed on numerical values.
     */
    @Schema(description = "The operation to be performed in the formula. Required.")
    private NumericalOperation operation;
}
