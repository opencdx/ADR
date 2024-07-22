package cdx.opencdx.adr.repository;

import cdx.opencdx.adr.model.LogicalExpressionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogicalExpressionRepository extends JpaRepository<LogicalExpressionModel, Long> {
    Boolean existsByExpression(String expression);
    LogicalExpressionModel findByExpression(String expression);

    default LogicalExpressionModel saveOrFind(LogicalExpressionModel logicalExpressionModel) {
        if(existsByExpression(logicalExpressionModel.getExpression())) {
            return findByExpression(logicalExpressionModel.getExpression());
        }

        return save(logicalExpressionModel);
    }
}
