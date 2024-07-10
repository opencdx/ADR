package cdx.opencdx.adr.controller;

import cdx.opencdx.adr.service.OpenCDXAdrService;
import cdx.opencdx.grpc.data.ANFStatement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "/anf", produces = MediaType.APPLICATION_JSON_VALUE)
public class AnfController {

    private final ObjectMapper objectMapper;
    private final OpenCDXAdrService openCDXAdrService;

    public AnfController(ObjectMapper objectMapper, OpenCDXAdrService openCDXAdrService) throws JsonProcessingException {
        this.objectMapper = objectMapper;
        this.openCDXAdrService = openCDXAdrService;

        ANFStatement anfStatement = ANFStatement.newBuilder()
                .setId(UUID.randomUUID().toString())
                .build();

        log.info("Sample ANF statement: \n{}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(anfStatement));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> postANFStatement(@RequestBody String data ) throws JsonProcessingException {
        ANFStatement anfStatement = objectMapper.readValue(data, ANFStatement.class);
        this.openCDXAdrService.storeAnfStatement(anfStatement);
        return ResponseEntity.ok(0L);
    }


}
