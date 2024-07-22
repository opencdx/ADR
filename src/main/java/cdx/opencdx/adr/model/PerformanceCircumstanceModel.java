package cdx.opencdx.adr.model;

import cdx.opencdx.adr.repository.ANFRepo;
import cdx.opencdx.grpc.data.PerformanceCircumstance;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "factperformancecircumstance")
public class PerformanceCircumstanceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timing_id")
    private MeasureModel timing;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private LogicalExpressionModel status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "result_id")
    private MeasureModel result;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "health_risk_id")
    private LogicalExpressionModel healthRisk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "normal_range_id")
    private MeasureModel normalRange;

    @ElementCollection  // For the list of deviceIds
    @CollectionTable(
            name = "performanceCircumstance_DeviceId",
            joinColumns = @JoinColumn(name = "performance_circumstance_id")
    )
    @Column(name = "deviceId")
    private List<String> deviceIds;

    @ManyToMany
    @JoinTable(
            name = "unionperformancecircumstance_participant",
            joinColumns = @JoinColumn(name = "performance_circumstance_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id"))
    private List<ParticipantModel> participants = new LinkedList<>();

    @ManyToMany
    @JoinTable(
            name = "unionperformancecircumstance_purpose",
            joinColumns = @JoinColumn(name = "performance_circumstance_id"),
            inverseJoinColumns = @JoinColumn(name = "purpose_id"))
    private List<LogicalExpressionModel> purposes = new LinkedList<>();


    public PerformanceCircumstanceModel(PerformanceCircumstance circumstance, ANFRepo anfRepo) {
        this.timing = anfRepo.getMeasureRepository().save(new MeasureModel(circumstance.getTiming(),anfRepo));
        this.result = anfRepo.getMeasureRepository().save(new MeasureModel(circumstance.getResult(),anfRepo));
        if(circumstance.hasNormalRange()) {
            this.normalRange = anfRepo.getMeasureRepository().save(new MeasureModel(circumstance.getNormalRange(), anfRepo));
        }
        this.status = anfRepo.getLogicalExpressionRepository().saveOrFind(new LogicalExpressionModel(circumstance.getStatus(),anfRepo));
        if(circumstance.hasHealthRisk()) {
            this.healthRisk = anfRepo.getLogicalExpressionRepository().saveOrFind(new LogicalExpressionModel(circumstance.getHealthRisk(), anfRepo));
        }
        this.participants = circumstance.getParticipantList().stream().map(participant -> anfRepo.getParticipantRepository().save(new ParticipantModel(participant,anfRepo))).toList();
        this.purposes = circumstance.getPurposeList().stream().map(purpose -> anfRepo.getLogicalExpressionRepository().saveOrFind(new LogicalExpressionModel(purpose,anfRepo))).toList();

        this.deviceIds = circumstance.getDeviceIdList();
    }
}