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
     * outside the class and its value cannot be changed once assigned.
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
     */
    public AnfController(ObjectMapper objectMapper, OpenCDXAdrService openCDXAdrService) {
        this.objectMapper = objectMapper;
        this.openCDXAdrService = openCDXAdrService;
    }

    /**
     * Posts ANF statement and stores it using OpenCDXAdrService.
     *
     * @param data The ANF statement data in JSON format as a string.
     * @return A ResponseEntity object with a Long value of 0 indicating success.
     * @throws JsonProcessingException If JSON processing fails.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long[]> postANFStatement(@RequestBody String data) throws JsonProcessingException {
        ANFStatement[] anfStatements = objectMapper.readValue(data, ANFStatement[].class);
        return ResponseEntity.ok(this.openCDXAdrService.storeAnfStatements(anfStatements));
    }


}
