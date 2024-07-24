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
