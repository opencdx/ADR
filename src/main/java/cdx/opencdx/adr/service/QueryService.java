package cdx.opencdx.adr.service;


import cdx.opencdx.adr.dto.ADRQuery;
import cdx.opencdx.adr.dto.Query;

import java.io.PrintWriter;
import java.util.List;

/**
 * The QueryService interface defines the methods to process query DTOs and write the result to a PrintWriter.
 */
public interface QueryService {

    /**
     * Processes the given ADRQuery object and writes the result to the provided PrintWriter.
     *
     * @param adrQuery the ADRQuery object representing the query to be processed
     * @param writer   the PrintWriter object used to write the result
     */
    void processQuery(ADRQuery adrQuery, PrintWriter writer);
}
