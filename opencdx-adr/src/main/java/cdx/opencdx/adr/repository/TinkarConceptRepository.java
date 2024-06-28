package cdx.opencdx.adr.repository;

import cdx.opencdx.adr.model.TinkarConceptModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface TinkarConceptRepository extends JpaRepository<TinkarConceptModel, Long>{
    TinkarConceptModel findByConceptId(UUID conceptId);
    TinkarConceptModel findByParentConceptId(UUID parentConceptId);
    boolean existsByConceptId(UUID conceptId);
}
