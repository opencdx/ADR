package cdx.opencdx.adr.service;

import cdx.opencdx.adr.model.TinkarConceptModel;

import java.util.UUID;

public interface OpenCDXIkmService {
    TinkarConceptModel getTinkarConcept(UUID conceptId);
    UUID getTinkarConceptForSnowmed(String number);
}
