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

import cdx.opencdx.adr.dto.Query;
import cdx.opencdx.adr.model.TinkarConceptModel;
import cdx.opencdx.grpc.data.ANFStatement;

import java.io.PrintWriter;
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
     * Retrieves the queryable data.
     *
     * @return a list of TinkarConceptModel objects.
     */
    List<TinkarConceptModel> getQueryableData();

    /**
     * Executes a stream query on a list of {@link Query} objects and writes the results to a {@link PrintWriter}.
     *
     * @param query  The list of {@link Query} objects representing the queries to be executed.
     * @param writer The {@link PrintWriter} to which the query results will be written.
     */
    void streamQuery(List<Query> query, PrintWriter writer);
}
