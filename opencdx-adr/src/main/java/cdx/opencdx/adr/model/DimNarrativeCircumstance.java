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
@Table(name = "DimNarrativeCircumstance")
public class DimNarrativeCircumstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Adjust if needed
    private Integer narrativeCircumstanceID;

    @ManyToOne
    @JoinColumn(name = "TimingMeasureID")
    private DimMeasure timingMeasure;

    @Column(name = "Purpose")
    private String purpose;

    @Column(name = "Text")
    private String text;

    // Getters and Setters (omitted for brevity)

    public Integer getNarrativeCircumstanceID() {
        return narrativeCircumstanceID;
    }

    public void setNarrativeCircumstanceID(Integer narrativeCircumstanceID) {
        this.narrativeCircumstanceID = narrativeCircumstanceID;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
