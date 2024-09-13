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

import cdx.opencdx.adr.model.PractitionerModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This interface represents a repository for managing instances of the {@link PractitionerModel} class.
 * It extends the {@link JpaRepository} interface, providing basic CRUD operations.
 * The {@code PractitionerModel} class represents a practitioner entity with various attributes such as ID, Practitioner ID, practitioner value, and code.
 */
public interface PractitionerRepository extends JpaRepository<PractitionerModel, Long> {
}
