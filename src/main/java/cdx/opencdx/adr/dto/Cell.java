package cdx.opencdx.adr.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Cell {
    @Builder.Default
    private String value ="";

    @Builder.Default
    private String reported = "";
    @Builder.Default
    private String normalRange ="";
    @Builder.Default
    private List<String> notes = new ArrayList<>();
}
