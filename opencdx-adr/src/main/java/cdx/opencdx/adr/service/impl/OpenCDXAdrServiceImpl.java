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

import cdx.opencdx.adr.model.ANFStatementModel;
import cdx.opencdx.adr.model.TinkarConceptModel;
import cdx.opencdx.adr.repository.ANFRepo;
import cdx.opencdx.adr.repository.ANFStatementRepository;
import cdx.opencdx.adr.repository.TinkarConceptRepository;
import cdx.opencdx.adr.service.OpenCDXAdrService;
import cdx.opencdx.adr.service.OpenCDXTinkarService;
import cdx.opencdx.grpc.data.ANFStatement;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for processing HelloWorld Requests
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXAdrServiceImpl implements OpenCDXAdrService {

    private final ANFStatementRepository anfStatementRepository;
    private final OpenCDXTinkarService openCDXTinkarService;
    private final ANFRepo anfRepo;
    private final TinkarConceptRepository tinkarConceptRepository;

    /**
     * Constructor taking the a PersonRepository
     */
    public OpenCDXAdrServiceImpl(ANFStatementRepository anfStatementRepository, OpenCDXTinkarService openCDXTinkarService, ANFRepo anfRepo, TinkarConceptRepository tinkarConceptRepository) {

        this.anfStatementRepository = anfStatementRepository;
        this.openCDXTinkarService = openCDXTinkarService;
        this.anfRepo = anfRepo;
        this.tinkarConceptRepository = tinkarConceptRepository;
    }

    /**
     * Store the ANF Statement
     *
     * @param anfStatement
     */
    @Override
    public void storeAnfStatement(ANFStatement anfStatement) {
        this.openCDXTinkarService.processAnfStatement(anfStatement);
        this.anfStatementRepository.save(new ANFStatementModel(anfStatement, anfRepo));
    }

    public List<TinkarConceptModel> getQueryableData() {
        return this.getChildrenInList(this.tinkarConceptRepository.findAllByParentConceptIdIsNull());

    }

    private List<TinkarConceptModel> getChildrenInList(List<TinkarConceptModel> parents) {
        for (TinkarConceptModel parent : parents) {
            parent.setChildren(this.getChildren(parent));
            this.getChildrenInList(parent.getChildren());
        }
        return parents;

    }

    private List<TinkarConceptModel> getChildren(TinkarConceptModel parent) {
        return this.tinkarConceptRepository.finalAllByParentConceptId(parent.getConceptId());
    }
}
