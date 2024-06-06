package cdx.opencdx.adr.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "FactANFStatement")
public class FactANFStatement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Adjust if needed
    private Integer anfStatementID;

    @ManyToOne
    @JoinColumn(name = "MeasureID")
    private DimMeasure measure;

    @ManyToOne
    @JoinColumn(name = "SubjectOfRecordID")
    private DimParticipant subjectOfRecord;

    @Column(name = "SubjectOfInformation")
    private String subjectOfInformation;

    @Column(name = "Topic")
    private String topic;

    @Column(name = "Type")
    private String type;

    @ManyToOne
    @JoinColumn(name = "PerformanceCircumstanceID")
    private DimPerformanceCircumstance performanceCircumstance;

    @ManyToOne
    @JoinColumn(name = "RequestCircumstanceID")
    private DimRequestCircumstance requestCircumstance;

    @ManyToOne
    @JoinColumn(name = "NarrativeCircumstanceID")
    private DimNarrativeCircumstance narrativeCircumstance;

    @Column(name = "Created")
    private Timestamp created;

    @Column(name = "Modified")
    private Timestamp modified;

    @ManyToOne
    @JoinColumn(name = "CreatorID")
    private DimPractitioner creator;

    @ManyToOne
    @JoinColumn(name = "ModifierID")
    private DimPractitioner modifier;

    @Column(name = "Status")
    private String status;

    // Getters and Setters

    public Integer getAnfStatementID() {
        return anfStatementID;
    }

    public void setAnfStatementID(Integer anfStatementID) {
        this.anfStatementID = anfStatementID;
    }

    public DimMeasure getMeasure() {
        return measure;
    }

    public void setMeasure(DimMeasure measure) {
        this.measure = measure;
    }

    public DimParticipant getSubjectOfRecord() {
        return subjectOfRecord;
    }

    public void setSubjectOfRecord(DimParticipant subjectOfRecord) {
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

    public DimPerformanceCircumstance getPerformanceCircumstance() {
        return performanceCircumstance;
    }

    public void setPerformanceCircumstance(DimPerformanceCircumstance performanceCircumstance) {
        this.performanceCircumstance = performanceCircumstance;
    }

    public DimRequestCircumstance getRequestCircumstance() {
        return requestCircumstance;
    }

    public void setRequestCircumstance(DimRequestCircumstance requestCircumstance) {
        this.requestCircumstance = requestCircumstance;
    }

    public DimNarrativeCircumstance getNarrativeCircumstance() {
        return narrativeCircumstance;
    }

    public void setNarrativeCircumstance(DimNarrativeCircumstance narrativeCircumstance) {
        this.narrativeCircumstance = narrativeCircumstance;
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

    public DimPractitioner getCreator() {
        return creator;
    }

    public void setCreator(DimPractitioner creator) {
        this.creator = creator;
    }

    public DimPractitioner getModifier() {
        return modifier;
    }

    public void setModifier(DimPractitioner modifier) {
        this.modifier = modifier;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
