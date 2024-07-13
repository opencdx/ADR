package cdx.opencdx.adr.service;

import cdx.opencdx.adr.model.TinkarConceptModel;

import java.util.UUID;

/**
 * Interface for the OpenCDXIkmService
 */
public interface OpenCDXIkmService {
    /**
     * Get the Tinkar Concept by ID
     * @param conceptId
     * @return Tinkar Concept
     */
    TinkarConceptModel getTinkarConcept(UUID conceptId);
    /** Get the Tinkar Concept by SNOMED number
     * @return Tinkar Concept
     */
    UUID getTinkarConceptForSnowmed(String number);
}
