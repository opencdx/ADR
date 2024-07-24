package cdx.opencdx.adr.model;

import cdx.opencdx.adr.repository.ANFRepo;
import cdx.opencdx.grpc.data.Reference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The ReferenceModel class represents a reference object in the application.
 *
 * <p>
 * The class is annotated with <code>@Entity</code> to specify that it is an entity class
 * that maps to a database table. The <code>@Table</code> annotation specifies the name of the
 * database table as "dimreference".
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
@Table(name = "dimreference")
public class ReferenceModel {
    /**
     * The id variable represents the unique identifier of a reference model.
     * It is annotated with @Id to indicate that it is the primary key of the entity.
     * The @GeneratedValue annotation specifies the strategy for generating the value of the id.
     * In this case, the strategy is GenerationType.IDENTITY, which means that the database will automatically assign a value to the id.
     * The @Column annotation specifies the mapping of the id attribute to the database column named "id".
     * The nullable property of the @Column annotation is set to false, indicating that the id must have a value and cannot be null.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * The identifier variable is a private field of the class ReferenceModel.
     * It represents the identifier of the reference object.
     * <p>
     * The identifier is annotated with the @Column annotation, specifying the mapping to a column in the database table.
     * The name attribute of the @Column annotation is set to "identifier".
     * The length attribute is set to Integer.MAX_VALUE, indicating the maximum length of the column.
     * <p>
     * Example usage:
     * ReferenceModel model = new ReferenceModel();
     * model.setIdentifier("ABC123");
     */
    @Column(name = "identifier", length = Integer.MAX_VALUE)
    private String identifier;

    /**
     * The display variable represents the display string associated with a ReferenceModel object.
     * It is annotated with @Column to specify the database column name and length.
     */
    @Column(name = "display", length = Integer.MAX_VALUE)
    private String display;

    /**
     * The `reference` variable is a private instance variable of type `String`
     * that represents the reference value of a `ReferenceModel` object.
     * <p>
     * It is annotated with the `@Column` annotation, which specifies the mapping of
     * the entity's property `reference` to a column in the database table.
     * The `name` attribute specifies the name of the column as "reference",
     * and the `length` attribute specifies the maximum length of the column as `Integer.MAX_VALUE`.
     * This annotation is used by the Object Relational Mapping (ORM) framework to map
     * the property to the corresponding column in the database table.
     * <p>
     * This variable is declared in the `ReferenceModel` class, which is an entity class
     * representing a reference in the application. It is used to store information about
     * a reference, including its identifier, display name, URI, and reference value.
     * <p>
     * Example usage:
     * <p>
     * ```java
     * ReferenceModel referenceModel = new ReferenceModel();
     * referenceModel.setReference("1234567890");
     * ```
     *
     * @see ReferenceModel
     * @see Column
     */
    @Column(name = "reference", length = Integer.MAX_VALUE)
    private String reference;

    /**
     * The URI of the reference.
     * <p>
     * This variable is a private instance variable of the {@link ReferenceModel} class.
     * It represents the URI of the reference and is mapped to the "uri" column in the database table.
     *
     * @see Column
     * @see ReferenceModel
     * @see ReferenceModel#uri
     */
    @Column(name = "uri", length = Integer.MAX_VALUE)
    private String uri;

    /**
     * Constructs a new ReferenceModel object.
     *
     * @param reference the reference object used to initialize the fields of the ReferenceModel
     * @param anfRepo   the ANFRepo object to be used
     */
    public ReferenceModel(Reference reference, ANFRepo anfRepo) {
        this.identifier = reference.getIdentifier();
        this.display = reference.getDisplay();
        this.reference = reference.getReference();
        this.uri = reference.getUri();
    }
}