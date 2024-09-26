package cdx.opencdx.adr.service.impl;

import cdx.opencdx.adr.dto.UnitOutput;
import cdx.opencdx.adr.model.MeasureModel;
import cdx.opencdx.adr.model.TinkarConceptModel;
import cdx.opencdx.adr.repository.TinkarConceptRepository;
import cdx.opencdx.adr.service.ConversionService;
import cdx.opencdx.adr.service.OpenCDXIKMService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
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
     * <p>
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
        if (unit != null && unit.getConceptId() != null) {
            return this.convert(unit.getConceptId(), measure);
        }
        return this.convert((UUID) null, measure);
    }

    /**
     * Converts a measure to the specified unit.
     *
     * @param unit    the UUID of the unit to convert to
     * @param measure the MeasureModel to be converted
     * @return the converted MeasureModel object
     */
    @Override
    public MeasureModel convert(UUID unit, MeasureModel measure) {
        log.debug("Converting measure: {}", measure);
        if (unit == null || (measure.getSemantic() != null && unit.equals(measure.getSemantic().getConceptId()))) {
            return measure;
        }
        log.debug("Converting measure to unit: {}", unit);
        MeasureModel convertedMeasure = new MeasureModel();
        convertedMeasure.setIncludeLowerBound(measure.getIncludeLowerBound());
        convertedMeasure.setIncludeUpperBound(measure.getIncludeUpperBound());

        log.debug("Lower Bound Include: {}", convertedMeasure.getIncludeLowerBound());
        log.debug("Upper Bound Include: {}", convertedMeasure.getIncludeUpperBound());

        if (measure.getLowerBound() != null) {
            log.debug("Lower Bound: Unit: {} Current: {} Value: {}", unit, measure.getSemantic().getConceptId(), measure.getLowerBound());
            convertedMeasure.setLowerBound(this.process(unit, measure.getSemantic().getConceptId(), measure.getLowerBound()));
        } else {
            convertedMeasure.setLowerBound(null);
            log.debug("Lower bound is null");
        }

        if (measure.getUpperBound() != null) {
            log.debug("Upper Bound: Unit: {} Current: {} Value: {}", unit, measure.getSemantic().getConceptId(), measure.getUpperBound());
            convertedMeasure.setUpperBound(this.process(unit, measure.getSemantic().getConceptId(), measure.getUpperBound()));
        } else {
            convertedMeasure.setUpperBound(null);
            log.debug("Upper bound is null");
        }

        convertedMeasure.setSemantic(this.conceptRepository.findByConceptId(unit));
        log.debug("Converted measure: {}", convertedMeasure);
        return convertedMeasure;
    }

    /**
     * Converts the given measure to the specified unit output format.
     *
     * @param unitOutput The desired unit output format (IMPERIAL or METRIC).
     * @param measure    The measure to convert.
     * @return The converted measure in the specified unit output format.
     */
    @Override
    public MeasureModel output(UnitOutput unitOutput, MeasureModel measure) {
        if (measure == null || measure.getSemantic() == null) {
            return measure;
        }
        if (unitOutput.equals(UnitOutput.IMPERIAL)) {
            return this.convert(this.convertToImperial(measure), measure);
        } else if (unitOutput.equals(UnitOutput.METRIC)) {
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
        return switch (measure.getSemantic().getConceptId().toString()) {
            case OpenCDXIKMServiceImpl.UNIT_METER ->
                    UUID.fromString(OpenCDXIKMServiceImpl.UNIT_INCH);
            case OpenCDXIKMServiceImpl.UNIT_KILOGRAMS ->
                    UUID.fromString(OpenCDXIKMServiceImpl.UNIT_POUNDS);
            case OpenCDXIKMServiceImpl.UNIT_CELSIUS ->
                    UUID.fromString(OpenCDXIKMServiceImpl.UNIT_FAHRENHEIT);
            case OpenCDXIKMService.UNIT_MILILITER -> // milliliters
                    UUID.fromString(OpenCDXIKMService.UNIT_FLUID_OUNCE);
            case OpenCDXIKMService.UNIT_LITER -> // liters
                    UUID.fromString(OpenCDXIKMService.UNIT_PINT);
            default -> measure.getSemantic().getConceptId();
        };
    }

    /**
     * Converts the given measure to a metric unit.
     *
     * @param measure the measure to be converted
     * @return the UUID of the metric unit corresponding to the measure's unit
     */
    private UUID convertToMetric(MeasureModel measure) {
        return switch (measure.getSemantic().getConceptId().toString()) {
            case OpenCDXIKMServiceImpl.UNIT_INCH ->
                    UUID.fromString(OpenCDXIKMServiceImpl.UNIT_METER);
            case OpenCDXIKMServiceImpl.UNIT_POUNDS ->
                    UUID.fromString(OpenCDXIKMServiceImpl.UNIT_KILOGRAMS);
            case OpenCDXIKMServiceImpl.UNIT_FAHRENHEIT ->
                    UUID.fromString(OpenCDXIKMServiceImpl.UNIT_CELSIUS);
            case OpenCDXIKMService.UNIT_FLUID_OUNCE -> // fluid ounces
                    UUID.fromString(OpenCDXIKMService.UNIT_MILILITER);
            case OpenCDXIKMService.UNIT_PINT -> // pints
                    UUID.fromString(OpenCDXIKMService.UNIT_LITER);
            case OpenCDXIKMService.UNIT_GALLON ->
                    UUID.fromString(OpenCDXIKMService.UNIT_LITER);
            default -> measure.getSemantic().getConceptId();
        };
    }

    /**
     * Processes the given value based on the operation unit.
     *
     * @param operationUnit The operation unit to be used for processing. Valid values are:
     *                      - "inch" for inches
     *                      - "meter" for meters
     *                      - "pound" for pounds
     *                      - "kilogram" for kilograms
     * @param unit          The unit of the value to be processed.
     * @param value         The value to be processed.
     * @return The processed value. If the operation unit is not recognized, the original value is returned.
     */
    private Double process(UUID operationUnit, UUID unit, Double value) {
        return switch (operationUnit.toString()) {
            case OpenCDXIKMService.UNIT_INCH -> // inches
                    convertToInches(unit, value);
            case OpenCDXIKMService.UNIT_METER -> // meters
                    convertToMeters(unit, value);
            case OpenCDXIKMService.UNIT_POUNDS -> // pounds
                    convertToPounds(unit, value);
            case OpenCDXIKMService.UNIT_KILOGRAMS -> // kilograms
                    convertToKilograms(unit, value);
            case OpenCDXIKMService.UNIT_DAY -> // days
                    convertToDays(unit, value);
            case OpenCDXIKMService.UNIT_MONTH -> // months
                    convertToMonths(unit, value);
            case OpenCDXIKMService.UNIT_YEAR -> // years
                    convertToYears(unit, value);
            case OpenCDXIKMService.UNIT_SECONDS -> // seconds
                    convertToSeconds(unit, value);
            case OpenCDXIKMService.UNIT_HOUR -> // hours
                    convertToHours(unit, value);
            case OpenCDXIKMService.UNIT_MINUTE -> // minutes
                    convertToMinutes(unit, value);
            case OpenCDXIKMService.UNIT_CALENDAR_TIME -> // calendar time
                    convertToCalendarTime(unit, value);
            case OpenCDXIKMService.UNIT_DATE_TIME -> // date time
                    convertToDateTime(unit, value);
            case OpenCDXIKMService.UNIT_DATE -> // date
                    convertToDate(unit, value);
            case OpenCDXIKMService.UNIT_MILLISECONDS -> // milliseconds
                    convertToMilliseconds(unit, value);
            case OpenCDXIKMService.UNIT_CELSIUS -> // Celsius
                    convertToCelsius(unit, value);
            case OpenCDXIKMService.UNIT_FAHRENHEIT -> // Fahrenheit
                    convertToFahrenheit(unit, value);
            case OpenCDXIKMService.UNIT_MILILITER -> // milliliters
                    convertToMilliliters(unit, value);
            case OpenCDXIKMService.UNIT_LITER -> // liters
                    convertToLiters(unit, value);
            case OpenCDXIKMService.UNIT_FLUID_OUNCE -> // fluid ounces
                    convertToFluidOunces(unit, value);
            case OpenCDXIKMService.UNIT_PINT -> // pints
                    convertToPints(unit, value);
            case OpenCDXIKMService.UNIT_GALLON -> // gallons
                    convertToGallons(unit, value);
            default -> value;
        };
    }

    private Double convertToGallons(UUID unit, Double value) {
        return switch (unit.toString()) {
            case OpenCDXIKMService.UNIT_LITER -> // liters
                    value / 3.78541;
            case OpenCDXIKMService.UNIT_MILILITER -> // milliliters
                    value / 3785.41;
            case OpenCDXIKMService.UNIT_FLUID_OUNCE -> // fluid ounces
                    value / 128;
            case OpenCDXIKMService.UNIT_PINT -> // pints
                    value / 8;
            default -> null;
        };
    }

    private Double convertToPints(UUID unit, Double value) {
        return switch (unit.toString()) {
            case OpenCDXIKMService.UNIT_LITER -> // liters
                    value * 2.11338;
            case OpenCDXIKMService.UNIT_MILILITER -> // milliliters
                    value * 0.00211338;
            case OpenCDXIKMService.UNIT_FLUID_OUNCE -> // fluid ounces
                    value / 16;
            case OpenCDXIKMService.UNIT_GALLON -> // gallons
                    value * 8;
            default -> null;
        };
    }

    private Double convertToFluidOunces(UUID unit, Double value) {
        return switch (unit.toString()) {
            case OpenCDXIKMService.UNIT_LITER -> // liters
                    value * 33.814;
            case OpenCDXIKMService.UNIT_MILILITER -> // milliliters
                    value * 0.033814;
            case OpenCDXIKMService.UNIT_PINT -> // pints
                    value * 16;
            case OpenCDXIKMService.UNIT_GALLON -> // gallons
                    value * 128;
            default -> null;
        };
    }

    private Double convertToLiters(UUID unit, Double value) {
        return switch (unit.toString()) {
            case OpenCDXIKMService.UNIT_MILILITER -> // milliliters
                    value / 1000;
            case OpenCDXIKMService.UNIT_FLUID_OUNCE -> // fluid ounces
                    value / 33.814;
            case OpenCDXIKMService.UNIT_PINT -> // pints
                    value / 2.11338;
            case OpenCDXIKMService.UNIT_GALLON -> // gallons
                    value / 0.264172;
            default -> null;
        };
    }

    private Double convertToMilliliters(UUID unit, Double value) {
        return switch (unit.toString()) {
            case OpenCDXIKMService.UNIT_LITER -> // liters
                    value * 1000;
            case OpenCDXIKMService.UNIT_FLUID_OUNCE -> // fluid ounces
                    value * 29.5735;
            case OpenCDXIKMService.UNIT_PINT -> // pints
                    value * 473.176;
            case OpenCDXIKMService.UNIT_GALLON -> // gallons
                    value * 3785.41;
            default -> null;
        };
    }

    private Double convertToFahrenheit(UUID unit, Double value) {
        return switch (unit.toString()) {
            case OpenCDXIKMService.UNIT_CELSIUS -> // Celsius
                    value * 9 / 5 + 32;
            default -> null;
        };
    }

    private Double convertToCelsius(UUID unit, Double value) {
        return switch (unit.toString()) {
            case OpenCDXIKMService.UNIT_FAHRENHEIT -> // Fahrenheit
                    (value - 32) * 5 / 9;
            default -> null;
        };
    }

    private Double convertToCalendarTime(UUID unit, Double value) {
        return switch (unit.toString()) {
            case OpenCDXIKMService.UNIT_DATE ->
                    value;
            case OpenCDXIKMService.UNIT_DATE_TIME ->
                    value * 1000;
            default -> null;
        };
    }

    private Double convertToDateTime(UUID unit, Double value) {
        return switch (unit.toString()) {
            case OpenCDXIKMService.UNIT_DATE_TIME ->
                    value / 1000;
            case OpenCDXIKMService.UNIT_CALENDAR_TIME ->
                    value / 1000;
            default -> null;
        };
    }

    private Double convertToDate(UUID unit, Double value) {
        return switch (unit.toString()) {
            case OpenCDXIKMService.UNIT_DATE_TIME ->
                    value * 1000;
            case OpenCDXIKMService.UNIT_CALENDAR_TIME ->
                    value;
            default -> null;
        };
    }

    private Double convertToMilliseconds(UUID unit, Double value) {
        return switch (unit.toString()) {
            case OpenCDXIKMService.UNIT_DAY -> // days
                    value * 86400000; // 24 hours/day * 60 minutes/hour * 60 seconds/minute * 1000 milliseconds/second
            case OpenCDXIKMService.UNIT_MONTH -> // Not a standard unit, assuming 30 days
                    value * 2592000000.0; // 30 days/month * 24 hours/day * 60 minutes/hour * 60 seconds/minute * 1000 milliseconds/second
            case OpenCDXIKMService.UNIT_YEAR -> // years
                    value * 31536000000.0; // 365 days/year * 24 hours/day * 60 minutes/hour * 60 seconds/minute * 1000 milliseconds/second
            case OpenCDXIKMService.UNIT_SECONDS -> // seconds
                    value * 1000; // 1000 milliseconds/second
            case OpenCDXIKMService.UNIT_HOUR -> // hours
                    value * 3600000; // 60 minutes/hour * 60 seconds/minute * 1000 milliseconds/second
            case OpenCDXIKMService.UNIT_MINUTE -> // minutes
                    value * 60000; // 60 seconds/minute * 1000 milliseconds/second
            default -> null;
        };
    }

    private Double convertToMinutes(UUID unit, Double value) {
        return switch (unit.toString()) {
            case OpenCDXIKMService.UNIT_DAY -> // days
                    value * 1440; // 24 hours/day * 60 minutes/hour
            case OpenCDXIKMService.UNIT_MONTH -> // Not a standard unit, assuming 30 days
                    value * 43200; // 30 days/month * 24 hours/day * 60 minutes/hour
            case OpenCDXIKMService.UNIT_YEAR -> // years
                    value * 525600; // 365 days/year * 24 hours/day * 60 minutes/hour
            case OpenCDXIKMService.UNIT_SECONDS -> // seconds
                    value / 60; // 60 seconds/minute
            case OpenCDXIKMService.UNIT_HOUR -> // hours
                    value * 60; // 60 minutes/hour
            case OpenCDXIKMService.UNIT_MILLISECONDS -> // milliseconds
                    value / 60000; // 60 seconds/minute * 1000 milliseconds/second
            default -> null;
        };
    }


    private Double convertToHours(UUID unit, Double value) {
        return switch (unit.toString()) {
            case OpenCDXIKMService.UNIT_DAY -> // days
                    value * 24; // 24 hours/day
            case OpenCDXIKMService.UNIT_MONTH -> // Not a standard unit, assuming 30 days
                    value * 30 * 24; // 30 days/month * 24 hours/day
            case OpenCDXIKMService.UNIT_YEAR -> // years
                    value * 365 * 24; // 365 days/year * 24 hours/day
            case OpenCDXIKMService.UNIT_SECONDS -> // seconds
                    value / 3600; // 60 minutes/hour * 60 seconds/minute
            case OpenCDXIKMService.UNIT_MINUTE -> // minutes
                    value / 60; // 60 seconds/minute
            case OpenCDXIKMService.UNIT_MILLISECONDS -> // milliseconds
                    value / 3600000; // 60 seconds/minute * 60 minutes/hour * 1000 milliseconds/second
            default -> null;
        };
    }

    private Double convertToSeconds(UUID unit, Double value) {
        return switch (unit.toString()) {
            case OpenCDXIKMService.UNIT_DAY -> // days
                    value * 86400; // 24 hours/day * 60 minutes/hour * 60 seconds/minute
            case OpenCDXIKMService.UNIT_MONTH -> // Not a standard unit, assuming 30 days
                    value * 30 * 86400; // 30 days/month * seconds/day
            case OpenCDXIKMService.UNIT_YEAR -> // years
                    value * 365 * 86400; // 365 days/year * seconds/day
            case OpenCDXIKMService.UNIT_HOUR -> // hours
                    value * 3600; // 60 minutes/hour * 60 seconds/minute
            case OpenCDXIKMService.UNIT_MINUTE -> // minutes
                    value * 60; // 60 seconds/minute
            case OpenCDXIKMService.UNIT_MILLISECONDS -> // milliseconds
                    value / 1000; // 1000 milliseconds/second
            default -> null;
        };
    }

    private Double convertToYears(UUID unit, Double value) {
        return switch (unit.toString()) {
            case OpenCDXIKMService.UNIT_DAY -> // days
                    value / 365.0;
            case OpenCDXIKMService.UNIT_MONTH -> // months
                    value / 12.0;
            case OpenCDXIKMService.UNIT_SECONDS -> // seconds
                    value / 31536000; // 365 days/year * 24 hours/day * 60 minutes/hour * 60 seconds/minute
            case OpenCDXIKMService.UNIT_HOUR -> // hours
                    value / 8760; // 24 hours/day * 365 days/year
            case OpenCDXIKMService.UNIT_MINUTE -> // minutes
                    value / 525600; // 60 minutes/hour * 24 hours/day * 365 days/year
            case OpenCDXIKMService.UNIT_MILLISECONDS -> // milliseconds
                    value / 31536000000.0; // 365 days/year * 24 hours/day * 60 minutes/hour * 60 seconds/minute * 1000 milliseconds/second
            case OpenCDXIKMService.UNIT_CALENDAR_TIME, OpenCDXIKMService.UNIT_DATE -> {
                log.info("Converting to years: {}", value);
                Date date = new Date( value.longValue());
                log.info("Date: {}", date);
                log.info("Year: {}",Integer.valueOf(date.getYear()).doubleValue());
                yield  Integer.valueOf(date.getYear()).doubleValue();
            }
            case OpenCDXIKMService.UNIT_DATE_TIME -> {
                Date date = new Date( value.longValue() * 1000);
                yield  Integer.valueOf(date.getYear()).doubleValue();
            }
            default -> null;
        };
    }

    private Double convertToMonths(UUID unit, Double value) {
        return switch (unit.toString()) {
            case OpenCDXIKMService.UNIT_DAY -> // days
                    value / 30.0;
            case OpenCDXIKMService.UNIT_YEAR -> // years
                    value * 12.0;
            case OpenCDXIKMService.UNIT_SECONDS -> // seconds
                    value / 2592000; // 30 days/month * 24 hours/day * 60 minutes/hour * 60 seconds/minute
            case OpenCDXIKMService.UNIT_HOUR -> // hours
                    value / 730; // 24 hours/day * 30 days/month
            case OpenCDXIKMService.UNIT_MINUTE -> // minutes
                    value / 43800; // 30 days/month * 24 hours/day * 60 minutes/hour
            case OpenCDXIKMService.UNIT_MILLISECONDS -> // milliseconds
                    value / 2592000000.0; // 30 days/month * 24 hours/day * 60 minutes/hour * 60 seconds/minute * 1000 milliseconds/second
            default -> null;
        };
    }

    private Double convertToDays(UUID unit, Double value) {
        return switch (unit.toString()) {
            case OpenCDXIKMService.UNIT_MONTH -> // months
                    value * 30.0;
            case OpenCDXIKMService.UNIT_YEAR -> // years
                    value * 365.0;
            case OpenCDXIKMService.UNIT_SECONDS -> // seconds
                    value / 86400; // 24 hours/day * 60 minutes/hour * 60 seconds/minute
            case OpenCDXIKMService.UNIT_HOUR -> // hours
                    value / 24; // 24 hours/day
            case OpenCDXIKMService.UNIT_MINUTE -> // minutes
                    value / 1440; // 24 hours/day * 60 minutes/hour
            case OpenCDXIKMService.UNIT_MILLISECONDS -> // milliseconds
                    value / 86400000.0; // 24 hours/day * 60 minutes/hour * 60 seconds/minute * 1000 milliseconds/second
            default -> null;
        };
    }

    /**
     * Converts the given value in the specified unit to kilograms.
     *
     * @param unit  the UUID of the unit to convert from
     * @param value the value to be converted
     * @return the converted value in kilograms, or null if the unit is not supported
     */
    private Double convertToKilograms(UUID unit, Double value) {
        return switch (unit.toString()) {
            case OpenCDXIKMService.UNIT_POUNDS -> // pounds
                    value / 2.20462;
            default -> null;
        };
    }

    /**
     * Convert the given value to pounds based on the given unit.
     *
     * @param unit  The unit of measurement represented as a UUID. Currently supported UUID is "20e0e0e0-70a1-4161-b7a4-e7725f5f583e" for kilograms.
     * @param value The value to be converted to pounds.
     * @return The value converted to pounds. Returns null if the unit is not supported.
     */
    private Double convertToPounds(UUID unit, Double value) {
        return switch (unit.toString()) {
            case OpenCDXIKMService.UNIT_KILOGRAMS -> // Kilograms
                    value * 2.20462;
            default -> null;
        };
    }

    /**
     * Converts the given value from a specified unit to meters.
     *
     * @param unit  The unit to convert from. Currently supports inches (UUID: 01759586-062f-455f-a0c4-23904464b5f4).
     * @param value The value to be converted.
     * @return The converted value in meters. If the unit is not supported, null is returned.
     */
    private Double convertToMeters(UUID unit, Double value) {
        return switch (unit.toString()) {
            case OpenCDXIKMService.UNIT_INCH -> // inches
                    value / 39.3701;
            default -> null;
        };
    }

    /**
     * Converts the given value to inches based on the unit specified.
     *
     * @param unit  The unit to convert from. Only supports meters at the moment.
     * @param value The value to be converted.
     * @return The converted value in inches, or null if the unit is not supported.
     */
    private Double convertToInches(UUID unit, Double value) {
        return switch (unit.toString()) {
            case OpenCDXIKMService.UNIT_METER -> // meters
                    value * 39.3701;
            default -> null;
        };
    }
}
