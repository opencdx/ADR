package cdx.opencdx.adr.service.impl;

import cdx.opencdx.adr.model.TinkarConceptModel;
import cdx.opencdx.adr.repository.TinkarConceptRepository;
import cdx.opencdx.adr.service.OpenCDXIKMService;
import cdx.opencdx.grpc.data.LogicalExpression;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
    public static final String UNIT_SECONDS = "b66571ca-bba7-4a5a-a90f-2ef9f0c33a56";
    public static final String UNIT_DAY = "9a6877c0-04b7-4d5d-9766-8695192790d4";
    public static final String UNIT_MONTH = "3a44167a-e94a-4962-8d2e-d94e57475732";
    public static final String UNIT_YEAR = "d9036e1e-3397-4f00-a40a-021626644970";
    public static final String UNIT_MINUTE = "d9036e1e-3397-4f00-a40a-021646644970";
    public static final String UNIT_HOUR = "d9036e1e-3397-4f00-a40a-021616644970";
    public static final String UNIT_DATE = "e37140a1-8180-4a30-91f4-70350c5e1736";


    /**
     * A private final variable conceptModelMap is declared as a Map, mapping strings to TinkarConceptModel objects.
     */

    private final TinkarConceptRepository conceptRepository;

    /**
     * OpenCDXIKMServiceImpl is a service class that initializes and populates a concept model map.
     * The concept model map is a mapping of concept codes to concept models.
     * Concept models contain the UUID, description, and display labels of concepts.
     */
    public OpenCDXIKMServiceImpl(TinkarConceptRepository conceptRepository) {
        this.conceptRepository = conceptRepository;

        if (!this.conceptRepository.existsByConceptId(UUID.fromString("628a2165-0999-40f5-87d4-a40501f1f5f9"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("628a2165-0999-40f5-87d4-a40501f1f5f9"), "Man", "339947000 | Man (person)"));
        }
        if (!this.conceptRepository.existsByConceptId(UUID.fromString("c4e07b26-88f9-4250-803c-86463391c001"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("c4e07b26-88f9-4250-803c-86463391c001"), "Woman", "224526002 | Woman (person)"));
        }
        if (!this.conceptRepository.existsByConceptId(UUID.fromString("359f77d5-6397-4b55-a2b6-c0695f165371"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("359f77d5-6397-4b55-a2b6-c0695f165371"), "Pregnant woman", "255409004 | Pregnant woman (person)"));
        }
        if (!this.conceptRepository.existsByConceptId(UUID.fromString("c59c3129-3f64-45e6-9901-6d7f053f6263"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("c59c3129-3f64-45e6-9901-6d7f053f6263"), "Negative", "260385009 | Negative (qualifier value)"));
        }
        if (!this.conceptRepository.existsByConceptId(UUID.fromString("7d540808-89e1-447a-a34b-7876369754b1"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("7d540808-89e1-447a-a34b-7876369754b1"), "Not Detected", "260415000 | Not Detected (qualifier value)"));
        }
        if (!this.conceptRepository.existsByConceptId(UUID.fromString("5b6a7283-d643-47e6-a2b6-03e49902a45e"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("5b6a7283-d643-47e6-a2b6-03e49902a45e"), "Positive", "10828004 | Positive (qualifier value)"));
        }
        if (!this.conceptRepository.existsByConceptId(UUID.fromString("4c197306-450d-4a5e-a468-011a36388383"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("4c197306-450d-4a5e-a468-011a36388383"), "Presumptive Positive", "720735008 | Presumptive Positive (qualifier value)"));
        }

        if(!this.conceptRepository.existsByConceptId(UUID.fromString("9f19e2d2-3992-4474-b717-e1050e3a5341"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("9f19e2d2-3992-4474-b717-e1050e3a5341"), "Patient", "116154003 | Patient (person)"));
        }
        if(!this.conceptRepository.existsByConceptId(UUID.fromString("4c3137e7-9215-415e-b840-5a580c275080"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("4c3137e7-9215-415e-b840-5a580c275080"), "Family medicine specialist", "62247001 | Family medicine specialist (occupation)"));
        }
        if(!this.conceptRepository.existsByConceptId(UUID.fromString("12e11755-845a-4e0c-9f39-24f7386a11ae"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("12e11755-845a-4e0c-9f39-24f7386a11ae"), "Evaluation procedure", "386053000 | Evaluation procedure (procedure)"));
        }
        if(!this.conceptRepository.existsByConceptId(UUID.fromString("9d11d012-7e48-4738-960f-420d78262a58"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("9d11d012-7e48-4738-960f-420d78262a58"), "Evaluation finding", "441742003 | Evaluation finding (finding)"));
        }

        if(!this.conceptRepository.existsByConceptId(UUID.fromString("dc12873c-8989-42f8-ac29-c2eecc3e3b69"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("dc12873c-8989-42f8-ac29-c2eecc3e3b69"), "Acute eruption of skin", "72301000510005 | Acute eruption of skin (disorder)"));
        }
        if (!this.conceptRepository.existsByConceptId(UUID.fromString("3a01b6d1-9613-42d6-a6b8-828882e69c2a"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("3a01b6d1-9613-42d6-a6b8-828882e69c2a"), "Age", "397669002 | Age (qualifier value)"));
        }
        if (!this.conceptRepository.existsByConceptId(UUID.fromString("9061a0d4-254a-42b2-b786-82e5e9f79661"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("9061a0d4-254a-42b2-b786-82e5e9f79661"), "Age at diagnosis", "423493009 | Age at diagnosis (observable entity)"));
        }
        if (!this.conceptRepository.existsByConceptId(UUID.fromString("f99f17a3-5248-472d-b88f-f4c894630084"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("f99f17a3-5248-472d-b88f-f4c894630084"), "Body height", "153637007 | Body height (observable entity)"));
        }
        if (!this.conceptRepository.existsByConceptId(UUID.fromString("1339a965-5c4d-40a7-b04f-60a17b43455b"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("1339a965-5c4d-40a7-b04f-60a17b43455b"), "Body weight", "27113001 | Body weight (observable entity)"));
        }
        if (!this.conceptRepository.existsByConceptId(UUID.fromString("f99f79f5-1075-4331-b0a8-52d7c647715c"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("f99f79f5-1075-4331-b0a8-52d7c647715c"), "Chronic lung disease", "413839001 | Chronic lung disease (disorder)"));
        }
        if (!this.conceptRepository.existsByConceptId(UUID.fromString("547b6170-4c31-416e-81c7-12625542c72e"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("547b6170-4c31-416e-81c7-12625542c72e"), "Chronic lung disease due to surfactant disorder", "707534000 | Chronic lung disease due to surfactant disorder (disorder)"));
        }
        if (!this.conceptRepository.existsByConceptId(UUID.fromString("03918653-4a61-4282-b146-9b68185e467f"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("03918653-4a61-4282-b146-9b68185e467f"), "Date of birth", "184099003 | Date of birth (observable entity)"));
        }
        if (!this.conceptRepository.existsByConceptId(UUID.fromString("c6f9e586-4173-43d4-9008-e50c1b7a7a2f"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("c6f9e586-4173-43d4-9008-e50c1b7a7a2f"), "Date of onset", "298059007 | Date of onset (observable entity)"));
        }
        if (!this.conceptRepository.existsByConceptId(UUID.fromString("90f740b3-f9a2-4532-8388-b31714a6a6f6"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("90f740b3-f9a2-4532-8388-b31714a6a6f6"), "Detected", "260373001 | Detected (qualifier value)"));
        }
        if (!this.conceptRepository.existsByConceptId(UUID.fromString("b78510a4-f56a-4e61-b7a4-c8438c673d1b"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("b78510a4-f56a-4e61-b7a4-c8438c673d1b"), "History of chronic lung disease", "414415007 | History of chronic lung disease (situation)"));
        }
        if (!this.conceptRepository.existsByConceptId(UUID.fromString("b3a45188-79f6-457c-b2b6-957780e84736"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("b3a45188-79f6-457c-b2b6-957780e84736"), "Invalid result", "455371000124106 | Invalid result (qualifier value)"));
        }


        //BMI
        if(!this.conceptRepository.existsByConceptId(UUID.fromString("99782a3a-2e09-4482-9c4c-2213f69792e5"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("99782a3a-2e09-4482-9c4c-2213f69792e5"), "Normal body mass index", "35425004 | Normal body mass index (finding)"));
        }
        if (!this.conceptRepository.existsByConceptId(UUID.fromString("0f0a0956-9019-4006-b74d-71c04d9a9b59"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("0f0a0956-9019-4006-b74d-71c04d9a9b59"), "Body mass index", "60621009 | Body mass index (observable entity)"));
        }
        if (!this.conceptRepository.existsByConceptId(UUID.fromString("64f4704d-d1f3-46d8-8330-780c1768e541"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("64f4704d-d1f3-46d8-8330-780c1768e541"), "Body mass index 25-29 - overweight", "162863004 | Body mass index 25-29 - overweight (finding)"));
        }
        if (!this.conceptRepository.existsByConceptId(UUID.fromString("49d74011-a43a-4258-b8d7-206679349384"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("49d74011-a43a-4258-b8d7-206679349384"), "Body mass index 30+ - obesity", "162864005 | Body mass index 30+ - obesity (finding)"));
        }
        if (!this.conceptRepository.existsByConceptId(UUID.fromString("d9188b17-d87e-4f70-92f1-8a9848f2c30f"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("d9188b17-d87e-4f70-92f1-8a9848f2c30f"), "Body mass index less than 20", "310252000 | Body mass index less than 20 (finding)"));
        }


        // Rash
        if (!this.conceptRepository.existsByConceptId(UUID.fromString("6f4b32d4-a39c-4d81-9f03-e678c5f2a1b5"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("6f4b32d4-a39c-4d81-9f03-e678c5f2a1b5"), "Acute desquamating eruption of skin (disorder)", "721543007 | Acute desquamating eruption of skin (disorder)"));
        }

        if (!this.conceptRepository.existsByConceptId(UUID.fromString("b0e7f3a2-d5c6-47e8-a73f-091c2e6d3b4a"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("b0e7f3a2-d5c6-47e8-a73f-091c2e6d3b4a"), "Acute exudative skin eruption (disorder)", "723014001 | Acute exudative skin eruption (disorder)"));
        }

        if (!this.conceptRepository.existsByConceptId(UUID.fromString("3c8d1f6e-9a57-4c0f-822a-65e4b7c3d2f1"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("3c8d1f6e-9a57-4c0f-822a-65e4b7c3d2f1"), "Acute maculopapular eruption of skin (disorder)", "723012002 | Acute maculopapular eruption of skin (disorder)"));
        }

        if (!this.conceptRepository.existsByConceptId(UUID.fromString("e2a607b8-f1d3-432b-b64c-5a901d8e7c06"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("e2a607b8-f1d3-432b-b64c-5a901d8e7c06"), "Acute purpuric eruption of skin (disorder)", "723011009 | Acute purpuric eruption of skin (disorder)"));
        }

        //Units

        if(!this.conceptRepository.existsByConceptId(UUID.fromString(UNIT_DAY))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString(UNIT_DAY), "day", "258703001 | day (qualifier value)"));
        }
        if(!this.conceptRepository.existsByConceptId(UUID.fromString(UNIT_MONTH))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString(UNIT_MONTH), "month", "258706009 | month (qualifier value)"));
        }
        if(!this.conceptRepository.existsByConceptId(UUID.fromString(UNIT_YEAR))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString(UNIT_YEAR), "year", "258707000 | year (qualifier value)"));
        }
        if(!this.conceptRepository.existsByConceptId(UUID.fromString(UNIT_SECONDS))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString(UNIT_SECONDS), "Seconds", "257997001 | Seconds (qualifier value)"));
        }

        if(!this.conceptRepository.existsByConceptId(UUID.fromString(OpenCDXIKMServiceImpl.UNIT_METER))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString(UNIT_METER), "meter", "258669008 | meter (qualifier value)"));
        }

        if(!this.conceptRepository.existsByConceptId(UUID.fromString(OpenCDXIKMServiceImpl.UNIT_INCH))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString(UNIT_INCH), "inch", "258677007 | inch (qualifier value)"));
        }

        if(!this.conceptRepository.existsByConceptId(UUID.fromString(OpenCDXIKMServiceImpl.UNIT_POUNDS))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString(UNIT_POUNDS), "pounds", "258693003 | pounds (qualifier value)"));
        }

        if(!this.conceptRepository.existsByConceptId(UUID.fromString(OpenCDXIKMServiceImpl.UNIT_KILOGRAMS))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString(UNIT_KILOGRAMS), "kilogram", "258683005 | kilogram (qualifier value)"));
        }

        if(!this.conceptRepository.existsByConceptId(UUID.fromString(OpenCDXIKMServiceImpl.UNIT_MINUTE))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString(UNIT_KILOGRAMS), "minute", "1156209001 | minute (qualifier value)"));
        }

        if(!this.conceptRepository.existsByConceptId(UUID.fromString(OpenCDXIKMServiceImpl.UNIT_HOUR))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString(UNIT_KILOGRAMS), "hour", "2258702006 | hour (qualifier value)"));
        }

        if(!this.conceptRepository.existsByConceptId(UUID.fromString(OpenCDXIKMServiceImpl.UNIT_DATE))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString(UNIT_DATE), "hour", "410672004 | Date property (qualifier value)"));
        }
    }

    /**
     * Returns the TinkarConceptModel associated with the given LogicalExpressionModel.
     *
     * @param logicalExpression the LogicalExpressionModel for which to retrieve the TinkarConceptModel
     * @return the TinkarConceptModel associated with the given LogicalExpressionModel, or null if not found
     */
    @Override
    public TinkarConceptModel getInkarConceptModel(LogicalExpression logicalExpression) {
        UUID conceptId = this.testAndConvert(logicalExpression.getExpression());

        if(conceptId != null) {
            return conceptRepository.findByConceptId(conceptId);
        } else {
            return conceptRepository.findByConceptDescription(logicalExpression.getExpression());
        }
    }

    private UUID testAndConvert(String uuidString) {
        try {
            return UUID.fromString(uuidString);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
