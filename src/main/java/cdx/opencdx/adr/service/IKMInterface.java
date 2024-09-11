package cdx.opencdx.adr.service;

import cdx.opencdx.adr.model.TinkarConceptModel;
import dev.ikm.tinkar.common.id.PublicId;
import dev.ikm.tinkar.common.service.PrimitiveData;
import dev.ikm.tinkar.common.service.PrimitiveDataSearchResult;
import java.util.List;
import java.util.UUID;

public interface IKMInterface {
    /**
     * Returns a list of descendant concept IDs of the given parent concept ID.
     *
     * @param parentConceptId The parent concept ID.
     * @return A list of descendant concept IDs.
     */
    List<PublicId> descendantsOf(PublicId parentConceptId);


    /**
     * Returns a list of PublicIds that the given member belongs to.
     *
     * @param member The PublicId of the member.
     * @return A list of PublicIds that the member belongs to.
     */
    List<PublicId> memberOf(PublicId member);
    /**
     * Returns a list of child PublicIds of the given parent PublicId.
     *
     * @param parentConceptId the parent PublicId
     * @return a list of child PublicIds
     */
    List<PublicId> childrenOf(PublicId parentConceptId);

    /**
     * Retrieves a list of Lidr record semantics from a test kit with the given testKitConceptId.
     *
     * @param testKitConceptId the concept Id of the test kit
     * @return a list of PublicIds representing the Lidr record semantics
     */
    List<PublicId> getLidrRecordSemanticsFromTestKit(PublicId testKitConceptId);

    /**
     * Retrieves a list of PublicIds representing the result conformances from a given LidrRecordConceptId.
     *
     * @param lidrRecordConceptId The PublicId of the LidrRecordConcept.
     * @return A list of PublicIds representing the result conformances.
     */
    List<PublicId> getResultConformancesFromLidrRecord(PublicId lidrRecordConceptId);

    /**
     * Retrieves a list of allowed results from a result conformance concept ID.
     *
     * @param resultConformanceConceptId The concept ID of the result conformance.
     * @return A list of public IDs representing the allowed results.
     */
    List<PublicId> getAllowedResultsFromResultConformance(PublicId resultConformanceConceptId);

    /**
     * Retrieves the descriptions of the given concept IDs.
     *
     * @param conceptIds the list of concept IDs
     * @return the list of descriptions for the given concept IDs
     */
    List<String> descriptionsOf(List<PublicId> conceptIds);

    /**
     * Retrieves the PublicId for a given concept.
     *
     * @param concept The concept for which to retrieve the PublicId.
     * @return The PublicId of the given concept.
     */
    PublicId getPublicId(String concept);

    /**
     * Retrieves the concept for a given PublicId.
     * @param device The device for which to retrieve the PublicId.
     * @return The PublicId of the given device.
     */
    PublicId getPublicIdForDevice(String device);
}
