package cdx.opencdx.adr.repository;

import cdx.opencdx.adr.model.FactANFStatement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FactANFStatementRepository extends JpaRepository<FactANFStatement, Long> {
}
