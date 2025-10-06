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
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * This class is a model for the narrative circumstance.
 */
@Getter
@Setter
@Table(name = "factnarrativecircumstance")
@Entity
public class NarrativeCircumstanceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "timing_id")
    private MeasureModel timing;

    @ElementCollection
    private List<String> purpose;

    private String text;

    /**
     * Default constructor
     */
    public NarrativeCircumstanceModel() {}

    /**
     * Constructor
     * @param narrativeCircumstance
     * @param anfRepo
     */
    public NarrativeCircumstanceModel(
            cdx.opencdx.grpc.data.NarrativeCircumstance narrativeCircumstance, ANFRepo anfRepo) {
        if (narrativeCircumstance.hasTiming()) {
            this.timing = anfRepo.getMeasureRepository().save(new MeasureModel(narrativeCircumstance.getTiming()));
        }
        this.purpose = narrativeCircumstance.getPurposeList();
        this.text = narrativeCircumstance.getText();
    }

}
