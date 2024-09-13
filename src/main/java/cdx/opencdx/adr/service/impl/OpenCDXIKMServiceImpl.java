package cdx.opencdx.adr.service.impl;

import cdx.opencdx.adr.model.TinkarConceptModel;
import cdx.opencdx.adr.repository.TinkarConceptRepository;
import cdx.opencdx.adr.service.IKMInterface;
import cdx.opencdx.adr.service.OpenCDXIKMService;
import cdx.opencdx.grpc.data.LogicalExpression;
import dev.ikm.tinkar.common.id.PublicId;
import dev.ikm.tinkar.common.id.PublicIds;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The OpenCDXIKMServiceImpl class is a service implementation that initializes and populates a concept model map.
 * <p>
 * The concept model map is a mapping of concept codes to concept models. Concept models contain the UUID, description, and display labels of concepts.
 */
@Service
@Slf4j
public class OpenCDXIKMServiceImpl implements OpenCDXIKMService {

    /**
     * A private final variable conceptModelMap is declared as a Map, mapping strings to TinkarConceptModel objects.
     */

    private final TinkarConceptRepository conceptRepository;

    private final IKMInterface ikmInterface;


    /**
     * OpenCDXIKMServiceImpl is a service class that initializes and populates a concept model map.
     * The concept model map is a mapping of concept codes to concept models.
     * Concept models contain the UUID, description, and display labels of concepts.
     */
    public OpenCDXIKMServiceImpl(TinkarConceptRepository conceptRepository, IKMInterface ikmInterface) {
        this.conceptRepository = conceptRepository;
        this.ikmInterface = ikmInterface;
    }

    /**
     * Returns the TinkarConceptModel associated with the given LogicalExpressionModel.
     *
     * @param logicalExpression the LogicalExpressionModel for which to retrieve the TinkarConceptModel
     * @return the TinkarConceptModel associated with the given LogicalExpressionModel, or null if not found
     */
    @Override
    public TinkarConceptModel getInkarConceptModel(LogicalExpression logicalExpression) {
        TinkarConceptModel result;
        UUID conceptId = this.testAndConvert(logicalExpression.getExpression());

        if(conceptId != null) {
            result = conceptRepository.findByConceptId(conceptId);
        } else {
            result = conceptRepository.findByConceptDescription(logicalExpression.getExpression());
        }

        if(result == null && logicalExpression.getExpression() != null && !logicalExpression.getExpression().isEmpty()) {
            result = new TinkarConceptModel();
            result.setConceptDescription(logicalExpression.getExpression());

            PublicId publicId;
            if(conceptId != null) {
                publicId = PublicIds.of(conceptId);
            } else {
                log.info("Creating PublicId for: {}", logicalExpression.getExpression());
                publicId = this.ikmInterface.getPublicId(logicalExpression.getExpression());
                log.info("Created PublicId: {}", publicId.asUuidArray()[0]);
            }
            if(publicId != null) {
                result.setConceptId(publicId.asUuidArray()[0]);
                List<String> descriptions = this.ikmInterface.descriptionsOf(List.of(publicId));
                if(descriptions != null && !descriptions.isEmpty() && !descriptions.getFirst().isEmpty()) {
                    result.setSync(true);
                    result.setConceptName(descriptions.getFirst());
                } else {
                    result.setSync(false);
                    result.setConceptName(logicalExpression.getExpression());
                }

            } else {
                if(conceptId != null) {
                    result.setConceptId(conceptId);
                } else {
                    result.setConceptId(UUID.randomUUID());
                    log.debug("Concept not found: \"{}\" assign to UUID: {}", result.getConceptName(),result.getConceptId());
                }
                result.setSync(false);
                result.setConceptName(logicalExpression.getExpression());
                result.setConceptDescription(logicalExpression.getExpression());
                log.warn("Concept not found: \"{}\" assign to UUID: {}", result.getConceptName(),result.getConceptId());
            }
            result = conceptRepository.save(result);
        }

        return result;
    }

    @Override
    public TinkarConceptModel getInkarConceptModelForDevice(String deviceId) {
        TinkarConceptModel result = conceptRepository.findByConceptDescription(deviceId);

        if(result == null) {

            result = new TinkarConceptModel();
            result.setConceptDescription(deviceId);

            PublicId publicId = this.ikmInterface.getPublicIdForDevice(deviceId);

            if(publicId != null) {
                result.setConceptId(publicId.asUuidArray()[0]);
                List<String> descriptions = this.ikmInterface.descriptionsOf(List.of(publicId));
                if(descriptions != null && !descriptions.isEmpty() && !descriptions.getFirst().isEmpty()) {
                    result.setConceptName(descriptions.getFirst());
                    result.setSync(true);
                } else {
                    result.setSync(false);
                    result.setConceptName(deviceId);
                }

            } else {
               result.setConceptId(UUID.randomUUID());
                result.setSync(false);
                result.setConceptName(deviceId);
                result.setConceptDescription(deviceId);
                log.warn("Concept not found: \"{}\" assign to UUID: {}", result.getConceptName(),result.getConceptId());
            }
            result = conceptRepository.save(result);
        }

        return result;
    }

    private UUID testAndConvert(String string) {
        // Regular expression pattern for UUID
        Pattern uuidPattern = Pattern.compile(
                "[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}");

        Matcher matcher = uuidPattern.matcher(string);
        if (matcher.find()) {
            // If finds match then convert it to UUID
            String uuidString = matcher.group(0);
            try {
                return UUID.fromString(uuidString);
            } catch (IllegalArgumentException e) {
                log.error("Error converting string to UUID: {}" ,string);
                return null;
            }
        }
        return null;
    }

    @Scheduled(cron = "0 */5 * * * *")
    public void syncConcepts() {
        List<TinkarConceptModel> requireSync = this.conceptRepository.findAllBySyncFalse();

        requireSync.forEach(concept -> {
            PublicId publicId = this.ikmInterface.getPublicId(concept.getConceptDescription());
            log.info("Created PublicId: {}", publicId.asUuidArray()[0]);
            if(publicId != null) {
                concept.setConceptId(publicId.asUuidArray()[0]);
                List<String> descriptions = this.ikmInterface.descriptionsOf(List.of(publicId));
                if(descriptions != null && !descriptions.isEmpty() && !descriptions.getFirst().isEmpty()) {
                    concept.setConceptName(descriptions.getFirst());
                    concept.setSync(true);
                } else {
                    log.warn("Concept not found: \"{}\" assign to UUID: {}", concept.getConceptName(),concept.getConceptId());
                    concept.setSync(false);
                }
                conceptRepository.save(concept);
            }
        });
    }
}
