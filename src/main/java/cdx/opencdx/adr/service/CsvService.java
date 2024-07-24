package cdx.opencdx.adr.service;

import cdx.opencdx.adr.model.AnfStatementModel;
import cdx.opencdx.adr.utils.CsvBuilder;

import java.util.List;
import java.util.UUID;

/**
 * The CsvService interface provides methods for building CSV (Comma Separated Values) data.
 */
public interface CsvService {
    /**
     * Build a CsvBuilder object with the provided list of UUIDs and AnfStatementModels.
     *
     * @param list    The list of UUIDs.
     * @param results The list of AnfStatementModels.
     * @return CsvBuilder The CsvBuilder object.
     * @throws IllegalArgumentException If the header name already exists.
     */
    CsvBuilder buildCsvDto(List<UUID> list, List<AnfStatementModel> results);
}
