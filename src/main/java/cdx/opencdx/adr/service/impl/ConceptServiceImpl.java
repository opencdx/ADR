package cdx.opencdx.adr.service.impl;

import cdx.opencdx.adr.model.TinkarConceptModel;
import cdx.opencdx.adr.service.ConceptService;
import cdx.opencdx.adr.service.IKMInterface;
import dev.ikm.tinkar.common.id.PublicId;
import dev.ikm.tinkar.common.id.PublicIds;
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

    /**
     *
     */
    private final IKMInterface ikmInterface;

    /**
     * Constructs an instance of the ConceptServiceImpl class with the provided IKMInterface object.
     *
     * @param ikmInterface The IKMInterface object used for retrieving concept information.
     */
    public ConceptServiceImpl(IKMInterface ikmInterface) {
        this.ikmInterface = ikmInterface;
    }

    @Override
    public List<UUID> getFocusConcepts(TinkarConceptModel conceptModel) {

        log.info("Retrieving focus concepts {} for concept model: {}", conceptModel.getFocus(), conceptModel.getConceptId());
        return switch (conceptModel.getFocus()) {
            case SELF, DATE -> List.of(conceptModel.getConceptId());
            case DESCENDANTS -> this.ikmInterface.descendantsOf(PublicIds.of(conceptModel.getConceptId())).stream().map(publicId -> publicId.asUuidArray()[0]).toList();
            case DESCENDANTS_OR_SELF -> {
                List<UUID> descendants = new java.util.ArrayList<>(this.ikmInterface.descendantsOf(PublicIds.of(conceptModel.getConceptId())).stream().map(publicId -> publicId.asUuidArray()[0]).toList());
                descendants.add(conceptModel.getConceptId());
                yield descendants;
            }
            case CHILDREN -> this.ikmInterface.childrenOf(PublicIds.of(conceptModel.getConceptId())).stream().map(publicId -> publicId.asUuidArray()[0]).toList();
            case CHILDREN_OR_SELF -> {
                List<UUID> children = new java.util.ArrayList<>(this.ikmInterface.childrenOf(PublicIds.of(conceptModel.getConceptId())).stream().map(publicId -> publicId.asUuidArray()[0]).toList());
                children.add(conceptModel.getConceptId());
                yield children;
            }
            case MEMBER -> this.ikmInterface.memberOf(PublicIds.of(conceptModel.getConceptId())).stream().map(publicId -> publicId.asUuidArray()[0]).toList();

            //TODO: Implement
            case ANCESTORS -> List.of(UUID.randomUUID());
            case ANCESTORS_OR_SELF -> List.of(UUID.randomUUID(),conceptModel.getConceptId());
            case PARENT -> List.of(UUID.randomUUID());
            case PARENT_OR_SELF -> List.of(UUID.randomUUID(),conceptModel.getConceptId());
        };
    }
}
