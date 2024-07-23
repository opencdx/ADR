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

import cdx.opencdx.adr.dto.Query;
import cdx.opencdx.adr.model.AnfStatementModel;
import cdx.opencdx.adr.model.TinkarConceptModel;
import cdx.opencdx.adr.repository.ANFRepo;
import cdx.opencdx.adr.repository.ANFStatementRepository;
import cdx.opencdx.adr.repository.TinkarConceptRepository;
import cdx.opencdx.adr.service.OpenCDXAdrService;
import cdx.opencdx.adr.service.OpenCDXANFProcessor;
import cdx.opencdx.adr.service.QueryService;
import cdx.opencdx.grpc.data.ANFStatement;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.io.PrintWriter;
import java.util.*;

/**
 * Service for processing HelloWorld Requests
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXAdrServiceImpl implements OpenCDXAdrService {

    private final ANFStatementRepository anfStatementRepository;
    private final TinkarConceptRepository conceptRepository;

    private final QueryService queryService;
    private final List<OpenCDXANFProcessor> openCDXANFProcessors;
    private final ANFRepo anfRepo;
    private final ObjectMapper mapper;

    private final List<UUID> blockConcepts = new ArrayList<>();

    /**
     * Constructor taking the a PersonRepository
     */
    public OpenCDXAdrServiceImpl(ANFStatementRepository anfStatementRepository, TinkarConceptRepository conceptRepository, QueryService queryService, List<OpenCDXANFProcessor> openCDXANFProcessors, ANFRepo anfRepo, ObjectMapper mapper) {
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
        this.blockConcepts.add(UUID.fromString("c4e07b26-88f9-4250-803c-86463391c001")); // seconds

    }

    /**
     * Store the ANF Statement
     *
     * @param anfStatement
     */
    @Override
    public synchronized Long  storeAnfStatement(ANFStatement anfStatement) {
        AnfStatementModel model =  this.anfStatementRepository.save(new AnfStatementModel(anfStatement, anfRepo));
        this.anfStatementRepository.flush();
        this.openCDXANFProcessors.forEach(processor -> processor.processAnfStatement(model));
        return model.getId();
    }

    @Override
    public List<TinkarConceptModel> getQueryableData() {
        //return this.conceptRepository.findAll() sorted by conceptName
        return this.conceptRepository.findAll().stream()
                .filter(concept -> !this.blockConcepts.contains(concept.getConceptId())).sorted(Comparator.comparing(TinkarConceptModel::getConceptName)).toList();
    }

    @Override
    public void streamQuery(Query query, PrintWriter writer) {
        this.queryService.processQuery(query,writer);
    }

}
