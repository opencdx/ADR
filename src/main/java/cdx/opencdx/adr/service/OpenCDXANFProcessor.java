package cdx.opencdx.adr.service;

import cdx.opencdx.grpc.data.ANFStatement;

/**
 * Interface for the OpenCDXANFProcessor
 */
public interface OpenCDXANFProcessor {
    /**
     * Process the ANF Statement
     * @param anfStatement
     */
    void processAnfStatement(ANFStatement anfStatement);
}
