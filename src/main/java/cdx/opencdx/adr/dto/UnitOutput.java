package cdx.opencdx.adr.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * The UnitOutput enum represents the different unit output options for a system.
 * <p>
 * The enum contains three options: IMPERIAL, METRIC, and DEFAULT.
 * The IMPERIAL option represents the output unit system as Imperial.
 * The METRIC option represents the unit output format in metric system.
 * The DEFAULT option represents the default unit output for a system.
 * </p>
 *
 * @see UnitOutput#IMPERIAL
 * @see UnitOutput#METRIC
 * @see UnitOutput#DEFAULT
 */
@Schema(description = "Unit output options for a system.")
public enum UnitOutput {
    /**
     * Represents the output unit system as Imperial.
     */
    @Schema(description = "Imperial unit output format.")
    IMPERIAL,
    /**
     * Represents the unit output format in metric system.
     */
    @Schema(description = "Metric unit output format.")
    METRIC,
    /**
     * Represents the default unit output for a system.
     *
     * <p>The {@code DEFAULT} variable is predefined and represents the default unit
     * output for a system. It is typically used as a fallback option when no specific
     * unit output is specified. This variable is part of the {@link UnitOutput} enum.
     *
     * @see UnitOutput
     */
    @Schema(description = "Default unit output format.")
    DEFAULT
}
