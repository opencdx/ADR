package cdx.opencdx.adr.model;

import cdx.opencdx.adr.dto.ConceptFocus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/**
 * The TinkarConceptModel class represents a Tinkar concept stored in the database.
 * It contains various properties of a concept including conceptId, conceptName, conceptDescription, and anfStatements.
 * This class is annotated as an entity and is mapped to the "dimtinkarconcept" table in the database.
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "dimtinkarconcept")
public class TinkarConceptModel {
    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    @Schema(description = "The ID of the concept")
    private Long id;

    /**
     * The concept ID associated with an instance of {@link TinkarConceptModel}.
     * It is a unique identifier for a concept.
     * <p>
     * The value is stored in the {@link Column} named "concept_id" in the database table "dimtinkarconcept".
     */
    @Schema(description = "The UUID of the concept")
    @Column(name = "concept_id")
    private UUID conceptId;

    /**
     * Boolean indicating if the concept has been synchronized with IKM
     */
    @Schema(description = "Boolean indicating if the concept has been synchronized with IKM")
    @Column(name = "sync")
    private boolean sync;


    /**
     *
     */
    @Schema(description = "The name of the concept")
    @Column(name = "concept_name", length = Integer.MAX_VALUE)
    private String conceptName;

    /**
     * This variable represents the concept description of a TinkarConceptModel.
     * <p>
     * The concept description is a string that provides additional information or details about the concept.
     * <p>
     * This variable is annotated with the @Column annotation to specify the mapping to the corresponding column in the database table.
     * The name attribute of the @Column annotation specifies the name of the database column as "concept_description".
     * The length attribute of the @Column annotation is set to Integer.MAX_VALUE to indicate that there is no maximum length limit for the concept description.
     * <p>
     * This variable is a private member variable of the TinkarConceptModel class, which is an entity class representing a Tinkar concept.
     * The TinkarConceptModel class is annotated with various JPA annotations like @Entity, @Table, @NoArgsConstructor.
     * It also contains other member variables like conceptId, conceptName, anfStatements, etc. for representing different properties of a Tinkar concept.
     * <p>
     * The TinkarConceptModel class is used to store and retrieve Tinkar concepts in the database.
     * <p>
     * The concept description can be accessed through the getter and setter methods generated for this variable.
     */
    @Schema(description = "The description of the concept")
    @Column(name = "concept_description", length = Integer.MAX_VALUE)
    private String conceptDescription;

    /**
     * Represents a list of ANF (All Normal Form) statements associated with a Tinkar concept.
     * The ANF statements are stored in a database table "unionanfstatement_tinkarconcept" with a many-to-many relationship.
     * Each element in the list is an instance of {@link AnfStatementModel}.
     * <p>
     * This variable is a private member variable of the {@link TinkarConceptModel} class, which is an entity class representing a Tinkar concept.
     * The TinkarConceptModel class is annotated with various JPA annotations like @Entity, @Table, @NoArgsConstructor.
     * It also contains other member variables like conceptId, conceptName, conceptDescription, etc. for representing different properties of a Tinkar concept.
     * <p>
     * The TinkarConceptModel class is used to store and retrieve Tinkar concepts in the database.
     */
    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "unionanfstatement_tinkarconcept",
            joinColumns = @JoinColumn(name = "concept_id"),
            inverseJoinColumns = @JoinColumn(name = "anf_statement_id"))
    private List<AnfStatementModel> anfStatements = new LinkedList<>();


    /**
     * Transient field representing the concept focus mode.
     * The concept focus mode determines the scope or focus of operations or queries performed on a concept.
     * It is an enumerated type with various possible values defined in the {@link ConceptFocus} enum.
     * The concept focus can be used to perform operations or access information related to different aspects of a concept.
     * <p>
     * Example usage:
     * <pre>
     *     ConceptFocus focus = ConceptFocus.DESCENDANTS;
     * </pre>
     *
     * @see ConceptFocus
     */
    @Transient
    @Schema(description = "The focus of the concept")
    private ConceptFocus focus = ConceptFocus.SELF;

    /**
     * Represents a Tinkar concept model.
     *
     * @param conceptId          The UUID of the concept.
     * @param conceptName        The name of the concept.
     * @param conceptDescription The description of the concept.
     */
    public TinkarConceptModel(UUID conceptId, String conceptName, String conceptDescription, Boolean sync) {
        this.conceptId = conceptId;
        this.conceptName = conceptName;
        this.conceptDescription = conceptDescription;
        this.sync = sync;
    }
}