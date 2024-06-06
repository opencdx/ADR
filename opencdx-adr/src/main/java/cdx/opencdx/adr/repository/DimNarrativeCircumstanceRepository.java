package cdx.opencdx.adr.repository;

import cdx.opencdx.adr.model.DimNarrativeCircumstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DimNarrativeCircumstanceRepository extends JpaRepository<DimNarrativeCircumstance, Integer> {
}
