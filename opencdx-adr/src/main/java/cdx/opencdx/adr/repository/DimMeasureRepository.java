package cdx.opencdx.adr.repository;

import cdx.opencdx.adr.model.DimMeasure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DimMeasureRepository extends JpaRepository<DimMeasure, Integer> {
}
