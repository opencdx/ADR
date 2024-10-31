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

import cdx.opencdx.adr.model.MeasureModel;
import cdx.opencdx.adr.model.TinkarConceptModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * MeasureRepository is an interface that extends the JpaRepository interface. It is used for managing measures.
 */
public interface MeasureRepository extends JpaRepository<MeasureModel, Long> {

    /**
     * Finds all distinct semantics for Tinkar concepts.
     *
     * @return a List of TinkarConceptModel objects representing all distinct semantics.
     */
    @Query("SELECT DISTINCT m.semantic FROM MeasureModel m")
    List<TinkarConceptModel> findAllDistinctSemantics();
}
