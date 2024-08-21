package cdx.opencdx.adr.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * The ADRQuery class represents a query object used for searching concepts.
 * <p>
 * The class includes the following fields:
 * - unitOutput: Represents different unit output options for a system.
 * - queries: A list of Query objects representing a collection of queries.
 * <p>
 * The UnitOutput enum represents the different unit output options for a system.
 * The Query class represents a query object used for searching concepts.
 * The Operation enum provides constants for comparison operators used in queries.
 * <p>
 * Usage example:
 * <p>
 * ADRQuery adrQuery = new ADRQuery();
 * adrQuery.setUnitOutput(UnitOutput.METRIC);
 * adrQuery.setQueries(new ArrayList<>());
 * <p>
 * Query query = new Query();
 * query.setConceptId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
 * query.setOperation(Operation.GREATER_THAN);
 * query.setOperationDouble(10.5);
 * query.setJoinOperation(JoinOperation.AND);
 * adrQuery.getQueries().add(query);
 * <p>
 * List<Query> queries = adrQuery.getQueries();
 * for (Query q : queries) {
 *     // Perform some operation
 * }
 * <p>
 * To access and manipulate the list of queries, use the getter and setter methods provided by the ADRQuery class.
 *
 * @see Query
 * @see ComparisonOperation
 * @see UnitOutput
 */
@Data
public class ADRQuery {
    /**
     * The unitOutput variable represents different unit output options for a system.
     * <p>
     * It is of type UnitOutput enum, which contains three options: IMPERIAL, METRIC, and DEFAULT.
     * The IMPERIAL option represents the unit output system in imperial units.
     * The METRIC option represents the unit output system in metric units.
     * The DEFAULT option represents the default unit output for a system.
     * </p>
     *
     * @see UnitOutput
     */
    @Schema(description = "Unit output for the system. This maybe IMPERIAL or METRIC or DEFAULT. All units that can be converted will be converted")
    private UnitOutput unitOutput;
    /**
     * The queries variable is a private list of Query objects.
     * It is used to store and manipulate a collection of queries.
     * A query represents a search operation for concepts and includes
     * information such as the concept ID, operation, and join operation.
     *
     * <p>
     * The Query class, which represents a query object used for searching concepts,
     * includes the following fields:
     * - conceptId: Represents the unique identifier for a concept.
     * - operation: Represents the comparison operator used in the query.
     * - operationDouble: Represents a double value used in querying operations.
     * - operationUnit: Represents the unique identifier for an operation unit.
     * - operationText: Represents a string value representing the operation for querying.
     * - joinOperation: Represents the type of join operation to be used in the query.
     * - anfStatements: A list of AnfStatementModel objects representing an ANF statement.
     * - conceptIds: A list of UUIDs representing multiple concept IDs associated with a query.
     * </p>
     *
     * <p>
     * The Operation enum, defined in the Query class, provides constants for comparison operators
     * used in queries, such as greater than, less than, equal to, etc.
     * </p>
     *
     * <p>
     * The UnitOutput enum, defined in the ADRQuery class, represents the different unit output options for a system
     * and includes options such as IMPERIAL, METRIC, and DEFAULT.
     * </p>
     *
     * Usage example:
     * <p>
     * ADRQuery adrQuery = new ADRQuery();
     * adrQuery.setUnitOutput(UnitOutput.METRIC);
     * adrQuery.setQueries(new ArrayList<>());
     * <p>
     * Query query = new Query();
     * query.setConceptId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
     * query.setOperation(Operation.GREATER_THAN);
     * query.setOperationDouble(10.5);
     * query.setJoinOperation(JoinOperation.AND);
     * adrQuery.getQueries().add(query);
     * <p>
     * List<Query> queries = adrQuery.getQueries();
     * for (Query q : queries) {
     *     // Perform some operation
     * }
     *
     * <p>
     * To access and manipulate the list of queries, use the getter and setter methods provided by the ADRQuery class.
     * </p>
     *
     * @see Query
     * @see ComparisonOperation
     * @see UnitOutput
     */
    @Schema(description = "The query that is be executed. It can be made up of multiple individual queries, combined with AND or OR")
    private List<Query> queries;
}
