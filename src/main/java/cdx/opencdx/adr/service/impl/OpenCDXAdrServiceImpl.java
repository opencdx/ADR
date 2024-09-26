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
import cdx.opencdx.adr.dto.SavedQuery;
import cdx.opencdx.adr.model.AnfStatementModel;
import cdx.opencdx.adr.model.SavedQueryModel;
import cdx.opencdx.adr.model.TinkarConceptModel;
import cdx.opencdx.adr.repository.ANFStatementRepository;
import cdx.opencdx.adr.repository.SavedQueryRepository;
import cdx.opencdx.adr.repository.TinkarConceptRepository;
import cdx.opencdx.adr.service.OpenCDXANFProcessor;
import cdx.opencdx.adr.service.OpenCDXAdrService;
import cdx.opencdx.adr.service.OpenCDXIKMService;
import cdx.opencdx.adr.service.QueryService;
import cdx.opencdx.adr.utils.ANFHelper;
import cdx.opencdx.grpc.data.ANFStatement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;


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
    private final List<OpenCDXANFProcessor> postOpenCDXANFProcessors;
    private final List<OpenCDXANFProcessor> preOpenCDXANFProcessors;
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
     * This variable represents an instance of the SavedQueryRepository interface, which
     * is responsible for managing saved queries in the application.
     * <p>
     * The SavedQueryRepository interface provides methods to perform CRUD (Create, Read,
     * Update, and Delete) operations on saved queries in the application's data storage.
     * <p>
     * This variable is declared as private and final, indicating that it is intended to be
     * accessed only within the current class and cannot be reassigned to another instance
     * of the SavedQueryRepository interface.
     */
    private final SavedQueryRepository savedQueryRepository;

    /**
     * Initializes an instance of OpenCDXAdrServiceImpl.
     *
     * @param anfStatementRepository the ANF statement repository
     * @param conceptRepository      the Tinkar concept repository
     * @param queryService           the query service
     * @param preOpenCDXANFProcessors the pre-OpenCDX ANF processors
     * @param postOpenCDXANFProcessors the post-OpenCDX ANF processors
     * @param anfRepo                the ANF repository
     * @param mapper                 the object mapper
     * @param savedQueryRepository   the saved query repository
     */
    public OpenCDXAdrServiceImpl(ANFStatementRepository anfStatementRepository,
                                 TinkarConceptRepository conceptRepository,
                                 QueryService queryService,
                                 @Qualifier("preOpenCDXANFProcessors") List<OpenCDXANFProcessor> preOpenCDXANFProcessors,
                                 @Qualifier("postOpenCDXANFProcessors") List<OpenCDXANFProcessor> postOpenCDXANFProcessors,
                                 ANFHelper anfRepo,
                                 ObjectMapper mapper,
                                 SavedQueryRepository savedQueryRepository) {
        this.anfStatementRepository = anfStatementRepository;
        this.conceptRepository = conceptRepository;
        this.queryService = queryService;
        this.postOpenCDXANFProcessors = postOpenCDXANFProcessors;
        this.preOpenCDXANFProcessors = preOpenCDXANFProcessors;
        this.anfRepo = anfRepo;
        this.mapper = mapper;
        this.savedQueryRepository = savedQueryRepository;

        this.preOpenCDXANFProcessors.forEach(processor -> log.info("Pre Processor: {}", processor.getClass().getName()));
        this.postOpenCDXANFProcessors.forEach(processor -> log.info("Post Processor: {}", processor.getClass().getName()));

        this.blockConcepts.add(UUID.fromString(OpenCDXIKMService.UNIT_DAY)); // day
        this.blockConcepts.add(UUID.fromString(OpenCDXIKMService.UNIT_INCH)); // inch
        this.blockConcepts.add(UUID.fromString(OpenCDXIKMService.UNIT_KILOGRAMS)); // kilogram
        this.blockConcepts.add(UUID.fromString(OpenCDXIKMService.UNIT_METER)); // meter
        this.blockConcepts.add(UUID.fromString(OpenCDXIKMService.UNIT_MONTH)); // month
        this.blockConcepts.add(UUID.fromString(OpenCDXIKMService.UNIT_POUNDS)); // pounds
        this.blockConcepts.add(UUID.fromString(OpenCDXIKMService.UNIT_YEAR)); // year
        this.blockConcepts.add(UUID.fromString(OpenCDXIKMService.UNIT_SECONDS)); // seconds
        this.blockConcepts.add(UUID.fromString(OpenCDXIKMService.UNIT_CALENDAR_TIME)); // Unit of Calendar Time
        this.blockConcepts.add(UUID.fromString(OpenCDXIKMService.UNIT_DATE)); // Unit of Calendar Time
        this.blockConcepts.add(UUID.fromString(OpenCDXIKMService.UNIT_HOUR)); // Unit of Calendar Time
        this.blockConcepts.add(UUID.fromString(OpenCDXIKMService.UNIT_MINUTE)); // Unit of Calendar Time
        this.blockConcepts.add(UUID.fromString(OpenCDXIKMService.UNIT_DATE_TIME)); // Unit of Calendar Time
        this.blockConcepts.add(UUID.fromString(OpenCDXIKMService.UNIT_MILLISECONDS)); // Unit of Calendar Time
        this.blockConcepts.add(UUID.fromString(OpenCDXIKMService.UNIT_CELSIUS)); // Unit of Calendar Time
        this.blockConcepts.add(UUID.fromString(OpenCDXIKMService.UNIT_FAHRENHEIT)); // Unit of Calendar Time

    }

    /**
     * Stores the given ANF statement in the system and returns the assigned ID.
     *
     * @param anfStatement The ANF statement to be stored.
     * @return The assigned ID of the stored ANF statement.
     */
    @Override
    public synchronized Long storeAnfStatement(ANFStatement anfStatement) {

        AnfStatementModel preModel = new AnfStatementModel(anfStatement, anfRepo);
        this.preOpenCDXANFProcessors.forEach(processor -> processor.processAnfStatement(preModel));
        AnfStatementModel postModel = this.anfStatementRepository.save(preModel);
        this.anfStatementRepository.flush();
        this.postOpenCDXANFProcessors.forEach(processor -> processor.processAnfStatement(postModel));
        return postModel.getId();
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
                .filter(concept -> !this.blockConcepts.contains(concept.getConceptId()))
                .filter(concept -> concept.getConceptName() != null && !concept.getConceptName().isEmpty())
                .sorted(Comparator.comparing(TinkarConceptModel::getConceptName)).toList();
    }

    @Override
    public List<TinkarConceptModel> getUnits() {
        return this.conceptRepository.findAll().stream()
                .filter(concept -> this.blockConcepts.contains(concept.getConceptId())).sorted(Comparator.comparing(TinkarConceptModel::getConceptName)).toList();
    }


    @Override
    public List<String> streamQuery(ADRQuery adrQuery) {
        return this.queryService.processQuery(adrQuery);
    }

    @Override
    public SavedQuery saveQuery(SavedQuery save) throws JsonProcessingException {
        SavedQueryModel model = new SavedQueryModel(save.getName(), this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(save.getQuery()));
        model = this.savedQueryRepository.save(model);

        return new SavedQuery(model.getId(), model.getName(), save.getQuery());
    }

    public SavedQuery updateQuery(SavedQuery save) throws JsonProcessingException {
        Optional<SavedQueryModel> optionalModel = this.savedQueryRepository.findById(save.getId());

        if (optionalModel.isPresent()) {
            log.debug("Updating query with ID: {}", save.getId());
            SavedQueryModel model = optionalModel.get();
            if (save.getName() != null) {
                log.debug("Updating query name to: {}", save.getName());
                model.setName(save.getName());
            }
            if (save.getQuery() != null) {
                log.debug("Updating query content");
                model.setContent(this.mapper.writerWithDefaultPrettyPrinter().writeValueAsString(save.getQuery()));
            }
            model = this.savedQueryRepository.save(model);
            return new SavedQuery(model.getId(), model.getName(), save.getQuery());
        }
        return null;
    }

    @Override
    public List<SavedQuery> listSavedQueries() throws JsonProcessingException {
        List<SavedQueryModel> all = this.savedQueryRepository.findAll();

        List<SavedQuery> savedQueries = new ArrayList<>();

        for (SavedQueryModel model : all) {
            savedQueries.add(new SavedQuery(model.getId(), model.getName(), this.mapper.readValue(model.getContent(), ADRQuery.class)));
        }

        return savedQueries.stream().sorted(Comparator.comparing(SavedQuery::getName)).toList();
    }

    @Override
    public void deleteSavedQuery(Long id) {
        this.savedQueryRepository.deleteById(id);
    }

}
