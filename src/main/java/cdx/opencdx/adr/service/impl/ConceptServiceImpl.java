package cdx.opencdx.adr.service.impl;

import cdx.opencdx.adr.model.TinkarConceptModel;
import cdx.opencdx.adr.service.ConceptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * The ConceptServiceImpl class is an implementation of the ConceptService interface.
 * It provides methods to retrieve focus concepts associated with a TinkarConceptModel.
 */
@Slf4j
@Service
public class ConceptServiceImpl implements ConceptService {

    @Override
    public List<UUID> getFocusConcepts(TinkarConceptModel conceptModel) {

        log.info("Retrieving focus concepts {} for concept model: {}", conceptModel.getFocus(), conceptModel.getConceptId());
        return switch (conceptModel.getFocus()) {
            case SELF, DATE -> List.of(conceptModel.getConceptId());
            case DESCENDANTS -> List.of(UUID.randomUUID());
            case DESCENDANTS_OR_SELF -> List.of(UUID.randomUUID(),conceptModel.getConceptId());
            case ANCESTORS -> List.of(UUID.randomUUID());
            case ANCESTORS_OR_SELF -> List.of(UUID.randomUUID(),conceptModel.getConceptId());
            case CHILDREN -> List.of(UUID.randomUUID());
            case CHILDREN_OR_SELF -> List.of(UUID.randomUUID(),conceptModel.getConceptId());
            case PARENT -> List.of(UUID.randomUUID());
            case PARENT_OR_SELF -> List.of(UUID.randomUUID(),conceptModel.getConceptId());
            case MEMBER -> List.of(UUID.randomUUID(),conceptModel.getConceptId());
        };
    }
}
