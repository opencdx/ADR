package cdx.opencdx.adr.model;

import cdx.opencdx.adr.repository.ANFRepo;
import cdx.opencdx.grpc.data.LogicalExpression;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The LogicalExpressionModel class represents a logical expression stored in the database.
 * It contains properties such as id, expression, and tinkarConcept that represent different attributes of a logical expression.
 * This class is annotated as an entity and is mapped to the "dimlogicalexpression" table in the database.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "dimlogicalexpression")
public class LogicalExpressionModel {
    /**
     * The id variable represents the primary key of an entity in the "dimlogicalexpression" table.
     * It is annotated with @Id to indicate that it is the primary key of the entity.
     * The @GeneratedValue annotation specifies that the value of the id will be automatically generated.
     * The GenerationType.IDENTITY strategy is used to generate the value of the id.
     * The @Column annotation is used to map the id to the "id" column in the "dimlogicalexpression" table.
     * The nullable = false attribute of the @Column annotation specifies that the id column cannot be null.
     * The id variable is a private member of the LogicalExpressionModel class, which represents a logical expression stored in the database.
     * The LogicalExpressionModel class is annotated with various JPA annotations like @Entity, @Table, @NoArgsConstructor.
     * It also contains other member variables like expression, tinkarConcept, etc. for representing different properties of a logical expression.
     * <p>
     * The LogicalExpressionModel class is used to store and retrieve logical expressions in the database.
     * <p>
     * The value of the id can be accessed through the getter and setter methods generated for this variable.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * The expression variable represents a logical expression stored in the database.
     * It is annotated with the @Column annotation to specify the mapping to the corresponding column in the database table.
     * The name attribute of the @Column annotation specifies the name of the database column as "expression".
     * The length attribute of the @Column annotation is set to Integer.MAX_VALUE to indicate that there is no maximum length limit for the expression.
     * <p>
     * This variable is a private member variable of the LogicalExpressionModel class, which is an entity class representing a logical expression.
     * The LogicalExpressionModel class is annotated with various JPA annotations like @Entity, @Table, @NoArgsConstructor.
     * It also contains other member variables like id, tinkarConcept, etc. for representing different properties of a logical expression.
     * <p>
     * The LogicalExpressionModel class is used to store and retrieve logical expressions in the database.
     * <p>
     * The expression can be accessed through the getter and setter methods generated for this variable.
     */
    @Column(name = "expression", length = Integer.MAX_VALUE)
    private String expression;

    /**
     * The tinkarConcept variable represents a related Tinkar concept stored in the database.
     * This variable is annotated with the @JsonIgnore and @ManyToOne annotations to define the relationship between the entities LogicalExpressionModel and TinkarConceptModel.
     * The @JsonIgnore annotation is used to indicate that the tinkarConcept variable should be ignored during the serialization and deserialization processes.
     * The @ManyToOne annotation is used to define a many-to-one relationship between the entities, where a logical expression may be associated with multiple Tinkar concepts.
     * The fetch attribute of the @ManyToOne annotation is set to LAZY, indicating that the related Tinkar concept should be loaded lazily.
     * The joinColumn attribute of the @JoinColumn annotation is used to specify the foreign key column name in the database table "dimlogicalexpression" that references the "dim
     * tinkarconcept" table.
     * The name attribute of the @JoinColumn annotation is set to "tinkar_concept_id".
     * The tinkarConcept variable is of type TinkarConceptModel, which represents a Tinkar concept stored in the database.
     * The TinkarConceptModel class is an entity class annotated with various JPA annotations, including @Entity, @Table, @NoArgsConstructor.
     * It contains properties such as conceptId, conceptName, conceptDescription, and anfStatements that represent different attributes of a Tinkar concept.
     * The TinkarConceptModel class is used to store and retrieve Tinkar concepts in the database.
     */
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tinkar_concept_id")
    private TinkarConceptModel tinkarConcept;

    /**
     * Constructs a LogicalExpressionModel object.
     *
     * @param logicalExpression the LogicalExpression object to retrieve the expression from
     * @param anfRepo           the ANFRepo object used to interact with the ANF database
     */
    public LogicalExpressionModel(LogicalExpression logicalExpression, ANFRepo anfRepo) {
        this.expression = logicalExpression.getExpression();
    }
}