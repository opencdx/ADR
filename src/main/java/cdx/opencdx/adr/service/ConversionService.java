package cdx.opencdx.adr.service;

import cdx.opencdx.adr.dto.UnitOutput;
import cdx.opencdx.adr.model.MeasureModel;
import cdx.opencdx.adr.model.TinkarConceptModel;

import java.util.UUID;

/**
 * This interface represents a conversion service that can convert measure model instances to a specified unit.
 * Implementations of this interface must provide a way to convert a measure model instance to a target unit identified by a UUID.
 */
public interface ConversionService {

    /**
     * Converts the given measure model instances to the specified unit.
     *
     * @param unit    the UUID of the target unit to convert the measure to
     * @param measure the measure model instance to be converted
     * @return the converted measure model instance
     */
    MeasureModel convert(UUID unit, MeasureModel measure);

    /**
     * Converts the given measure model instances to the specified unit.
     *
     * @param unit    the TinkarConceptModel of the target unit to convert the measure to
     * @param measure the measure model instance to be converted
     * @return the converted measure model instance
     */
    MeasureModel convert(TinkarConceptModel unit, MeasureModel measure);

    /**
     * This method is responsible for producing the output of a measure model in a specified unit of measurement.
     *
     * @param unitOutput the unit of measurement for the output
     * @param measure    the measure model to be outputted
     * @return the measure model converted to the specified unit of measurement
     */
    MeasureModel output(UnitOutput unitOutput, MeasureModel measure);
}
