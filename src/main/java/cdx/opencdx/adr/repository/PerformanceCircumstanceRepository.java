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

import cdx.opencdx.adr.model.PerformanceCircumstanceModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * The PerformanceCircumstanceRepository interface extends the JpaRepository interface and provides the ability to perform CRUD operations on PerformanceCircumstanceModel objects
 * in the database.
 * <p>
 * PerformanceCircumstanceModel is an entity class that represents a performance circumstance. It contains various properties such as timing, status, result, health risk, normal range
 * , deviceIds, participants, and purposes.
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
 * The PerformanceCircumstanceModel class has a constructor that takes a PerformanceCircumstance object and an ANFRepo object as parameters and initializes its properties based on
 * the values of the PerformanceCircumstance object.
 * <p>
 * This interface is used by the ANFRepo class in its constructor to instantiate an instance of PerformanceCircumstanceRepository.
 * <p>
 * Superclass: JpaRepository<PerformanceCircumstanceModel, Long>
 */
public interface PerformanceCircumstanceRepository extends JpaRepository<PerformanceCircumstanceModel, Long> {
}
