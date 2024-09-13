package cdx.opencdx.adr.service.impl;

import cdx.opencdx.adr.model.TinkarConceptModel;
import cdx.opencdx.adr.repository.TinkarConceptRepository;
import cdx.opencdx.adr.service.IKMInterface;
import cdx.opencdx.adr.service.OpenCDXIKMService;
import dev.ikm.tinkar.common.id.PublicId;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.LongConsumer;

@Slf4j
public class MapInterfaceImpl implements IKMInterface {

    /**
     * A private final variable conceptModelMap is declared as a Map, mapping strings to TinkarConceptModel objects.
     */

    private final TinkarConceptRepository conceptRepository;

    /**
     * OpenCDXIKMServiceImpl is a service class that initializes and populates a concept model map.
     * The concept model map is a mapping of concept codes to concept models.
     * Concept models contain the UUID, description, and display labels of concepts.
     */
    public MapInterfaceImpl(TinkarConceptRepository conceptRepository) {
        this.conceptRepository = conceptRepository;

        log.warn("Generating Map of IKM Concepts");;

        addConceptIfMissing("628a2165-0999-40f5-87d4-a40501f1f5f9", "Man", "339947000 | Man (person)");
        addConceptIfMissing("c4e07b26-88f9-4250-803c-86463391c001", "Woman", "224526002 | Woman (person)");
        addConceptIfMissing("359f77d5-6397-4b55-a2b6-c0695f165371", "Pregnant woman", "255409004 | Pregnant woman (person)");
        addConceptIfMissing("86cf994c-70f9-36d0-9703-463b77086a74", "Negative", "260385009 | Negative (qualifier value)");
        addConceptIfMissing("cff1d554-6d56-33f3-bf5d-9d5a6e231128", "Not Detected", "260415000 | Not Detected (qualifier value)");
        addConceptIfMissing("b63ad834-4fde-3d33-8b6b-1da0afc7da7d", "Positive", "10828004 | Positive (qualifier value)");
        addConceptIfMissing("d17289f3-1b97-3191-b873-9768cf8673f3", "Presumptive Positive", "720735008 | Presumptive Positive (qualifier value)");

        addConceptIfMissing("86490a44-8539-31e2-8e0f-3a6253a72fca", "Patient", "116154003 | Patient (person)");
        addConceptIfMissing("4c3137e7-9215-415e-b840-5a580c275080", "Family medicine specialist", "62247001 | Family medicine specialist (occupation)");
        addConceptIfMissing("12e11755-845a-4e0c-9f39-24f7386a11ae", "Evaluation procedure", "386053000 | Evaluation procedure (procedure)");
        addConceptIfMissing("9d11d012-7e48-4738-960f-420d78262a58", "Evaluation finding", "441742003 | Evaluation finding (finding)");

        addConceptIfMissing("dc12873c-8989-42f8-ac29-c2eecc3e3b69", "Acute eruption of skin", "72301000510005 | Acute eruption of skin (disorder)");
        addConceptIfMissing("3a01b6d1-9613-42d6-a6b8-828882e69c2a", "Age", "397669002 | Age (qualifier value)");
        addConceptIfMissing("dd44b7a7-015a-336a-a5e0-2330f3ec44dd", "Current chronological age", "Current chronological age");
        addConceptIfMissing("9061a0d4-254a-42b2-b786-82e5e9f79661", "Age at diagnosis", "423493009 | Age at diagnosis (observable entity)");
        addConceptIfMissing("f99f17a3-5248-472d-b88f-f4c894630084", "Body height", "153637007 | Body height (observable entity)");
        addConceptIfMissing("c185810a-f541-36fa-bfe2-81d58e626326", "Self reported body height", "Self reported body height");
        addConceptIfMissing("1339a965-5c4d-40a7-b04f-60a17b43455b", "Body weight", "27113001 | Body weight (observable entity)");
        addConceptIfMissing("11e160b0-f40e-3911-912e-e6973d787ea0", "Self reported body weight (observable entity)", "Self reported body weight (observable entity)");
        addConceptIfMissing("f99f79f5-1075-4331-b0a8-52d7c647715c", "Chronic lung disease", "413839001 | Chronic lung disease (disorder)");
        addConceptIfMissing("547b6170-4c31-416e-81c7-12625542c72e", "Chronic lung disease due to surfactant disorder", "707534000 | Chronic lung disease due to surfactant disorder (disorder)");
        addConceptIfMissing("40584511-f4fa-33f5-9120-e74f69f1db36", "Date of birth", "184099003 | Date of birth (observable entity)");
        addConceptIfMissing("c6f9e586-4173-43d4-9008-e50c1b7a7a2f", "Date of onset", "298059007 | Date of onset (observable entity)");
        addConceptIfMissing("90f740b3-f9a2-4532-8388-b31714a6a6f6", "Detected", "260373001 | Detected (qualifier value)");
        addConceptIfMissing("b78510a4-f56a-4e61-b7a4-c8438c673d1b", "History of chronic lung disease", "414415007 | History of chronic lung disease (situation)");
        addConceptIfMissing("b3a45188-79f6-457c-b2b6-957780e84736", "Invalid result", "455371000124106 | Invalid result (qualifier value)");
        addConceptIfMissing("395cc864-7c51-4072-b3e7-f9195b40053a", "Performance", "Performance");
        addConceptIfMissing("9570bad9-ade1-3c77-bbb3-30f0924ee1a6", "Complete", "Complete");

        addConceptIfMissing("6308b821-ebb6-3742-8e4a-12c33f37ecde", "Evidence", "Evidence");
        addConceptIfMissing("97b0fbff-cd01-3018-9f72-03ffc7c9027c", "Detected", "Detected");
        addConceptIfMissing("96671aa4-750a-3fc3-923d-dfb4fc443e86", "Cough", "Cough");
        addConceptIfMissing("0686fe06-286c-34e0-83c6-b8d441e545c4", "Fever", "Fever");



//BMI
        addConceptIfMissing("99782a3a-2e09-4482-9c4c-2213f69792e5", "Normal body mass index", "35425004 | Normal body mass index (finding)");
        addConceptIfMissing("293d1da5-7b8c-39f6-baaf-2d0f63aff260", "Body mass index", "60621009 | Body mass index (observable entity)");
        addConceptIfMissing("64f4704d-d1f3-46d8-8330-780c1768e541", "Body mass index 25-29 - overweight", "162863004 | Body mass index 25-29 - overweight (finding)");
        addConceptIfMissing("edb0e4da-fb18-3c4e-b467-3c52f19fbdbc", "Body mass index 30+ - obesity", "162864005 | Body mass index 30+ - obesity (finding)");
        addConceptIfMissing("d9188b17-d87e-4f70-92f1-8a9848f2c30f", "Body mass index less than 20", "310252000 | Body mass index less than 20 (finding)");
        addConceptIfMissing("7ac8a369-6a8a-3e79-a5ee-282e90de498a", "Underweight", "Underweight");
        addConceptIfMissing("a6648c48-3276-3c1f-874d-6929e869f14c", "Normal weight", "Normal weight");
        addConceptIfMissing("ba89934e-befa-32dd-9f77-43b72f94b2a3", "Obese", "Obese");
        addConceptIfMissing("50a29e33-e9eb-3fb5-aefa-dc5a5751b7e3", "Obese class I", "Obese class I");
        addConceptIfMissing("2ed0e11c-d46d-3bde-bca7-355739ab75e5", "Obese class II", "Obese class II");
        addConceptIfMissing("04eb78ff-675a-3c3c-95ea-849fb2938d40", "Obese class III", "Obese class III");
        addConceptIfMissing("7510b39a-563b-39b3-bf87-52ee10699172", "Body mass index 40+ - severely obese", "Body mass index 40+ - severely obese");


// Rash
        addConceptIfMissing("12cdf8e0-c564-36b2-8480-9547ec83c297", "Acute desquamating eruption of skin (disorder)", "721543007 | Acute desquamating eruption of skin (disorder)");
        addConceptIfMissing("fc6c2e7b-7898-36c2-906d-6de2e441eafb", "Acute exudative skin eruption (disorder)", "723014001 | Acute exudative skin eruption (disorder)");
        addConceptIfMissing("ae5364f8-f918-3b80-895d-33e30eba8207", "Acute maculopapular eruption of skin (disorder)", "723012002 | Acute maculopapular eruption of skin (disorder)");
        addConceptIfMissing("dbc3d2f3-5ec3-36be-be9d-180addb0d7a6", "Acute purpuric eruption of skin (disorder)", "723011009 | Acute purpuric eruption of skin (disorder)");
        addConceptIfMissing("b19a7962-d9b8-3a09-9735-bff09fd2591f", "Acute eruption of skin", "Acute eruption of skin");

// Chronic Lung Disease

        addConceptIfMissing("3e07078-f1e2-3f6a-9b7a-9397bcd91cfe", "Chronic lung disease", "Chronic lung disease");
        addConceptIfMissing("d7c4af8c-9967-3897-a579-c94aaa44ce14", "Chronic lung disease due to surfactant disorder", "Chronic lung disease due to surfactant disorder");
        addConceptIfMissing("f7c234c5-69d5-3764-ae83-ad2517e8e120", "Chronic pneumonia", "Chronic pneumonia");
        addConceptIfMissing("f2dec93c-add4-3060-96fa-62e3094d878c", "Chronic pulmonary congestion", "Chronic pulmonary congestion");
        addConceptIfMissing("d29fa47b-8170-3918-a416-68da7fb79969", "Chronic pulmonary edema", "Chronic pulmonary edema");
        addConceptIfMissing("29abe445-84dc-332b-9808-811df5eaaef2", "Chronic silicosis", "Chronic silicosis");
        addConceptIfMissing("93f9c151-1bd6-31b4-8af7-bac3e3724cda", "Pulmonary emphysem", "Pulmonary emphysem");

// Covid tests

        addConceptIfMissing("5d95570a-0098-4607-b418-e21820c56562", "SARS-CoV-2 (COVID-19) Ag [Presence] in Respiratory system specimen by Rapid immunoassay", "SARS-CoV-2 (COVID-19) Ag [Presence] in Respiratory system specimen by Rapid immunoassay");
        addConceptIfMissing("ae4a6ffb-4011-4645-8e14-23890af7c674", "SARS-CoV-2 (COVID-19) RNA [Presence] in Respiratory system specimen by NAA with probe detection", "SARS-CoV-2 (COVID-19) RNA [Presence] in Respiratory system specimen by NAA with probe detection");

//Units

        addConceptIfMissing(OpenCDXIKMService.UNIT_DAY, "day", "258703001 | day (qualifier value)");
        addConceptIfMissing(OpenCDXIKMService.UNIT_MONTH, "month", "258706009 | month (qualifier value)");
        addConceptIfMissing(OpenCDXIKMService.UNIT_YEAR, "year", "258707000 | year (qualifier value)");
        addConceptIfMissing(OpenCDXIKMService.UNIT_SECONDS, "Seconds", "257997001 | Seconds (qualifier value)");
        addConceptIfMissing(OpenCDXIKMService.UNIT_METER, "meter", "258669008 | meter (qualifier value)");
        addConceptIfMissing(OpenCDXIKMService.UNIT_INCH, "inch", "258677007 | inch (qualifier value)");
        addConceptIfMissing(OpenCDXIKMService.UNIT_POUNDS, "pounds", "258693003 | pounds (qualifier value)");
        addConceptIfMissing(OpenCDXIKMService.UNIT_KILOGRAMS, "kilogram", "258683005 | kilogram (qualifier value)");
        addConceptIfMissing(OpenCDXIKMService.UNIT_MINUTE, "minute", "1156209001 | minute (qualifier value)");
        addConceptIfMissing(OpenCDXIKMService.UNIT_HOUR, "kilograms", "2258702006 | hour (qualifier value)"); // There seems to be a typo here. It should likely be "hour" instead of "kilograms"
        addConceptIfMissing(OpenCDXIKMService.UNIT_DATE, "date", "410672004 | Date property (qualifier value)");
        addConceptIfMissing(OpenCDXIKMService.UNIT_CALENDAR_TIME, "calendar time", "Unit of calendar time");

        // Test Kits
        addConceptIfMissing("f7c234c5-69d5-3764-ae83-ad2517e8e120", "Test Kit A", "00000123456789");
        addConceptIfMissing("4c99a870-6f84-4eb9-b226-1cf964c6b19f", "Test Kit B", "99999123456789");
        addConceptIfMissing("9dd5e472-2363-4743-a56f-49c700f9fbfd", "Test Kit C", "00000987654321");
        addConceptIfMissing("024f4556-8834-4add-84c6-eccbd17194ea", "Test Kit D", "99999987654321");

        // Knowledge
        addConceptIfMissing(OpenCDXIKMService.COVID_PRESENCE,"Presence of COVID","Presence of COVID");
        addConceptIfMissing(OpenCDXIKMService.COVID_TEST_KITS,"Covid-19 Test Kits (Lookup)","Covid-19 Test Kits (Lookup)");
        addConceptIfMissing(OpenCDXIKMService.BMI_PATTERN,"Body Mass Index (Lookup)","Body Mass Index (Lookup)");

    }

