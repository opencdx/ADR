package cdx.opencdx.adr.model;

import cdx.opencdx.adr.repository.ANFRepo;
import cdx.opencdx.grpc.data.AssociatedStatement;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "dimassociatedstatement")
public class AssociatedStatementModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private ReferenceModel stateId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semantic_id")
    private LogicalExpressionModel semantic;

    public AssociatedStatementModel(AssociatedStatement associatedStatement, ANFRepo anfRepo) {
        this.stateId = anfRepo.getReferenceRepository().save(new ReferenceModel(associatedStatement.getId(),anfRepo));
        this.semantic = anfRepo.getLogicalExpressionRepository().saveOrFind(new LogicalExpressionModel(associatedStatement.getSemantic(),anfRepo));
    }
}