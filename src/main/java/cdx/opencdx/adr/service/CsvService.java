package cdx.opencdx.adr.service;

import cdx.opencdx.adr.model.AnfStatementModel;
import cdx.opencdx.adr.utils.CsvBuilder;

import java.util.List;
import java.util.UUID;

public interface CsvService {
    CsvBuilder buildCsvDto(List<UUID> list, List<AnfStatementModel> results);
}
