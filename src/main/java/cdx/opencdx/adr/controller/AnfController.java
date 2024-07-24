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
 * The AnfController class is a REST controller that handles operations related to ANF statements.
 */
@Slf4j
@RestController
@RequestMapping(value = "/anf", produces = MediaType.APPLICATION_JSON_VALUE)
public class AnfController {

    /**
     * The variable objectMapper is used to map JSON strings to Java objects and vice versa.
     * It is an instance of the ObjectMapper class, which is provided by the Jackson library.
     * This variable is marked as private and final, indicating that it cannot be accessed or modified
     * outside of the class and its value cannot be changed once assigned.
     */
    private final ObjectMapper objectMapper;
    /**
     * This variable represents an instance of the OpenCDXAdrService class.
     * <p>
     * The OpenCDXAdrService class is responsible for providing functionality related to the OpenCDX Address Service.
     * <p>
     * This variable is marked as private and final to ensure that it can only be accessed and modified within the class
     * where it is declared, and that its value cannot be changed once it is assigned.
     * <p>
     * Example usage:
     * <p>
     * OpenCDXAdrService openCDXAdrService = new OpenCDXAdrService();
     * <p>
     * openCDXAdrService.someMethod();
     *
     * @see OpenCDXAdrService
     */
    private final OpenCDXAdrService openCDXAdrService;

    /**
     * This constructor initializes an instance of AnfController with the provided ObjectMapper
     * and OpenCDXAdrService. It generates a sample ANFStatement and logs it using the provided
     * ObjectMapper.
     *
     * @param objectMapper      The ObjectMapper instance used for logging the sample ANFStatement.
     * @param openCDXAdrService The OpenCDXAdrService instance to be used.
     * @throws JsonProcessingException If there is an error processing the ANFStatement as JSON.
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
                        .setPractitionerValue(Reference.newBuilder()
                                .setDisplay("Dr. John Doe")
                                .setIdentifier("123456")
                                .setReference("http://example.com")
                                .setUri("http://example.com"))
                        .setCode(LogicalExpression.newBuilder().setExpression("311287001 | General practitioner (role)")))
                .setSubjectOfInformation(LogicalExpression.newBuilder()
                        .setExpression(nhId + " |Identifier| : 363704007 |Associated with| = 363698007 |National identifier|")
                        .build())
                .setTime(Measure.newBuilder()
                        .setResolution(1.0)
                        .setIncludeUpperBound(true)
                        .setIncludeLowerBound(true)
                        .setSemantic(LogicalExpression.newBuilder().setExpression("81170007 | Second (qualifier value)"))
                        .setLowerBound(now.getEpochSecond())
                        .setUpperBound(now.getEpochSecond()))
                .setTopic(LogicalExpression.newBuilder().setExpression("423493009 | Age at diagnosis (observable entity)"))
                .setType(LogicalExpression.newBuilder().setExpression("423493009 | Age at diagnosis (observable entity)"))
                .setPerformanceCircumstance(PerformanceCircumstance.newBuilder()
                        .setStatus(LogicalExpression.newBuilder().setExpression("260271001 | Completed (qualifier value)").build())
                        .addParticipant(Participant.newBuilder()
                                .setId(practitionerId.toString())
                                .setCode(LogicalExpression.newBuilder().setExpression("311287001 | General practitioner (role)").build()))
                        .addPurpose(LogicalExpression.newBuilder().setExpression("386053000 |Evaluation procedure (procedure)|"))
                        .setTiming(Measure.newBuilder()
                                .setResolution(1.0)
                                .setIncludeUpperBound(true)
                                .setIncludeLowerBound(true)
                                .setSemantic(LogicalExpression.newBuilder().setExpression("81170007 | Second (qualifier value)"))
                                .setLowerBound(now.getEpochSecond())
                                .setUpperBound(now.getEpochSecond()))
                        .setResult(Measure.newBuilder()
                                .setLowerBound(22.0)
                                .setUpperBound(22.0)
                                .setIncludeLowerBound(true)
                                .setIncludeUpperBound(true)
                                .setResolution(1.0)
                                .setSemantic(LogicalExpression.newBuilder().setExpression("258707000 | Year (qualifier value)"))))
                .build();

        log.info("Sample ANF statement: \n{}", objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(anfStatement));
    }

    /**
     * Posts ANF statement and stores it using OpenCDXAdrService.
     *
     * @param data The ANF statement data in JSON format as a string.
     * @return A ResponseEntity object with a Long value of 0 indicating success.
     * @throws JsonProcessingException If JSON processing fails.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> postANFStatement(@RequestBody String data) throws JsonProcessingException {
        ANFStatement anfStatement = objectMapper.readValue(data, ANFStatement.class);
        this.openCDXAdrService.storeAnfStatement(anfStatement);
        return ResponseEntity.ok(0L);
    }


}
