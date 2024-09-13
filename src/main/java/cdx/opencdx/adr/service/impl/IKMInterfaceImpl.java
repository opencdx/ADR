package cdx.opencdx.adr.service.impl;

import cdx.opencdx.adr.model.TinkarConceptModel;
import cdx.opencdx.adr.repository.TinkarConceptRepository;
import cdx.opencdx.adr.service.IKMInterface;
import dev.ikm.tinkar.common.id.IntIdSet;
import dev.ikm.tinkar.common.id.PublicId;
import dev.ikm.tinkar.common.id.PublicIds;
import dev.ikm.tinkar.common.service.*;
import dev.ikm.tinkar.coordinate.Calculators;
import dev.ikm.tinkar.coordinate.stamp.calculator.Latest;
import dev.ikm.tinkar.coordinate.stamp.calculator.StampCalculatorWithCache;
import dev.ikm.tinkar.coordinate.view.calculator.ViewCalculator;
import dev.ikm.tinkar.entity.*;
import dev.ikm.tinkar.provider.search.Searcher;
import dev.ikm.tinkar.terms.EntityProxy;
import dev.ikm.tinkar.terms.TinkarTerm;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.LongConsumer;

import static dev.ikm.tinkar.provider.search.Searcher.LIDR_RECORD_PATTERN;

/**
 * Implementation class for IKMInterface.
 * Provides various methods to retrieve information from a data store.
 */
@Slf4j
public class IKMInterfaceImpl implements IKMInterface, AutoCloseable {

    /**
     * A private final variable conceptModelMap is declared as a Map, mapping strings to TinkarConceptModel objects.
     */

    private final TinkarConceptRepository conceptRepository;
    /**
     * Constructs an instance of IKMInterfaceImpl with the specified pathParent and pathChild.
     *
     * @param pathParent the parent path
     * @param pathChild the child path
     *                  @param conceptRepository the concept repository
     */
    public IKMInterfaceImpl(String pathParent, String pathChild, TinkarConceptRepository conceptRepository) {
        this.conceptRepository = conceptRepository;
        log.info("Creating IKM Interface: pathParent={}, pathChild={}", pathParent, pathChild);
        if (!PrimitiveData.running()) {
            log.debug("Initializing Primitive Data");
            CachingService.clearAll();
            log.debug("Cleared all caches");
            ServiceProperties.set(ServiceKeys.DATA_STORE_ROOT, new File(pathParent, pathChild));
            log.debug("Set data store root");
            PrimitiveData.selectControllerByName(ARRAY_STORE_TO_OPEN);
            log.debug("Selected controller by name");
            PrimitiveData.start();
            log.debug("Primitive data started");
        }

        addConceptIfMissing("ec55b876-1200-4470-abbc-878a3fa57bfb","Presence of COVID","Presence of COVID");
        addConceptIfMissing("e2e79d53-7a29-4f64-9322-5065eec84985","Covid-19 Test Kits (Lookup)","Covid-19 Test Kits (Lookup)");
        addConceptIfMissing("0b44d8e9-2aff-4f00-965c-9d7d42226d57","Body Mass Index (Lookup)","Body Mass Index (Lookup)");

        test();
    }
    @Override
    public void close() {
        log.info("Closing IKM Interface");
        if (PrimitiveData.running()) {
            log.debug("Stopping Primitive Data");
            PrimitiveData.stop();
            log.debug("Primitive data stopped");
        }
    }



