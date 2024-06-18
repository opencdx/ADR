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

@Table(name = "dimpractitioner")
@Entity
public class PractitionerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String practitionerValue;
    private String code;

    public PractitionerModel() {}

    public PractitionerModel(cdx.opencdx.grpc.data.Practitioner practitioner) {
        this.practitionerValue = practitioner.getPractitionerValue();
        this.code = practitioner.getCode();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    // Getters and Setters
}
