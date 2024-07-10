package cdx.opencdx.adr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.UUID;

@Table(name = "dimtinkarconcept")
@Entity
@Getter
@Setter
@ToString
public class TinkarConceptModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private UUID conceptId;

    @JsonIgnore
    private UUID parentConceptId;

    private String description;
    private long count = 0;

    @Transient
    private List<TinkarConceptModel> children;


    public TinkarConceptModel(UUID conceptId, UUID parentConceptId, String description) {
        this.id = null;
        this.conceptId = conceptId;
        this.parentConceptId = parentConceptId;
        this.description = description;
        this.count = 0;
    }

    public TinkarConceptModel() {

    }
}
