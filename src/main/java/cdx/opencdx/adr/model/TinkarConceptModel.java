package cdx.opencdx.adr.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "dimtinkarconcept")
public class TinkarConceptModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "concept_id")
    private UUID conceptId;

    @Column(name = "concept_name", length = Integer.MAX_VALUE)
    private String conceptName;

    @Column(name = "concept_description", length = Integer.MAX_VALUE)
    private String conceptDescription;

    @ManyToMany
    @JoinTable(name = "unionanfstatement_tinkarconcept",
            joinColumns = @JoinColumn(name = "concept_id"),
            inverseJoinColumns = @JoinColumn(name = "anf_statement_id"))
    private List<AnfStatementModel> anfStatements = new LinkedList<>();

    public TinkarConceptModel(UUID conceptId, String conceptName, String conceptDescription) {
        this.conceptId = conceptId;
        this.conceptName = conceptName;
        this.conceptDescription = conceptDescription;
    }
}