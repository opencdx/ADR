package cdx.opencdx.adr.service.impl;

import cdx.opencdx.adr.model.TinkarConceptModel;
import cdx.opencdx.adr.repository.TinkarConceptRepository;
import cdx.opencdx.adr.service.OpenCDXIkmService;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class BaseAnfProcessor {

    private final TinkarConceptRepository tinkarConceptRepository;
    private final OpenCDXIkmService openCDXTinkarLookupService;

    public BaseAnfProcessor(TinkarConceptRepository tinkarConceptRepository, OpenCDXIkmService openCDXTinkarLookupService) {
        this.tinkarConceptRepository = tinkarConceptRepository;
        this.openCDXTinkarLookupService = openCDXTinkarLookupService;
    }

    protected void loadConceptTree(UUID conceptId) {
        UUID uuid = conceptId;
        while(uuid != null && !this.tinkarConceptRepository.existsByConceptId(uuid)) {
            log.info("UUID does not exist in database: {}", uuid);
            TinkarConceptModel conceptModel = openCDXTinkarLookupService.getTinkarConcept(uuid);
            if(conceptModel != null) {
                this.tinkarConceptRepository.save(conceptModel);
                uuid = conceptModel.getParentConceptId();
            } else {
                uuid = null;
            }
        }
    }

    protected void updateConceptTree(UUID conceptId) {
        UUID uuid = conceptId;

        while(uuid != null && this.tinkarConceptRepository.existsByConceptId(uuid)) {
            log.info("UUID exists in database: {}", uuid);
            TinkarConceptModel conceptModel = this.tinkarConceptRepository.findByConceptId(uuid);
            if(conceptModel != null) {
                conceptModel.setCount(conceptModel.getCount() + 1);
                conceptModel = this.tinkarConceptRepository.save(conceptModel);
                uuid = conceptModel.getParentConceptId();
            } else {
                uuid = null;
            }
        }
    }
}
