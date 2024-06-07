package cdx.opencdx.adr.repository;

import cdx.opencdx.adr.model.ANFStatementModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ANFStatementRepository extends JpaRepository<ANFStatementModel, Long>{
}
