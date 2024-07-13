package cdx.opencdx.adr.service.impl;

import cdx.opencdx.adr.repository.TinkarConceptRepository;
import cdx.opencdx.adr.service.OpenCDXANFProcessor;
import cdx.opencdx.adr.service.OpenCDXIkmService;
import cdx.opencdx.grpc.data.ANFStatement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implementation of the OpenCDX Tinkar Processor
 */
@Service
@Slf4j
public class OpenCDXTinkarProcessorImpl extends BaseAnfProcessor implements OpenCDXANFProcessor {

    private final ObjectMapper objectMapper;


    /**
     * Constructor taking the a PersonRepository
     */
    @SuppressWarnings("java:S1192")
    public OpenCDXTinkarProcessorImpl(TinkarConceptRepository tinkarConceptRepository, ObjectMapper objectMapper, OpenCDXIkmService openCDXTinkarLookupService) {
        super(tinkarConceptRepository, openCDXTinkarLookupService);
        this.objectMapper = objectMapper;
    }

    /**
     * Process the ANF Statement
     * @param anfStatement  ANF Statement to process
     */
    @Override
    public void processAnfStatement(ANFStatement anfStatement) {
        try {
            String json = this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(anfStatement);

            List<UUID> uuids = this.findUuids(json);

            for (UUID uuid : uuids) {
                log.info("Processing UUID: {}", uuid);

                this.loadConceptTree(uuid);
                this.updateConceptTree(uuid);
            }


        } catch (JsonProcessingException e) {
            log.error("Error processing ANF statement: {}", e.getMessage(),e);
        }
    }

    private List<UUID> findUuids(String text) {
        List<UUID> uuids = new ArrayList<>();
        // Regular expression pattern for UUIDs (version 1, 4, 5)
        Pattern uuidPattern = Pattern.compile("[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}");
        Matcher matcher = uuidPattern.matcher(text);

        // Find all matches of the pattern in the text
        while (matcher.find()) {
            String uuidStr = matcher.group();
            UUID uuid = UUID.fromString(uuidStr);
            uuids.add(uuid);
        }

        return uuids;
    }

}
