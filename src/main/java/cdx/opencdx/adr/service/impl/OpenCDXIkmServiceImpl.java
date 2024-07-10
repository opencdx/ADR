package cdx.opencdx.adr.service.impl;

import cdx.opencdx.adr.model.TinkarConceptModel;
import cdx.opencdx.adr.service.OpenCDXIkmService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class OpenCDXIkmServiceImpl implements OpenCDXIkmService {
    private final Map<UUID,TinkarConceptModel> conceptMap;
    private final Map<String,UUID> snowmedMap;

    public OpenCDXIkmServiceImpl() {
        conceptMap = new HashMap<>();

        conceptMap.put(UUID.fromString("b987a1f2-d10f-4b0e-9e9c-e27f22e2d8b3"), new TinkarConceptModel( UUID.fromString("b987a1f2-d10f-4b0e-9e9c-e27f22e2d8b3"),null, "Vitals"));
        conceptMap.put(UUID.fromString("ffc2d03c-e34b-4d98-a9e4-f152e2b7d4b2"), new TinkarConceptModel( UUID.fromString("ffc2d03c-e34b-4d98-a9e4-f152e2b7d4b2"), UUID.fromString("b987a1f2-d10f-4b0e-9e9c-e27f22e2d8b3"), "Body Mass Index"));
        conceptMap.put(UUID.fromString("4d21f10c-2e13-4493-86e8-d1f20a282b1c"), new TinkarConceptModel( UUID.fromString("4d21f10c-2e13-4493-86e8-d1f20a282b1c"), UUID.fromString("b987a1f2-d10f-4b0e-9e9c-e27f22e2d8b3"), "Height"));
        conceptMap.put(UUID.fromString("1a7dba9e-2076-42e9-874e-a03d0f1d022f"), new TinkarConceptModel( UUID.fromString("1a7dba9e-2076-42e9-874e-a03d0f1d022f"), UUID.fromString("b987a1f2-d10f-4b0e-9e9c-e27f22e2d8b3"), "Weight"));

        conceptMap.put(UUID.fromString("078e2b4a-f02d-492c-a2e2-2c1e2d8f079a"), new TinkarConceptModel( UUID.fromString("078e2b4a-f02d-492c-a2e2-2c1e2d8f079a"), null, "Root"));
        conceptMap.put(UUID.fromString("e81c4b8b-d81d-4e2e-b48a-7d4b20f2a7e2"), new TinkarConceptModel( UUID.fromString("e81c4b8b-d81d-4e2e-b48a-7d4b20f2a7e2"), null, "Root"));
        conceptMap.put(UUID.fromString("92f1a21e-8a06-4d2a-a0b0-e2f30b42b59d"), new TinkarConceptModel( UUID.fromString("92f1a21e-8a06-4d2a-a0b0-e2f30b42b59d"), null, "Root"));
        conceptMap.put(UUID.fromString("3c84b10a-7b32-4d0b-b98d-e0b04d87d92e"), new TinkarConceptModel( UUID.fromString("3c84b10a-7b32-4d0b-b98d-e0b04d87d92e"), null, "Root"));
        conceptMap.put(UUID.fromString("d0a72318-0e9f-4e23-952d-eef31d28a034"), new TinkarConceptModel( UUID.fromString("d0a72318-0e9f-4e23-952d-eef31d28a034"), null, "Root"));
        conceptMap.put(UUID.fromString("a561c0f2-e27b-4b3a-8f19-d277b08c2f1a"), new TinkarConceptModel( UUID.fromString("a561c0f2-e27b-4b3a-8f19-d277b08c2f1a"), null, "Root"));
        conceptMap.put(UUID.fromString("7138b9a1-f210-448b-894a-7187772d31a1"), new TinkarConceptModel( UUID.fromString("7138b9a1-f210-448b-894a-7187772d31a1"), null, "Root"));
        conceptMap.put(UUID.fromString("1f42d08e-d8e0-4e0d-9e2c-29b248d32b07"), new TinkarConceptModel( UUID.fromString("1f42d08e-d8e0-4e0d-9e2c-29b248d32b07"), null, "Root"));
        conceptMap.put(UUID.fromString("68e1a3f4-a20c-428d-a277-d0272036784b"), new TinkarConceptModel( UUID.fromString("68e1a3f4-a20c-428d-a277-d0272036784b"), null, "Root"));
        conceptMap.put(UUID.fromString("03b7c214-3b13-4b41-b922-d10f4419b819"), new TinkarConceptModel( UUID.fromString("03b7c214-3b13-4b41-b922-d10f4419b819"), null, "Root"));
        conceptMap.put(UUID.fromString("e76a21d2-78b2-472d-9009-b9828d178e2f"), new TinkarConceptModel( UUID.fromString("e76a21d2-78b2-472d-9009-b9828d178e2f"), null, "Root"));
        conceptMap.put(UUID.fromString("d418b0a7-882a-4b11-80b2-0721b174a398"), new TinkarConceptModel( UUID.fromString("d418b0a7-882a-4b11-80b2-0721b174a398"), null, "Root"));
        conceptMap.put(UUID.fromString("b071ca3e-410b-49e3-a91b-27f01b4808b1"), new TinkarConceptModel( UUID.fromString("b071ca3e-410b-49e3-a91b-27f01b4808b1"), null, "Root"));
        conceptMap.put(UUID.fromString("8924d32c-a31e-4942-a24e-1b780871278c"), new TinkarConceptModel( UUID.fromString("8924d32c-a31e-4942-a24e-1b780871278c"), null, "Root"));
        conceptMap.put(UUID.fromString("24e7a0f7-c128-440c-9812-c0b13284b092"), new TinkarConceptModel( UUID.fromString("24e7a0f7-c128-440c-9812-c0b13284b092"), null, "Root"));
        conceptMap.put(UUID.fromString("60b0c12a-190f-4f3b-b342-873a2c38b4e7"), new TinkarConceptModel( UUID.fromString("60b0c12a-190f-4f3b-b342-873a2c38b4e7"), null, "Root"));

        snowmedMap = new HashMap<>();
        snowmedMap.put("60621009", UUID.fromString("ffc2d03c-e34b-4d98-a9e4-f152e2b7d4b2"));
        snowmedMap.put("50373000", UUID.fromString("4d21f10c-2e13-4493-86e8-d1f20a282b1c"));
        snowmedMap.put("60621009", UUID.fromString("1a7dba9e-2076-42e9-874e-a03d0f1d022f"));
    }


    @Override
    public TinkarConceptModel getTinkarConcept(UUID conceptId) {
        return this.conceptMap.get(conceptId);
    }

    @Override
    public UUID getTinkarConceptForSnowmed(String number) {
        return this.snowmedMap.get(number);
    }
}
