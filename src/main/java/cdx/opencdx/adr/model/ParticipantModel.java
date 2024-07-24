package cdx.opencdx.adr.model;

import cdx.opencdx.adr.repository.ANFRepo;
import cdx.opencdx.grpc.data.Participant;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

/**
 * The ParticipantModel class represents a participant in the application.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "dimparticipant")
public class ParticipantModel {
    /**
     * The id variable represents the unique identifier of a ParticipantModel object.
     *
     * <p>
     * It is annotated with <code>@Id</code> to indicate that it is the primary key of the entity.
     * The <code>@GeneratedValue</code> annotation specifies the strategy for generating the value of the id.
     * In this case, the strategy is <code>GenerationType.IDENTITY</code>, which means that the database will automatically assign a value to the id.
     * The <code>@Column</code> annotation specifies the mapping of the id attribute to the database column named "id".
     * The <code>nullable</code> property of the <code>@Column</code> annotation is set to <code>false</code>,
     * indicating that the id must have a value and cannot be null.
     * </p>
     *
     * <p>
     * The id variable is a private field of the <code>ParticipantModel</code> class, which is an entity class
     * representing a participant in the application. It is used to store the unique identifier of a participant.
     * </p>
     * <p>
     * Example usage:
     *
     * <pre>{@code
     * ParticipantModel participant = new ParticipantModel();
     * participant.setId(1L);
     * }</pre>
     *
     * @see Id
     * @see GeneratedValue
     * @see Column
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * The partId variable represents the unique identifier of a part in the application.
     * It is annotated with {@code @Column} to indicate the mapping to a database column named "part_id".
     * The nullable property of the {@code @Column} annotation is set to false, indicating that the partId must have a value and cannot be null.
     * The partId variable is a private field of the {@link ParticipantModel} class, which is an entity class representing a participant in the application.
     * <p>
     * Example usage:
     * {@code ParticipantModel participant = new ParticipantModel();
     * participant.setPartId(UUID.randomUUID());}
     *
     * @see ParticipantModel
     * @see Column
     */
    @Column(name = "part_id", nullable = false)
    private UUID partId;

    /**
     * The practitioner variable represents a reference to a practitioner in the application.
     *
     * <p>
     * It is annotated with {@code @ManyToOne(fetch = FetchType.LAZY)} and {@code @JoinColumn(name = "practitioner_value_id")}
     * to specify that it is a many-to-one relationship with the {@code ReferenceModel} entity and to define the join column
     * name as "practitioner_value_id".
     * </p>
     *
     * <p>
     * This variable is declared in the {@code ParticipantModel} class, which is an entity class representing a participant in the application.
     * It is used to store information about a participant, including the reference to a practitioner associated with the participant.
     * </p>
     *
     * <p>
     * Example usage:
     * </p>
     *
     * <pre>
     *     ParticipantModel participant = new ParticipantModel();
     *     ReferenceModel practitioner = new ReferenceModel();
     *     practitioner.setIdentifier("123456");
     *     practitioner.setDisplay("John Doe");
     *     practitioner.setReference("urn:practitioner:123456");
     *     participant.setPractitioner(practitioner);
     * </pre>
     *
     * @see ParticipantModel
     * @see ReferenceModel
     * @see ManyToOne
     * @see JoinColumn
     * @see FetchType
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "practitioner_value_id")
    private ReferenceModel practitioner;

    /**
     * The code variable represents a logical expression model in the ParticipantModel class.
     *
     * <p>
     * The class is annotated with <code>@ManyToOne</code> and <code>@JoinColumn</code> to specify that it is a many-to-one relationship with the LogicalExpressionModel class.
     * The <code>fetch</code> attribute of the <code>@ManyToOne</code> annotation is set to <code>FetchType.LAZY</code>, indicating that the association should be lazily fetched from
     * the database.
     * The <code>name</code> attribute of the <code>@JoinColumn</code> annotation is set to "code_id", specifying the name of the foreign key column in the database table.
     * </p>
     *
     * @see ManyToOne
     * @see JoinColumn
     * @see FetchType
     * @see LogicalExpressionModel
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_id")
    private LogicalExpressionModel code;

    /**
     * Set of AnfStatementModel objects representing the DIM ANF statements.
     * <p>
     * This variable represents the collection of ANF statements associated with a ParticipantModel object.
     * An ANF statement is an instance of the AnfStatementModel class, which contains various properties and relationships.
     * The statements are stored in a Set implementation to ensure uniqueness and order. The LinkedHashSet implementation is used to preserve insertion order.
     * <p>
     * Access to this variable should be done through appropriate getter and setter methods defined in the ParticipantModel class.
     * Use of the @JsonIgnore annotation indicates that this variable should be excluded from serialization and deserialization processes.
     * The @OneToMany annotation specifies a one-to-many relationship between ParticipantModel and AnfStatementModel entities, with ParticipantModel being the owning side.
     * The "mappedBy" attribute references the "subjectOfRecord" property in the AnfStatementModel class.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "subjectOfRecord")
    private Set<AnfStatementModel> dimanfstatements = new LinkedHashSet<>();


    /**
     * Creates a new ParticipantModel object.
     *
     * @param participant The Participant object used to set the values of the ParticipantModel.
     * @param anfRepo     The ANFRepo object used for database operations.
     */
    public ParticipantModel(Participant participant, ANFRepo anfRepo) {
        this.partId = UUID.fromString(participant.getId());
        this.practitioner = anfRepo.getReferenceRepository().save(new ReferenceModel(participant.getPractitionerValue(), anfRepo));
        this.code = anfRepo.getLogicalExpressionRepository().saveOrFind(new LogicalExpressionModel(participant.getCode(), anfRepo));
    }
}