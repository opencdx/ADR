package cdx.opencdx.adr.controller;


import cdx.opencdx.adr.dto.ADRQuery;
import cdx.opencdx.adr.dto.Query;
import cdx.opencdx.adr.dto.SavedQuery;
import cdx.opencdx.adr.model.TinkarConceptModel;
import cdx.opencdx.adr.service.OpenCDXAdrService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * The QueryController class is responsible for handling queries.
 */
@Slf4j
@RestController
@RequestMapping(value = "/query", produces = MediaType.APPLICATION_JSON_VALUE)
public class QueryController {

    /**
     * The adrService variable represents an instance of OpenCDXAdrService, which is an interface that provides methods for managing ANF statements and executing queries.
     * It is a private final variable, which means it cannot be reassigned once initialized.
     * <p>
     * The OpenCDXAdrService interface is implemented by a class that contains methods for storing ANF statements, retrieving queryable data, and executing stream queries.
     * <p>
     * The adrService variable is used in methods of the QueryController class to access the functionality provided by the OpenCDXAdrService interface.
     */
    private final OpenCDXAdrService adrService;

    /**
     * The QueryController class is responsible for handling queries.
     */
    public QueryController(OpenCDXAdrService adrService) {
        this.adrService = adrService;
    }

    /**
     * Retrieves the queryable data.
     *
     * @return A {@link ResponseEntity} containing a list of {@link TinkarConceptModel} objects.
     */
    @GetMapping
    public ResponseEntity<List<TinkarConceptModel>> getQueryableData() {
        log.trace("Received queryable data request");
        return ResponseEntity.ok(this.adrService.getQueryableData());
    }

    @GetMapping("/units")
    public ResponseEntity<List<TinkarConceptModel>> getUnits() {
        log.trace("Received queryable data request");
        return ResponseEntity.ok(this.adrService.getUnits());
    }

    /**
     * Handles POST requests for querying concepts.
     * Writes the query results to a CSV file.
     *
     * @param query    The list of {@link Query} objects representing the queries to be executed.
     * @param response The {@link HttpServletResponse} object used to write the response.
     * @throws IOException If an I/O error occurs while writing the response.
     */
    @PostMapping
    public void postQuery(@RequestBody ADRQuery adrQuery, HttpServletResponse response) throws IOException {
        log.info("Received query request");
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"generated_data.csv\"");

        try (PrintWriter writer = response.getWriter()) {
            adrService.streamQuery(adrQuery, writer);
        }
    }

    /**
     * Saves a query and returns the saved query object.
     *
     * @param save The SavedQuery object representing the query to be saved.
     * @return A ResponseEntity object containing the saved query object.
     * @throws JsonProcessingException If an error occurs while processing the query.
     */
    @PostMapping("/save")
    public ResponseEntity<SavedQuery> saveQuery(@RequestBody SavedQuery save) throws JsonProcessingException {
        log.info("Received save query request");
        return ResponseEntity.ok(adrService.saveQuery(save));
    }

    @GetMapping("/list")
    public ResponseEntity<List<SavedQuery>> listQueries() throws JsonProcessingException {
        log.info("Received list queries request");
        return ResponseEntity.ok(adrService.listSavedQueries());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuery(@PathVariable Long id) {
        log.info("Received delete query request");
        adrService.deleteSavedQuery(id);
        return ResponseEntity.ok().build();
    }
}