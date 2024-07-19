package cdx.opencdx.adr.service.impl;

import cdx.opencdx.adr.model.AnfStatementModel;
import cdx.opencdx.adr.service.OpenCDXANFProcessor;
import cdx.opencdx.grpc.data.ANFStatement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ANFLogProcessor implements OpenCDXANFProcessor {

    private final ObjectMapper objectMapper;

    public ANFLogProcessor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Process the ANF Statement
     *
     * @param anfStatement
     */
    @Override
    public void processAnfStatement(AnfStatementModel anfStatement) {
        try {
            log.info("Processing ANF Statement: \n{}", this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(anfStatement));
        } catch (JsonProcessingException e) {
            log.error("Error processing ANF Statement", e);
        }
    }
}
