/*
 * Copyright 2024 Safe Health Systems, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cdx.opencdx.adr.model;

import cdx.opencdx.adr.repository.ANFRepo;
import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Table(name = "dimanfstatement")
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
            name = "unionanfstatementauthors",
            joinColumns = @JoinColumn(name = "anfstatement_id"),
            inverseJoinColumns = @JoinColumn(name = "practitioner_id"))
    private List<PractitionerModel> authors;

    @ManyToMany
    @JoinTable(
            name = "unionanfstatementassociatedstatement",
            joinColumns = @JoinColumn(name = "anfstatement_id"),
            inverseJoinColumns = @JoinColumn(name = "associated_statement_id"))
    private List<AssociatedStatementModel> associatedStatements;

    @OneToOne
    @JoinTable(
            name = "unionanfstatementperformancecircumstance",
            joinColumns = @JoinColumn(name = "anfstatement_id"),
            inverseJoinColumns = @JoinColumn(name = "performance_circumstance_id"))
    private PerformanceCircumstanceModel performanceCircumstance;

    @OneToOne
    @JoinTable(
            name = "unionanfstatementrequestcircumstance",
            joinColumns = @JoinColumn(name = "anfstatement_id"),
            inverseJoinColumns = @JoinColumn(name = "request_circumstance_id"))
    private RequestCircumstanceModel requestCircumstance;

    @OneToOne
    @JoinTable(
            name = "unionanfstatementnarrativecircumstance",
            joinColumns = @JoinColumn(name = "anfstatement_id"),
            inverseJoinColumns = @JoinColumn(name = "narrative_circumstance_id"))
    private NarrativeCircumstanceModel narrativeCircumstance;

    public ANFStatementModel() {}

    public ANFStatementModel(cdx.opencdx.grpc.data.ANFStatement anfStatement, ANFRepo anfRepo) {

        if (anfStatement.hasTime()) {
            this.time = anfRepo.getMeasureRepository().save(new MeasureModel(anfStatement.getTime()));
        }
        if (anfStatement.hasSubjectOfRecord()) {
            this.subjectOfRecord =
                    anfRepo.getParticipantRepository().save(new ParticipantModel(anfStatement.getSubjectOfRecord()));
        }

        this.subjectOfInformation = anfStatement.getSubjectOfInformation();
        this.topic = anfStatement.getTopic();
        this.type = anfStatement.getType();
        this.status = Status.valueOf(anfStatement.getStatus().name());
        this.authors = anfStatement.getAuthorsList().stream()
                .map(PractitionerModel::new)
                .map(practitioner -> anfRepo.getPractitionerRepository().save(practitioner))
                .toList();
        this.associatedStatements = anfStatement.getAssociatedStatementList().stream()
                .map(AssociatedStatementModel::new)
                .toList();
        if (anfStatement.hasPerformanceCircumstance()) {
            this.performanceCircumstance = anfRepo.getPerformanceCircumstanceRepository()
                    .save(new PerformanceCircumstanceModel(anfStatement.getPerformanceCircumstance(), anfRepo));
        }
        if (anfStatement.hasRequestCircumstance()) {
            this.requestCircumstance = anfRepo.getRequestCircumstanceRepository()
                    .save(new RequestCircumstanceModel(anfStatement.getRequestCircumstance(), anfRepo));
        }
        if (anfStatement.hasNarrativeCircumstance()) {
            this.narrativeCircumstance = anfRepo.getNarrativeCircumstanceRepository()
                    .save(new NarrativeCircumstanceModel(anfStatement.getNarrativeCircumstance(), anfRepo));
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
