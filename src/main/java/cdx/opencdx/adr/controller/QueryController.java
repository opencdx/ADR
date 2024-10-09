package cdx.opencdx.adr.controller;


import cdx.opencdx.adr.dto.ADRQuery;
import cdx.opencdx.adr.dto.SavedQuery;
import cdx.opencdx.adr.model.TinkarConceptModel;
import cdx.opencdx.adr.service.IKMInterface;
import cdx.opencdx.adr.service.OpenCDXAdrService;
import com.fasterxml.jackson.core.JsonProcessingException;
import dev.ikm.tinkar.common.service.PrimitiveData;
import dev.ikm.tinkar.entity.EntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.List;

/**
 * The QueryController class is responsible for handling queries.
 */
@Slf4j
@RestController
@RequestMapping(value = "/query", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin
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

    private final IKMInterface ikmInterface;

    /**
     * The QueryController class is responsible for handling queries.
     */
    public QueryController(OpenCDXAdrService adrService, IKMInterface ikmInterface) {
        this.adrService = adrService;
        this.ikmInterface = ikmInterface;
    }

    /**
     * Retrieves the queryable data.
     *
     * @return A {@link ResponseEntity} containing a list of {@link TinkarConceptModel} objects.
     */
    @GetMapping
    public ResponseEntity<List<TinkarConceptModel>> getQueryableData() {
        log.trace("Received Available Criteria request");
        return ResponseEntity.ok(this.adrService.getQueryableData());
    }

    @GetMapping("/units")
    public ResponseEntity<List<TinkarConceptModel>> getUnits() {
        log.trace("Received Units request");
        return ResponseEntity.ok(this.adrService.getUnits());
    }


    @PostMapping
    public ResponseEntity<List<String>> postQuery(@RequestBody ADRQuery adrQuery) {
        log.info("Received query request");
        return ResponseEntity.ok(adrService.streamQuery(adrQuery));
    }

    @PostMapping("/csv")
    public ResponseEntity<byte[]> queryCSV(@RequestBody ADRQuery adrQuery) {
        List<String> data = adrService.streamQuery(adrQuery);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(outputStream);

        data.forEach(writer::println);

        writer.flush();

        byte[] csvContent = outputStream.toByteArray();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.setContentDispositionFormData("attachment",
                "results.csv");

        return new ResponseEntity<>(csvContent, headers, HttpStatus.OK);
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

    @PutMapping("/update")
    public ResponseEntity<SavedQuery> updateQuery(@RequestBody SavedQuery save) throws JsonProcessingException {
        log.info("Received save query request");
        return ResponseEntity.ok(adrService.updateQuery(save));
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

    @GetMapping("/search")
    public ResponseEntity<List<TinkarConceptModel>> search(@RequestParam String search) {
        return ResponseEntity.ok(ikmInterface.search(search, 30).stream().map(publicId -> {
            TinkarConceptModel model = new TinkarConceptModel();
            model.setConceptName(PrimitiveData.textOptional(EntityService.get().nidForPublicId(publicId)).orElse(null));
            model.setConceptDescription(ikmInterface.descriptionsOf(List.of(publicId)).getFirst());
            model.setConceptId(publicId.asUuidArray()[0]);

            return model;
        }).toList());
    }
}