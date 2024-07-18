package cdx.opencdx.adr.controller;

import cdx.opencdx.adr.service.OpenCDXAdrService;
import cdx.opencdx.grpc.data.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.UUID;

/**
 * Controller for handling ANF statements.
 */
@Slf4j
@RestController
@RequestMapping(value = "/anf", produces = MediaType.APPLICATION_JSON_VALUE)
public class AnfController {

    private final ObjectMapper objectMapper;
    private final OpenCDXAdrService openCDXAdrService;

    /**
     * Constructor for the controller.
     * @param objectMapper Object Mapper for conversion
     * @param openCDXAdrService ADR Service to process
     * @throws JsonProcessingException Exception if Object Mapper fails.
     */
    public AnfController(ObjectMapper objectMapper, OpenCDXAdrService openCDXAdrService) throws JsonProcessingException {
        this.objectMapper = objectMapper;
        this.openCDXAdrService = openCDXAdrService;

        UUID nhId = UUID.randomUUID();
        UUID practitionerId = UUID.randomUUID();
        Instant now = Instant.now();

        ANFStatement anfStatement = ANFStatement.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setSubjectOfRecord(Participant.newBuilder()
                        .setId(nhId.toString())
                        .setCode(LogicalExpression.newBuilder().setExpression("363698007 |National identifier|")))
                .addAuthors(Practitioner.newBuilder()
                        .setId(practitionerId.toString())
                        .setCode(LogicalExpression.newBuilder().setExpression("311287001 | General practitioner (role)")))
                .setSubjectOfInformation(LogicalExpression.newBuilder()
                        .setExpression(nhId +  " |Identifier| : 363704007 |Associated with| = 363698007 |National identifier|")
                        .build())
                .setTime(Measure.newBuilder()
                        .setResolution("1")
                        .setIncludeUpperBound(true)
                        .setIncludeLowerBound(true)
                        .setSemantic(LogicalExpression.newBuilder().setExpression("41063100 | Second (qualifier value)"))
                        .setLowerBound(Long.toString(now.getEpochSecond()))
                        .setUpperBound(Long.toString(now.getEpochSecond())))
                .setTopic(LogicalExpression.newBuilder().setExpression("423493009 | Age at diagnosis (observable entity)"))
                .setType(LogicalExpression.newBuilder().setExpression("423493009 | Age at diagnosis (observable entity)"))
                .setPerformanceCircumstance(PerformanceCircumstance.newBuilder()
                        .setStatus(LogicalExpression.newBuilder().setExpression("260271001 | Completed (qualifier value)").build())
                        .addParticipant(Participant.newBuilder()
                                .setId(practitionerId.toString())
                                .setCode(LogicalExpression.newBuilder().setExpression("311287001 | General practitioner (role)").build()))
                        .addPurpose(LogicalExpression.newBuilder().setExpression("386053000 |Evaluation procedure (procedure)|"))
                        .setTiming(Measure.newBuilder()
                                .setResolution("1")
                                .setIncludeUpperBound(true)
                                .setIncludeLowerBound(true)
                                .setSemantic(LogicalExpression.newBuilder().setExpression("41063100 | Second (qualifier value)"))
                                .setLowerBound(Long.toString(now.getEpochSecond()))
                                .setUpperBound(Long.toString(now.getEpochSecond())))
                        .setResult(Measure.newBuilder()
                                .setLowerBound("22")
                                .setUpperBound("22")
                                .setIncludeLowerBound(true)
                                .setIncludeUpperBound(true)
                                .setResolution("1")
                                .setSemantic(LogicalExpression.newBuilder().setExpression("41063000 | Year (qualifier value)"))))
                .build();

        log.info("Sample ANF statement: \n{}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(anfStatement));
    }

    /**
     * Post an ANF statement.
     * @param data String data to post
     * @return ResponseEntity<Long> with the response
     * @throws JsonProcessingException Exception if Object Mapper fails.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> postANFStatement(@RequestBody String data ) throws JsonProcessingException {
        ANFStatement anfStatement = objectMapper.readValue(data, ANFStatement.class);
        this.openCDXAdrService.storeAnfStatement(anfStatement);
        return ResponseEntity.ok(0L);
    }


}
