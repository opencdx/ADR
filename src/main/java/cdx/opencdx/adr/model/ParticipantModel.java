package cdx.opencdx.adr.model;

import cdx.opencdx.adr.repository.ANFRepo;
import cdx.opencdx.grpc.data.Participant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "dimparticipant")
public class ParticipantModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "part_id", nullable = false)
    private UUID partId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "practitioner_value_id")
    private ReferenceModel practitioner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_id")
    private LogicalExpressionModel code;

    public ParticipantModel(Participant participant, ANFRepo anfRepo) {
        this.partId = UUID.fromString(participant.getId());
        this.practitioner = anfRepo.getReferenceRepository().save(new ReferenceModel(participant.getPractitionerValue(),anfRepo));
        this.code = anfRepo.getLogicalExpressionRepository().saveOrFind(new LogicalExpressionModel(participant.getCode(),anfRepo));
    }
}