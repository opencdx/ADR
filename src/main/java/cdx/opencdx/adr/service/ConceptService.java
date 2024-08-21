package cdx.opencdx.adr.service;

import cdx.opencdx.adr.model.TinkarConceptModel;

import java.util.List;
import java.util.UUID;

/**
 * ConceptService is an interface that defines the methods for retrieving focus concepts associated with a TinkarConceptModel.
 */
public interface ConceptService {
    /**
     * Retrieves the focus concepts associated with the given TinkarConceptModel.
     *
     * @param conceptModel The TinkarConceptModel for which to retrieve the focus concepts.
     * @return A List of UUIDs representing the focus concepts.
     */
    public List<UUID> getFocusConcepts(TinkarConceptModel conceptModel);
}
