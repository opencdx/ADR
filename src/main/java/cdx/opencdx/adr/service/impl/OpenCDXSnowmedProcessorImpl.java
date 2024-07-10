package cdx.opencdx.adr.service.impl;

import cdx.opencdx.adr.repository.TinkarConceptRepository;
import cdx.opencdx.adr.service.OpenCDXANFProcessor;
import cdx.opencdx.adr.service.OpenCDXIkmService;
import cdx.opencdx.grpc.data.ANFStatement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class OpenCDXSnowmedProcessorImpl extends BaseAnfProcessor implements OpenCDXANFProcessor {

     private final ObjectMapper objectMapper;
    private final OpenCDXIkmService openCDXIkmService;

    public OpenCDXSnowmedProcessorImpl(TinkarConceptRepository tinkarConceptRepository, ObjectMapper objectMapper, OpenCDXIkmService openCDXIkmService) {
        super(tinkarConceptRepository, openCDXIkmService);
        this.objectMapper = objectMapper;
        this.openCDXIkmService = openCDXIkmService;
    }


    @Override
    public void processAnfStatement(ANFStatement anfStatement) {
        try {

            String json = this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(anfStatement);

            List<String> snowmedIds = this.findSnowmedIds(json);

            for (String snowmedId : snowmedIds) {

                UUID uuid = this.openCDXIkmService.getTinkarConceptForSnowmed(snowmedId);

                log.info("Processing UUID: {}  SnowmedID: {}", uuid,snowmedId);
                this.loadConceptTree(uuid);
                this.updateConceptTree(uuid);
            }


        } catch (JsonProcessingException e) {
            log.error("Error processing ANF statement: {}", e.getMessage(),e);
        }
    }

    private List<String> findSnowmedIds(String text) {
        List<String> snowmedIds = new ArrayList<>();
        // Regular expression pattern for UUIDs (version 1, 4, 5)
        Pattern uuidPattern = Pattern.compile("(?<!\\d)(\\d{6,8})(?!\\d)");
        Matcher matcher = uuidPattern.matcher(text);

        // Find all matches of the pattern in the text
        while (matcher.find()) {
            String str = matcher.group();
            snowmedIds.add(str);
        }

        return snowmedIds;
    }
}
