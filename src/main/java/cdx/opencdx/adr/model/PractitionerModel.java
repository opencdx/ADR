package cdx.opencdx.adr.model;

import cdx.opencdx.adr.repository.ANFRepo;
import cdx.opencdx.grpc.data.Participant;
import cdx.opencdx.grpc.data.Practitioner;
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
@Table(name = "dimpractitioner")
public class PractitionerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "pract_id", nullable = false)
    private UUID practId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "practitioner_value_id")
    private ReferenceModel practitioner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_id")
    private LogicalExpressionModel code;

    public PractitionerModel(Practitioner practitioner, ANFRepo anfRepo) {
        this.practId = UUID.fromString(practitioner.getId());
        this.practitioner = anfRepo.getReferenceRepository().save(new ReferenceModel(practitioner.getPractitionerValue(),anfRepo));
        this.code = anfRepo.getLogicalExpressionRepository().save(new LogicalExpressionModel(practitioner.getCode(),anfRepo));
    }
}