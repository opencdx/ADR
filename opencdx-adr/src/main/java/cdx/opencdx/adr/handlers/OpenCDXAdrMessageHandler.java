package cdx.opencdx.adr.handlers;

import cdx.opencdx.adr.service.OpenCDXAdrService;
import cdx.opencdx.adr.service.OpenCDXMessageService;
import cdx.opencdx.grpc.data.ANFStatement;
import cdx.opencdx.grpc.data.Notification;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

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
            OpenCDXMessageService openCDXMessageService, OpenCDXAdrService openCDXAdrService) {
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
