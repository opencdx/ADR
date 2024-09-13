package cdx.opencdx.adr.service;


import cdx.opencdx.adr.dto.ADRQuery;

import java.util.List;

/**
 * The QueryService interface defines the methods to process query DTOs and write the result to a PrintWriter.
 */
public interface QueryService {

    /**
     * Processes the given ADRQuery object and writes the result to the provided PrintWriter.
     *
     * @param adrQuery the ADRQuery object representing the query to be processed
     * @return
     */
    List<String> processQuery(ADRQuery adrQuery);
}
