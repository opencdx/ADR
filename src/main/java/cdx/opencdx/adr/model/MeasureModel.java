package cdx.opencdx.adr.model;

import cdx.opencdx.adr.repository.ANFRepo;
import cdx.opencdx.grpc.data.Measure;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "dimmeasure")
public class MeasureModel {
    /**
     * The id variable represents the identifier of a MeasureModel object.
     * It is annotated with the @Id annotation to indicate that it is the primary key of the entity.
     * The @GeneratedValue annotation specifies the strategy for generating the identifier values, in this case, GenerationType.IDENTITY.
     * The @Column annotation is used to specify the mapping to the corresponding column in the database table, with the name attribute set to "id".
     * The nullable attribute is set to false, indicating that the id field cannot be null in the database.
     * <p>
     * The MeasureModel class is an entity class representing a measure, annotated with @Entity and @Table annotations.
     * It contains various properties of the measure, including the id field.
     * The MeasureModel class has a one-to-one relationship with the TinkarConceptModel and LogicalExpressionModel classes.
     * The TinkarConceptModel class represents a Tinkar concept stored in the database,
     * and the LogicalExpressionModel class represents a logical expression associated with the measure.
     * <p>
     * To instantiate a MeasureModel object, you can use the default constructor or the constructor that takes a Measure and ANFRepo as parameters.
     * In the latter case, the values of the measure object are assigned to the corresponding fields of the MeasureModel object,
     * and the logical expression is saved or retrieved from the logical expression repository.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * The upperBound variable represents the upper bound value of a measure.
     * It is stored in the "upper_bound" column of the "dimmeasure" table in the database.
     * The upperBound variable is of type Double and is annotated with @Column to specify the mapping to the corresponding column in the database table.
     * This variable is a private member variable of the MeasureModel class, which is an entity class representing a measure.
     * The MeasureModel class is annotated with various JPA annotations like @Entity, @Table, @NoArgsConstructor.
     * It also contains other member variables like id, lowerBound, includeLowerBound, etc. for representing different properties of a measure.
     * <p>
     * The upper bound value represents the maximum value that the measure can take.
     * It is used to define the range within which the measure can vary.
     * For example, if the upper bound value is set to 100, the measure can have a value up to 100.
     * <p>
     * The upperBound variable can be accessed through the getter and setter methods generated for this variable.
     */
    @Column(name = "upper_bound")
    private Double upperBound;

    /**
     * The lowerBound variable represents the lower bound value of a measure.
     * It is a Double type variable and is annotated with the @Column annotation to specify the mapping to the corresponding column in the database table.
     * The name attribute of the @Column annotation specifies the name of the database column as "lower_bound".
     * <p>
     * The lowerBound variable is a private member variable of the MeasureModel class, which is an entity class representing a measure.
     * The MeasureModel class is annotated with various JPA annotations like @Entity, @Table, @NoArgsConstructor.
     * It also contains other member variables like id, upperBound, includeUpperBound, etc. for representing different properties of a measure.
     * <p>
     * The lowerBound value can be accessed through the getter and setter methods generated for this variable.
     */
    @Column(name = "lower_bound")
    private Double lowerBound;

    /**
     * The includeUpperBound variable represents whether the upper bound is included or excluded in a measurement.
     * It is a Boolean value indicating whether the upper bound value should be considered inclusive or exclusive.
     * This variable is used in the MeasureModel class to determine the inclusion or exclusion of the upper bound in a measurement.
     * <p>
     * The value of this variable is stored in the "include_upper_bound" column in the "dimmeasure" database table.
     * It is annotated with the @Column annotation to specify the mapping to the corresponding column in the database.
     * <p>
     * The MeasureModel class is an entity class representing a measure in the database.
     * It is annotated with various JPA annotations like @Entity, @Table, @NoArgsConstructor.
     * It also contains other member variables like upperBound, lowerBound, includeLowerBound, etc.
     * <p>
     * This variable can be accessed through the getter and setter methods generated for this variable.
     */
    @Column(name = "include_upper_bound")
    private Boolean includeUpperBound;