    public void test() {

        this.memberOf(TinkarTerm.TINKAR_BASE_MODEL_COMPONENT_PATTERN);

        PublicId lidrRecordId = PublicIds.newRandom();

        StampCalculatorWithCache stampCalc = Calculators.Stamp.DevelopmentLatest();
        Latest<PatternEntityVersion> latestPatternVersion = stampCalc.latest(LIDR_RECORD_PATTERN);
        List<PublicId> resultConformanceList = new ArrayList<>();

        if (PrimitiveData.get().hasPublicId(lidrRecordId)) {
            EntityService.get().getEntity(lidrRecordId.asUuidArray()).ifPresent((lidrRecordEntity) -> {
                Latest<EntityVersion> latestLidrRecordVersion = stampCalc.latest(lidrRecordEntity);

                if (latestLidrRecordVersion.get() instanceof SemanticEntityVersion latestLidrRecordSemanticVersion) {
                    IntIdSet targetNids = latestPatternVersion.get().getFieldWithMeaning(TinkarTerm.TARGET, latestLidrRecordSemanticVersion);
                    targetNids.map(PrimitiveData::publicId).forEach(resultConformanceList::add);
                }
            });
        }

        resultConformanceList.forEach(result -> {
            List<String> strings = this.descriptionsOf(Collections.singletonList(result));
            log.info("LIDR ID: {}, Description: {}", result, strings.getFirst());
        });
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
     * Determines the list of PublicId objects to which the given member belongs.
     *
     * @param member the PublicId object whose group memberships are to be found
     * @return a list of PublicId objects representing the groups to which the specified member belongs
     */
    @Override
    public List<PublicId> memberOf(PublicId member) {
        ArrayList<PublicId> memberOfList = new ArrayList<>();

        // BMI
        if(member.asUuidArray() != null && member.asUuidArray().length > 0 && member.asUuidArray()[0].equals(UUID.fromString("0b44d8e9-2aff-4f00-965c-9d7d42226d57")) ) {

            EntityProxy.Pattern valueConstraintPattern = TinkarTerm.VALUE_CONSTRAINT_PATTERN;
            StampCalculatorWithCache stampCalc = Calculators.Stamp.DevelopmentLatest();

            PrimitiveData.get().forEachSemanticNidOfPattern(valueConstraintPattern.nid(), (valueConstraintSemanticNid) -> {
                Latest<SemanticEntityVersion> latestValueConstraintSemanticVersion = stampCalc.latest(valueConstraintSemanticNid);
                memberOfList.add(latestValueConstraintSemanticVersion.get().publicId());
            });

        }
//        // Presense of Covid
//        else if(member.asUuidArray() != null && member.asUuidArray().length > 0 && member.asUuidArray()[0].equals(UUID.fromString("ec55b876-1200-4470-abbc-878a3fa57bfb")) ) {
//
//        }
//        // Covid 19 Test Kits
//        else if(member.asUuidArray() != null && member.asUuidArray().length > 0 && member.asUuidArray()[0].equals(UUID.fromString("e2e79d53-7a29-4f64-9322-5065eec84985")) ) {
//
//        }
        else if (PrimitiveData.get().hasPublicId(member)) {
            EntityService.get().getEntity(member.asUuidArray()).ifPresent((entity) -> {
                if (entity instanceof PatternEntity<?> patternEntity) {
                    EntityService.get().forEachSemanticOfPattern(patternEntity.nid(), (semanticEntityOfPattern) ->
                            memberOfList.add(semanticEntityOfPattern.referencedComponent().publicId()));
                }
            });
        }

        if(log.isInfoEnabled()) {
            memberOfList.forEach(memberOf -> {
                List<String> strings = this.descriptionsOf(Collections.singletonList(memberOf));
                log.info("Member ID: {}, Description: {}", memberOf.asUuidArray()[0], strings.getFirst());
            });
        }

        return memberOfList;
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

    /**
     * Retrieves the concept for a given PublicId.
     *
     * @param device The device for which to retrieve the PublicId.
     * @return The PublicId of the given device.
     */
    @Override
    public PublicId getPublicIdForDevice(String device) {

        ViewCalculator viewCalc = Calculators.View.Default();
        Latest<PatternEntityVersion> latestIdPattern = viewCalc.latestPatternEntityVersion(TinkarTerm.IDENTIFIER_PATTERN);
        AtomicReference<PublicId> result = new AtomicReference<>();

        try {
            EntityService.get().forEachSemanticOfPattern(TinkarTerm.IDENTIFIER_PATTERN.nid(), (semanticEntity) -> {
                viewCalc.latest(semanticEntity).ifPresent(latestSemanticVersion -> {
                    String idValue = latestIdPattern.get().getFieldWithMeaning(TinkarTerm.IDENTIFIER_VALUE, latestSemanticVersion);
                    if (idValue.equals(device)) {
                        result.set(latestSemanticVersion.referencedComponent().publicId());
                    }
                });
            });
        } catch (Exception e) {
            log.error("Encountered exception {}", e.getMessage());
        }

        if(result.get() != null) {
            return result.get();
        }

        return null;
    }

    private void addConceptIfMissing(String conceptId, String conceptName, String conceptDescription) {
        UUID concept = UUID.fromString(conceptId);
        if (!this.conceptRepository.existsByConceptId(concept)) {
            this.conceptRepository.save(new TinkarConceptModel(concept, conceptName, conceptDescription,true));
        }
    }
}
