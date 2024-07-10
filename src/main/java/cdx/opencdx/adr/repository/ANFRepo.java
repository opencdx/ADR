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
package cdx.opencdx.adr.repository;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ANFRepo {

    private final ANFStatementRepository anfStatementRepository;
    private final AssociatedStatementRespository associatedStatementRespository;
    private final MeasureRepository measureRepository;
    private final NarrativeCircumstanceRepository narrativeCircumstanceRepository;
    private final ParticipantRepository participantRepository;
    private final PerformanceCircumstanceRepository performanceCircumstanceRepository;
    private final PractitionerRepository practitionerRepository;
    private final ReferenceRepository referenceRepository;
    private final RepetitionRepository repetitionRepository;
    private final RequestCircumstanceRepository requestCircumstanceRepository;

    public ANFRepo(
            ANFStatementRepository anfStatementRepository,
            AssociatedStatementRespository associatedStatementRespository,
            MeasureRepository measureRepository,
            NarrativeCircumstanceRepository narrativeCircumstanceRepository,
            ParticipantRepository participantRepository,
            PerformanceCircumstanceRepository performanceCircumstanceRepository,
            PractitionerRepository practitionerRepository,
            ReferenceRepository referenceRepository,
            RepetitionRepository repetitionRepository,
            RequestCircumstanceRepository requestCircumstanceRepository) {
        this.anfStatementRepository = anfStatementRepository;
        this.associatedStatementRespository = associatedStatementRespository;
        this.measureRepository = measureRepository;
        this.narrativeCircumstanceRepository = narrativeCircumstanceRepository;
        this.participantRepository = participantRepository;
        this.performanceCircumstanceRepository = performanceCircumstanceRepository;
        this.practitionerRepository = practitionerRepository;
        this.referenceRepository = referenceRepository;
        this.repetitionRepository = repetitionRepository;
        this.requestCircumstanceRepository = requestCircumstanceRepository;
    }
}