    /**
     * The includeLowerBound variable represents whether the lower bound of a measure model should be included or not.
     * It is a Boolean value that determines whether the lower bound value should be considered as part of the measure or not.
     * If the includeLowerBound is true, it means that the lower bound value is inclusive and should be considered as part of the measure.
     * If the includeLowerBound is false, it means that the lower bound value is exclusive and should not be considered as part of the measure.
     * <p>
     * The includeLowerBound variable is annotated with the @Column annotation to specify the mapping to the corresponding column in the database table.
     * The name attribute of the @Column annotation specifies the name of the database column as "include_lower_bound".
     * <p>
     * The includeLowerBound variable is a private member variable of the MeasureModel class.
     * The MeasureModel class is an entity class representing a measure model and is annotated with various JPA annotations like @Entity, @Table, @NoArgsConstructor.
     * It also contains other member variables like id, upperBound, lowerBound, includeUpperBound, unit, semantic, and resolution for representing different properties of a measure
     * model.
     * <p>
     * The MeasureModel class is used to store and retrieve measure models in the database.
     * The includeLowerBound value can be accessed through the getter and setter methods generated for this variable.
     */
    @Column(name = "include_lower_bound")
    private Boolean includeLowerBound;

    /**
     * The variable "unit" represents the TinkarConceptModel associated with a MeasureModel instance.
     * It is a ManyToOne relationship, indicating that multiple MeasureModel instances can refer to the same TinkarConceptModel instance.
     * The FetchType.LAZY attribute specifies that the TinkarConceptModel should be lazily fetched, i.e., loaded only when accessed.
     * The JoinColumn annotation specifies the database column name "unit_id" that is used to store the relationship between MeasureModel and TinkarConceptModel.
     * <p>
     * The TinkarConceptModel class represents a Tinkar concept stored in the database.
     * It contains properties like conceptId, conceptName, conceptDescription, and anfStatements.
     * The TinkarConceptModel class is annotated as an entity and is mapped to the "dimtinkarconcept" table in the database.
     * <p>
     * The MeasureModel class represents a measure stored in the database.
     * It contains properties like id, upperBound, lowerBound, includeUpperBound, includeLowerBound, unit, semantic, and resolution.
     * The MeasureModel class is annotated as an entity and is mapped to the "dimmeasure" table in the database.
     * <p>
     * The unit property of a MeasureModel instance is used to associate a TinkarConceptModel instance with the measure.
     * This allows for semantic referencing and grouping of measures based on the concept represented by the TinkarConceptModel.
     * <p>
     * Example usage:
     * <p>
     * MeasureModel measure = new MeasureModel();
     * TinkarConceptModel unit = new TinkarConceptModel();
     * measure.setUnit(unit);
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    private TinkarConceptModel unit;

    /**
     *
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semantic_id")
    private LogicalExpressionModel semantic;

    /**
     * The resolution variable represents the resolution of a measure in the MeasureModel class.
     * It is a Double value that specifies the level of detail or precision of the measure.
     * <p>
     * The value is stored in the {@link Column} named "resolution" in the database table "dimmeasure".
     * <p>
     * The MeasureModel class is an entity class that represents a measure and is mapped to the "dimmeasure" table in the database.
     * It contains various properties of a measure including id, upperBound, lowerBound, includeUpperBound, includeLowerBound, unit, semantic, and resolution.
     * <p>
     * The resolution variable can be accessed through the getter and setter methods generated for this variable.
     * <p>
     * Example usage:
     * <p>
     * MeasureModel measure = new MeasureModel();
     * measure.setResolution(0.01);
     * Double resolution = measure.getResolution();
     * System.out.println("Resolution: " + resolution);
     */
    @Column(name = "resolution")
    private Double resolution;

    /**
     * Represents a model for storing a Measure object.
     *
     * @param measure The Measure object to be stored.
     * @param anfRepo The ANFRepo object used to save or find a LogicalExpressionModel.
     */
    public MeasureModel(Measure measure, ANFRepo anfRepo) {
        this.upperBound = measure.getUpperBound();
        this.lowerBound = measure.getLowerBound();
        this.includeLowerBound = measure.getIncludeLowerBound();
        this.includeUpperBound = measure.getIncludeUpperBound();
        this.semantic = anfRepo.getLogicalExpressionRepository().saveOrFind(new LogicalExpressionModel(measure.getSemantic(), anfRepo));
        this.resolution = measure.getResolution();
    }
}