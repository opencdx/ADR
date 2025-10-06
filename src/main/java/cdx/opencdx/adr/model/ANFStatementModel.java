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
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

/**
 * ANFStatementModel class.
 */
@Getter
@Setter
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

    /**
     * Default constructor.
     */
    public ANFStatementModel() {}

    /** Constructor for ANFStatementModel.
     * @param anfStatement ANFStatement
     * @param anfRepo ANFRepo
     */
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
        this.authors = anfStatement.getAuthorsList().stream()
                .map(PractitionerModel::new)
                .map(practitioner -> anfRepo.getPractitionerRepository().save(practitioner))
                .toList();
        this.associatedStatements = anfStatement.getAssociatedStatementList().stream()
                .map(AssociatedStatementModel::new)
                .map(associatedStatement -> anfRepo.getAssociatedStatementRespository().save(associatedStatement))
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
}