    /**
     * Returns a list of descendant concept IDs of the given parent concept ID.
     *
     * @param parentConceptId The parent concept ID.
     * @return A list of descendant concept IDs.
     */
    @Override
    public List<PublicId> descendantsOf(PublicId parentConceptId) {
        return new ArrayList<>();
    }

    /**
     * Returns a list of PublicIds that the given member belongs to.
     *
     * @param member The PublicId of the member.
     * @return A list of PublicIds that the member belongs to.
     */
    @Override
    public List<PublicId> memberOf(PublicId member) {
        return new ArrayList<>();
    }

    /**
     * Returns a list of child PublicIds of the given parent PublicId.
     *
     * @param parentConceptId the parent PublicId
     * @return a list of child PublicIds
     */
    @Override
    public List<PublicId> childrenOf(PublicId parentConceptId) {
        return new ArrayList<>();
    }

    /**
     * Retrieves a list of Lidr record semantics from a test kit with the given testKitConceptId.
     *
     * @param testKitConceptId the concept Id of the test kit
     * @return a list of PublicIds representing the Lidr record semantics
     */
    @Override
    public List<PublicId> getLidrRecordSemanticsFromTestKit(PublicId testKitConceptId) {
        return List.of();
    }

    /**
     * Retrieves a list of PublicIds representing the result conformances from a given LidrRecordConceptId.
     *
     * @param lidrRecordConceptId The PublicId of the LidrRecordConcept.
     * @return A list of PublicIds representing the result conformances.
     */
    @Override
    public List<PublicId> getResultConformancesFromLidrRecord(PublicId lidrRecordConceptId) {
        return List.of();
    }

    /**
     * Retrieves a list of allowed results from a result conformance concept ID.
     *
     * @param resultConformanceConceptId The concept ID of the result conformance.
     * @return A list of public IDs representing the allowed results.
     */
    @Override
    public List<PublicId> getAllowedResultsFromResultConformance(PublicId resultConformanceConceptId) {
        return List.of();
    }

    /**
     * Retrieves the descriptions of the given concept IDs.
     *
     * @param conceptIds the list of concept IDs
     * @return the list of descriptions for the given concept IDs
     */
    @Override
    public List<String> descriptionsOf(List<PublicId> conceptIds) {
        return new ArrayList<>();
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
                return new UUID[] { UUID.randomUUID() };
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
        return this.getPublicId(device);
    }

    private void addConceptIfMissing(String conceptId, String conceptName, String conceptDescription) {
        UUID concept = UUID.fromString(conceptId);

        if (!this.conceptRepository.existsByConceptId(concept)) {
            this.conceptRepository.save(new TinkarConceptModel(concept, conceptName, conceptDescription,true));
        }
    }
}
