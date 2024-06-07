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

import cdx.opencdx.adr.service.OpenCDXAdrService;
import cdx.opencdx.grpc.data.ANFStatement;
import io.micrometer.observation.annotation.Observed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Service for processing HelloWorld Requests
 */
@Slf4j
@Service
@Observed(name = "opencdx")
public class OpenCDXAdrServiceImpl implements OpenCDXAdrService {

    private final FactANFStatementRepository factANFStatementRepository;

    /**
     * Constructor taking the a PersonRepository
     */
    public OpenCDXAdrServiceImpl(FactANFStatementRepository factANFStatementRepository) {
        // Explicit declaration to prevent this class from inadvertently being made instantiable
        this.factANFStatementRepository = factANFStatementRepository;
    }

    /**
     * Store the ANF Statement
     *
     * @param anfStatement
     */
    @Override
    public void storeAnfStatement(ANFStatement anfStatement) {

    }
}
