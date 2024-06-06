package cdx.opencdx.adr.repository;

import cdx.opencdx.adr.model.DimAssociatedStatement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DimAssociatedStatementRepository extends JpaRepository<DimAssociatedStatement, Integer> {
}
