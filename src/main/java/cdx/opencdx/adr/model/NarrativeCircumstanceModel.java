package cdx.opencdx.adr.model;

import cdx.opencdx.adr.utils.ANFHelper;
import cdx.opencdx.grpc.data.NarrativeCircumstance;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

/**
 * The NarrativeCircumstanceModel class represents a model for narrative circumstances.
 * It is an entity class annotated with "@Entity" and maps to the "factnarrativecircumstance" table in the database.
 * The class provides getters and setters for its properties and contains constructors for initializing its values.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "factnarrativecircumstance")
public class NarrativeCircumstanceModel {
    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    /**
     * The timing variable represents the timing of a narrative circumstance.
     * <p>
     * It is annotated with "@ManyToOne" and "@JoinColumn" to define the many-to-one relationship with the MeasureModel table.
     * The mapping is done through the "timing_id" column in the "factnarrativecircumstance" table.
     * The fetch type is set to "LAZY".
     * <p>
     * This variable is a private instance variable of the class NarrativeCircumstanceModel, which is an entity class annotated with "@Entity".
     * It is used to store the timing value of a narrative circumstance in the database.
     *
     * @see NarrativeCircumstanceModel
     * @see MeasureModel
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timing_id")
    private MeasureModel timing;

    /**
     * The text variable represents the text of a narrative circumstance.
     * <p>
     * It is annotated with "@Column" to specify the mapping to the database column "text" in the table "factnarrativecircumstance".
     * The length of the column is set to the maximum possible value using "Integer.MAX_VALUE".
     * <p>
     * This variable is a private instance variable of the class NarrativeCircumstanceModel, which is an entity class annotated with "@Entity".
     * It is used to store the text value of a narrative circumstance in the database.
     *
     * @see NarrativeCircumstanceModel
     */
    @Column(name = "text", length = Integer.MAX_VALUE)
    private String text;

    /**
     * This variable represents the purposes associated with a NarrativeCircumstance.
     * It is a list of LogicalExpressionModel objects.
     * <p>
     * The purposes are stored in the "unionnarrativecircumstance_purpose" table,
     * in a many-to-many relationship with the NarrativeCircumstanceModel table.
     * <p>
     * The list of purposes is accessed through getter and setter methods.
     */
    @ManyToMany
    @JoinTable(
            name = "unionnarrativecircumstance_purpose",
            joinColumns = @JoinColumn(name = "narrative_circumstance_id"),
            inverseJoinColumns = @JoinColumn(name = "purpose_id"))
    private List<TinkarConceptModel> purposes = new LinkedList<>();

    /**
     * Constructs a new NarrativeCircumstanceModel object.
     *
     * @param circumstance the NarrativeCircumstance object to be used in the construction of the model
     * @param anfRepo      the ANFRepo object used for saving data to the repository
     */
    public NarrativeCircumstanceModel(NarrativeCircumstance circumstance, ANFHelper anfRepo) {
        this.timing = anfRepo.getMeasureRepository().save(new MeasureModel(circumstance.getTiming(), anfRepo));
        this.text = circumstance.getText();
        this.purposes = circumstance.getPurposeList().stream().map(purpose -> anfRepo.getOpenCDXIKMService().getInkarConceptModel(purpose)).toList();
    }

}