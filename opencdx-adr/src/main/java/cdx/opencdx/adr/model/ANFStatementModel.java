package cdx.opencdx.adr.model;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;

@Table(name = "anfstatement")
@Entity
public class ANFStatementModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "time")
    private MeasureModel time;

    @ManyToOne
    @JoinColumn(name = "subject_of_record")
    private ParticipantModel subjectOfRecord;

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
            name = "anfstatement_authors",
            joinColumns = @JoinColumn(name = "anfstatement_id"),
            inverseJoinColumns = @JoinColumn(name = "practitioner_id")
    )
    private List<PractitionerModel> authors;

    @ManyToMany
    @JoinTable(
            name = "anfstatement_associatedstatement",
            joinColumns = @JoinColumn(name = "anfstatement_id"),
            inverseJoinColumns = @JoinColumn(name = "associated_statement_id")
    )
    private List<AssociatedStatementModel> associatedStatements;

    @OneToOne
    @JoinTable(name = "anfstatement_performancecircumstance",
            joinColumns = @JoinColumn(name = "anfstatement_id"),
            inverseJoinColumns = @JoinColumn(name = "performance_circumstance_id")
    )
    private PerformanceCircumstanceModel performanceCircumstance;

    @OneToOne
    @JoinTable(name = "anfstatement_requestcircumstance",
            joinColumns = @JoinColumn(name = "anfstatement_id"),
            inverseJoinColumns = @JoinColumn(name = "request_circumstance_id")
    )
    private RequestCircumstanceModel requestCircumstance;

    @OneToOne
    @JoinTable(name = "anfstatement_narrativecircumstance",
            joinColumns = @JoinColumn(name = "anfstatement_id"),
            inverseJoinColumns = @JoinColumn(name = "narrative_circumstance_id")
    )
    private NarrativeCircumstanceModel narrativeCircumstance;

    public ANFStatementModel() {
    }

    public ANFStatementModel(cdx.opencdx.grpc.data.ANFStatement anfStatement) {

        if(anfStatement.hasTime()) {
            this.time = new MeasureModel(anfStatement.getTime());
        }
        if(anfStatement.hasSubjectOfRecord()) {
            this.subjectOfRecord = new ParticipantModel(anfStatement.getSubjectOfRecord());
        }

        this.subjectOfInformation = anfStatement.getSubjectOfInformation();
        this.topic = anfStatement.getTopic();
        this.type = anfStatement.getType();
        this.status = Status.valueOf(anfStatement.getStatus().name());
        this.authors = anfStatement.getAuthorsList().stream().map(PractitionerModel::new).toList();
        this.associatedStatements = anfStatement.getAssociatedStatementList().stream().map(AssociatedStatementModel::new).toList();
        if(anfStatement.hasPerformanceCircumstance()) {
            this.performanceCircumstance = new PerformanceCircumstanceModel(anfStatement.getPerformanceCircumstance());
        }
        if(anfStatement.hasRequestCircumstance()) {
            this.requestCircumstance = new RequestCircumstanceModel(anfStatement.getRequestCircumstance());
        }
        if(anfStatement.hasNarrativeCircumstance()) {
            this.narrativeCircumstance = new NarrativeCircumstanceModel(anfStatement.getNarrativeCircumstance());
        }
    }


    // Getters and Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MeasureModel getTime() {
        return time;
    }

    public void setTime(MeasureModel time) {
        this.time = time;
    }

    public ParticipantModel getSubjectOfRecord() {
        return subjectOfRecord;
    }

    public void setSubjectOfRecord(ParticipantModel subjectOfRecord) {
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

    public List<PractitionerModel> getAuthors() {
        return authors;
    }

    public void setAuthors(List<PractitionerModel> authors) {
        this.authors = authors;
    }

    public List<AssociatedStatementModel> getAssociatedStatements() {
        return associatedStatements;
    }

    public void setAssociatedStatements(List<AssociatedStatementModel> associatedStatements) {
        this.associatedStatements = associatedStatements;
    }

    public PerformanceCircumstanceModel getPerformanceCircumstance() {
        return performanceCircumstance;
    }

    public void setPerformanceCircumstance(PerformanceCircumstanceModel performanceCircumstance) {
        this.performanceCircumstance = performanceCircumstance;
    }

    public RequestCircumstanceModel getRequestCircumstance() {
        return requestCircumstance;
    }

    public void setRequestCircumstance(RequestCircumstanceModel requestCircumstance) {
        this.requestCircumstance = requestCircumstance;
    }

    public NarrativeCircumstanceModel getNarrativeCircumstance() {
        return narrativeCircumstance;
    }

    public void setNarrativeCircumstance(NarrativeCircumstanceModel narrativeCircumstance) {
        this.narrativeCircumstance = narrativeCircumstance;
    }
}