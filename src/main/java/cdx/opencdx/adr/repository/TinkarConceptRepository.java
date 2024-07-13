package cdx.opencdx.adr.repository;

import cdx.opencdx.adr.model.TinkarConceptModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * This class is a repository for the Tinkar concept.
 */
@Repository
public interface TinkarConceptRepository extends JpaRepository<TinkarConceptModel, Long>{
    TinkarConceptModel findByConceptId(UUID conceptId);
    boolean existsByConceptId(UUID conceptId);

    List<TinkarConceptModel> findAllByParentConceptIdIsNull();
    List<TinkarConceptModel> findAllByParentConceptId(UUID parentConceptId);
}
