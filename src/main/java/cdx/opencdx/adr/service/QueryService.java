package cdx.opencdx.adr.service;


import cdx.opencdx.adr.dto.Query;

import java.io.PrintWriter;
import java.util.List;

public interface QueryService {

    void processQuery(List<Query> queryDto, PrintWriter writer);
}
