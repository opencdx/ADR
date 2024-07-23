package cdx.opencdx.adr.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class Query {
    private UUID conceptId;
    private Query or;
    private Query and;
}
