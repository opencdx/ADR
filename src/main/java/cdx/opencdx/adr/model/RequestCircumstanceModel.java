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

import java.util.List;
import java.util.stream.Collectors;

@Table(name = "factrequestcircumstance")
@Entity
public class RequestCircumstanceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "timing_id")
    private MeasureModel timing;

    @ElementCollection
    private List<String> purpose;

    @Enumerated(EnumType.STRING)
    private CircumstancePriority priority;

    @ManyToOne
    @JoinColumn(name = "requested_result_id")
    private MeasureModel requestedResult;

    @ManyToOne
    @JoinColumn(name = "repetition_id")
    private RepetitionModel repetition;

    @ManyToMany
    @JoinTable(
            name = "unionrequestCircumstanceconditionaltrigger",
            joinColumns = @JoinColumn(name = "request_circumstance_id"),
            inverseJoinColumns = @JoinColumn(name = "associated_statement_id"))
    private List<AssociatedStatementModel> conditionalTriggers;

    @ManyToMany
    @JoinTable(
            name = "unionrequestCircumstanceparticipant",
            joinColumns = @JoinColumn(name = "request_circumstance_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id"))
    private List<ParticipantModel> requestedParticipants;

    public RequestCircumstanceModel() {}

    public RequestCircumstanceModel(cdx.opencdx.grpc.data.RequestCircumstance requestCircumstance, ANFRepo anfRepo) {
        if (requestCircumstance.hasTiming()) {
            this.timing = new MeasureModel(requestCircumstance.getTiming());
        }
        this.purpose = requestCircumstance.getPurposeList();
        this.priority =
                CircumstancePriority.valueOf(requestCircumstance.getPriority().name());
        if (requestCircumstance.hasRequestedResult()) {
            this.requestedResult =
                    anfRepo.getMeasureRepository().save(new MeasureModel(requestCircumstance.getRequestedResult()));
        }
        if (requestCircumstance.hasRepetition()) {
            this.repetition =
                    anfRepo.getRepetitionRepository().save(new RepetitionModel(requestCircumstance.getRepetition()));
        }
        this.conditionalTriggers = requestCircumstance.getConditionalTriggerList().stream()
                .map(AssociatedStatementModel::new)
                .collect(Collectors.toList());
        this.requestedParticipants = requestCircumstance.getRequestedParticipantList().stream()
                .map(ParticipantModel::new)
                .map(participant -> anfRepo.getParticipantRepository().save(participant))
                .toList();
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

    public CircumstancePriority getPriority() {
        return priority;
    }

    public void setPriority(CircumstancePriority priority) {
        this.priority = priority;
    }

    public MeasureModel getRequestedResult() {
        return requestedResult;
    }

    public void setRequestedResult(MeasureModel requestedResult) {
        this.requestedResult = requestedResult;
    }

    public RepetitionModel getRepetition() {
        return repetition;
    }

    public void setRepetition(RepetitionModel repetition) {
        this.repetition = repetition;
    }

    public List<AssociatedStatementModel> getConditionalTriggers() {
        return conditionalTriggers;
    }

    public void setConditionalTriggers(List<AssociatedStatementModel> conditionalTriggers) {
        this.conditionalTriggers = conditionalTriggers;
    }

    public List<ParticipantModel> getRequestedParticipants() {
        return requestedParticipants;
    }

    public void setRequestedParticipants(List<ParticipantModel> requestedParticipants) {
        this.requestedParticipants = requestedParticipants;
    }
}
