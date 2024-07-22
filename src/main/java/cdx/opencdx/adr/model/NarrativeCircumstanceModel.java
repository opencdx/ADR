package cdx.opencdx.adr.model;

import cdx.opencdx.adr.repository.ANFRepo;
import cdx.opencdx.grpc.data.NarrativeCircumstance;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "factnarrativecircumstance")
public class NarrativeCircumstanceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timing_id")
    private MeasureModel timing;

    @Column(name = "text", length = Integer.MAX_VALUE)
    private String text;

    @ManyToMany
    @JoinTable(
            name = "unionnarrativecircumstance_purpose",
            joinColumns = @JoinColumn(name = "narrative_circumstance_id"),
            inverseJoinColumns = @JoinColumn(name = "purpose_id"))
    private List<LogicalExpressionModel> purposes = new LinkedList<>();

    public NarrativeCircumstanceModel(NarrativeCircumstance circumstance, ANFRepo anfRepo) {
        this.timing = anfRepo.getMeasureRepository().save(new MeasureModel(circumstance.getTiming(),anfRepo));
        this.text = circumstance.getText();
        this.purposes = circumstance.getPurposeList().stream().map(purpose -> anfRepo.getLogicalExpressionRepository().saveOrFind(new LogicalExpressionModel(purpose,anfRepo))).toList();
    }

}