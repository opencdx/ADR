package cdx.opencdx.adr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Table(name = "dimtinkarconcept")
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
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
}
