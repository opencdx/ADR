package cdx.opencdx.adr.service;

import cdx.opencdx.adr.model.AnfStatementModel;

/**
 * An interface representing an OpenCDX ANF processor.
 */
public interface OpenCDXANFProcessor {
    /**
     * Processes an AnfStatement by storing its information in the database.
     *
     * @param anfStatement The AnfStatement to process.
     */
    void processAnfStatement(AnfStatementModel anfStatement);
}
