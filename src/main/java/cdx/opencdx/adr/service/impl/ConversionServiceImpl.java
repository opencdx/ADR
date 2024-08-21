package cdx.opencdx.adr.service.impl;

import cdx.opencdx.adr.dto.UnitOutput;
import cdx.opencdx.adr.model.MeasureModel;
import cdx.opencdx.adr.model.TinkarConceptModel;
import cdx.opencdx.adr.repository.TinkarConceptRepository;
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
     * Represents a Tinkar concept repository.
     *
     * The concept repository holds a collection of Tinkar concepts and provides
     * methods to access and manipulate these concepts.
     */
    private final TinkarConceptRepository conceptRepository;


    /**
     * Constructs a new instance of ConversionServiceImpl with the given concept repository.
     *
     * @param conceptRepository the TinkarConceptRepository to be used for conversion
     */
    public ConversionServiceImpl(TinkarConceptRepository conceptRepository) {
        this.conceptRepository = conceptRepository;
    }

    /**
     * Converts the given measure model instances to the specified unit.
     *
     * @param unit    the UUID of the target unit to convert the measure to
     * @param measure the measure model instance to be converted
     * @return the converted measure model instance
     */
    @Override
    public MeasureModel convert(TinkarConceptModel unit, MeasureModel measure) {
        if(unit != null && unit.getConceptId() != null) {
            return this.convert(unit.getConceptId(), measure);
        }
        return this.convert((UUID)null, measure);
    }

    /**
     * Converts a measure to the specified unit.
     *
     * @param unit the UUID of the unit to convert to
     * @param measure the MeasureModel to be converted
     * @return the converted MeasureModel object
     */
    @Override
    public MeasureModel convert(UUID unit, MeasureModel measure) {
        if(unit == null || (measure.getSemantic() != null && unit.equals(measure.getSemantic().getConceptId()))) {
            return measure;
        }
        log.info("Converting measure to unit: " + unit);
        MeasureModel convertedMeasure = new MeasureModel();
        convertedMeasure.setIncludeLowerBound(measure.getIncludeLowerBound());
        convertedMeasure.setIncludeUpperBound(measure.getIncludeUpperBound());

        if(measure.getLowerBound() != null) {
            convertedMeasure.setLowerBound(this.process(unit, measure.getSemantic().getConceptId(), measure.getLowerBound()));
        }

        if(measure.getUpperBound() != null) {
            convertedMeasure.setUpperBound(this.process(unit, measure.getSemantic().getConceptId(), measure.getUpperBound()));
        }

        convertedMeasure.setSemantic(this.conceptRepository.findByConceptId(unit));

        return convertedMeasure;
    }

    /**
     * Converts the given measure to the specified unit output format.
     *
     * @param unitOutput The desired unit output format (IMPERIAL or METRIC).
     * @param measure The measure to convert.
     * @return The converted measure in the specified unit output format.
     */
    @Override
    public MeasureModel output(UnitOutput unitOutput, MeasureModel measure) {
        if(measure == null || measure.getSemantic() == null) {
            return measure;
        }
        if(unitOutput.equals(UnitOutput.IMPERIAL)) {
            return this.convert(this.convertToImperial(measure), measure);
        } else if(unitOutput.equals(UnitOutput.METRIC)) {
            return this.convert(this.convertToMetric(measure), measure);
        }
        return measure;
    }

    /**
     * Converts a measure to the imperial unit.
     *
     * @param measure the measure to be converted
     * @return a UUID representing the converted measure in the imperial unit
     */
    private UUID convertToImperial(MeasureModel measure) {
        switch (measure.getSemantic().getConceptId().toString()) {
            case OpenCDXIKMServiceImpl.UNIT_INCH , OpenCDXIKMServiceImpl.UNIT_METER:
                return UUID.fromString(OpenCDXIKMServiceImpl.UNIT_INCH);
            case OpenCDXIKMServiceImpl.UNIT_POUNDS, OpenCDXIKMServiceImpl.UNIT_KILOGRAMS:
                return UUID.fromString(OpenCDXIKMServiceImpl.UNIT_POUNDS);
            default:
                return measure.getSemantic().getConceptId();
        }
    }

    /**
     * Converts the given measure to a metric unit.
     *
     * @param measure the measure to be converted
     * @return the UUID of the metric unit corresponding to the measure's unit
     */
    private UUID convertToMetric(MeasureModel measure) {
        switch (measure.getSemantic().getConceptId().toString()) {
            case OpenCDXIKMServiceImpl.UNIT_INCH, OpenCDXIKMServiceImpl.UNIT_METER:
                return UUID.fromString(OpenCDXIKMServiceImpl.UNIT_METER);
            case OpenCDXIKMServiceImpl.UNIT_POUNDS, OpenCDXIKMServiceImpl.UNIT_KILOGRAMS:
                return UUID.fromString(OpenCDXIKMServiceImpl.UNIT_KILOGRAMS);
            default:
                return measure.getSemantic().getConceptId();
        }
    }

    /**
     * Processes the given value based on the operation unit.
     *
     * @param operationUnit The operation unit to be used for processing. Valid values are:
     *                      - "inch" for inches
     *                      - "meter" for meters
     *                      - "pound" for pounds
     *                      - "kilogram" for kilograms
     * @param unit The unit of the value to be processed.
     * @param value The value to be processed.
     * @return The processed value. If the operation unit is not recognized, the original value is returned.
     */
    private Double process(UUID operationUnit, UUID unit, Double value) {
        switch(operationUnit.toString()) {
            case OpenCDXIKMServiceImpl.UNIT_INCH: // inches
                return convertToInches(unit,value);
            case OpenCDXIKMServiceImpl.UNIT_METER: // meters
                return convertToMeters(unit,value);
            case OpenCDXIKMServiceImpl.UNIT_POUNDS: // pounds
                return convertToPounds(unit,value);
            case  OpenCDXIKMServiceImpl.UNIT_KILOGRAMS: // kilograms
                return convertToKilograms(unit,value);
            case OpenCDXIKMServiceImpl.UNIT_DAY: // days
                return convertToDays(unit,value);
            case OpenCDXIKMServiceImpl.UNIT_MONTH: // months
                return convertToMonths(unit,value);
            case OpenCDXIKMServiceImpl.UNIT_YEAR: // years
                return convertToYears(unit,value);
            case OpenCDXIKMServiceImpl.UNIT_SECONDS: // seconds
                return convertToSeconds(unit,value);
            case OpenCDXIKMServiceImpl.UNIT_HOUR: // hours
                return convertToHours(unit,value);
            case OpenCDXIKMServiceImpl.UNIT_MINUTE: // minutes
                return convertToMinutes(unit,value);
            default:
                return value;
        }
    }

    private Double convertToMinutes(UUID unit, Double value) {
        switch (unit.toString()) {
            case OpenCDXIKMServiceImpl.UNIT_DAY: // days
                return value * 1440; // 24 hours/day * 60 minutes/hour
            case OpenCDXIKMServiceImpl.UNIT_MONTH: // Not a standard unit, assuming 30 days
                return value * 43200; // 30 days/month * 24 hours/day * 60 minutes/hour
            case OpenCDXIKMServiceImpl.UNIT_YEAR: // years
                return value * 525600; // 365 days/year * 24 hours/day * 60 minutes/hour
            case OpenCDXIKMServiceImpl.UNIT_SECONDS: // seconds
                return value / 60; // 60 seconds/minute
            case OpenCDXIKMServiceImpl.UNIT_HOUR: // hours
                return value * 60; // 60 minutes/hour
            default:
                return null;
        }
    }


    private Double convertToHours(UUID unit, Double value) {
        switch (unit.toString()) {
            case OpenCDXIKMServiceImpl.UNIT_DAY: // days
                return value * 24; // 24 hours/day
            case OpenCDXIKMServiceImpl.UNIT_MONTH: // Not a standard unit, assuming 30 days
                return value * 30 * 24; // 30 days/month * 24 hours/day
            case OpenCDXIKMServiceImpl.UNIT_YEAR: // years
                return value * 365 * 24; // 365 days/year * 24 hours/day
            case OpenCDXIKMServiceImpl.UNIT_SECONDS: // seconds
                return value / 3600; // 60 minutes/hour * 60 seconds/minute
            case OpenCDXIKMServiceImpl.UNIT_MINUTE: // minutes
                return value / 60; // 60 seconds/minute
            default:
                return null;
        }
    }

    private Double convertToSeconds(UUID unit, Double value) {
        switch (unit.toString()) {
            case OpenCDXIKMServiceImpl.UNIT_DAY: // days
                return value * 86400; // 24 hours/day * 60 minutes/hour * 60 seconds/minute
            case OpenCDXIKMServiceImpl.UNIT_MONTH: // Not a standard unit, assuming 30 days
                return value * 30 * 86400; // 30 days/month * seconds/day
            case OpenCDXIKMServiceImpl.UNIT_YEAR: // years
                return value * 365 * 86400; // 365 days/year * seconds/day
            case OpenCDXIKMServiceImpl.UNIT_HOUR: // hours
                return value * 3600; // 60 minutes/hour * 60 seconds/minute
            case OpenCDXIKMServiceImpl.UNIT_MINUTE: // minutes
                return value * 60; // 60 seconds/minute
            default:
                return null;
        }
    }

    private Double convertToYears(UUID unit, Double value) {
        switch (unit.toString()) {
            case OpenCDXIKMServiceImpl.UNIT_DAY: // days
                return value / 365.0;
            case OpenCDXIKMServiceImpl.UNIT_MONTH: // months
                return value / 12.0;
            case OpenCDXIKMServiceImpl.UNIT_SECONDS: // seconds
                return value / 31536000; // 365 days/year * 24 hours/day * 60 minutes/hour * 60 seconds/minute
            case OpenCDXIKMServiceImpl.UNIT_HOUR: // hours
                return value / 8760; // 24 hours/day * 365 days/year
            default:
                return null;
        }
    }

    private Double convertToMonths(UUID unit, Double value) {
        switch (unit.toString()) {
            case OpenCDXIKMServiceImpl.UNIT_DAY: // days
                return value / 30.0;
            case OpenCDXIKMServiceImpl.UNIT_YEAR: // years
                return value * 12.0;
            case OpenCDXIKMServiceImpl.UNIT_SECONDS: // seconds
                return value / 2592000; // 30 days/month * 24 hours/day * 60 minutes/hour * 60 seconds/minute
            case OpenCDXIKMServiceImpl.UNIT_HOUR: // hours
                return value / 730; // 24 hours/day * 30 days/month
            default:
                return null;
        }
    }

    private Double convertToDays(UUID unit, Double value) {
        switch (unit.toString()) {
            case OpenCDXIKMServiceImpl.UNIT_MONTH: // months
                return value * 30.0;
            case OpenCDXIKMServiceImpl.UNIT_YEAR: // years
                return value * 365.0;
            case OpenCDXIKMServiceImpl.UNIT_SECONDS: // seconds
                return value / 86400; // 24 hours/day * 60 minutes/hour * 60 seconds/minute
            case OpenCDXIKMServiceImpl.UNIT_HOUR: // hours
                return value / 24; // 24 hours/day
            default:
                return null;
        }
    }

    /**
     * Converts the given value in the specified unit to kilograms.
     *
     * @param unit the UUID of the unit to convert from
     * @param value the value to be converted
     * @return the converted value in kilograms, or null if the unit is not supported
     */
    private Double convertToKilograms(UUID unit, Double value) {
        switch(unit.toString()) {
            case OpenCDXIKMServiceImpl.UNIT_POUNDS: // pounds
                return value / 2.20462;
            default:
                return null;
        }
    }

    /**
     * Convert the given value to pounds based on the given unit.
     *
     * @param unit The unit of measurement represented as a UUID. Currently supported UUID is "20e0e0e0-70a1-4161-b7a4-e7725f5f583e" for kilograms.
     * @param value The value to be converted to pounds.
     * @return The value converted to pounds. Returns null if the unit is not supported.
     */
    private Double convertToPounds(UUID unit, Double value) {
        switch(unit.toString()) {
            case OpenCDXIKMServiceImpl.UNIT_KILOGRAMS: // Kilograms
                return value * 2.20462;
            default:
                return null;
        }
    }

    /**
     * Converts the given value from a specified unit to meters.
     *
     * @param unit The unit to convert from. Currently supports inches (UUID: 01759586-062f-455f-a0c4-23904464b5f4).
     * @param value The value to be converted.
     * @return The converted value in meters. If the unit is not supported, null is returned.
     */
    private Double convertToMeters(UUID unit, Double value) {
        switch(unit.toString()) {
            case OpenCDXIKMServiceImpl.UNIT_INCH: // inches
                return value / 39.3701;
            default:
                return null;
        }
    }

    /**
     * Converts the given value to inches based on the unit specified.
     *
     * @param unit  The unit to convert from. Only supports meters at the moment.
     * @param value The value to be converted.
     * @return The converted value in inches, or null if the unit is not supported.
     */
    private Double convertToInches(UUID unit, Double value) {
        switch(unit.toString()) {
            case OpenCDXIKMServiceImpl.UNIT_METER: // meters
                return value * 39.3701;
            default:
                return null;
        }
    }
}
