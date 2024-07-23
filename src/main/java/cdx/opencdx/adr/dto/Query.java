package cdx.opencdx.adr.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Transient;
import jakarta.persistence.criteria.Predicate;
import lombok.Data;

import java.util.UUID;

@Data
public class Query {
    private UUID conceptId;
    private JoinOperation joinOperation;

    @JsonIgnore
    @Transient
    private Predicate predicate;
}
