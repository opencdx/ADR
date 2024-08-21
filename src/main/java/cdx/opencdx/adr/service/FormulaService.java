package cdx.opencdx.adr.service;

import cdx.opencdx.adr.dto.Formula;
import cdx.opencdx.adr.model.AnfStatementModel;

import java.util.List;

public interface FormulaService {
    List<AnfStatementModel> evaluateFormula(Formula formula);
}
