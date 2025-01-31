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
package cdx.opencdx.adr.service;

import cdx.opencdx.adr.dto.ADRQuery;
import cdx.opencdx.adr.dto.Report;
import cdx.opencdx.adr.dto.SavedQuery;
import cdx.opencdx.adr.model.TinkarConceptModel;
import cdx.opencdx.grpc.data.ANFStatement;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

/**
 * The OpenCDXAdrService interface provides methods for managing ANF statements and executing queries.
 */
public interface OpenCDXAdrService {
    /**
     * Stores an ANF statement in the system.
     *
     * @param anfStatement The ANF statement to store.
     * @return The ID of the stored ANF statement as a {@code Long}.
     */
    Long storeAnfStatement(ANFStatement anfStatement);

    /**
     * Stores an array of ANF statements in the system.
     *
     * @param anfStatements The ANF statement to store.
     * @return The array of IDs of the stored ANF statements as a {@code Long}.
     */
    Long[] storeAnfStatements(ANFStatement[] anfStatements);

    /**
     * Retrieves the queryable data.
     *
     * @return a list of TinkarConceptModel objects.
     */
    List<TinkarConceptModel> getQueryableData();

    /**
     * Retrieves the units.
     *
     * @return a list of TinkarConceptModel objects.
     */
    List<TinkarConceptModel> getUnits();

    /**
     * Executes a stream query on the provided ADRQuery object and returns a list of strings.
     *
     * @param adrQuery The ADRQuery object representing the query to be executed.
     * @return A list of strings representing the query results.
     */
    List<String> streamQuery(ADRQuery adrQuery);

    /**
     * Saves a list of SavedQuery objects.
     *
     * @param save The list of SavedQuery objects to be saved.
     * @return
     */
    SavedQuery saveQuery(SavedQuery save) throws JsonProcessingException;

    /**
     * Updates a SavedQuery object.
     *
     * @param save The SavedQuery object to update.
     * @return The updated SavedQuery object.
     */
    SavedQuery updateQuery(SavedQuery save) throws JsonProcessingException;

    /**
     * Retrieves a list of saved queries.
     *
     * @return a List of SavedQuery objects representing the saved queries.
     * @throws JsonProcessingException if there is an error processing JSON data.
     */
    List<SavedQuery> listSavedQueries() throws JsonProcessingException;

    /**
     * Deletes a saved query with the specified ID.
     *
     * @param id The ID of the saved query to delete.
     */
    void deleteSavedQuery(Long id);

    /**
     * Executes a JSON query based on the provided ADRQuery object and returns a Report object.
     *
     * @param adrQuery The ADRQuery object representing the parameters and conditions for the query.
     * @return A Report object containing the results of the JSON query.
     */
    Report getJsonQuery(ADRQuery adrQuery);
}
