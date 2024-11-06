package cdx.opencdx.adr.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Report {
    private List<String> headers;
    private List<Row> rows;
}
