package cdx.opencdx.adr.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The SavedQuery class represents a saved query object.
 *
 * It includes the following fields:
 * - id: The unique identifier for a saved query.
 * - name: The name of the saved query.
 * - query: An ADRQuery object representing the query.
 *
 * Usage example:
 * SavedQuery savedQuery = new SavedQuery();
 * savedQuery.setId(1L);
 * savedQuery.setName("Example Query");
 * savedQuery.setQuery(new ADRQuery());
 *
 * To access and manipulate the fields of a SavedQuery object, use the getter and setter methods provided by the class.
 *
 * @see ADRQuery
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SavedQuery {
    /**
     * The id variable represents the unique identifier for a saved query.
     * It is of type Long.
     *
     * Usage example:
     * SavedQuery savedQuery = new SavedQuery();
     * savedQuery.setId(1L);
     *
     * To access and manipulate the id value of a SavedQuery object, use the getter and setter methods provided by the class.
     *
     * @see SavedQuery
     */
    private Long id;
    /**
     * The name variable represents the name of a saved query.
     *
     * This variable is of type String and is a field of the SavedQuery class.
     * The SavedQuery class is used to represent a saved query object and includes fields such as id, name, and query.
     *
     * Usage example:
     * SavedQuery savedQuery = new SavedQuery();
     * savedQuery.setName("Example Query");
     *
     * To access and manipulate the name field of a SavedQuery object, use the getter and setter methods provided by the class.
     *
     * @see SavedQuery
     */
    private String name;
    /**
     * The ADRQuery class represents a query object used for searching concepts.
     *
     * The class includes the following fields:
     * - unitOutput: Represents different unit output options for a system.
     * - queries: A list of Query objects representing a collection of queries.
     *
     * The UnitOutput enum represents the different unit output options for a system.
     * The Query class represents a query object used for searching concepts.
     * The Operation enum provides constants for comparison operators used in queries.
     *
     * Usage example:
     *
     * ADRQuery adrQuery = new ADRQuery();
     * adrQuery.setUnitOutput(UnitOutput.METRIC);
     * adrQuery.setQueries(new ArrayList<>());
     *
     * Query query = new Query();
     * query.setConceptId(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"));
     * query.setOperation(Operation.GREATER_THAN);
     * query.setOperationDouble(10.5);
     * query.setJoinOperation(JoinOperation.AND);
     * adrQuery.getQueries().add(query);
     *
     * List<Query> queries = adrQuery.getQueries();
     * for (Query q : queries) {
     *     // Perform some operation
     * }
     *
     * To access and manipulate the list of queries, use the getter and setter methods provided by the ADRQuery class.
     *
     * @see Query
     * @see ComparisonOperation
     * @see UnitOutput
     */
    private ADRQuery query;
}
