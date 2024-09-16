package cdx.opencdx.adr.service;

import cdx.opencdx.adr.model.TinkarConceptModel;
import cdx.opencdx.grpc.data.LogicalExpression;

/**
 * The OpenCDXIKMService interface provides methods for retrieving TinkarConceptModel
 * associated with LogicalExpressionModel.
 */
public interface OpenCDXIKMService {
    String UNIT_INCH = "81bafefa-819a-31e9-882a-b0020ad95be6";
    String UNIT_METER = "ed6a9820-ba24-3917-b1b2-151e9c5a7a8d";
    String UNIT_POUNDS = "c5cd4522-8276-35a2-97c6-0c40e980986f";
    String UNIT_KILOGRAMS = "1303a31e-9698-34f2-b724-f9b8d61c07ff";
    String UNIT_SECONDS = "736b5125-c39e-3d7e-836b-7fedc9b186ad";
    String UNIT_DAY = "89292bee-da9b-3dfa-816a-4607b0054918";
    String UNIT_MONTH = "c4214a24-37e0-315a-9ca3-4c5fe0a3fa75";
    String UNIT_YEAR = "03057bd4-1b5c-3b97-be04-eb0aa70b9ebf";
    String UNIT_MINUTE = "a34a72bc-cd6a-32d9-b8f2-97da50832ff4";
    String UNIT_HOUR = "aca700b1-1500-3c2f-bcc6-87f3121e7913";
    String UNIT_DATE = "b03a930b-1bb3-3c8d-9a3a-cdb010522126";

    String UNIT_CALENDAR_TIME = "b5ba88ba-22d5-37fe-a9a1-b82c377e0212";
    String UNIT_DATE_TIME = "f81f5dc6-aaa3-372a-aa2d-5934fe79dfb0";
    String UNIT_MILLISECONDS = "86b3f8c4-9a2f-35ca-9183-bb8c0090930e";

    String COVID_PRESENCE = "fe300419-06e6-53eb-9e4e-d54fe770114b";
    String COVID_TEST_KITS = "e0ac20ad-ce6f-3ee4-8c71-51b070aa5737";
    String BMI_PATTERN = "922697f7-36ba-4afc-9dd5-f29d54b0fdec";

    String UNIT_BOOLEAN = "8175b7b9-9ab8-3ddc-a665-0061a8d96a6d";
    String UNIT_POSITIVE = "b63ad834-4fde-3d33-8b6b-1da0afc7da7d";
    String UNIT_NEGATIVE = "86cf994c-70f9-36d0-9703-463b77086a74";
    String UNIT_PRESUMPTIVE_POSITIVE = "d17289f3-1b97-3191-b873-9768cf8673f3";
    String UNIT_NOT_DETECTED = "cff1d554-6d56-33f3-bf5d-9d5a6e231128";
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
