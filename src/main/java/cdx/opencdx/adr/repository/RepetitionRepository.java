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
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This interface represents a repository for managing instances of the {@link RepetitionModel} class.
 * It extends the {@link JpaRepository} interface, providing basic CRUD operations.
 * The {@code RepetitionModel} class represents a repetition with various attributes such as period start, period duration,
 * event frequency, event separation, and event duration.
 *
 * @param <T> The type of the entity (RepetitionModel) managed by this repository.
 */
public interface RepetitionRepository extends JpaRepository<RepetitionModel, Long> {
}
