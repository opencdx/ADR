package cdx.opencdx.adr.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;

@Entity
public class ANFStatement {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "time_id")
    private Measure time;

    @ManyToOne
    @JoinColumn(name = "subject_of_record_id")
    private Participant subjectOfRecord;

    private String subjectOfInformation;

    private String topic;

    private String type;

    private Timestamp created;

    private Timestamp modified;

    private String creator;

    private String modifier;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToMany
    @JoinTable(
            name = "ANFStatement_Authors",
            joinColumns = @JoinColumn(name = "anfstatement_id"),
            inverseJoinColumns = @JoinColumn(name = "practitioner_id")
    )
    private List<Practitioner> authors;

    @ManyToMany
    @JoinTable(
            name = "ANFStatement_AssociatedStatement",
            joinColumns = @JoinColumn(name = "anfstatement_id"),
            inverseJoinColumns = @JoinColumn(name = "associated_statement_id")
    )
    private List<AssociatedStatement> associatedStatements;

    @OneToOne
    @JoinColumn(name = "performance_circumstance_id")
    private PerformanceCircumstance performanceCircumstance;

    @OneToOne
    @JoinColumn(name = "request_circumstance_id")
    private RequestCircumstance requestCircumstance;

    @OneToOne
    @JoinColumn(name = "narrative_circumstance_id")
    private NarrativeCircumstance narrativeCircumstance;

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Measure getTime() {
        return time;
    }

    public void setTime(Measure time) {
        this.time = time;
    }

    public Participant getSubjectOfRecord() {
        return subjectOfRecord;
    }

    public void setSubjectOfRecord(Participant subjectOfRecord) {
        this.subjectOfRecord = subjectOfRecord;
    }

    public String getSubjectOfInformation() {
        return subjectOfInformation;
    }

    public void setSubjectOfInformation(String subjectOfInformation) {
        this.subjectOfInformation = subjectOfInformation;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public Timestamp getModified() {
        return modified;
    }

    public void setModified(Timestamp modified) {
        this.modified = modified;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<Practitioner> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Practitioner> authors) {
        this.authors = authors;
    }

    public List<AssociatedStatement> getAssociatedStatements() {
        return associatedStatements;
    }

    public void setAssociatedStatements(List<AssociatedStatement> associatedStatements) {
        this.associatedStatements = associatedStatements;
    }

    public PerformanceCircumstance getPerformanceCircumstance() {
        return performanceCircumstance;
    }

    public void setPerformanceCircumstance(PerformanceCircumstance performanceCircumstance) {
        this.performanceCircumstance = performanceCircumstance;
    }

    public RequestCircumstance getRequestCircumstance() {
        return requestCircumstance;
    }

    public void setRequestCircumstance(RequestCircumstance requestCircumstance) {
        this.requestCircumstance = requestCircumstance;
    }

    public NarrativeCircumstance getNarrativeCircumstance() {
        return narrativeCircumstance;
    }

    public void setNarrativeCircumstance(NarrativeCircumstance narrativeCircumstance) {
        this.narrativeCircumstance = narrativeCircumstance;
    }
}