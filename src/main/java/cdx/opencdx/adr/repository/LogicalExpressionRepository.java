package cdx.opencdx.adr.repository;

import cdx.opencdx.adr.model.LogicalExpressionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogicalExpressionRepository extends JpaRepository<LogicalExpressionModel, Long> {

}
