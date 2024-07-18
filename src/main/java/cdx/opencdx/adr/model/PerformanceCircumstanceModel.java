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
import cdx.opencdx.grpc.data.LogicalExpression;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
/**
 * This class is a model for the performance circumstance.
 */

@Getter
@Setter
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

    /**
     * Default constructor.
     */
    public PerformanceCircumstanceModel() {}

    /** Constructor for the performance circumstance model.
     * @param performanceCircumstance The performance circumstance.
     * @param anfRepo The ANF repository.
     */
    public PerformanceCircumstanceModel(
            cdx.opencdx.grpc.data.PerformanceCircumstance performanceCircumstance, ANFRepo anfRepo) {
        if (performanceCircumstance.hasTiming()) {
            this.timing = anfRepo.getMeasureRepository().save(new MeasureModel(performanceCircumstance.getTiming()));
        }

        this.purpose = performanceCircumstance.getPurposeList().stream().map(LogicalExpression::getExpression).toList();
        this.status = performanceCircumstance.getStatus().getExpression();
        if (performanceCircumstance.hasResult()) {
            this.result = anfRepo.getMeasureRepository().save(new MeasureModel(performanceCircumstance.getResult()));
        }
        this.healthRisk = performanceCircumstance.getHealthRisk().getExpression();

        if (performanceCircumstance.hasNormalRange()) {
            this.normalRange =
                    anfRepo.getMeasureRepository().save(new MeasureModel(performanceCircumstance.getNormalRange()));
        }
        this.participants = performanceCircumstance.getParticipantList().stream()
                .map(ParticipantModel::new)
                .map(participant -> anfRepo.getParticipantRepository().save(participant))
                .toList();
    }
}
