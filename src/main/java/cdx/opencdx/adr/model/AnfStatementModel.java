package cdx.opencdx.adr.model;

import cdx.opencdx.adr.utils.ANFHelper;
import cdx.opencdx.grpc.data.ANFStatement;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * The AnfStatementModel class represents a model that encapsulates the data of a statement in the ANF (Abstract Narrative Framework).
 * It provides methods to initialize and access various properties of the statement.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "dimanfstatement")
public class AnfStatementModel {
    /**
     * Represents the unique identifier for an AnfStatementModel object.
     * <p>
     * The id field is annotated with @Id, which signifies that it is the primary key for the corresponding database table.
     * The @GeneratedValue annotation specifies the strategy for generating the id value, in this case, GenerationType.IDENTITY.
     * The @Column annotation specifies the column name in the database table as "id" and sets the nullable constraint to false.
     *
     * @see AnfStatementModel
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Represents a unique identifier for an ANF (Annotated Narrative Framework) statement.
     * <p>
     * The "anfid" variable is of type UUID, which stands for Universally Unique Identifier. This identifier
     * is used to uniquely identify an ANF statement within the system.
     * <p>
     * This variable is annotated with the @Column annotation to specify the mapping to the corresponding
     * database column named "anfid". The "nullable" attribute is set to false, indicating that this column
     * must have a non-null value in the database table.
     * <p>
     * This variable is declared as private, meaning it can only be accessed within the same class. It is recommended
     * to use appropriate getter and setter methods to access and modify the value of this variable.
     */
    @Column(name = "anfid", nullable = false)
    private UUID anfid;

