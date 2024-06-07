package cdx.opencdx.adr.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class PerformanceCircumstance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "timing_id")
    private Measure timing;

    @ElementCollection
    private List<String> purpose;

    private String status;

    @ManyToOne
    @JoinColumn(name = "result_id")
    private Measure result;

    private String healthRisk;

    @ManyToOne
    @JoinColumn(name = "normal_range_id")
    private Measure normalRange;

    @ManyToMany
    @JoinTable(
            name = "PerformanceCircumstance_Participant",
            joinColumns = @JoinColumn(name = "performance_circumstance_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id")
    )
    private List<Participant> participants;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Measure getTiming() {
        return timing;
    }

    public void setTiming(Measure timing) {
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

    public Measure getResult() {
        return result;
    }

    public void setResult(Measure result) {
        this.result = result;
    }

    public String getHealthRisk() {
        return healthRisk;
    }

    public void setHealthRisk(String healthRisk) {
        this.healthRisk = healthRisk;
    }

    public Measure getNormalRange() {
        return normalRange;
    }

    public void setNormalRange(Measure normalRange) {
        this.normalRange = normalRange;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }
}