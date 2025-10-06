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
 * This class is a model for the measure.
 */
@Getter
@Setter
@Table(name = "dimmeasure")
@Entity
public class MeasureModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String upperBound;
    private String lowerBound;
    private Boolean includeUpperBound;
    private Boolean includeLowerBound;
    private String semantic;
    private String resolution;

    /**
     * Default constructor.
     */
    public MeasureModel() {}

    /**
     * Constructor for the measure model.
     * @param measure The measure.
     */
    public MeasureModel(cdx.opencdx.grpc.data.Measure measure) {
        this.upperBound = measure.getUpperBound();
        this.lowerBound = measure.getLowerBound();
        this.includeUpperBound = measure.getIncludeUpperBound();
        this.includeLowerBound = measure.getIncludeLowerBound();
        this.semantic = measure.getSemantic();
        this.resolution = measure.getResolution();
    }
}
