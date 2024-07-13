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
import lombok.Getter;
import lombok.Setter;

/**
 * This class is a model for the participant.
 */
@Getter
@Setter
@Table(name = "dimparticipant")
@Entity
public class ParticipantModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String practitionerValue;
    private String code;

    /**
     * Default constructor.
     */
    public ParticipantModel() {}

    /**
     * Constructor for the participant model.
     * @param participant The participant.
     */
    public ParticipantModel(cdx.opencdx.grpc.data.Participant participant) {
        this.practitionerValue = participant.getPractitionerValue();
        this.code = participant.getCode();
    }
}
