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
package cdx.opencdx.adr.service.impl;

import cdx.opencdx.adr.dto.ADRQuery;
import cdx.opencdx.adr.dto.Query;
import cdx.opencdx.adr.model.AnfStatementModel;
import cdx.opencdx.adr.model.TinkarConceptModel;
import cdx.opencdx.adr.repository.ANFStatementRepository;
import cdx.opencdx.adr.repository.TinkarConceptRepository;
import cdx.opencdx.adr.service.OpenCDXANFProcessor;
import cdx.opencdx.adr.service.OpenCDXAdrService;
import cdx.opencdx.adr.service.QueryService;
import cdx.opencdx.adr.utils.ANFHelper;
import cdx.opencdx.grpc.data.ANFStatement;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;


/**
 * This class implements the OpenCDXAdrService interface and provides methods
 * for storing ANF statements, retrieving queryable data, and streaming queries.
 *
 * @see OpenCDXAdrService
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXAdrServiceImpl implements OpenCDXAdrService {

    /**
     * The ANFStatementRepository variable represents an instance of a repository
     * for ANF (Abstract Syntax Tree Normal Form) statements. It is a private final
     * variable, indicating that it cannot be modified once initialized and
     * only accessible within the class it is defined.
     * <p>
     * This repository provides methods to access and manipulate ANF statements,
     * which are representations of code or expressions transformed into a specific
     * normal form. It encapsulates the logic for storing and retrieving ANF
     * statements, enabling the class to interact with the repository for
     * performing operations on ANF statements.
     * <p>
     * This variable is generally instantiated and assigned a value using dependency
     * injection or object creation during class initialization.
     */
    private final ANFStatementRepository anfStatementRepository;
    /**
     * Represents a Tinkar concept repository.
     */
    private final TinkarConceptRepository conceptRepository;

    /**
     *
     */
    private final QueryService queryService;
    /**
     * This variable is a list of OpenCDXANFProcessors.
     * Each OpenCDXANFProcessor represents a processor for the OpenCDXANF format.
     * The list is final, meaning it cannot be reassigned to a new instance once initialized.
     *
     * @see OpenCDXANFProcessor
     */
    private final List<OpenCDXANFProcessor> openCDXANFProcessors;
    /**
     * This private final variable represents an instance of ANFRepo.
     * ANFRepo is a repository class that provides functionality for
     * accessing and managing ANF objects.
     */
    private final ANFHelper anfRepo;
    /**
     * The variable mapper for object-to-JSON serialization and deserialization.
     * It is an instance of the ObjectMapper class, which provides functionality
     * for converting between JSON and Java objects.
     */
    private final ObjectMapper mapper;

    /**
     * Represents a list of unique identifiers for block concepts.
     */
    private final List<UUID> blockConcepts = new ArrayList<>();

    /**
     * Initialize the OpenCDXAdrServiceImpl with the required dependencies.
     *
     * @param anfStatementRepository The ANF Statement Repository
     * @param conceptRepository      The Tinkar Concept Repository
     * @param queryService           The Query Service
     * @param openCDXANFProcessors   List of OpenCDXANFProcessors
     * @param anfRepo                The ANF Repo
     * @param mapper                 The ObjectMapper
     */
    public OpenCDXAdrServiceImpl(ANFStatementRepository anfStatementRepository, TinkarConceptRepository conceptRepository, QueryService queryService, List<OpenCDXANFProcessor> openCDXANFProcessors, ANFHelper anfRepo, ObjectMapper mapper) {
        this.anfStatementRepository = anfStatementRepository;
        this.conceptRepository = conceptRepository;
        this.queryService = queryService;
        this.openCDXANFProcessors = openCDXANFProcessors;
        this.anfRepo = anfRepo;
        this.mapper = mapper;

        this.openCDXANFProcessors.forEach(processor -> log.info("Processor: {}", processor.getClass().getName()));

        this.blockConcepts.add(UUID.fromString("9a6877c0-04b7-4d5d-9766-8695192790d4")); // day
        this.blockConcepts.add(UUID.fromString("01759586-062f-455f-a0c4-23904464b5f4")); // inch
        this.blockConcepts.add(UUID.fromString("20e0e0e0-70a1-4161-b7a4-e7725f5f583e")); // kilogram
        this.blockConcepts.add(UUID.fromString("757702f5-2516-4d25-ab74-4a226806857f")); // meter
        this.blockConcepts.add(UUID.fromString("3a44167a-e94a-4962-8d2e-d94e57475732")); // month
        this.blockConcepts.add(UUID.fromString("98999a1c-11b1-4777-a9b6-3b25482676c4")); // pounds
        this.blockConcepts.add(UUID.fromString("d9036e1e-3397-4f00-a40a-021626644970")); // year
        this.blockConcepts.add(UUID.fromString("b66571ca-bba7-4a5a-a90f-2ef9f0c33a56")); // seconds

    }

    /**
     * Stores the given ANF statement in the system and returns the assigned ID.
     *
     * @param anfStatement The ANF statement to be stored.
     * @return The assigned ID of the stored ANF statement.
     */
    @Override
    public synchronized Long storeAnfStatement(ANFStatement anfStatement) {
        AnfStatementModel model = this.anfStatementRepository.save(new AnfStatementModel(anfStatement, anfRepo));
        this.anfStatementRepository.flush();
        this.openCDXANFProcessors.forEach(processor -> processor.processAnfStatement(model));
        return model.getId();
    }

    /**
     * Retrieves the queryable data from the concept repository.
     *
     * @return A list of TinkarConceptModel objects representing the queryable data.
     */
    @Override
    public List<TinkarConceptModel> getQueryableData() {
        //return this.conceptRepository.findAll() sorted by conceptName
        return this.conceptRepository.findAll().stream()
                .filter(concept -> !this.blockConcepts.contains(concept.getConceptId())).sorted(Comparator.comparing(TinkarConceptModel::getConceptName)).toList();
    }

    @Override
    public List<TinkarConceptModel> getUnits() {
        return this.conceptRepository.findAll().stream()
                .filter(concept -> this.blockConcepts.contains(concept.getConceptId())).sorted(Comparator.comparing(TinkarConceptModel::getConceptName)).toList();
    }

    /**
     * Streams the result of an ADR query to the specified print writer.
     *
     * @param adrQuery The ADR query to process.
     * @param writer   The print writer to stream the result to.
     */
    @Override
    public void streamQuery(ADRQuery adrQuery, PrintWriter writer) {
        this.queryService.processQuery(adrQuery, writer);
    }

}
