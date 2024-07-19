package cdx.opencdx.adr.repository;

import cdx.opencdx.adr.model.TinkarConceptModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TinkarConceptRepository extends JpaRepository<TinkarConceptModel,Long> {

    boolean existsByConceptId(UUID conceptId);

    TinkarConceptModel findByConceptId(UUID conceptId);
}
