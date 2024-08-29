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
    public static final String UNIT_CALENDAR_TIME = "b5ba88ba-22d5-37fe-a9a1-b82c377e0212";


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
        if (!this.conceptRepository.existsByConceptId(UUID.fromString("86cf994c-70f9-36d0-9703-463b77086a74"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("86cf994c-70f9-36d0-9703-463b77086a74"), "Negative", "260385009 | Negative (qualifier value)"));
        }
        if (!this.conceptRepository.existsByConceptId(UUID.fromString("cff1d554-6d56-33f3-bf5d-9d5a6e231128"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("cff1d554-6d56-33f3-bf5d-9d5a6e231128"), "Not Detected", "260415000 | Not Detected (qualifier value)"));
        }
        if (!this.conceptRepository.existsByConceptId(UUID.fromString("b63ad834-4fde-3d33-8b6b-1da0afc7da7d"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("b63ad834-4fde-3d33-8b6b-1da0afc7da7d"), "Positive", "10828004 | Positive (qualifier value)"));
        }
        if (!this.conceptRepository.existsByConceptId(UUID.fromString("d17289f3-1b97-3191-b873-9768cf8673f3"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("d17289f3-1b97-3191-b873-9768cf8673f3"), "Presumptive Positive", "720735008 | Presumptive Positive (qualifier value)"));
        }

        if(!this.conceptRepository.existsByConceptId(UUID.fromString("86490a44-8539-31e2-8e0f-3a6253a72fca"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("86490a44-8539-31e2-8e0f-3a6253a72fca"), "Patient", "116154003 | Patient (person)"));
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
        if (!this.conceptRepository.existsByConceptId(UUID.fromString("dd44b7a7-015a-336a-a5e0-2330f3ec44dd"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("dd44b7a7-015a-336a-a5e0-2330f3ec44dd"), "Current chronological age", "Current chronological age"));
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
        if (!this.conceptRepository.existsByConceptId(UUID.fromString("40584511-f4fa-33f5-9120-e74f69f1db36"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("40584511-f4fa-33f5-9120-e74f69f1db36"), "Date of birth", "184099003 | Date of birth (observable entity)"));
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
        if (!this.conceptRepository.existsByConceptId(UUID.fromString("395cc864-7c51-4072-b3e7-f9195b40053a"))) {
                    this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("395cc864-7c51-4072-b3e7-f9195b40053a"), "Performance", "Performance"));
                }
        if (!this.conceptRepository.existsByConceptId(UUID.fromString("9570bad9-ade1-3c77-bbb3-30f0924ee1a6"))) {
                    this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("9570bad9-ade1-3c77-bbb3-30f0924ee1a6"), "Complete", "Complete"));
                }

        if (!this.conceptRepository.existsByConceptId(UUID.fromString("6308b821-ebb6-3742-8e4a-12c33f37ecde"))) {
                    this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("6308b821-ebb6-3742-8e4a-12c33f37ecde"), "Evidence", "Evidence"));
                }
      if (!this.conceptRepository.existsByConceptId(UUID.fromString("97b0fbff-cd01-3018-9f72-03ffc7c9027c"))) {
                    this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("97b0fbff-cd01-3018-9f72-03ffc7c9027c"), "Detected", "Detected"));
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
        if (!this.conceptRepository.existsByConceptId(UUID.fromString("12cdf8e0-c564-36b2-8480-9547ec83c297"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("12cdf8e0-c564-36b2-8480-9547ec83c297"), "Acute desquamating eruption of skin (disorder)", "721543007 | Acute desquamating eruption of skin (disorder)"));
        }

        if (!this.conceptRepository.existsByConceptId(UUID.fromString("fc6c2e7b-7898-36c2-906d-6de2e441eafb"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("fc6c2e7b-7898-36c2-906d-6de2e441eafb"), "Acute exudative skin eruption (disorder)", "723014001 | Acute exudative skin eruption (disorder)"));
        }

        if (!this.conceptRepository.existsByConceptId(UUID.fromString("ae5364f8-f918-3b80-895d-33e30eba8207"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("ae5364f8-f918-3b80-895d-33e30eba8207"), "Acute maculopapular eruption of skin (disorder)", "723012002 | Acute maculopapular eruption of skin (disorder)"));
        }

        if (!this.conceptRepository.existsByConceptId(UUID.fromString("dbc3d2f3-5ec3-36be-be9d-180addb0d7a6"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("dbc3d2f3-5ec3-36be-be9d-180addb0d7a6"), "Acute purpuric eruption of skin (disorder)", "723011009 | Acute purpuric eruption of skin (disorder)"));
        }

        if (!this.conceptRepository.existsByConceptId(UUID.fromString("b19a7962-d9b8-3a09-9735-bff09fd2591f"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("b19a7962-d9b8-3a09-9735-bff09fd2591f"), "Acute eruption of skin", "Acute eruption of skin"));
        }

        // Chronic Lung Disease

        if (!this.conceptRepository.existsByConceptId(UUID.fromString("3e07078-f1e2-3f6a-9b7a-9397bcd91cfe"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("3e07078-f1e2-3f6a-9b7a-9397bcd91cfe"), "Chronic lung disease", "Chronic lung disease"));
        }

        if (!this.conceptRepository.existsByConceptId(UUID.fromString("d7c4af8c-9967-3897-a579-c94aaa44ce14"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("d7c4af8c-9967-3897-a579-c94aaa44ce14"), "Chronic lung disease due to surfactant disorder", "Chronic lung disease due to surfactant disorder"));
        }

        if (!this.conceptRepository.existsByConceptId(UUID.fromString("f7c234c5-69d5-3764-ae83-ad2517e8e120"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("f7c234c5-69d5-3764-ae83-ad2517e8e120"), "Chronic pneumonia", "Chronic pneumonia"));
        }

        if (!this.conceptRepository.existsByConceptId(UUID.fromString("f2dec93c-add4-3060-96fa-62e3094d878c"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("f2dec93c-add4-3060-96fa-62e3094d878c"), "Chronic pulmonary congestion", "Chronic pulmonary congestion"));
        }


        if (!this.conceptRepository.existsByConceptId(UUID.fromString("d29fa47b-8170-3918-a416-68da7fb79969"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("d29fa47b-8170-3918-a416-68da7fb79969"), "Chronic pulmonary edema", "Chronic pulmonary edema"));
        }

        if (!this.conceptRepository.existsByConceptId(UUID.fromString("29abe445-84dc-332b-9808-811df5eaaef2"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("29abe445-84dc-332b-9808-811df5eaaef2"), "Chronic silicosis", "Chronic silicosis"));
        }

        if (!this.conceptRepository.existsByConceptId(UUID.fromString("93f9c151-1bd6-31b4-8af7-bac3e3724cda"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("93f9c151-1bd6-31b4-8af7-bac3e3724cda"), "Pulmonary emphysem", "Pulmonary emphysem"));
        }

        // Covid tests

        if (!this.conceptRepository.existsByConceptId(UUID.fromString("5d95570a-0098-4607-b418-e21820c56562"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("5d95570a-0098-4607-b418-e21820c56562"), "SARS-CoV-2 (COVID-19) Ag [Presence] in Respiratory system specimen by Rapid immunoassay", "SARS-CoV-2 (COVID-19) Ag [Presence] in Respiratory system specimen by Rapid immunoassay"));
        }

        if (!this.conceptRepository.existsByConceptId(UUID.fromString("ae4a6ffb-4011-4645-8e14-23890af7c674"))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString("ae4a6ffb-4011-4645-8e14-23890af7c674"), "SARS-CoV-2 (COVID-19) RNA [Presence] in Respiratory system specimen by NAA with probe detection", "SARS-CoV-2 (COVID-19) RNA [Presence] in Respiratory system specimen by NAA with probe detection"));
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
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString(UNIT_KILOGRAMS), "kilograms", "2258702006 | hour (qualifier value)"));
        }

        if(!this.conceptRepository.existsByConceptId(UUID.fromString(OpenCDXIKMServiceImpl.UNIT_DATE))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString(UNIT_DATE), "date", "410672004 | Date property (qualifier value)"));
        }
        if(!this.conceptRepository.existsByConceptId(UUID.fromString(OpenCDXIKMServiceImpl.UNIT_CALENDAR_TIME))) {
            this.conceptRepository.save(new TinkarConceptModel(UUID.fromString(UNIT_CALENDAR_TIME), "calendar time", "Unit of calendar time"));
        }
    }

    private void addConceptIfMissing(UUID conceptId, String conceptName, String conceptDescription) {
        if (!this.conceptRepository.existsByConceptId(conceptId)) {
            this.conceptRepository.save(new TinkarConceptModel(conceptId, conceptName, conceptDescription));
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
