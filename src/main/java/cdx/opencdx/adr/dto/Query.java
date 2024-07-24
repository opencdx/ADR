package cdx.opencdx.adr.dto;

import cdx.opencdx.adr.model.AnfStatementModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Transient;
import jakarta.persistence.criteria.Predicate;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class Query {
    private UUID conceptId;
    private JoinOperation joinOperation;

    @JsonIgnore
    @Transient
    private List<AnfStatementModel> anfStatements;

    @JsonIgnore
    @Transient
    private List<UUID> conceptIds;
}
