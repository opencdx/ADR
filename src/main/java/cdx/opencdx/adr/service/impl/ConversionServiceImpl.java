package cdx.opencdx.adr.service.impl;

import cdx.opencdx.adr.model.MeasureModel;
import cdx.opencdx.adr.service.ConversionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * The ConversionServiceImpl class is an implementation of the ConversionService interface.
 * It provides methods to convert a MeasureModel instance to a specified unit.
 */
@Slf4j
@Service
public class ConversionServiceImpl implements ConversionService {
    /**
     * Converts a MeasureModel to the specified unit.
     *
     * @param unit    The UUID of the unit to convert to.
     * @param measure The MeasureModel to be converted.
     * @return The converted MeasureModel. If the unit of the measure is already the same as the specified unit,
     *         the original measure is returned. Otherwise, null is returned.
     */
    @Override
    public MeasureModel convert(UUID unit, MeasureModel measure) {
        if(measure.getUnit() != null && unit.equals(measure.getUnit().getConceptId())) {
            return measure;
        }
        MeasureModel convertedMeasure = new MeasureModel();
        convertedMeasure.setIncludeLowerBound(measure.getIncludeLowerBound());
        convertedMeasure.setIncludeUpperBound(measure.getIncludeUpperBound());

        if(measure.getLowerBound() != null) {
            convertedMeasure.setLowerBound(this.process(unit, measure.getUnit().getConceptId(), measure.getLowerBound()));
        }

        if(measure.getUpperBound() != null) {
            convertedMeasure.setLowerBound(this.process(unit, measure.getUnit().getConceptId(), measure.getUpperBound()));
        }

        return convertedMeasure;
    }

    private Double process(UUID operationUnit, UUID unit, Double value) {
        switch(operationUnit.toString()) {
            case "01759586-062f-455f-a0c4-23904464b5f4": // inches
                return convertToInches(unit,value);
            case "757702f5-2516-4d25-ab74-4a226806857f": // meters
                return convertToMeters(unit,value);
            case "98999a1c-11b1-4777-a9b6-3b25482676c4": // pounds
                return convertToPounds(unit,value);
            case  "20e0e0e0-70a1-4161-b7a4-e7725f5f583e": // kilograms
                return convertToKilograms(unit,value);
            default:
                return value;
        }
    }

    private Double convertToKilograms(UUID unit, Double value) {
        switch(unit.toString()) {
            case "98999a1c-11b1-4777-a9b6-3b25482676c4": // pounds
                return value / 2.20462;
            default:
                return null;
        }
    }

    private Double convertToPounds(UUID unit, Double value) {
        switch(unit.toString()) {
            case "20e0e0e0-70a1-4161-b7a4-e7725f5f583e": // Kilograms
                return value * 2.20462;
            default:
                return null;
        }
    }

    private Double convertToMeters(UUID unit, Double value) {
        switch(unit.toString()) {
            case "01759586-062f-455f-a0c4-23904464b5f4": // inches
                return value / 9.3701;
            default:
                return null;
        }
    }

    private Double convertToInches(UUID unit, Double value) {
        switch(unit.toString()) {
            case "757702f5-2516-4d25-ab74-4a226806857f": // meters
                return value * 39.3701;
            default:
                return null;
        }
    }
}
