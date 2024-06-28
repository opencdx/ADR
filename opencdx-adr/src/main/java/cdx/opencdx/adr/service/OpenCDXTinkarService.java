package cdx.opencdx.adr.service;

import cdx.opencdx.grpc.data.ANFStatement;

public interface OpenCDXTinkarService {
    void processAnfStatement(ANFStatement anfStatement);
}
