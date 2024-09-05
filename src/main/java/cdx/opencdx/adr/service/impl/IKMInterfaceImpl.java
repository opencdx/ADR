package cdx.opencdx.adr.service.impl;

import cdx.opencdx.adr.service.IKMInterface;
import dev.ikm.tinkar.common.id.PublicId;
import dev.ikm.tinkar.common.service.*;
import dev.ikm.tinkar.provider.search.Searcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.util.List;
import java.util.UUID;
import java.util.function.LongConsumer;
/**
 * Implementation class for IKMInterface.
 * Provides various methods to retrieve information from a data store.
 */
@Slf4j
public class IKMInterfaceImpl implements IKMInterface {
    private final String pathParent;
    private final String pathChild;

    /**
     * Constructs an instance of IKMInterfaceImpl with the specified pathParent and pathChild.
     *
     * @param pathParent the parent path
     * @param pathChild the child path
     */
    public IKMInterfaceImpl(String pathParent, String pathChild) {
        this.pathParent = pathParent;
        this.pathChild = pathChild;
    }

    @Scheduled(initialDelay = 120000, fixedDelay = Long.MAX_VALUE)
    public void init() {
        log.info("Creating IKM Interface: pathParent={}, pathChild={}", pathParent, pathChild);
        if (!PrimitiveData.running()) {
            log.info("Initializing Primitive Data");
            CachingService.clearAll();
            log.info("Cleared all caches");
            ServiceProperties.set(ServiceKeys.DATA_STORE_ROOT, new File(pathParent, pathChild));
            log.info("Set data store root");
            PrimitiveData.selectControllerByName(ARRAY_STORE_TO_OPEN);
            log.info("Selected controller by name");
            PrimitiveData.start();
            log.info("Primitive data started");
        }
    }

    /**
     * The name of the controller used to open SpinedArrayStore.
     */
    private static final String ARRAY_STORE_TO_OPEN = "Open SpinedArrayStore";

    /**
     * Returns a list of descendant concept IDs of the given parent concept ID.
     *
     * @param parentConceptId The parent concept ID.
     * @return A list of descendant concept IDs.
     */
    @Override
    public List<PublicId> descendantsOf(PublicId parentConceptId) {
        return Searcher.descendantsOf(parentConceptId);
    }

    /**
     * Returns a list of child PublicIds of the given parent PublicId.
     *
     * @param parentConceptId the parent PublicId
     * @return a list of child PublicIds
     */
    @Override
    public List<PublicId> childrenOf(PublicId parentConceptId) {
        return Searcher.childrenOf(parentConceptId);
    }

    /**
     * Retrieves a list of Lidr record semantics from a test kit with the given testKitConceptId.
     *
     * @param testKitConceptId The concept ID of the test kit.
     * @return A list of PublicIds representing the Lidr record semantics.
     */
    @Override
    public List<PublicId> getLidrRecordSemanticsFromTestKit(PublicId testKitConceptId) {
        return Searcher.getLidrRecordSemanticsFromTestKit(testKitConceptId);
    }

    /**
     * Retrieves a list of PublicIds representing the result conformances from a given LidrRecordConceptId.
     *
     * @param lidrRecordConceptId The PublicId of the LidrRecordConcept.
     * @return A list of PublicIds representing the result conformances.
     */
    @Override
    public List<PublicId> getResultConformancesFromLidrRecord(PublicId lidrRecordConceptId) {
        return Searcher.getResultConformancesFromLidrRecord(lidrRecordConceptId);
    }

    /**
     * Retrieves a list of allowed results from a result conformance concept ID.
     *
     * @param resultConformanceConceptId The concept ID of the result conformance.
     * @return A list of public IDs representing the allowed results.
     */
    @Override
    public List<PublicId> getAllowedResultsFromResultConformance(PublicId resultConformanceConceptId) {
        return Searcher.getAllowedResultsFromResultConformance(resultConformanceConceptId);
    }

    /**
     * Retrieves the descriptions of the given concept IDs.
     *
     * @param conceptIds the list of concept IDs
     * @return the list of descriptions for the given concept IDs
     */
    @Override
    public List<String> descriptionsOf(List<PublicId> conceptIds) {
        return Searcher.descriptionsOf(conceptIds);
    }

    /**
     * Retrieves the PublicId for a given concept.
     *
     * @param concept The concept for which to retrieve the PublicId.
     * @return The PublicId of the given concept.
     */
    @Override
    public  PublicId getPublicId(String concept) {
        return new PublicId() {
            @Override
            public UUID[] asUuidArray() {
                return new UUID[]{UUID.randomUUID()};
            }

            @Override
            public int uuidCount() {
                return 1;
            }

            @Override
            public void forEach(LongConsumer longConsumer) {

            }
        };
    }
}
