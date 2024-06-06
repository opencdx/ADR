package cdx.opencdx.adr.repository;

import cdx.opencdx.adr.model.DimPractitioner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DimPractitionerRepository extends JpaRepository<DimPractitioner, Integer> {
}
