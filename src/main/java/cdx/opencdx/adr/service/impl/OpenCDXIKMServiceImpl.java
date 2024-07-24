package cdx.opencdx.adr.service.impl;

import cdx.opencdx.adr.model.LogicalExpressionModel;
import cdx.opencdx.adr.model.TinkarConceptModel;
import cdx.opencdx.adr.repository.TinkarConceptRepository;
import cdx.opencdx.adr.service.OpenCDXIKMService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The OpenCDXIKMServiceImpl class is a service implementation that initializes and populates a concept model map.
 * <p>
 * The concept model map is a mapping of concept codes to concept models. Concept models contain the UUID, description, and display labels of concepts.
 */
@Service
@Slf4j
public class OpenCDXIKMServiceImpl implements OpenCDXIKMService {
    public static final String UNIT_INCH = "01759586-062f-455f-a0c4-23904464b5f4";
    public static final String UNIT_METER = "757702f5-2516-4d25-ab74-4a226806857f";
    public static final String UNIT_POUNDS = "98999a1c-11b1-4777-a9b6-3b25482676c4";
    public static final String UNIT_KILOGRAMS = "20e0e0e0-70a1-4161-b7a4-e7725f5f583e";
    /**
     * A private final variable conceptModelMap is declared as a Map, mapping strings to TinkarConceptModel objects.
     */
    private final Map<String, TinkarConceptModel> conceptModelMap;

    private final TinkarConceptRepository conceptRepository;

