package cdx.opencdx.adr.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class Row {
    List<Cell> cells;
}
