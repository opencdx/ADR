package cdx.opencdx.adr.service;

import cdx.opencdx.adr.model.TinkarConceptModel;
import cdx.opencdx.grpc.data.LogicalExpression;

/**
 * The OpenCDXIKMService interface provides methods for retrieving TinkarConceptModel
 * associated with LogicalExpressionModel.
 */
public interface OpenCDXIKMService {
    String UNIT_INCH = "81bafefa-819a-31e9-882a-b0020ad95be6";
    String UNIT_METER = "757702f5-2516-4d25-ab74-4a226806857f";
    String UNIT_POUNDS = "c5cd4522-8276-35a2-97c6-0c40e980986f";
    String UNIT_KILOGRAMS = "20e0e0e0-70a1-4161-b7a4-e7725f5f583e";
    String UNIT_SECONDS = "b66571ca-bba7-4a5a-a90f-2ef9f0c33a56";
    String UNIT_DAY = "9a6877c0-04b7-4d5d-9766-8695192790d4";
    String UNIT_MONTH = "3a44167a-e94a-4962-8d2e-d94e57475732";
    String UNIT_YEAR = "d9036e1e-3397-4f00-a40a-021626644970";
    String UNIT_MINUTE = "d9036e1e-3397-4f00-a40a-021646644970";
    String UNIT_HOUR = "d9036e1e-3397-4f00-a40a-021616644970";
    String UNIT_DATE = "e37140a1-8180-4a30-91f4-70350c5e1736";
    String UNIT_CALENDAR_TIME = "b5ba88ba-22d5-37fe-a9a1-b82c377e0212";

    String COVID_PRESENCE = "fe300419-06e6-53eb-9e4e-d54fe770114b";
    String COVID_TEST_KITS = "e0ac20ad-ce6f-3ee4-8c71-51b070aa5737";
    String BMI_PATTERN = "922697f7-36ba-4afc-9dd5-f29d54b0fdec";

    /**
     * Retrieves the TinkarConceptModel associated with the given LogicalExpressionModel.
     *
     * @param logicalExpression The LogicalExpression for which to retrieve the TinkarConceptModel.
     * @return The TinkarConceptModel associated with the given LogicalExpressionModel.
     */
    TinkarConceptModel getInkarConceptModel(LogicalExpression logicalExpression);

    /**
     * Retrieves the TinkarConceptModel associated with the given device ID.
     *
     * @param deviceId The device ID for which to retrieve the TinkarConceptModel.
     * @return The TinkarConceptModel associated with the given device ID.
     */
    TinkarConceptModel getInkarConceptModelForDevice(String deviceId);


}
