package cdx.opencdx.adr.model;

import cdx.opencdx.adr.repository.ANFRepo;
import cdx.opencdx.grpc.data.Measure;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "dimmeasure")
public class MeasureModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dimmeasure_id_gen")
    @SequenceGenerator(name = "dimmeasure_id_gen", sequenceName = "dimmeasure_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "upper_bound")
    private Double upperBound;

    @Column(name = "lower_bound")
    private Double lowerBound;

    @Column(name = "include_upper_bound")
    private Boolean includeUpperBound;

    @Column(name = "include_lower_bound")
    private Boolean includeLowerBound;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id")
    private TinkarConceptModel unit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "semantic_id")
    private LogicalExpressionModel semantic;

    @Column(name = "resolution")
    private Double resolution;

    public MeasureModel(Measure measure, ANFRepo anfRepo) {
        this.upperBound = measure.getUpperBound();
        this.lowerBound = measure.getLowerBound();
        this.includeLowerBound = measure.getIncludeLowerBound();
        this.includeUpperBound = measure.getIncludeUpperBound();
        this.semantic = anfRepo.getLogicalExpressionRepository().save(new LogicalExpressionModel(measure.getSemantic(),anfRepo));
        this.resolution = measure.getResolution();
    }
}