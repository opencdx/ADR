package cdx.opencdx.adr.model;

import jakarta.persistence.*;
import lombok.*;

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
    private UUID parentConceptId;
    private String description;
    private long count = 0;
}
