package cdx.opencdx.adr.model;

import cdx.opencdx.adr.utils.ANFHelper;
import cdx.opencdx.grpc.data.Practitioner;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * The PractitionerModel class represents a practitioner in the application.
 * It is an entity class that maps to a database table named "dimpractitioner".
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "dimpractitioner")
public class PractitionerModel {
    /**
     * The id variable represents the unique identifier of an object.
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
     * The practId variable represents the unique identifier of a PractitionerModel object.
     * It is annotated with @Column to specify the mapping of the practId attribute to the database column named "pract_id".
     * The nullable property of the @Column annotation is set to false, indicating that the practId must have a value and cannot be null.
     * <p>
     * Example usage:
     * PractitionerModel practitionerModel = new PractitionerModel();
     * practitionerModel.setPractId(UUID.randomUUID());
     */
    @Column(name = "pract_id", nullable = false)
    private UUID practId;

    /**
     * The practitioner variable represents a many-to-one relationship with the ReferenceModel class.
     *
     * <p>
     * The practitioner variable is annotated with <code>@ManyToOne</code> to specify a many-to-one relationship with the
     * ReferenceModel class. The <code>fetch</code> attribute is set to <code>LAZY</code>, indicating that the association
     * should be lazily initialized. The <code>name</code> attribute of the <code>@JoinColumn</code> annotation is set to
     * "practitioner_value_id", specifying the name of the foreign key column in the database table.
     * </p>
     *
     * <p>
     * The practitioner variable is declared in the PractitionerModel class, which represents a practitioner in the application.
     * It is annotated with <code>@Entity</code> to indicate that it is an entity class that maps to a database table.
     * The <code>@Table</code> annotation specifies the name of the database table as "dimpractitioner".
     * </p>
     *
     * <p>
     * Example usage:
     * <p>
     * ```java
     * PractitionerModel practitioner = new PractitionerModel();
     * ReferenceModel reference = new ReferenceModel();
     * practitioner.setPractitioner(reference);
     * ```
     * </p>
     *
     * @see ManyToOne
     * @see FetchType
     * @see JoinColumn
     * @see PractitionerModel
     * @see ReferenceModel
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "practitioner_value_id")
    private ReferenceModel practitioner;

    /**
     * The code variable represents a logical expression associated with a PractitionerModel object.
     *
     * <p>
     * The code is annotated with <code>@ManyToOne</code> to specify a many-to-one relationship with the
     * LogicalExpressionModel class. The <code>fetch</code> attribute is set to <code>LAZY</code>, indicating
     * that the association should be lazily initialized. The <code>name</code> attribute of the
     * <code>@JoinColumn</code> annotation is set to "code_id", specifying the name of the foreign key column
     * in the database table.
     * </p>
     *
     * <p>
     * The PractitionerModel class represents a practitioner in the application. It is annotated with
     * <code>@Entity</code> to indicate that it is an entity class that maps to a database table.
     * The <code>@Table</code> annotation specifies the name of the database table as "dimpractitioner".
     * </p>
     *
     * <p>
     * Example usage:
     * PractitionerModel practitioner = new PractitionerModel();
     * LogicalExpressionModel code = new LogicalExpressionModel();
     * practitioner.setCode(code);
     * </p>
     *
     * @see ManyToOne
     * @see FetchType
     * @see JoinColumn
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_id")
    private TinkarConceptModel code;

    /**
     * Constructs a new PractitionerModel object.
     *
     * @param practitioner The Practitioner object from which the data is obtained.
     * @param anfRepo      The ANFRepo object used to save the reference and logical expression models.
     */
    public PractitionerModel(Practitioner practitioner, ANFHelper anfRepo) {
        this.practId = UUID.fromString(practitioner.getId());
        this.practitioner = anfRepo.getReferenceRepository().save(new ReferenceModel(practitioner.getPractitionerValue(), anfRepo));
        this.code = anfRepo.getOpenCDXIKMService().getInkarConceptModel(practitioner.getCode());
    }
}