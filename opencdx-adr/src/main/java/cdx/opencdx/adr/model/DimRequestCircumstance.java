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

import jakarta.persistence.*;

@Entity
@Table(name = "DimRequestCircumstance")
public class DimRequestCircumstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Adjust if needed
    private Integer requestCircumstanceID;

    @ManyToOne
    @JoinColumn(name = "TimingMeasureID")
    private DimMeasure timingMeasure;

    @Column(name = "Purpose")
    private String purpose;

    @Column(name = "ConditionalTriggerID")
    private Integer conditionalTriggerID;

    @ManyToOne
    @JoinColumn(name = "RequestedParticipantID")
    private DimParticipant requestedParticipant;

    @Column(name = "Priority")
    private String priority;

    @ManyToOne
    @JoinColumn(name = "RequestedResultMeasureID")
    private DimMeasure requestedResultMeasure;

    @Column(name = "RepetitionID")
    private Integer repetitionID;

    // Getters and Setters

    public Integer getRequestCircumstanceID() {
        return requestCircumstanceID;
    }

    public void setRequestCircumstanceID(Integer requestCircumstanceID) {
        this.requestCircumstanceID = requestCircumstanceID;
    }

    public DimMeasure getTimingMeasure() {
        return timingMeasure;
    }

    public void setTimingMeasure(DimMeasure timingMeasure) {
        this.timingMeasure = timingMeasure;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Integer getConditionalTriggerID() {
        return conditionalTriggerID;
    }

    public void setConditionalTriggerID(Integer conditionalTriggerID) {
        this.conditionalTriggerID = conditionalTriggerID;
    }

    public DimParticipant getRequestedParticipant() {
        return requestedParticipant;
    }

    public void setRequestedParticipant(DimParticipant requestedParticipant) {
        this.requestedParticipant = requestedParticipant;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public DimMeasure getRequestedResultMeasure() {
        return requestedResultMeasure;
    }

    public void setRequestedResultMeasure(DimMeasure requestedResultMeasure) {
        this.requestedResultMeasure = requestedResultMeasure;
    }

    public Integer getRepetitionID() {
        return repetitionID;
    }

    public void setRepetitionID(Integer repetitionID) {
        this.repetitionID = repetitionID;
    }
}
