package cdx.opencdx.adr.controller;

import cdx.opencdx.adr.model.TinkarConceptModel;
import cdx.opencdx.adr.service.OpenCDXAdrService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/query", produces = MediaType.APPLICATION_JSON_VALUE)
public class QueryController {

    private final OpenCDXAdrService adrService;

    public QueryController(OpenCDXAdrService adrService) {
        this.adrService = adrService;
    }

    @GetMapping
    public ResponseEntity<List<TinkarConceptModel>> getQueryableData() {
        log.trace("Received queryable data request");
        return ResponseEntity.ok(this.adrService.getQueryableData());
    }
}
