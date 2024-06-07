package cdx.opencdx.adr.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class RequestCircumstance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "timing_id")
    private Measure timing;

    @ElementCollection
    private List<String> purpose;

    @Enumerated(EnumType.STRING)
    private CircumstancePriority priority;

    @ManyToOne
    @JoinColumn(name = "requested_result_id")
    private Measure requestedResult;

    @ManyToOne
    @JoinColumn(name = "repetition_id")
    private Repetition repetition;

    @ManyToMany
    @JoinTable(
            name = "RequestCircumstance_ConditionalTrigger",
            joinColumns = @JoinColumn(name = "request_circumstance_id"),
            inverseJoinColumns = @JoinColumn(name = "associated_statement_id")
    )
    private List<AssociatedStatement> conditionalTriggers;

    @ManyToMany
    @JoinTable(
            name = "RequestCircumstance_Participant",
            joinColumns = @JoinColumn(name = "request_circumstance_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id")
    )
    private List<Participant> requestedParticipants;

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

    public CircumstancePriority getPriority() {
        return priority;
    }

    public void setPriority(CircumstancePriority priority) {
        this.priority = priority;
    }

    public Measure getRequestedResult() {
        return requestedResult;
    }

    public void setRequestedResult(Measure requestedResult) {
        this.requestedResult = requestedResult;
    }

    public Repetition getRepetition() {
        return repetition;
    }

    public void setRepetition(Repetition repetition) {
        this.repetition = repetition;
    }

    public List<AssociatedStatement> getConditionalTriggers() {
        return conditionalTriggers;
    }

    public void setConditionalTriggers(List<AssociatedStatement> conditionalTriggers) {
        this.conditionalTriggers = conditionalTriggers;
    }

    public List<Participant> getRequestedParticipants() {
        return requestedParticipants;
    }

    public void setRequestedParticipants(List<Participant> requestedParticipants) {
        this.requestedParticipants = requestedParticipants;
    }
}