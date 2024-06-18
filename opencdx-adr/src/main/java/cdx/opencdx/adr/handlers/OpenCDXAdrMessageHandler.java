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
package cdx.opencdx.adr.handlers;

import cdx.opencdx.adr.service.OpenCDXAdrService;
import cdx.opencdx.adr.service.OpenCDXMessageService;
import cdx.opencdx.grpc.data.ANFStatement;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OpenCDXAdrMessageHandler implements OpenCDXMessageHandler {

    private final ObjectMapper objectMapper;
    private final OpenCDXAdrService openCDXAdrService;

    /**
     * Constructor for the Audit microservice
     *
     * @param objectMapper               Object mapper used for conversion
     * @param openCDXMessageService      Message service used for receiving messages.
     * @param openCDXAdrService      Service to process ADR Statements.
     */
    public OpenCDXAdrMessageHandler(
            ObjectMapper objectMapper,
            OpenCDXMessageService openCDXMessageService,
            OpenCDXAdrService openCDXAdrService) {
        this.objectMapper = objectMapper;
        this.openCDXAdrService = openCDXAdrService;

        openCDXMessageService.subscribe(OpenCDXMessageService.ADR_MESSAGE_SUBJECT, this);
    }

    /**
     * Method to receive the byte array of data for the message being
     * handled. This handlers is responsible for converting the bytes
     * to the appropriate representation.
     *
     * @param message Byte array containing the message
     */
    @Override
    public void receivedMessage(byte[] message) {
        try {
            ANFStatement statement = objectMapper.readValue(message, ANFStatement.class);
            log.info("Received ANF Statement: {}", statement.toString());
            this.openCDXAdrService.storeAnfStatement(statement);
        } catch (IOException e) {
            log.error("Error processing message: {}", e.getMessage());
        }
    }
}
