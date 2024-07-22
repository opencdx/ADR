package cdx.opencdx.adr.model;

import cdx.opencdx.adr.repository.ANFRepo;
import cdx.opencdx.grpc.data.Repetition;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "dimrepetition")
public class RepetitionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dimrepetition_id_gen")
    @SequenceGenerator(name = "dimrepetition_id_gen", sequenceName = "dimrepetition_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "period_start_id")
    private MeasureModel periodStart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "period_duration_id")
    private MeasureModel periodDuration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_frequency_id")
    private MeasureModel eventFrequency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_separation_id")
    private MeasureModel eventSeparation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_duration_id")
    private MeasureModel eventDuration;

    public RepetitionModel(Repetition repetition, ANFRepo anfRepo) {
        this.periodStart = anfRepo.getMeasureRepository().save(new MeasureModel(repetition.getPeriodStart(),anfRepo));
        this.periodDuration = anfRepo.getMeasureRepository().save(new MeasureModel(repetition.getPeriodDuration(),anfRepo));
        this.eventFrequency = anfRepo.getMeasureRepository().save(new MeasureModel(repetition.getEventFrequency(),anfRepo));
        this.eventSeparation = anfRepo.getMeasureRepository().save(new MeasureModel(repetition.getEventSeparation(),anfRepo));
        this.eventDuration = anfRepo.getMeasureRepository().save(new MeasureModel(repetition.getEventDuration(),anfRepo));
    }
}