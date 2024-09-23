package cdx.opencdx.adr.service.impl;

import cdx.opencdx.adr.model.TinkarConceptModel;
import cdx.opencdx.adr.service.ConceptService;
import cdx.opencdx.adr.service.IKMInterface;
import dev.ikm.tinkar.common.id.PublicIds;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

        log.debug("Retrieving focus concepts {} for concept model: {}", conceptModel.getFocus(), conceptModel.getConceptId());
        List<UUID> uuids = switch (conceptModel.getFocus()) {
            case SELF, DATE -> List.of(conceptModel.getConceptId());
            case DESCENDANTS ->
                    this.ikmInterface.descendantsOf(PublicIds.of(conceptModel.getConceptId())).stream().map(publicId -> publicId.asUuidArray()[0]).toList();
            case DESCENDANTS_OR_SELF -> {
                List<UUID> descendants = new java.util.ArrayList<>(this.ikmInterface.descendantsOf(PublicIds.of(conceptModel.getConceptId())).stream().map(publicId -> publicId.asUuidArray()[0]).toList());
                descendants.add(conceptModel.getConceptId());
                yield descendants;
            }
            case CHILDREN ->
                    this.ikmInterface.childrenOf(PublicIds.of(conceptModel.getConceptId())).stream().map(publicId -> publicId.asUuidArray()[0]).toList();
            case CHILDREN_OR_SELF -> {
                List<UUID> children = new java.util.ArrayList<>(this.ikmInterface.childrenOf(PublicIds.of(conceptModel.getConceptId())).stream().map(publicId -> publicId.asUuidArray()[0]).toList());
                children.add(conceptModel.getConceptId());
                yield children;
            }

            case MEMBER ->
                    this.ikmInterface.memberOf(PublicIds.of(conceptModel.getConceptId())).stream().map(publicId -> publicId.asUuidArray()[0]).toList();

            //TODO: Implement
            case ANCESTORS -> this.ikmInterface.ancestorOf(PublicIds.of(conceptModel.getConceptId())).stream().map(publicId -> publicId.asUuidArray()[0]).toList();
            case ANCESTORS_OR_SELF -> {
                List<UUID> ancestors = new java.util.ArrayList<>(this.ikmInterface.ancestorOf(PublicIds.of(conceptModel.getConceptId())).stream().map(publicId -> publicId.asUuidArray()[0]).toList());
                ancestors.add(conceptModel.getConceptId());
                yield ancestors;
            }
            case PARENT -> this.ikmInterface.parentsOf(PublicIds.of(conceptModel.getConceptId())).stream().map(publicId -> publicId.asUuidArray()[0]).toList();
            case PARENT_OR_SELF -> {
                List<UUID> parents = new java.util.ArrayList<>(this.ikmInterface.parentsOf(PublicIds.of(conceptModel.getConceptId())).stream().map(publicId -> publicId.asUuidArray()[0]).toList());
                parents.add(conceptModel.getConceptId());
                yield parents;
            }
        };

        if(log.isInfoEnabled()) {
            log.info("Retrieved focus concepts {} for concept model: {}", uuids.stream()
                    .map(UUID::toString)
                    .collect(Collectors.joining(", ")), conceptModel.getConceptId());
        }
        return uuids;
    }
}
