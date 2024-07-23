package cdx.opencdx.adr.service;


import cdx.opencdx.adr.dto.Query;

import java.io.PrintWriter;

public interface QueryService {
    void processQuery(Query query, PrintWriter writer);
}