    /**
     * OpenCDXIKMServiceImpl is a service class that initializes and populates a concept model map.
     * The concept model map is a mapping of concept codes to concept models.
     * Concept models contain the UUID, description, and display labels of concepts.
     */
    public OpenCDXIKMServiceImpl(TinkarConceptRepository conceptRepository) {
        this.conceptRepository = conceptRepository;
        this.conceptModelMap = new HashMap<>();

        this.conceptModelMap.put("723010005 | Acute eruption of skin (disorder)", new TinkarConceptModel(UUID.fromString("dc12873c-8989-42f8-ac29-c2eecc3e3b69"), "Acute eruption of skin (disorder)", "Acute eruption of skin (disorder)"));
        this.conceptModelMap.put("397669002 | Age (qualifier value)", new TinkarConceptModel(UUID.fromString("3a01b6d1-9613-42d6-a6b8-828882e69c2a"), "Age (qualifier value)", "Age (qualifier value)"));
        this.conceptModelMap.put("423493009 | Age at diagnosis (observable entity)", new TinkarConceptModel(UUID.fromString("9061a0d4-254a-42b2-b786-82e5e9f79661"), "Age at diagnosis (observable entity)", "Age at diagnosis (observable entity)"));
        this.conceptModelMap.put("1153637007 | Body height (observable entity)", new TinkarConceptModel(UUID.fromString("f99f17a3-5248-472d-b88f-f4c894630084"), "Body height (observable entity)", "Body height (observable entity)"));
        this.conceptModelMap.put("60621009 | Body mass index (observable entity)", new TinkarConceptModel(UUID.fromString("0f0a0956-9019-4006-b74d-71c04d9a9b59"), "Body mass index (observable entity)", "Body mass index (observable entity)"));
        this.conceptModelMap.put("162863004 | Body mass index 25-29 - overweight (finding)", new TinkarConceptModel(UUID.fromString("64f4704d-d1f3-46d8-8330-780c1768e541"), "Body mass index 25-29 - overweight (finding)", "Body mass index 25-29 - overweight (finding)"));
        this.conceptModelMap.put("162864005 | Body mass index 30+ - obesity (finding)", new TinkarConceptModel(UUID.fromString("49d74011-a43a-4258-b8d7-206679349384"), "Body mass index 30+ - obesity (finding)", "Body mass index 30+ - obesity (finding)"));
        this.conceptModelMap.put("310252000 | Body mass index less than 20 (finding)", new TinkarConceptModel(UUID.fromString("d9188b17-d87e-4f70-92f1-8a9848f2c30f"), "Body mass index less than 20 (finding)", "Body mass index less than 20 (finding)"));
        this.conceptModelMap.put("27113001 | Body weight (observable entity)", new TinkarConceptModel(UUID.fromString("1339a965-5c4d-40a7-b04f-60a17b43455b"), "Body weight (observable entity)", "Body weight (observable entity)"));
        this.conceptModelMap.put("413839001 | Chronic lung disease (disorder)", new TinkarConceptModel(UUID.fromString("f99f79f5-1075-4331-b0a8-52d7c647715c"), "Chronic lung disease (disorder)", "Chronic lung disease (disorder)"));
        this.conceptModelMap.put("707534000 | Chronic lung disease due to surfactant disorder (disorder)", new TinkarConceptModel(UUID.fromString("547b6170-4c31-416e-81c7-12625542c72e"), "Chronic lung disease due to surfactant disorder (disorder)", "Chronic lung disease due to surfactant disorder (disorder)"));
        this.conceptModelMap.put("184099003 | Date of birth (observable entity)", new TinkarConceptModel(UUID.fromString("03918653-4a61-4282-b146-9b68185e467f"), "Date of birth (observable entity)", "Date of birth (observable entity)"));
        this.conceptModelMap.put("298059007 | Date of onset (observable entity)", new TinkarConceptModel(UUID.fromString("c6f9e586-4173-43d4-9008-e50c1b7a7a2f"), "Date of onset (observable entity)", "Date of onset (observable entity)"));
        this.conceptModelMap.put("260373001 | Detected (qualifier value)", new TinkarConceptModel(UUID.fromString("90f740b3-f9a2-4532-8388-b31714a6a6f6"), "Detected (qualifier value)", "Detected (qualifier value)"));
        this.conceptModelMap.put("414415007 | History of chronic lung disease (situation)", new TinkarConceptModel(UUID.fromString("b78510a4-f56a-4e61-b7a4-c8438c673d1b"), "History of chronic lung disease (situation)", "History of chronic lung disease (situation)"));
        this.conceptModelMap.put("455371000124106 | Invalid result (qualifier value)", new TinkarConceptModel(UUID.fromString("b3a45188-79f6-457c-b2b6-957780e84736"), "Invalid result (qualifier value)", "Invalid result (qualifier value)"));
        this.conceptModelMap.put("339947000 | Man (person)", new TinkarConceptModel(UUID.fromString("628a2165-0999-40f5-87d4-a40501f1f5f9"), "Man (person)", "Man (person)"));
        this.conceptModelMap.put("260385009 | Negative (qualifier value)", new TinkarConceptModel(UUID.fromString("c59c3129-3f64-45e6-9901-6d7f053f6263"), "Negative (qualifier value)", "Negative (qualifier value)"));
        this.conceptModelMap.put("260415000 | Not Detected (qualifier value)", new TinkarConceptModel(UUID.fromString("7d540808-89e1-447a-a34b-7876369754b1"), "Not Detected (qualifier value)", "Not Detected (qualifier value)"));
        this.conceptModelMap.put("10828004 | Positive (qualifier value)", new TinkarConceptModel(UUID.fromString("5b6a7283-d643-47e6-a2b6-03e49902a45e"), "Positive (qualifier value)", "Positive (qualifier value)"));
        this.conceptModelMap.put("255409004 | Pregnant woman (person)", new TinkarConceptModel(UUID.fromString("359f77d5-6397-4b55-a2b6-c0695f165371"), "Pregnant woman (person)", "Pregnant woman (person)"));
        this.conceptModelMap.put("720735008 | Presumptive Positive (qualifier value)", new TinkarConceptModel(UUID.fromString("4c197306-450d-4a5e-a468-011a36388383"), "Presumptive Positive (qualifier value)", "Presumptive Positive (qualifier value)"));
        this.conceptModelMap.put("224526002 | Woman (person)", new TinkarConceptModel(UUID.fromString("c4e07b26-88f9-4250-803c-86463391c001"), "Woman (person)", "Woman (person)"));
        this.conceptModelMap.put("258703001 | day (qualifier value)", new TinkarConceptModel(UUID.fromString("9a6877c0-04b7-4d5d-9766-8695192790d4"), "day (qualifier value)", "day (qualifier value)"));
        this.conceptModelMap.put("258677007 | inch (qualifier value)", new TinkarConceptModel(UUID.fromString(UNIT_INCH), "inch (qualifier value)", "inch (qualifier value)"));
        this.conceptModelMap.put("258683005 | kilogram (qualifier value)", new TinkarConceptModel(UUID.fromString(UNIT_KILOGRAMS), "kilogram (qualifier value)", "kilogram (qualifier value)"));
        this.conceptModelMap.put("258669008 | meter (qualifier value)", new TinkarConceptModel(UUID.fromString(UNIT_METER), "meter (qualifier value)", "meter (qualifier value)"));
        this.conceptModelMap.put("258706009 | month (qualifier value)", new TinkarConceptModel(UUID.fromString("3a44167a-e94a-4962-8d2e-d94e57475732"), "month (qualifier value)", "month (qualifier value)"));
        this.conceptModelMap.put("258693003 | pounds (qualifier value)", new TinkarConceptModel(UUID.fromString(UNIT_POUNDS), "pounds (qualifier value)", "pounds (qualifier value)"));
        this.conceptModelMap.put("258707000 | year (qualifier value)", new TinkarConceptModel(UUID.fromString("d9036e1e-3397-4f00-a40a-021626644970"), "year (qualifier value)", "year (qualifier value)"));
        this.conceptModelMap.put("35425004 | Normal body mass index (finding)", new TinkarConceptModel(UUID.fromString("99782a3a-2e09-4482-9c4c-2213f69792e5"), "Normal body mass index (finding)", "Normal body mass index (finding)"));
        this.conceptModelMap.put("257997001 | Seconds (qualifier value)", new TinkarConceptModel(UUID.fromString("c4e07b26-88f9-4250-803c-86463391c001"), "Seconds (qualifier value)", "Seconds (qualifier value)"));
        this.conceptModelMap.put("116154003 | Patient (person)", new TinkarConceptModel(UUID.fromString("9f19e2d2-3992-4474-b717-e1050e3a5341"), "Patient (person)", "Patient (person)"));
        this.conceptModelMap.put("62247001 | Family medicine specialist (occupation)", new TinkarConceptModel(UUID.fromString("4c3137e7-9215-415e-b840-5a580c275080"), "Family medicine specialist (occupation)", "Family medicine specialist (occupation)"));
        this.conceptModelMap.put("386053000 | Evaluation procedure (procedure)", new TinkarConceptModel(UUID.fromString("12e11755-845a-4e0c-9f39-24f7386a11ae"), "Evaluation procedure (procedure)", "Evaluation procedure (procedure)"));
        this.conceptModelMap.put("441742003 | Evaluation finding (finding)", new TinkarConceptModel(UUID.fromString("9d11d012-7e48-4738-960f-420d78262a58"), "Evaluation finding (finding)", "Evaluation finding (finding)"));


        // Inserting Conversion Topics
        if(!this.conceptRepository.existsByConceptId(UUID.fromString(OpenCDXIKMServiceImpl.UNIT_METER))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString(UNIT_METER), "meter (qualifier value)", "meter (qualifier value)"));
        }

        if(!this.conceptRepository.existsByConceptId(UUID.fromString(OpenCDXIKMServiceImpl.UNIT_INCH))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString(UNIT_INCH), "inch (qualifier value)", "inch (qualifier value)"));
        }

        if(!this.conceptRepository.existsByConceptId(UUID.fromString(OpenCDXIKMServiceImpl.UNIT_POUNDS))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString(UNIT_POUNDS), "pounds (qualifier value)", "pounds (qualifier value)"));
        }

        if(!this.conceptRepository.existsByConceptId(UUID.fromString(OpenCDXIKMServiceImpl.UNIT_KILOGRAMS))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString(UNIT_KILOGRAMS), "kilogram (qualifier value)", "kilogram (qualifier value)"));
        }
    }

    /**
     * Returns the TinkarConceptModel associated with the given LogicalExpressionModel.
     *
     * @param logicalExpression the LogicalExpressionModel for which to retrieve the TinkarConceptModel
     * @return the TinkarConceptModel associated with the given LogicalExpressionModel, or null if not found
     */
    @Override
    public TinkarConceptModel getInkarConceptModel(LogicalExpressionModel logicalExpression) {
        return this.conceptModelMap.get(logicalExpression.getExpression());
    }
}
