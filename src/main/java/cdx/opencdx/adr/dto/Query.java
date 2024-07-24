package cdx.opencdx.adr.dto;

import cdx.opencdx.adr.model.AnfStatementModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Transient;
import lombok.Data;

import java.util.List;
import java.util.UUID;

/**
 * The Query class represents a query object used for searching concepts.
 * It holds the concept ID and join operation to be used in the query.
 */
@Data
public class Query {
    /**
     * The conceptId variable represents the unique identifier for a concept.
     * <p>
     * Concept identifiers are represented as UUIDs (Universally Unique Identifiers),
     * which provide a standardized format for identifying concepts in a system.
     * <p>
     * This variable is used in the Query class, which is a query object used for
     * searching concepts. The conceptId is one of the fields in the Query class,
     * along with the joinOperation field.
     * <p>
     * The joinOperation field is an enumeration type that specifies how multiple
     * query criteria are combined: either using the logical OR or logical AND
     * operation.
     * <p>
     * In addition to the conceptId and joinOperation fields, the Query class also
     * contains the anfStatements and conceptIds fields. These fields are annotated
     * with @JsonIgnore and @Transient, indicating that they should be ignored
     * during JSON serialization/deserialization and that they are not persistent
     * fields.
     * <p>
     * Use this conceptId variable to store and manipulate the unique identifier
     * for a concept in your system.
     */
    private UUID conceptId;

    /**
     * The Operation class represents the comparison operators used in a query.
     * It is an enumeration that provides constants for various comparison operations,
     * such as greater than, less than, equal, etc.
     *
     * <p>
     * This class is defined in the {@link Operation} enum and can be used in conjunction
     * with other values defined in the enum to perform comparison operations.
     * </p>
     *
     * <p>
     * The allowed comparison operations are:
     * <ul>
     * <li>{@code GREATER_THAN} - Represents the greater than operator</li>
     * <li>{@code LESS_THAN} - Represents the less than operator</li>
     * <li>{@code GREATER_THAN_OR_EQUAL} - Represents the greater than or equal to operator</li>
     * <li>{@code LESS_THAN_OR_EQUAL} - Represents the less than or equal to operator</li>
     * <li>{@code EQUAL} - Represents the equal operator</li>
     * <li>{@code NOT_EQUAL} - Represents the not equal operator</li>
     * </ul>
     * </p>
     *
     * <p>
     * Usage example:
     * <pre>
     * {@code
     * Operation operation = Operation.LESS_THAN;
     * if (operation == Operation.LESS_THAN) {
     *     // Perform some action
     * }
     * }
     * </pre>
     * </p>
     */
    private Operation operation;

    /**
     * The operationDouble variable represents a double value used in querying operations.
     * It is a private field of the Query class.
     *
     * <p>
     * This variable can be used in conjunction with the operation field to specify comparison operations
     * on the conceptId field when querying.
     * </p>
     *
     * Example usage:
     *
     * Query query = new Query();
     * query.setOperationDouble(10.5);
     * query.setOperation(Operation.GREATER_THAN);
     * // This will perform a query to find conceptIds greater than 10.5
     *
     * query.setOperationDouble(20.0);
     * query.setOperation(Operation.LESS_THAN_OR_EQUAL);
     * // This will perform a query to find conceptIds less than or equal to 20.0
     *
     * <p>
     * The operationDouble field should be used in conjunction with the operation field to specify
     * the desired comparison operation.
     * </p>
     *
     * <p>
     * The possible operations for querying include greater than, less than, greater than or equal to,
     * less than or equal to, equal to, and not equal to.
     * </p>
     *
     * <p>
     * The possible values for the operation field are defined in the Operation enum.
     * </p>
     *
     * @see Query
     * @see Operation
     */
    private Double operationDouble;

    /**
     * The operationText variable stores a String value representing the operation for querying.
     * It is used in the Query class to specify the operation to be performed in the query.
     *
     * <p>
     * The value of operationText should match one of the enum constants defined in the Operation enum.
     * The Operation enum provides constants for various comparison operations such as greater than, less than, equal to, etc.
     * </p>
     *
     * <p>
     * Example usage:
     *
     * Query query = new Query();
     * query.setOperationText(Operation.GREATER_THAN.toString());
     *
     * </p>
     *
     * @see Operation
     * @see Query
     */
    private String operationText;

    /**
     * The JoinOperation represents the type of join operation to be used in a query.
     * It can have two possible values: OR and AND.
     */
    private JoinOperation joinOperation;

    /**
     * The anfStatements variable is a private list of AnfStatementModel objects.
     * It is annotated with @JsonIgnore and @Transient, indicating that it should
     * be ignored during JSON serialization/deserialization and that it is not a
     * persistent field.
     * <p>
     * AnfStatementModel is a model class representing an ANF statement. ANF stands
     * for Alternative Normal Form, which is a way to represent logical formulas.
     * AnfStatementModel holds the necessary information to construct an ANF statement,
     * such as the symbol, inputs, and outputs.
     * <p>
     * The variable is used in the Query class, which represents a query object used for
     * searching concepts. It is one of the fields along with the conceptId and joinOperation.
     * <p>
     * To access and manipulate the list of ANF statements, use the getter and setter
     * methods provided by the Query class.
     */
    @JsonIgnore
    @Transient
    private List<AnfStatementModel> anfStatements;

    /**
     * The conceptIds variable is a private list of UUIDs. It is annotated with @JsonIgnore and @Transient, which means
     * that it will be ignored during JSON serialization and that it is not persistent. This variable is used to store
     * multiple concept IDs associated with a query in the Query class.
     * <p>
     * The conceptIds variable is defined in the class Query, which represents a query object used for searching concepts.
     * It holds the concept ID and join operation to be used in the query.
     */
    @JsonIgnore
    @Transient
    private List<UUID> conceptIds;
}
