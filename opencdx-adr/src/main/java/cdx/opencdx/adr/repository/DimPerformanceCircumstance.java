package cdx.opencdx.adr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DimPerformanceCircumstance extends JpaRepository<DimPerformanceCircumstance, Integer> {
}