    /**
     * Variable representing a time measurement.
     * <p>
     * This variable is used in the class AnfStatementModel as a MeasureModel object.
     * It is an association with the MeasureModel class using a Many-to-One relationship.
     * The fetch type is set to LAZY, indicating that the time object should be loaded lazily,
     * only when it is accessed.
     * <p>
     * The time variable is mapped to the "time_id" column in the database table using the
     *
     * @JoinColumn annotation. This annotation specifies the name of the foreign key column
     * in the AnfStatementModel table that references the primary key of the MeasureModel table.
     * <p>
     * Note that the documentation may contain additional information about the containing class
     * and other symbols, but this information may be omitted from the final response.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_id")
    private MeasureModel time;

    /**
     * The subjectOfRecord variable represents the participant of the record.
     *
     * <p>
     * It is annotated with @ManyToOne(fetch = FetchType.LAZY) and @JoinColumn(name = "subject_of_record_id")
     * to specify that it is a many-to-one relationship with the ParticipantModel entity and to define the join column
     * name as "subject_of_record_id".
     * </p>
     *
     * <p>
     * Example usage:
     * </p>
     *
     * <pre>
     *     AnfStatementModel anfStatement = new AnfStatementModel();
     *     ParticipantModel subject = new ParticipantModel();
     *     subject.setId(1L);
     *     anfStatement.setSubjectOfRecord(subject);
     * </pre>
     *
     * @see AnfStatementModel
     * @see ParticipantModel
     * @see ManyToOne
     * @see JoinColumn
     * @see FetchType
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_of_record_id")
    private ParticipantModel subjectOfRecord;

    /**
     * The subjectOfInformation variable represents the subject of information in the AnfStatementModel class.
     * It is a reference to a LogicalExpressionModel object stored in the database.
     * The subjectOfInformation variable is annotated with @ManyToOne to indicate a many-to-one relationship between AnfStatementModel and LogicalExpressionModel.
     * The fetch attribute is set to LAZY, indicating that the LogicalExpressionModel object should be loaded lazily.
     * The joinColumn attribute is used to specify the foreign key column name in the database table "anfstatement" that references the "dimlogicalexpression" table.
     * The name attribute is set to "subject_of_information_id".
     * The subjectOfInformation variable is declared as private and of type LogicalExpressionModel.
     * <p>
     * The LogicalExpressionModel class represents a logical expression stored in the database.
     * It contains properties such as id, expression, and tinkarConcept that represent different attributes of a logical expression.
     * The LogicalExpressionModel class is annotated as an entity and is mapped to the "dimlogicalexpression" table in the database.
     * <p>
     * The value of the subjectOfInformation can be accessed through the getter and setter methods generated for this variable.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_of_information_id")
    private TinkarConceptModel subjectOfInformation;

    /**
     * The `topic` variable represents a related `LogicalExpressionModel` that is associated with the current `AnfStatementModel` entity.
     * It is annotated with the `@ManyToOne` and `@JoinColumn` annotations to define the many-to-one relationship between the entities.
     * The `ManyToOne` annotation is used to specify that the `topic` variable is related to a single `LogicalExpressionModel` object.
     * The `fetch` attribute of the `ManyToOne` annotation is set to `LAZY`, indicating that the related `LogicalExpressionModel` should be loaded lazily.
     * The `JoinColumn` annotation is used to specify the foreign key column name in the database table "anfstatementmodel" that references the "dimlogicalexpression" table.
     * The name attribute of the `JoinColumn` annotation is set to "topic_id".
     * The `topic` variable is of type `LogicalExpressionModel`, which represents a logical expression stored in the database.
     * The `LogicalExpressionModel` class is an entity class annotated with various JPA annotations, including `@Entity`, `@Table`, `@NoArgsConstructor`.
     * It contains properties such as `id`, `expression`, `tinkarConcept`, etc., which represent different attributes of a logical expression.
     * The `LogicalExpressionModel` class is used to store and retrieve logical expressions in the database.
     * <p>
     * The `topic` variable can be accessed through the getter and setter methods generated for this variable.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private TinkarConceptModel topic;

    /**
     * The type variable represents the type of a logical expression stored in the database.
     * It is annotated with the @ManyToOne and @JoinColumn annotations to define the relationship between the entities AnfStatementModel and LogicalExpressionModel.
     * The @ManyToOne annotation is used to define a many-to-one relationship between the entities, where an AnfStatementModel can have only one type of logical expression.
     * The fetch attribute of the @ManyToOne annotation is set to LAZY, indicating that the related LogicalExpressionModel should be loaded lazily.
     * The joinColumn attribute of the @JoinColumn annotation is used to specify the foreign key column name in the database table "anfstatement" that references the "dimlogicalex
     * pression" table.
     * The name attribute of the @JoinColumn annotation is set to "type_id".
     * The type variable is of type LogicalExpressionModel, which represents a logical expression stored in the database.
     * The LogicalExpressionModel class is an entity class annotated with various JPA annotations, including @Entity, @Table, @NoArgsConstructor.
     * It contains properties such as id, expression, and tinkarConcept that represent different attributes of a logical expression.
     * The LogicalExpressionModel class is used to store and retrieve logical expressions in the database.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private TinkarConceptModel type;

    /**
     * Variable representing the performance circumstance of an AnfStatementModel.
     * <p>
     * This variable is annotated with @ManyToOne, indicating a many-to-one relationship
     * with the PerformanceCircumstanceModel class. The fetch attribute is set to LAZY,
     * meaning that the associated PerformanceCircumstanceModel will be loaded on demand
     * when accessed. The join column name is "performance_circumstance_id".
     *
     * @see PerformanceCircumstanceModel
     * @see javax.persistence.ManyToOne
     * @see javax.persistence.FetchType
     * @see javax.persistence.JoinColumn
     * @since (version)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_circumstance_id")
    private PerformanceCircumstanceModel performanceCircumstance;

    /**
     * Represents the request circumstance associated with an ANF statement.
     * <p>
     * The request circumstance describes the reason or motivation behind the ANF statement. It is a
     * Many-to-One relationship to the RequestCircumstanceModel class.
     * The FetchType.LAZY strategy is used to only fetch the request circumstance when explicitly accessed.
     * The join column name "request_circumstance_id" is used to map the relationship between the ANF statement
     * and the request circumstance model.
     *
     * @see RequestCircumstanceModel
     * @since 1.0
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_circumstance_id")
    private RequestCircumstanceModel requestCircumstance;

    /**
     * The narrativeCircumstance variable represents a narrative circumstance associated with an AnfStatementModel.
     * <p>
     * It is annotated with "@ManyToOne" and "@JoinColumn" to define the many-to-one relationship with the NarrativeCircumstanceModel table.
     * The mapping is done through the "narrative_circumstance_id" column in the "AnfStatementModel" table.
     * The fetch type is set to "LAZY".
     * <p>
     * This variable is a private instance variable of the class AnfStatementModel.
     * It is used to store the narrative circumstance associated with the AnfStatementModel in the database.
     *
     * @see AnfStatementModel
     * @see NarrativeCircumstanceModel
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "narrative_circumstance_id")
    private NarrativeCircumstanceModel narrativeCircumstance;

    /**
     * The associatedStatements variable represents a list of AssociatedStatementModel objects associated with an AnfStatementModel object.
     *
     * <p>
     * It is annotated with <code>@ManyToMany</code> to specify a many-to-many relationship with the AssociatedStatementModel entity.
     * The <code>@JoinTable</code> annotation specifies the name of the join table as "unionanfstatement_associatedstatement".
     * The <code>joinColumns</code> attribute of the <code>@JoinTable</code> annotation specifies the foreign key column name in the join table that references the "anf_statement
     * _id" column in the "anfstatementmodel" table.
     * The <code>inverseJoinColumns</code> attribute of the <code>@JoinTable</code> annotation specifies the foreign key column name in the join table that references the "associated
     * _statement_id" column in the "dimassociatedstatement" table.
     * </p>
     *
     * <p>
     * The associatedStatements variable is a list of AssociatedStatementModel objects.
     * The AssociatedStatementModel class represents an associated statement in the application.
     * It is annotated with <code>@Entity</code> to specify that it is an entity class that maps to a database table named "dimassociatedstatement".
     * It contains various instance variables such as id, stateId, and semantic, which represent different attributes of an associated statement.
     * </p>
     *
     * <p>
     * Example usage:
     * <pre>{@code
     * AnfStatementModel statement = new AnfStatementModel();
     * AssociatedStatementModel associatedStatement1 = new AssociatedStatementModel();
     * AssociatedStatementModel associatedStatement2 = new AssociatedStatementModel();
     * statement.getAssociatedStatements().add(associatedStatement1);
     * statement.getAssociatedStatements().add(associatedStatement2);
     * }</pre>
     * </p>
     *
     * @see ManyToMany
     * @see JoinTable
     * @see AssociatedStatementModel
     */
    @ManyToMany
    @JoinTable(name = "unionanfstatement_associatedstatement",
            joinColumns = @JoinColumn(name = "anf_statement_id"),
            inverseJoinColumns = @JoinColumn(name = "associated_statement_id"))
    private List<AssociatedStatementModel> associatedStatements = new LinkedList<>();

