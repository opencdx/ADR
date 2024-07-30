package cdx.opencdx.adr.model;

import cdx.opencdx.adr.utils.ANFHelper;
import cdx.opencdx.grpc.data.PerformanceCircumstance;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

/**
 * The PerformanceCircumstanceModel class represents a performance circumstance.
 * It stores information about the timing, status, result, health risk, normal range,
 * device IDs, participants, and purposes associated with the performance circumstance.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "factperformancecircumstance")
public class PerformanceCircumstanceModel {
    /**
     * The unique identifier for an instance of a class.
     * This variable is annotated with the @Id annotation, indicating that it represents the primary key of the corresponding database table.
     * It is generated automatically using the GenerationType.IDENTITY strategy, which relies on an auto-incrementing column in the database.
     * The column is named "id" and is not nullable, meaning it must always have a value.
     * The type of this variable is Long.
     * It is a field of the PerformanceCircumstanceModel class.
     *
     * @see PerformanceCircumstanceModel
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Represents the timing of a performance circumstance.
     * This variable is associated with the MeasureModel class and is used to specify the timing of a performance circumstance.
     * <p>
     * Usage:
     * The timing variable is a ManyToOne relationship property, which means it refers to a single instance of MeasureModel.
     * It is annotated with @ManyToOne(fetch = FetchType.LAZY) to indicate that the associated MeasureModel should be fetched lazily.
     * The association is mapped to the "timing_id" column in the database using the @JoinColumn annotation.
     * <p>
     * Example usage:
     * MeasureModel measure = new MeasureModel();
     * PerformanceCircumstanceModel circumstance = new PerformanceCircumstanceModel();
     * circumstance.setTiming(measure);
     * <p>
     * Note:
     * The MeasureModel class should be imported in order to use the timing variable.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timing_id")
    private MeasureModel timing;

    /**
     * Represents the status of a performance circumstance.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private TinkarConceptModel status;

    /**
     * Represents the result of a performance circumstance.
     * <p>
     * The MeasureModel class is associated with the dimmeasure database table.
     * It contains information about the upper bound, lower bound, inclusion of upper bound, inclusion of lower bound,
     * unit, semantic expression, and resolution of a measure.
     * <p>
     * The MeasureModel class has the following fields:
     * - id: The unique identifier of the measure.
     * - upperBound: The upper bound value of the measure.
     * - lowerBound: The lower bound value of the measure.
     * - includeUpperBound: Indicates whether the upper bound value should be included in the measure.
     * - includeLowerBound: Indicates whether the lower bound value should be included in the measure.
     * - unit: The unit associated with the measure.
     * - semantic: The logical expression representing the semantics of the measure.
     * - resolution: The resolution of the measure.
     * <p>
     * The MeasureModel class is associated with the following classes:
     * - PerformanceCircumstanceModel (as a variable named 'result')
     * - LogicalExpressionModel
     * - ParticipantModel
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "result_id")
    private MeasureModel result;

    /**
     * This variable represents the health risk associated with a performance circumstance.
     *
     * @ManyToOne(fetch = FetchType.LAZY)
     * @JoinColumn(name = "health_risk_id")
     * private LogicalExpressionModel healthRisk;
     * <p>
     * The healthRisk field is a reference to a {@link TinkarConceptModel} object. It is annotated with @ManyToOne to indicate that it represents a many-to-one relationship with
     * the parent class PerformanceCircumstanceModel.
     * The fetch type is set to LAZY, meaning that the related LogicalExpressionModel object will be loaded lazily in the application when needed.
     * The JoinColumn annotation specifies the name of the foreign key column in the database table that represents the health risk association.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "health_risk_id")
    private TinkarConceptModel healthRisk;

    /**
     * Represents the normal range of a measure.
     * The normal range is defined by an upper and lower bound, along with inclusion/exclusion flags for the bounds.
     * It also includes information about the unit of measurement and the semantic expression associated with the range.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "normal_range_id")
    private MeasureModel normalRange;

    /**
     * List of device identifiers associated with a performance circumstance.
     * This variable is a part of the PerformanceCircumstanceModel class.
     * <p>
     * The deviceIds variable is annotated with @ElementCollection to indicate that it is a collection of elements.
     * It is also annotated with @CollectionTable to specify the name of the table that will store the collection.
     * The "name" attribute is set to "performanceCircumstance_DeviceId" and the "joinColumns" attribute is set to
     * "performance_circumstance_id" which is the foreign key column that links to the PerformanceCircumstanceModel table.
     * The "Column" annotation is used to specify the name of the column in the table that will store the deviceIds.
     * <p>
     * The deviceIds variable is a list of strings that represents the unique identifiers of the devices associated with
     * a performance circumstance.
     * <p>
     * Example usage:
     * PerformanceCircumstanceModel performanceCircumstance = new PerformanceCircumstanceModel();
     * List<String> deviceIds = performanceCircumstance.getDeviceIds();
     * deviceIds.add("device1");
     * deviceIds.add("device2");
     * <p>
     * Note: The above example code is for illustration purposes only and may not represent the actual usage of the variable.
     */
    @ElementCollection  // For the list of deviceIds
    @CollectionTable(
            name = "performanceCircumstance_DeviceId",
            joinColumns = @JoinColumn(name = "performance_circumstance_id")
    )
    @Column(name = "deviceId")
    private List<String> deviceIds;

    /**
     * The participants variable represents a list of ParticipantModel objects.
     * Each ParticipantModel object represents a participant in a performance circumstance.
     * <p>
     * The participants are stored in a Many-to-Many relationship table called "unionperformancecircumstance_participant".
     * The join columns are "performance_circumstance_id" and "participant_id".
     * <p>
     * The ParticipantModel class defines the properties and behavior of a participant:
     * - id: The unique identifier of the participant (Long)
     * - partId: The unique identifier of the participant (UUID)
     * - practitioner: The reference to the practitioner associated with the participant (ReferenceModel)
     * - code: The logical expression representing the participant (LogicalExpressionModel)
     * <p>
     * The ParticipantModel class also has a constructor that takes a Participant object and ANFRepo object as parameters.
     * It initializes the properties of the ParticipantModel object using the values from the Participant object.
     * <p>
     * Participants are linked to the PerformanceCircumstanceModel class through the participants variable.
     * PerformanceCircumstanceModel is a container class that represents a performance circumstance.
     * It has various properties including id, timing, status, result, healthRisk, normalRange, deviceIds, and purposes.
     * <p>
     * Note: This documentation does not show example code or author and version information.
     */
    @ManyToMany
    @JoinTable(
            name = "unionperformancecircumstance_participant",
            joinColumns = @JoinColumn(name = "performance_circumstance_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id"))
    private List<ParticipantModel> participants = new LinkedList<>();

    /**
     * This variable represents the list of purposes associated with a performance circumstance.
     * The purposes are stored in a many-to-many relationship with the PerformanceCircumstanceModel class.
     * Each purpose is represented by a LogicalExpressionModel object.
     * <p>
     * The purposes are stored in a join table named "unionperformancecircumstance_purpose",
     * which has two foreign key columns - "performance_circumstance_id" and "purpose_id".
     * The "performance_circumstance_id" column references the id column of the PerformanceCircumstanceModel table,
     * while the "purpose_id" column references the id column of the LogicalExpressionModel table.
     * <p>
     * The purposes variable is annotated with @ManyToMany to indicate the many-to-many relationship.
     * The @JoinTable annotation specifies the name of the join table and the foreign key columns.
     * The joinColumns attribute specifies the join columns for the PerformanceCircumstanceModel class,
     * while the inverseJoinColumns attribute specifies the join columns for the LogicalExpressionModel class.
     * <p>
     * The purposes variable is a list of LogicalExpressionModel objects.
     * The LogicalExpressionModel class represents a logical expression and is annotated with @Entity.
     * It has an id field annotated with @Id and @GeneratedValue to generate a unique identifier.
     * The expression field stores the logical expression as a string.
     * The tinkarConcept field is a foreign key referencing the TinkarConceptModel table,
     * and it is annotated with @ManyToOne and @JoinColumn.
     * <p>
     * Example usage:
     * PerformanceCircumstanceModel performanceCircumstance = new PerformanceCircumstanceModel();
     * List<LogicalExpressionModel> purposes = performanceCircumstance.getPurposes();
     * purposes.add(new LogicalExpressionModel("purpose 1"));
     * purposes.add(new LogicalExpressionModel("purpose 2"));
     */
    @ManyToMany
    @JoinTable(
            name = "unionperformancecircumstance_purpose",
            joinColumns = @JoinColumn(name = "performance_circumstance_id"),
            inverseJoinColumns = @JoinColumn(name = "purpose_id"))
    private List<TinkarConceptModel> purposes = new LinkedList<>();


    /**
     * Constructs a PerformanceCircumstanceModel object with the provided circumstances and ANFRepo instance.
     *
     * @param circumstance The PerformanceCircumstance object containing the circumstances for the model.
     * @param anfRepo      The ANFRepo instance used to interact with the repository.
     */
    public PerformanceCircumstanceModel(PerformanceCircumstance circumstance, ANFHelper anfRepo) {
        this.timing = anfRepo.getMeasureRepository().save(new MeasureModel(circumstance.getTiming(), anfRepo));
        this.result = anfRepo.getMeasureRepository().save(new MeasureModel(circumstance.getResult(), anfRepo));
        if (circumstance.hasNormalRange()) {
            this.normalRange = anfRepo.getMeasureRepository().save(new MeasureModel(circumstance.getNormalRange(), anfRepo));
        }
        this.status = anfRepo.getOpenCDXIKMService().getInkarConceptModel(circumstance.getStatus());
        if (circumstance.hasHealthRisk()) {
            this.healthRisk = anfRepo.getOpenCDXIKMService().getInkarConceptModel(circumstance.getHealthRisk());
        }
        this.participants = circumstance.getParticipantList().stream().map(participant -> anfRepo.getParticipantRepository().save(new ParticipantModel(participant, anfRepo))).toList();
        this.purposes = circumstance.getPurposeList().stream().map(purpose -> anfRepo.getOpenCDXIKMService().getInkarConceptModel(purpose)).toList();

        this.deviceIds = circumstance.getDeviceIdList();
    }
}