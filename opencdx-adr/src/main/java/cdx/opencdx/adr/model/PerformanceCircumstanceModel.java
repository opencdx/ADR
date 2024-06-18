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

@Table(name = "factperformancecircumstance")
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
            name = "unionperformancecircumstanceparticipant",
            joinColumns = @JoinColumn(name = "performance_circumstance_id"),
            inverseJoinColumns = @JoinColumn(name = "participant_id"))
    private List<ParticipantModel> participants;

    public PerformanceCircumstanceModel() {}

    public PerformanceCircumstanceModel(
            cdx.opencdx.grpc.data.PerformanceCircumstance performanceCircumstance, ANFRepo anfRepo) {
        if (performanceCircumstance.hasTiming()) {
            this.timing = anfRepo.getMeasureRepository().save(new MeasureModel(performanceCircumstance.getTiming()));
        }

        this.purpose = performanceCircumstance.getPurposeList();
        this.status = performanceCircumstance.getStatus();
        if (performanceCircumstance.hasResult()) {
            this.result = anfRepo.getMeasureRepository().save(new MeasureModel(performanceCircumstance.getResult()));
        }
        this.healthRisk = performanceCircumstance.getHealthRisk();

        if (performanceCircumstance.hasNormalRange()) {
            this.normalRange =
                    anfRepo.getMeasureRepository().save(new MeasureModel(performanceCircumstance.getNormalRange()));
        }
        this.participants = performanceCircumstance.getParticipantList().stream()
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
