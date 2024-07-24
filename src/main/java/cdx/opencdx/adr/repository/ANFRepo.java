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

import cdx.opencdx.adr.model.RepetitionModel;
import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

/**
 * The ANFRepo class represents a repository for the ANF.
 */
@Getter
@Component
public class ANFRepo {

    /**
     * The ANFStatementRepository class is a private final variable that represents a repository
     * for ANFStatementModel objects. It is used to interact with the database table "dimanfstatement"
     * and extends the JpaRepository interface, providing basic CRUD operations for the ANFStatementModel entity.
     *
     * @see ANFStatementModel
     */
    private final ANFStatementRepository anfStatementRepository;
    /**
     * This class is a repository for managing AssociatedStatementModel objects.
     */
    private final AssociatedStatementRespository associatedStatementRespository;
    /**
     * The MeasureRepository class is a repository interface that extends the JpaRepository interface.
     * It is used for managing measures.
     *
     * @param <MeasureModel> the entity type for the measure
     * @param <Long> the type of the measure's ID
     */
    private final MeasureRepository measureRepository;
    /**
     * The NarrativeCircumstanceRepository interface extends the JpaRepository interface and provides methods for managing narrative circumstances.
     */
    private final NarrativeCircumstanceRepository narrativeCircumstanceRepository;
    /**
     * The ParticipantRepository interface provides methods for managing participants.
     */
    private final ParticipantRepository participantRepository;
    /**
     * The PerformanceCircumstanceRepository interface extends the JpaRepository interface and provides the ability to perform
     * CRUD operations on PerformanceCircumstanceModel objects in the database.
     * <p>
     * PerformanceCircumstanceModel is an entity class that represents a performance circumstance. It contains various properties such as timing,
     * status, result, health risk, normal range, deviceIds, participants, and purposes.
     * <p>
     * The PerformanceCircumstanceModel class has the following properties:
     * - id: The unique identifier for a performance circumstance.
     * - timing: The timing associated with the performance circumstance.
     * - status: The status associated with the performance circumstance.
     * - result: The result associated with the performance circumstance.
     * - healthRisk: The health risk associated with the performance circumstance.
     * - normalRange: The normal range associated with the performance circumstance.
     * - deviceIds: The list of deviceIds associated with the performance circumstance.
     * - participants: The list of participants associated with the performance circumstance.
     * - purposes: The list of purposes associated with the performance circumstance.
     * <p>
     * The PerformanceCircumstanceRepository interface is used by the ANFRepo class in its constructor to instantiate an instance of PerformanceCircumstanceRepository.
     */
    private final PerformanceCircumstanceRepository performanceCircumstanceRepository;
    /**
     * The practitionerRepository variable is an instance of the PractitionerRepository interface.
     * It is used to interact with the database table "practitioner" and manage instances of the PractitionerModel class.
     * The PractitionerRepository interface extends the JpaRepository interface, providing basic CRUD operations for the PractitionerModel entity.
     * <p>
     * Example usage:
     * practitionerRepository.save(practitionerModel); // Save a new practitioner to the database
     * practitionerRepository.findById(practitionerId); // Find a practitioner by ID
     * practitionerRepository.findAll(); // Retrieve all practitioners from the database
     * practitionerRepository.delete(practitionerModel); // Delete a practitioner from the database
     */
    private final PractitionerRepository practitionerRepository;
    /**
     * This interface represents a repository for managing instances of the {@link RepetitionModel} class.
     * It extends the {@link JpaRepository} interface, providing basic CRUD operations.
     * The {@code RepetitionModel} class represents a repetition with various attributes such as period start, period duration,
     * event frequency, event separation, and event duration.
     *
     * @param <T> The type of the entity (RepetitionModel) managed by this repository.
     */
    private final RepetitionRepository repetitionRepository;
    /**
     * The RequestCircumstanceRepository interface represents a repository for managing instances of the RequestCircumstanceModel class.
     * It extends the JpaRepository interface, providing basic CRUD operations.
     * The RequestCircumstanceModel class represents a request circumstance that includes various attributes such as timing, priority, requested result, repetition, conditional trigger
     * , and purposes.
     *
     * @param <T> The type of the entity (RequestCircumstanceModel) managed by this repository.
     */
    private final RequestCircumstanceRepository requestCircumstanceRepository;
    /**
     * The logicalExpressionRepository variable is an instance of the LogicalExpressionRepository interface.
     * It is used to manage logical expressions in the application.
     * The repository provides methods for checking if an expression exists, finding a logical expression by its expression,
     * and saving or finding a logical expression model.
     */
    private final LogicalExpressionRepository logicalExpressionRepository;
    /**
     * The referenceRepository variable is an instance of the ReferenceRepository interface.
     * It is used as a repository for managing instances of the ReferenceModel class.
     * <p>
     * The ReferenceRepository interface extends the JpaRepository interface, providing basic CRUD operations for managing instances of the ReferenceModel class.
     * <p>
     * The ReferenceRepository interface is declared in the ANFRepo class and is initialized in the constructor of the ANFRepo class.
     * <p>
     * Example usage:
     * <p>
     * // Get all references
     * List<ReferenceModel> references = referenceRepository.findAll();
     * <p>
     * // Save a new reference
     * ReferenceModel newReference = new ReferenceModel();
     * newReference.setName("New Reference");
     * referenceRepository.save(newReference);
     * <p>
     * // Update an existing reference
     * ReferenceModel existingReference = referenceRepository.findById(1L).orElse(null);
     * if (existingReference != null) {
     * existingReference.setName("Updated Reference");
     * referenceRepository.save(existingReference);
     * }
     * <p>
     * // Delete a reference
     * referenceRepository.deleteById(1L);
     */
    private final ReferenceRepository referenceRepository;
    /**
     * The TinkarConceptRepository variable is an instance of the TinkarConceptRepository interface.
     * It is used to access and manipulate TinkarConceptModel objects.
     * <p>
     * The TinkarConceptRepository interface extends the JpaRepository interface,
     * which provides basic CRUD operations for the TinkarConceptModel entity.
     * <p>
     * This variable is declared as private and final, indicating that it cannot be modified after initialization.
     * It is typically initialized through the constructor of the containing class,
     * where the appropriate implementation of the TinkarConceptRepository interface is injected.
     * <p>
     * Example usage:
     * TinkarConceptModel concept = tinkarConceptRepository.findByConceptId(UUID.randomUUID());
     * boolean exists = tinkarConceptRepository.existsByConceptId(concept.getConceptId());
     */
    private final TinkarConceptRepository tinkarConceptRepository;

