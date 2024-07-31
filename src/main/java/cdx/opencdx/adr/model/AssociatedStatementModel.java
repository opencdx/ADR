package cdx.opencdx.adr.model;

import cdx.opencdx.adr.utils.ANFHelper;
import cdx.opencdx.grpc.data.AssociatedStatement;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The AssociatedStatementModel class represents an associated statement in the application.
 *
 * <p>
 * The class is annotated with <code>@Entity</code> to specify that it is an entity class
 * that maps to a database table. The <code>@Table</code> annotation specifies the name of the
 * database table as "dimassociatedstatement".
 * </p>
 *
 * <p>
 * The class is annotated with <code>@Getter</code> and <code>@Setter</code> to automatically generate
 * the getter and setter methods for the instance variables.
 * </p>
 *
 * <p>
 * The class is annotated with <code>@NoArgsConstructor</code> to generate a no-argument constructor.
 * </p>
 *
 * @see Entity
 * @see Table
 * @see Getter
 * @see Setter
 * @see NoArgsConstructor
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "dimassociatedstatement")
public class AssociatedStatementModel {
    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * The stateId variable represents the state ID associated with an AssociatedStatementModel object.
     *
     * <p>
     * It is annotated with <code>@ManyToOne</code> to specify a many-to-one relationship with the ReferenceModel entity.
     * The <code>@FetchType.LAZY</code> annotation indicates that the associated ReferenceModel should be lazily fetched from the database.
     * The <code>@JoinColumn</code> annotation specifies the foreign key column name as "state_id" in the "dimassociatedstatement" table.
     * </p>
     *
     * <p>
     * The ReferenceModel is a class representing a reference object in the application.
     * It is annotated with <code>@Entity</code> to specify that it is an entity class that maps to a database table named "dimreference".
     * It contains various instance variables such as id, identifier, display, reference, and uri, which represent different attributes of a reference object.
     * </p>
     *
     * <p>
     * Example usage:
     * <pre>{@code
     * AssociatedStatementModel statement = new AssociatedStatementModel();
     * ReferenceModel stateId = new ReferenceModel();
     * stateId.setIdentifier("STATE-001");
     * stateId.setDisplay("State 001");
     * stateId.setReference("ABC123");
     * stateId.setUri("http://example.com/states/001");
     * statement.setStateId(stateId);
     * }</pre>
     * </p>
     *
     * @see ManyToOne
     * @see FetchType
     * @see JoinColumn
     * @see ReferenceModel
     * @see AssociatedStatementModel
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private ReferenceModel stateId;


    /**
     * The semantic variable represents a logical expression associated with an AssociatedStatementModel.
     * It is used to define the relationship between the AssociatedStatementModel and the LogicalExpressionModel entities.
     * The associated logical expression can be accessed through the getter and setter methods generated for this variable.
     * <p>
     * The semantic variable is annotated with various JPA annotations like @ManyToOne, @FetchType.LAZY, @JoinColumn.
     * The @ManyToOne annotation is used to define a many-to-one relationship between the entities, where an AssociatedStatementModel can be associated with multiple logical expressions
     * .
     * The fetch attribute of the @ManyToOne annotation is set to LAZY, indicating that the associated logical expression should be loaded lazily.
     * The joinColumn attribute of the @JoinColumn annotation is used to specify the foreign key column name in the database table "dimassociatedstatement" that references the "dim
     * <p>
     * logicalexpression" table.
     * The name attribute of the @JoinColumn annotation is set to "semantic_id".
     * <p>
     * The AssociatedStatementModel class is an entity class annotated with various JPA annotations, including @Entity, @Table, @NoArgsConstructor.
     * It contains properties such as id, stateId, and semantic that represent different attributes of an associated statement.
     * The AssociatedStatementModel class is used to store and retrieve associated statements in the database.
     * <p>
     * The value of the semantic variable can be accessed through the getter and setter methods generated for this variable.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semantic_id")
    private TinkarConceptModel semantic;

    /**
     * Creates a new AssociatedStatementModel instance with the given associatedStatement and anfRepo.
     *
     * @param associatedStatement the associated statement object
     * @param anfRepo             the ANF repository object
     */
    public AssociatedStatementModel(AssociatedStatement associatedStatement, ANFHelper anfRepo) {
        this.stateId = anfRepo.getReferenceRepository().save(new ReferenceModel(associatedStatement.getId(), anfRepo));
        this.semantic = anfRepo.getOpenCDXIKMService().getInkarConceptModel(associatedStatement.getSemantic());
    }
}