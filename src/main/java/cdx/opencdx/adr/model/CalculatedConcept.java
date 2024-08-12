package cdx.opencdx.adr.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "dimcalculatedconcept")
public class CalculatedConcept {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "concept_name", length = Integer.MAX_VALUE)
    private String conceptName;

    @Column(name = "participant_id")
    private UUID participantId;

    @Column(name = "thread_name", length = Integer.MAX_VALUE)
    private String threadName;

    @Column(name = "value")
    private Double value;

}