    /**
     * The PractitionerModel class represents a practitioner in the application.
     * It is an entity class that maps to a database table named "dimpractitioner".
     */
    @ManyToMany
    @JoinTable(name = "unionanfstatement_authors",
            joinColumns = @JoinColumn(name = "anf_statement_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<PractitionerModel> authors = new LinkedList<>();


    /**
     * Constructs a new instance of AnfStatementModel.
     *
     * @param anfStatement the ANFStatement object to initialize the AnfStatementModel with
     * @param anfRepo      the ANFRepo object for accessing repositories
     */
    public AnfStatementModel(ANFStatement anfStatement, ANFHelper anfRepo) {
        this.anfid = UUID.fromString(anfStatement.getId());
        this.time = anfRepo.getMeasureRepository().save(new MeasureModel(anfStatement.getTime(), anfRepo));
        this.subjectOfRecord = anfRepo.getParticipantRepository().save(new ParticipantModel(anfStatement.getSubjectOfRecord(), anfRepo));
        this.subjectOfInformation = anfRepo.getOpenCDXIKMService().getInkarConceptModel(anfStatement.getSubjectOfInformation());
        this.topic = anfRepo.getOpenCDXIKMService().getInkarConceptModel(anfStatement.getTopic());
        this.type = anfRepo.getOpenCDXIKMService().getInkarConceptModel(anfStatement.getType());

        if (anfStatement.hasPerformanceCircumstance()) {
            this.performanceCircumstance = anfRepo.getPerformanceCircumstanceRepository().save(new PerformanceCircumstanceModel(anfStatement.getPerformanceCircumstance(), anfRepo));
        }
        if (anfStatement.hasRequestCircumstance()) {
            this.requestCircumstance = anfRepo.getRequestCircumstanceRepository().save(new RequestCircumstanceModel(anfStatement.getRequestCircumstance(), anfRepo));
        }
        if (anfStatement.hasNarrativeCircumstance()) {
            this.narrativeCircumstance = anfRepo.getNarrativeCircumstanceRepository().save(new NarrativeCircumstanceModel(anfStatement.getNarrativeCircumstance(), anfRepo));
        }
        this.associatedStatements = anfStatement.getAssociatedStatementList().stream()
                .map(associatedStatement -> anfRepo.getAssociatedStatementRespository().save(new AssociatedStatementModel(associatedStatement, anfRepo)))
                .toList();
        this.authors = anfStatement.getAuthorsList().stream().map(author -> anfRepo.getPractitionerRepository().save(new PractitionerModel(author, anfRepo)))
                .toList();
    }
}