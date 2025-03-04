package cdx.opencdx.adr.model;

import cdx.opencdx.adr.utils.ANFHelper;
import cdx.opencdx.grpc.data.Repetition;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The RepetitionModel class represents a repetition in the system.
 * <p>
 * It is used to store and retrieve information about a repetitive event or duration.
 * The repetition is defined by various properties, such as period start, period duration, event frequency, event separation, and event duration.
 * <p>
 * The RepetitionModel class is annotated with @Entity to specify that it is a JPA entity and @Table to specify the name of the database table.
 * It has the following properties:
 * - id: The ID of the RepetitionModel object. It is the primary key of the table and is automatically generated by the database.
 * - periodStart: The starting period of the repetition, represented by a MeasureModel object.
 * - periodDuration: The duration of the period, represented by a MeasureModel object.
 * - eventFrequency: The event frequency of the repetition, represented by a MeasureModel object.
 * - eventSeparation: The event separation for the repetition, represented by a MeasureModel object.
 * - eventDuration: The duration of an event within the repetition, represented by a MeasureModel object.
 * <p>
 * The RepetitionModel class has a constructor that takes a Repetition object and an ANFRepo object, and initializes the properties with values from the input repetition.
 * <p>
 * Example usage:
 * RepetitionModel repetition = new RepetitionModel();
 * repetition.setPeriodStart(periodStart);
 * repetition.setPeriodDuration(periodDuration);
 * repetition.setEventFrequency(eventFrequency);
 * repetition.setEventSeparation(eventSeparation);
 * repetition.setEventDuration(eventDuration);
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "dimrepetition")
public class RepetitionModel {
    /**
     * The ID of the RepetitionModel object.
     * <p>
     * This variable is used to uniquely identify a RepetitionModel object in the database.
     * It is annotated with @Id to specify that it is the primary key of the table.
     * The @GeneratedValue annotation is used to specify the strategy for generating the ID value.
     * In this case, it is set to GenerationType.IDENTITY which means that the ID value will be automatically generated by the database.
     * The @Column annotation is used to specify the details of the column in the database table.
     * Here, it is set to "id" with "nullable = false" to indicate that the ID column is required and cannot be null.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * Represents the starting period of a repetition.
     * <p>
     * The period start is a MeasureModel object that contains the information about
     * the upper and lower bounds, inclusion of bounds, unit of measurement,
     * semantic expression, and resolution of the period start.
     * <p>
     * Use the period start to specify the starting point of a repetitive event or
     * duration.
     * <p>
     * Example usage:
     * <p>
     * RepetitionModel repetition = new RepetitionModel();
     * MeasureModel periodStart = new MeasureModel();
     * periodStart.setUpperBound(10.0);
     * periodStart.setLowerBound(0.0);
     * periodStart.setIncludeUpperBound(true);
     * periodStart.setIncludeLowerBound(true);
     * periodStart.setUnit(unit);
     * periodStart.setSemantic(semantic);
     * periodStart.setResolution(1.0);
     * repetition.setPeriodStart(periodStart);
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "period_start_id")
    private MeasureModel periodStart;

    /**
     * Represents the duration of a period.
     * <p>
     * This variable is a reference to a MeasureModel object, which contains information about the duration of a period.
     * <p>
     * The MeasureModel class contains the following properties:
     * - upperBound: The upper bound of the duration
     * - lowerBound: The lower bound of the duration
     * - includeUpperBound: Whether the upper bound is inclusive or exclusive
     * - includeLowerBound: Whether the lower bound is inclusive or exclusive
     * - unit: The unit of measurement for the duration
     * - semantic: The logical expression that specifies the semantics of the duration
     * - resolution: The resolution of the duration
     * <p>
     * This variable is annotated with @ManyToOne(fetch = FetchType.LAZY), indicating that it is a many-to-one relationship
     * with the MeasureModel class. It is mapped to the "period_duration_id" column in the "dimrepetition" table.
     * <p>
     * Example usage:
     * RepetitionModel repetition = new RepetitionModel();
     * repetition.setPeriodDuration(new MeasureModel(10.0, 5.0, true, false, new UnitModel("seconds"), null, 1.0));
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "period_duration_id")
    private MeasureModel periodDuration;

    /**
     * Represents the event frequency of a repetition.
     * <p>
     * This variable is used in the "RepetitionModel" class to specify the event frequency for a repetition.
     * The "eventFrequency" variable is of type "MeasureModel".
     * <p>
     * The "MeasureModel" class is used to represent a measure, or a range of values with a unit.
     * It has the following properties:
     * - upperBound: The upper bound of the measure range.
     * - lowerBound: The lower bound of the measure range.
     * - includeUpperBound: Indicates if the upper bound is inclusive or exclusive.
     * - includeLowerBound: Indicates if the lower bound is inclusive or exclusive.
     * - unit: The unit of measurement.
     * - semantic: The logical expression representing the measure.
     * - resolution: The smallest interval between two values within the range.
     * <p>
     * The "eventFrequency" variable is annotated with "@ManyToOne(fetch = FetchType.LAZY)" and "@JoinColumn(name = "event_frequency_id")",
     * which indicates that it is a many-to-one relationship with another entity, and specifies the foreign key column name.
     * <p>
     * Example usage:
     * RepetitionModel repetition = new RepetitionModel();
     * MeasureModel measure = new MeasureModel();
     * measure.setUpperBound(10.0);
     * measure.setLowerBound(0.0);
     * measure.setIncludeUpperBound(true);
     * measure.setIncludeLowerBound(false);
     * measure.setUnit(unit);
     * measure.setSemantic(semantic);
     * measure.setResolution(0.1);
     * repetition.setEventFrequency(measure);
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_frequency_id")
    private MeasureModel eventFrequency;

    /**
     * Represents the event separation for a repetition in the system.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_separation_id")
    private MeasureModel eventSeparation;

    /**
     * Represents the duration of an event.
     *
     * <p>
     * The event duration is a measure that specifies the length of time that an event lasts. It is represented by
     * an instance of the {@link MeasureModel} class.
     * </p>
     *
     * <p>
     * The event duration is a property of the {@link RepetitionModel} class. It is associated with a specific
     * repetition and is used to determine the duration of the event that occurs within each repetition.
     * </p>
     *
     * <p>
     * The event duration is stored in the database as a foreign key column named "event_duration_id" in the
     * "dimrepetition" table. It is mapped to the {@link MeasureModel} entity using the {@link ManyToOne} annotation
     * with the fetch type set to FetchType.LAZY.
     * </p>
     *
     * <p>
     * The {@link MeasureModel} class represents a measure, which is a quantity that has both a value and a unit of
     * measurement. It provides various properties to represent the upper bound, lower bound, inclusion of bounds,
     * unit, semantic, and resolution of the measure.
     * </p>
     *
     * <p>
     * The constructor of the {@link RepetitionModel} class initializes the event duration by creating a new instance
     * of the {@link MeasureModel} class using the value from the input repetition and the {@link ANFHelper} repository.
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_duration_id")
    private MeasureModel eventDuration;

    /**
     * Represents a repetition in the system.
     */
    public RepetitionModel(Repetition repetition, ANFHelper anfRepo) {
        this.periodStart = anfRepo.getMeasureRepository().save(new MeasureModel(repetition.getPeriodStart(), anfRepo));
        this.periodDuration = anfRepo.getMeasureRepository().save(new MeasureModel(repetition.getPeriodDuration(), anfRepo));
        this.eventFrequency = anfRepo.getMeasureRepository().save(new MeasureModel(repetition.getEventFrequency(), anfRepo));
        this.eventSeparation = anfRepo.getMeasureRepository().save(new MeasureModel(repetition.getEventSeparation(), anfRepo));
        this.eventDuration = anfRepo.getMeasureRepository().save(new MeasureModel(repetition.getEventDuration(), anfRepo));
    }
}