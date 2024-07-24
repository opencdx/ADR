package cdx.opencdx.adr.service;

import cdx.opencdx.adr.dto.UnitOutput;
import cdx.opencdx.adr.model.AnfStatementModel;
import cdx.opencdx.adr.utils.CsvBuilder;

import java.util.List;
import java.util.UUID;

/**
 * The CsvService interface provides methods for building CSV (Comma Separated Values) data.
 */
public interface CsvService {
    /**
     * Builds a CsvBuilder object from the given parameters.
     *
     * @param list       the list of UUIDs
     * @param results    the list of AnfStatementModel objects
     * @param unitOutput the UnitOutput object
     * @return a CsvBuilder object
     */
    CsvBuilder buildCsvDto(List<UUID> list, List<AnfStatementModel> results, UnitOutput unitOutput);
}
