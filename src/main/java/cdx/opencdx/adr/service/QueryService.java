package cdx.opencdx.adr.service;


import cdx.opencdx.adr.dto.Query;

import java.io.PrintWriter;
import java.util.List;

/**
 * The QueryService interface defines the methods to process query DTOs and write the result to a PrintWriter.
 */
public interface QueryService {

    /**
     * Processes the given list of query DTOs and writes the result to the provided PrintWriter.
     *
     * @param queryDto the list of Query DTOs to process
     * @param writer   the PrintWriter to write the result to
     */
    void processQuery(List<Query> queryDto, PrintWriter writer);
}
