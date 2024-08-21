package cdx.opencdx.adr.dto;

import cdx.opencdx.adr.model.AnfStatementModel;
import cdx.opencdx.adr.model.TinkarConceptModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Transient;
import lombok.Data;

import java.util.List;
import java.util.UUID;

/**
 * The Query class represents a query object used for searching concepts.
 * It holds the concept ID and join operation to be used in the query.
 */
@Schema(description = "A query object used for searching concepts.  Only one of these fields should be used at a time: formula, conceptId, group. Operation fields are used to compare the value of those fields against another value.  JoinOperation is used to bring together the multiple queries in an ADRQuery.")
@Data
public class Query {

    /**
     * The concept variable represents a Tinkar Concept ID that will be queried. It is optional.
     * <p>
     * This variable is of type TinkarConceptModel, which is a class representing a Tinkar concept stored in the database.
     * The TinkarConceptModel class is annotated as an entity and is mapped to the "dimtinkarconcept" table in the database.
     * It contains various properties of a concept including conceptId, conceptName, conceptDescription, and anfStatements.
     * <p>
     * The concept variable is a private member variable of the Query class, which represents a query object used for searching concepts.
     * The Query class is annotated with @Schema to provide a description for the concept variable.
     * <p>
     * The concept ID is a unique identifier for a concept and is stored in the "concept_id" column of the "dimtinkarconcept" table.
     * <p>
     * The concept variable can be set or retrieved using getter and setter methods.
     * <p>
     * Example usage:
     * Query query = new Query();
     * TinkarConceptModel concept = new TinkarConceptModel(UUID.randomUUID(), "Example Concept", "This is an example concept");
     * query.setConcept(concept);
     * TinkarConceptModel retrievedConcept = query.getConcept();
     */
    @Schema(description = "Tinkar Concept ID that will be queried.  Optional")
    private TinkarConceptModel concept;


    /**
     * The ComparisonOperation variable represents the comparison operation to be performed on the value coming from the query.
     * It is an optional field.
     */
    @Schema(description = "The comparison operation to be performance on the value coming from the query.  Optional")
    private ComparisonOperation operation;

    /**
     * The operationDouble variable represents the value to be compared against the conceptId if the value is to be compared against a number.
     * It is an optional field in the Query class and is of type Double.
     */
    @Schema(description = "The value to be compared against the conceptId, if the value is to be compared against a number..  Optional")
    private Double operationDouble;


    /**
     * The operationUnit variable represents the unit of the value to be compared against the conceptId,
     * if the value is to be compared against a number. This variable is optional.
     * <p>
     * The operationUnit variable is a member variable of the Query class, which is the containing class
     * of the initial symbol. The Query class contains other member variables like concept, operation,
     * operationDouble, operationText, formula, joinOperation, group, anfStatements, and conceptIds.
     * <p>
     * The operationUnit variable is an instance of the TinkarConceptModel class, which is an entity class
     * representing a Tinkar concept stored in the database. It contains various properties of a concept
     * including conceptId, conceptName, conceptDescription, and anfStatements.
     * <p>
     * The operationUnit variable is annotated with the @Schema annotation to provide additional information
     * about the variable. The description attribute of the @Schema annotation describes the purpose of the
     * variable.
     */
    @Schema(description = "The unit of the value to be compared against the conceptId, if the value is to be compared against a number.  Optional")
    private TinkarConceptModel operationUnit;
    /**
     * The operationText variable represents the value to be compared against the conceptId,
     * if the value is to be compared against a string. It is an optional field.
     * <p>
     * This variable is a member of the Query class, which represents a query object used for searching concepts.
     * The Query class contains various fields for specifying query criteria like conceptId, joinOperation, etc.
     * The operationText field is used to compare the value coming from the query against the conceptId.
     * <p>
     * Example usage:
     * Query query = new Query();
     * query.setOperationText("example");
     * <p>
     * // Perform query operation using operationText
     * // ...
     */
    @Schema(description = "The value to be compared against the conceptId, if the value is to be compared against a string.  Optional")
    private String operationText;

    /**
     * A formula that would be run to calculate a value.
     */
    @Schema(description = "A formula that would be run to calculate a value.  Optional")
    private Formula formula;

    /**
     * The JoinOperation enum represents logical join operations for querying.
     * It provides two constants: OR and AND.
     */
    @Schema(description = "Indicates how queries should be applied AND / OR  order of operation is the given order of queries  Optional")
    private JoinOperation joinOperation;


    /**
     * A group of queries that should be run together. Optional.
     * <p>
     * This variable represents a list of {@link Query} objects that should be executed together as a group.
     * The queries in the group can be applied using either "AND" or "OR" join operation specified by the {@link JoinOperation} field.
     * <p>
     * The group field is optional and may contain zero or more queries.
     *
     * @see Query
     * @see JoinOperation
     */
    @Schema(description = "A group of queries that should be run together.  Optional")
    private List<Query> group;

    /**
     * This variable represents a list of AnfStatementModel objects.
     * An AnfStatementModel represents a statement in ANF format (Assertion and Normal Form), which is a logical
     * representation of a statement using atomic concepts and logical operators.
     * <p>
     * The list of AnfStatementModel objects in this variable is used for storing multiple ANF statements.
     * It is annotated with @JsonIgnore and @Transient, which means it will be ignored during JSON serialization
     * and it is not persisted in the database.
     * <p>
     * AnfStatementModel:
     * - Represents a statement in Assertion and Normal Form (ANF).
     * - Consists of a subject, predicate, object, and context, each represented by a Concept.
     * - Allows expressing relationships between concepts using logical operators like AND, OR, and NOT.
     * - Provides methods to manipulate and evaluate ANF statements.
     * <p>
     * Usage:
     * - To add a new ANF statement to the list:
     *   anfStatements.add(anfStatement);
     * <p>
     * - To iterate over the ANF statements:
     *   for (AnfStatementModel statement : anfStatements) {
     *       // do something with the statement
     *   }
     * <p>
     * - To retrieve the size of the list of ANF statements:
     *   int size = anfStatements.size();
     * <p>
     * Important Note: This variable is marked as @JsonIgnore and @Transient, so it will not be serialized
     * or persisted automatically. If you need to include it in JSON serialization or database persistence,
     * you should remove the annotations and adjust the corresponding logic accordingly.
     */
    @JsonIgnore
    @Transient
    private List<AnfStatementModel> anfStatements;

    /**
     *
     */
    @JsonIgnore
    @Transient
    private List<UUID> conceptIds;
}
