package cdx.opencdx.adr.service;

import cdx.opencdx.grpc.data.ANFStatement;

public interface OpenCDXANFProcessor {
    void processAnfStatement(ANFStatement anfStatement);
}
