package cdx.opencdx.adr.service.impl;

import cdx.opencdx.adr.model.TinkarConceptModel;
import cdx.opencdx.adr.repository.TinkarConceptRepository;
import cdx.opencdx.adr.service.OpenCDXTinkarService;
import cdx.opencdx.grpc.data.ANFStatement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class OpenCDXTinkarServiceImpl implements OpenCDXTinkarService {

    private final TinkarConceptRepository tinkarConceptRepository;
    private final ObjectMapper objectMapper;

    private final Map<UUID,TinkarConceptModel> conceptMap;

    @SuppressWarnings("java:S1192")
    public OpenCDXTinkarServiceImpl(TinkarConceptRepository tinkarConceptRepository, ObjectMapper objectMapper) {
        this.tinkarConceptRepository = tinkarConceptRepository;
        this.objectMapper = objectMapper;
        conceptMap = new HashMap<>();

        conceptMap.put(UUID.fromString("b987a1f2-d10f-4b0e-9e9c-e27f22e2d8b3"), new TinkarConceptModel(null, UUID.fromString("b987a1f2-d10f-4b0e-9e9c-e27f22e2d8b3"),null, "Vitals", 0));
        conceptMap.put(UUID.fromString("ffc2d03c-e34b-4d98-a9e4-f152e2b7d4b2"), new TinkarConceptModel(null, UUID.fromString("ffc2d03c-e34b-4d98-a9e4-f152e2b7d4b2"), UUID.fromString("b987a1f2-d10f-4b0e-9e9c-e27f22e2d8b3"), "Body Mass Index", 0));
        conceptMap.put(UUID.fromString("4d21f10c-2e13-4493-86e8-d1f20a282b1c"), new TinkarConceptModel(null, UUID.fromString("4d21f10c-2e13-4493-86e8-d1f20a282b1c"), UUID.fromString("b987a1f2-d10f-4b0e-9e9c-e27f22e2d8b3"), "Height", 0));
        conceptMap.put(UUID.fromString("1a7dba9e-2076-42e9-874e-a03d0f1d022f"), new TinkarConceptModel(null, UUID.fromString("1a7dba9e-2076-42e9-874e-a03d0f1d022f"), UUID.fromString("b987a1f2-d10f-4b0e-9e9c-e27f22e2d8b3"), "Weight", 0));

        conceptMap.put(UUID.fromString("078e2b4a-f02d-492c-a2e2-2c1e2d8f079a"), new TinkarConceptModel(null, UUID.fromString("078e2b4a-f02d-492c-a2e2-2c1e2d8f079a"), null, "Root", 0));
        conceptMap.put(UUID.fromString("e81c4b8b-d81d-4e2e-b48a-7d4b20f2a7e2"), new TinkarConceptModel(null, UUID.fromString("e81c4b8b-d81d-4e2e-b48a-7d4b20f2a7e2"), null, "Root", 0));
        conceptMap.put(UUID.fromString("92f1a21e-8a06-4d2a-a0b0-e2f30b42b59d"), new TinkarConceptModel(null, UUID.fromString("92f1a21e-8a06-4d2a-a0b0-e2f30b42b59d"), null, "Root", 0));
        conceptMap.put(UUID.fromString("3c84b10a-7b32-4d0b-b98d-e0b04d87d92e"), new TinkarConceptModel(null, UUID.fromString("3c84b10a-7b32-4d0b-b98d-e0b04d87d92e"), null, "Root", 0));
        conceptMap.put(UUID.fromString("d0a72318-0e9f-4e23-952d-eef31d28a034"), new TinkarConceptModel(null, UUID.fromString("d0a72318-0e9f-4e23-952d-eef31d28a034"), null, "Root", 0));
        conceptMap.put(UUID.fromString("a561c0f2-e27b-4b3a-8f19-d277b08c2f1a"), new TinkarConceptModel(null, UUID.fromString("a561c0f2-e27b-4b3a-8f19-d277b08c2f1a"), null, "Root", 0));
        conceptMap.put(UUID.fromString("7138b9a1-f210-448b-894a-7187772d31a1"), new TinkarConceptModel(null, UUID.fromString("7138b9a1-f210-448b-894a-7187772d31a1"), null, "Root", 0));
        conceptMap.put(UUID.fromString("1f42d08e-d8e0-4e0d-9e2c-29b248d32b07"), new TinkarConceptModel(null, UUID.fromString("1f42d08e-d8e0-4e0d-9e2c-29b248d32b07"), null, "Root", 0));
        conceptMap.put(UUID.fromString("68e1a3f4-a20c-428d-a277-d0272036784b"), new TinkarConceptModel(null, UUID.fromString("68e1a3f4-a20c-428d-a277-d0272036784b"), null, "Root", 0));
        conceptMap.put(UUID.fromString("03b7c214-3b13-4b41-b922-d10f4419b819"), new TinkarConceptModel(null, UUID.fromString("03b7c214-3b13-4b41-b922-d10f4419b819"), null, "Root", 0));
        conceptMap.put(UUID.fromString("e76a21d2-78b2-472d-9009-b9828d178e2f"), new TinkarConceptModel(null, UUID.fromString("e76a21d2-78b2-472d-9009-b9828d178e2f"), null, "Root", 0));
        conceptMap.put(UUID.fromString("d418b0a7-882a-4b11-80b2-0721b174a398"), new TinkarConceptModel(null, UUID.fromString("d418b0a7-882a-4b11-80b2-0721b174a398"), null, "Root", 0));
        conceptMap.put(UUID.fromString("b071ca3e-410b-49e3-a91b-27f01b4808b1"), new TinkarConceptModel(null, UUID.fromString("b071ca3e-410b-49e3-a91b-27f01b4808b1"), null, "Root", 0));
        conceptMap.put(UUID.fromString("8924d32c-a31e-4942-a24e-1b780871278c"), new TinkarConceptModel(null, UUID.fromString("8924d32c-a31e-4942-a24e-1b780871278c"), null, "Root", 0));
        conceptMap.put(UUID.fromString("24e7a0f7-c128-440c-9812-c0b13284b092"), new TinkarConceptModel(null, UUID.fromString("24e7a0f7-c128-440c-9812-c0b13284b092"), null, "Root", 0));
        conceptMap.put(UUID.fromString("60b0c12a-190f-4f3b-b342-873a2c38b4e7"), new TinkarConceptModel(null, UUID.fromString("60b0c12a-190f-4f3b-b342-873a2c38b4e7"), null, "Root", 0));
    }

    @Override
    public void processAnfStatement(ANFStatement anfStatement) {
        try {
            String json = this.objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(anfStatement);

            List<UUID> uuids = this.findUuids(json);

            for (UUID uuid : uuids) {
                log.info("Processing UUID: {}", uuid);

                this.loadConceptTree(uuid);
                this.updateConceptTree(uuid);
            }


        } catch (JsonProcessingException e) {
            log.error("Error processing ANF statement: {}", e.getMessage(),e);
        }
    }

    private void loadConceptTree(UUID conceptId) {
        UUID uuid = conceptId;
        while(uuid != null && !this.tinkarConceptRepository.existsByConceptId(uuid)) {
            log.info("UUID does not exist in database: {}", uuid);
            TinkarConceptModel conceptModel = this.getTinkarConcept(uuid);
            if(conceptModel != null) {
                this.tinkarConceptRepository.save(conceptModel);
                uuid = conceptModel.getParentConceptId();
            } else {
                uuid = null;
            }
        }
    }

    private void updateConceptTree(UUID conceptId) {
        UUID uuid = conceptId;

        while(uuid != null && this.tinkarConceptRepository.existsByConceptId(uuid)) {
            log.info("UUID exists in database: {}", uuid);
            TinkarConceptModel conceptModel = this.tinkarConceptRepository.findByConceptId(uuid);
            if(conceptModel != null) {
                conceptModel.setCount(conceptModel.getCount() + 1);
                conceptModel = this.tinkarConceptRepository.save(conceptModel);
                uuid = conceptModel.getParentConceptId();
            } else {
                uuid = null;
            }
        }
    }

    private static List<UUID> findUuids(String text) {
        List<UUID> uuids = new ArrayList<>();
        // Regular expression pattern for UUIDs (version 1, 4, 5)
        Pattern uuidPattern = Pattern.compile("[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}");
        Matcher matcher = uuidPattern.matcher(text);

        // Find all matches of the pattern in the text
        while (matcher.find()) {
            String uuidStr = matcher.group();
            UUID uuid = UUID.fromString(uuidStr);
            uuids.add(uuid);
        }

        return uuids;
    }

    private TinkarConceptModel getTinkarConcept(UUID conceptId) {
        return this.conceptMap.get(conceptId);
    }
}