    /**
     * The ANFRepo class represents a repository for the ANF.
     */
    public ANFRepo(
            ANFStatementRepository anfStatementRepository,
            AssociatedStatementRespository associatedStatementRespository,
            MeasureRepository measureRepository,
            NarrativeCircumstanceRepository narrativeCircumstanceRepository,
            ParticipantRepository participantRepository,
            PerformanceCircumstanceRepository performanceCircumstanceRepository,
            PractitionerRepository practitionerRepository,
            RepetitionRepository repetitionRepository,
            RequestCircumstanceRepository requestCircumstanceRepository, LogicalExpressionRepository logicalExpressionRepository, ReferenceRepository referenceRepository, TinkarConceptRepository tinkarConceptRepository) {
        this.anfStatementRepository = anfStatementRepository;
        this.associatedStatementRespository = associatedStatementRespository;
        this.measureRepository = measureRepository;
        this.narrativeCircumstanceRepository = narrativeCircumstanceRepository;
        this.participantRepository = participantRepository;
        this.performanceCircumstanceRepository = performanceCircumstanceRepository;
        this.practitionerRepository = practitionerRepository;
        this.repetitionRepository = repetitionRepository;
        this.requestCircumstanceRepository = requestCircumstanceRepository;
        this.logicalExpressionRepository = logicalExpressionRepository;
        this.referenceRepository = referenceRepository;
        this.tinkarConceptRepository = tinkarConceptRepository;
    }
}
