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
@Table(name = "DimParticipant")
public class DimParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Adjust if needed
    private Integer participantID;

    @Column(name = "PractitionerValue")
    private String practitionerValue;

    @Column(name = "Code")
    private String code;

    // Getters and Setters

    public Integer getParticipantID() {
        return participantID;
    }

    public void setParticipantID(Integer participantID) {
        this.participantID = participantID;
    }

    public String getPractitionerValue() {
        return practitionerValue;
    }

    public void setPractitionerValue(String practitionerValue) {
        this.practitionerValue = practitionerValue;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
