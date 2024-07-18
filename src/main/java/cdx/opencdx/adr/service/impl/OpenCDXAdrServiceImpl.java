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

import cdx.opencdx.adr.model.AnfStatementModel;
import cdx.opencdx.adr.repository.ANFRepo;
import cdx.opencdx.adr.repository.ANFStatementRepository;
import cdx.opencdx.adr.service.OpenCDXAdrService;
import cdx.opencdx.adr.service.OpenCDXANFProcessor;
import cdx.opencdx.grpc.data.ANFStatement;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.*;

/**
 * Service for processing HelloWorld Requests
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXAdrServiceImpl implements OpenCDXAdrService {

    private final ANFStatementRepository anfStatementRepository;
    private final List<OpenCDXANFProcessor> openCDXANFProcessors;
    private final ANFRepo anfRepo;
    private final ObjectMapper mapper;

    /**
     * Constructor taking the a PersonRepository
     */
    public OpenCDXAdrServiceImpl(ANFStatementRepository anfStatementRepository, List<OpenCDXANFProcessor> openCDXANFProcessors, ANFRepo anfRepo, ObjectMapper mapper) {

        this.anfStatementRepository = anfStatementRepository;
        this.openCDXANFProcessors = openCDXANFProcessors;
        this.anfRepo = anfRepo;
        this.mapper = mapper;

        this.openCDXANFProcessors.stream().forEach(processor -> log.info("Processor: {}", processor.getClass().getName()));
    }

    /**
     * Store the ANF Statement
     *
     * @param anfStatement
     */
    @Override
    public Long storeAnfStatement(ANFStatement anfStatement) {
        AnfStatementModel model = new AnfStatementModel(anfStatement, anfRepo);
        return this.anfStatementRepository.save(model).getId();
    }

}
