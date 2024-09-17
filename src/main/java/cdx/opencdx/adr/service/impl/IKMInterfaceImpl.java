package cdx.opencdx.adr.service.impl;

import cdx.opencdx.adr.model.MeasureModel;
import cdx.opencdx.adr.model.TinkarConceptModel;
import cdx.opencdx.adr.repository.TinkarConceptRepository;
import cdx.opencdx.adr.service.IKMInterface;
import cdx.opencdx.adr.service.OpenCDXIKMService;
import dev.ikm.tinkar.common.id.PublicId;
import dev.ikm.tinkar.common.service.CachingService;
import dev.ikm.tinkar.common.service.PrimitiveData;
import dev.ikm.tinkar.common.service.ServiceKeys;
import dev.ikm.tinkar.common.service.ServiceProperties;
import dev.ikm.tinkar.coordinate.Calculators;
import dev.ikm.tinkar.coordinate.stamp.calculator.Latest;
import dev.ikm.tinkar.coordinate.stamp.calculator.StampCalculatorWithCache;
import dev.ikm.tinkar.coordinate.view.calculator.ViewCalculator;
import dev.ikm.tinkar.entity.EntityService;
import dev.ikm.tinkar.entity.PatternEntity;
import dev.ikm.tinkar.entity.PatternEntityVersion;
import dev.ikm.tinkar.entity.SemanticEntityVersion;
import dev.ikm.tinkar.provider.search.Searcher;
import dev.ikm.tinkar.terms.EntityProxy;
import dev.ikm.tinkar.terms.TinkarTerm;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.LongConsumer;

/**
 * Implementation class for IKMInterface.
 * Provides various methods to retrieve information from a data store.
 */
@Slf4j
public class IKMInterfaceImpl implements IKMInterface, AutoCloseable {

    /**
     * The name of the controller used to open SpinedArrayStore.
     */
    private static final String ARRAY_STORE_TO_OPEN = "Open SpinedArrayStore";
    /**
     * A private final variable conceptModelMap is declared as a Map, mapping strings to TinkarConceptModel objects.
     */

    private final TinkarConceptRepository conceptRepository;

    /**
     * Constructs an instance of IKMInterfaceImpl with the specified pathParent and pathChild.
     *
     * @param pathParent        the parent path
     * @param pathChild         the child path
     * @param conceptRepository the concept repository
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

        addConceptIfMissing(OpenCDXIKMService.COVID_PRESENCE, "Presence of COVID", "Presence of COVID");
        addConceptIfMissing(OpenCDXIKMService.COVID_TEST_KITS, "Covid-19 Test Kits (Lookup)", "Covid-19 Test Kits (Lookup)");
        addConceptIfMissing(OpenCDXIKMService.BMI_PATTERN, "Body Mass Index (Lookup)", "Body Mass Index (Lookup)");
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

    /**
     * Returns a list of descendant concept IDs of the given parent concept ID.
     *
     * @param parentConceptId The parent concept ID.
     * @return A list of descendant concept IDs.
     */
    @Override
    public List<PublicId> descendantsOf(PublicId parentConceptId) {
        List<PublicId> descendents = Searcher.descendantsOf(parentConceptId);

        if (log.isInfoEnabled()) {
            log.info("Descendents of ID: {}, Description: {}", parentConceptId.asUuidArray()[0], this.descriptionsOf(Collections.singletonList(parentConceptId)).get(0));
            descendents.forEach(memberOf -> {
                List<String> strings = this.descriptionsOf(Collections.singletonList(memberOf));
                log.info("Descendent ID: {}, Description: {}", memberOf.asUuidArray()[0], strings.getFirst());
            });
        }

        return descendents;
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
        if (PrimitiveData.get().hasPublicId(member)) {
            EntityService.get().getEntity(member.asUuidArray()).ifPresent((entity) -> {
                if (entity instanceof PatternEntity<?> patternEntity) {
                    EntityService.get().forEachSemanticOfPattern(patternEntity.nid(), (semanticEntityOfPattern) ->
                            memberOfList.add(semanticEntityOfPattern.referencedComponent().publicId()));
                }
            });
        }

        if (log.isInfoEnabled()) {
            log.info("Members for Member ID: {}, Description: {}", member.asUuidArray()[0], this.descriptionsOf(Collections.singletonList(member)).get(0));
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

        List<PublicId> descendents = Searcher.childrenOf(parentConceptId);

        if (log.isInfoEnabled()) {
            log.info("Children of ID: {}, Description: {}", parentConceptId.asUuidArray()[0], this.descriptionsOf(Collections.singletonList(parentConceptId)).get(0));
            descendents.forEach(memberOf -> {
                List<String> strings = this.descriptionsOf(Collections.singletonList(memberOf));
                log.info("Child ID: {}, Description: {}", memberOf.asUuidArray()[0], strings.getFirst());
            });
        }

        return descendents;
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
    public PublicId getPublicId(String concept) {
        return new PublicId() {
            @Override
            public UUID[] asUuidArray() {
                return new UUID[]{UUID.fromString(concept)};
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

        if (result.get() != null) {
            return result.get();
        }

        return null;
    }

    private void addConceptIfMissing(String conceptId, String conceptName, String conceptDescription) {
        UUID concept = UUID.fromString(conceptId);
        if (!this.conceptRepository.existsByConceptId(concept)) {
            this.conceptRepository.save(new TinkarConceptModel(concept, conceptName, conceptDescription, true));
        }
    }

    public MeasureModel syncConstraintRanges(UUID topic, MeasureModel measureModel) {
        EntityProxy.Pattern valueConstraintPattern = TinkarTerm.VALUE_CONSTRAINT_PATTERN;
        StampCalculatorWithCache stampCalc = Calculators.Stamp.DevelopmentLatest();
        List<SemanticEntityVersion> latestSemanticList = new ArrayList<>();

        PrimitiveData.get().forEachSemanticNidOfPattern(valueConstraintPattern.nid(), (valueConstraintSemanticNid) -> {
            Latest<SemanticEntityVersion> latestValueConstraintSemanticVersion = stampCalc.latest(valueConstraintSemanticNid);
            latestSemanticList.add(latestValueConstraintSemanticVersion.get());
        });

        Latest<PatternEntityVersion> latestPatternVersion = stampCalc.latest(valueConstraintPattern);

        latestSemanticList.forEach(valueConstraintSemantic -> {

            UUID[] array = valueConstraintSemantic.referencedComponent().publicId().asUuidArray();

            // Search the entire Array for measureModel.getSemantic().getConceptId()
            for(UUID id : array) {
                if (id.equals(topic)) {
                    EntityProxy.Concept minOperator = latestPatternVersion.get().getFieldWithMeaning(TinkarTerm.MINIMUM_VALUE_OPERATOR, valueConstraintSemantic);
                    float refRangeMin = latestPatternVersion.get().getFieldWithMeaning(TinkarTerm.REFERENCE_RANGE_MINIMUM, valueConstraintSemantic);

                    EntityProxy.Concept maxOperator = latestPatternVersion.get().getFieldWithMeaning(TinkarTerm.MAXIMUM_VALUE_OPERATOR, valueConstraintSemantic);
                    float refRangeMax = latestPatternVersion.get().getFieldWithMeaning(TinkarTerm.REFERENCE_RANGE_MAXIMUM, valueConstraintSemantic);

                    log.info("Concept: {}  Min:{}  Max:{}", id, refRangeMin, refRangeMax);
                    measureModel.setLowerBound((double) refRangeMin);
                    measureModel.setUpperBound((double) refRangeMax);
                }
            }

        });

        return measureModel;
    }
}
