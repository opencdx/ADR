package cdx.opencdx.adr.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Repetition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Timestamp periodStart;
    private Integer periodDuration;
    @Enumerated(EnumType.STRING)
    private DurationType periodDurationType;
    private Integer eventFrequency;
    @Enumerated(EnumType.STRING)
    private DurationType eventFrequencyType;
    private Integer eventSeparation;
    @Enumerated(EnumType.STRING)
    private DurationType eventSeparationType;
    private Integer eventDuration;
    @Enumerated(EnumType.STRING)
    private DurationType eventDurationType;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(Timestamp periodStart) {
        this.periodStart = periodStart;
    }

    public Integer getPeriodDuration() {
        return periodDuration;
    }

    public void setPeriodDuration(Integer periodDuration) {
        this.periodDuration = periodDuration;
    }

    public DurationType getPeriodDurationType() {
        return periodDurationType;
    }

    public void setPeriodDurationType(DurationType periodDurationType) {
        this.periodDurationType = periodDurationType;
    }

    public Integer getEventFrequency() {
        return eventFrequency;
    }

    public void setEventFrequency(Integer eventFrequency) {
        this.eventFrequency = eventFrequency;
    }

    public DurationType getEventFrequencyType() {
        return eventFrequencyType;
    }

    public void setEventFrequencyType(DurationType eventFrequencyType) {
        this.eventFrequencyType = eventFrequencyType;
    }

    public Integer getEventSeparation() {
        return eventSeparation;
    }

    public void setEventSeparation(Integer eventSeparation) {
        this.eventSeparation = eventSeparation;
    }

    public DurationType getEventSeparationType() {
        return eventSeparationType;
    }

    public void setEventSeparationType(DurationType eventSeparationType) {
        this.eventSeparationType = eventSeparationType;
    }

    public Integer getEventDuration() {
        return eventDuration;
    }

    public void setEventDuration(Integer eventDuration) {
        this.eventDuration = eventDuration;
    }

    public DurationType getEventDurationType() {
        return eventDurationType;
    }

    public void setEventDurationType(DurationType eventDurationType) {
        this.eventDurationType = eventDurationType;
    }
}