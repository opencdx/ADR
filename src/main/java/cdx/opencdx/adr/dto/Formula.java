package cdx.opencdx.adr.dto;

import lombok.Data;

import java.util.UUID;

/**
 * This class represents a mathematical formula for performing calculations.
 * It contains various properties such as leftOperand, rightOperand, leftOperandValue, rightOperandValue, leftOperandFormula,
 * rightOperandFormula, leftOperandUnit, rightOperandUnit, and operation that define the components of the formula.
 *
 * The class provides getters and setters for accessing and modifying the formula's properties.
 *
 * Example usage:
 * <pre>
 *     // Create a formula object
 *     Formula formula = new Formula();
 *
 *     // Set the left operand value
 *     formula.setLeftOperandValue(5.0);
 *
 *     // Set the right operand value
 *     formula.setRightOperandValue(2.5);
 *
 *     // Set the operation to perform
 *     formula.setOperation(NumericalOperation.ADD);
 *
 *     // Perform the calculation by calling the evaluate method
 *     Double result = formula.evaluate();
 * </pre>
 *
 * @see NumericalOperation
 */
@Data
public class Formula {

    /**
     * The private variable 'name' represents the name of an object or entity.
     *
     * This variable is a string value that identifies the name of the object or entity it is associated with.
     *
     * It is used to store and retrieve the name of the object or entity in various operations and functions within the containing class.
     *
     * Example usage:
     *
     * <pre>
     *     Formula formula = new Formula();
     *     formula.setName("Example Formula");
     *     String name = formula.getName();
     * </pre>
     *
     * @see Formula
     */
    private String name;

    /**
     * The leftOperand variable represents the left operand of a formula.
     * It is of type UUID, which is a 128-bit value used to uniquely identify objects.
     *
     * This variable is used to store the UUID value of the left operand in a formula.
     * The left operand can be a UUID representing a numerical value, a UUID representing a formula,
     * or a UUID representing a unit of measurement.
     *
     * Example usage:
     * <pre>
     *     UUID leftOperand = UUID.randomUUID();
     *     Formula formula = new Formula();
     *     formula.setLeftOperand(leftOperand);
     *     UUID storedValue = formula.getLeftOperand();
     * </pre>
     *
     * @see Formula
     */
    private UUID leftOperand;
    /**
     * The rightOperand variable represents the right operand value of a mathematical formula.
     *
     * This variable is of type UUID, which is a unique identifier used to identify the right operand in the formula.
     *
     * Example usage:
     * <pre>
     *     Formula formula = new Formula();
     *     UUID rightOperand = formula.getRightOperand();
     * </pre>
     *
     * @see Formula
     */
    private UUID rightOperand;

    /**
     * The leftOperandValue variable holds the value of the left operand in a mathematical formula.
     *
     * It is a private variable of type Double that represents a numerical value.
     * The left operand is one of the components used in performing a mathematical operation, such as addition, subtraction, multiplication, or division.
     * The value stored in this variable can be used in conjunction with the right operand and operation type to calculate the result of the formula.
     *
     * Example usage:
     * <pre>
     *     Formula formula = new Formula();
     *     formula.setLeftOperandValue(5.0);
     *     Double result = formula.getLeftOperandValue() + formula.getRightOperandValue();
     * </pre>
     *
     * @see Formula
     * @see Formula#setLeftOperandValue(Double)
     * @see Formula#getLeftOperandValue()
     */
    private Double leftOperandValue;
    /**
     * The rightOperandValue is a private variable of type Double.
     * It represents the value of the right operand in a formula.
     * The right operand is used in numerical calculations along with the left operand and the operation.
     *
     * Example usage:
     * <pre>
     *     Formula formula = new Formula();
     *     formula.setRightOperandValue(2.5);
     * </pre>
     *
     * @see Formula
     */
    private Double rightOperandValue;

    /**
     * Represents a numerical formula used for calculations.
     *
     * The `leftOperandFormula` is an instance of the `Formula` class, representing the formula for the left operand of a calculation. It can be used recursively to represent complex
     *  formulas.
     *
     * The `Formula` class contains various properties such as `leftOperand`, `rightOperand`, `leftOperandValue`, `rightOperandValue`, `leftOperandFormula`, `rightOperandFormula`,
     *  `leftOperandUnit`, and `rightOperandUnit`, which define the components of the formula.
     *
     * The `NumericalOperation` enum is used to specify the operation to be performed between the left operand and the right operand. It includes operations like multiplication, division
     * , addition, subtraction, modulo, and power.
     *
     * Example usage:
     * <pre>
     *     Formula formula = new Formula();
     *     formula.setLeftOperandFormula(leftOperandFormula);
     *     // Perform calculations using the formula
     * </pre>
     *
     * @see Formula
     * @see NumericalOperation
     */
    private Formula leftOperandFormula;
    /**
     * Represents the right operand formula in a numerical calculation.
     *
     * A formula consists of two operands and an operation. The rightOperandFormula represents
     * the formula to be performed on the right operand. It can be used to recursively evaluate
     * formulas within formulas.
     *
     * Example usage:
     * <pre>
     *     Formula formula1 = new Formula();
     *     Formula formula2 = new Formula();
     *     formula1.setRightOperandFormula(formula2);
     * </pre>
     *
     * @see Formula
     */
    private Formula rightOperandFormula;

    /**
     * The leftOperandUnit variable represents the unit of measurement for the left operand in a formula.
     *
     * It is stored as a UUID (Universally Unique Identifier) to uniquely identify the unit of measurement.
     *
     * Example usage:
     * <pre>
     *     UUID leftOperandUnit = formula.getLeftOperandUnit();
     * </pre>
     *
     */
    private UUID leftOperandUnit;
    /**
     * The rightOperandUnit represents the unit of measurement for the right operand in a mathematical formula.
     *
     * This variable is of type UUID and is used to store the unique identifier of the unit of measurement.
     *
     * Example usage:
     * <pre>
     *     UUID rightOperandUnit = UUID.fromString("490dd828-7bfe-4b4e-be9e-4f4e45777aba");
     * </pre>
     *
     * @see Formula
     */
    private UUID rightOperandUnit;

    /**
     * The {@code operation} variable represents a numerical operation that can be performed on numerical values.
     *
     * This variable is of type {@link NumericalOperation}, which is an enum containing different operations
     * including multiply, divide, add, subtract, modulo, and power.
     *
     * Example usage:
     * <pre>
     *     NumericalOperation operation = NumericalOperation.ADD;
     *     double result = operand1 {operation} operand2;
     * </pre>
     *
     * @see NumericalOperation
     */
    private NumericalOperation operation;
}
