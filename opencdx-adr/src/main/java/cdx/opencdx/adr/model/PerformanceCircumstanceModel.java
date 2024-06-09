package cdx.opencdx.adr.model;

import cdx.opencdx.adr.repository.ANFRepo;
import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Table(name = "performancecircumstance")
@Entity
public class PerformanceCircumstanceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "timing")
    private MeasureModel timing;

    @ElementCollection
    private List<String> purpose;

    private String status;

    @ManyToOne
    @JoinColumn(name = "result")
    private MeasureModel result;

    private String healthRisk;

    @ManyToOne
    @JoinColumn(name = "normal_range")
    private MeasureModel normalRange;

    @ManyToMany
    @JoinTable(
            name = "performancecircumstance_participant",
            joinColumns = @JoinColumn(name = "performance_circumstance_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id")
    )
    private List<ParticipantModel> participants;

    public PerformanceCircumstanceModel() {
    }

    public PerformanceCircumstanceModel(cdx.opencdx.grpc.data.PerformanceCircumstance performanceCircumstance, ANFRepo anfRepo) {
        if(performanceCircumstance.hasTiming()) {
            this.timing = anfRepo.getMeasureRepository().save(new MeasureModel(performanceCircumstance.getTiming()));
        }

        this.purpose = performanceCircumstance.getPurposeList();
        this.status = performanceCircumstance.getStatus();
        if(performanceCircumstance.hasResult()) {
            this.result = anfRepo.getMeasureRepository().save(new MeasureModel(performanceCircumstance.getResult()));
        }
        this.healthRisk = performanceCircumstance.getHealthRisk();

        if(performanceCircumstance.hasNormalRange()) {
            this.normalRange = anfRepo.getMeasureRepository().save(new MeasureModel(performanceCircumstance.getNormalRange()));
        }
        this.participants = performanceCircumstance.getParticipantList().stream().map(ParticipantModel::new).map(participant -> anfRepo.getParticipantRepository().save(participant)).toList();
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MeasureModel getTiming() {
        return timing;
    }

    public void setTiming(MeasureModel timing) {
        this.timing = timing;
    }

    public List<String> getPurpose() {
        return purpose;
    }

    public void setPurpose(List<String> purpose) {
        this.purpose = purpose;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public MeasureModel getResult() {
        return result;
    }

    public void setResult(MeasureModel result) {
        this.result = result;
    }

    public String getHealthRisk() {
        return healthRisk;
    }

    public void setHealthRisk(String healthRisk) {
        this.healthRisk = healthRisk;
    }

    public MeasureModel getNormalRange() {
        return normalRange;
    }

    public void setNormalRange(MeasureModel normalRange) {
        this.normalRange = normalRange;
    }

    public List<ParticipantModel> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ParticipantModel> participants) {
        this.participants = participants;
    }
}