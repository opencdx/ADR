package cdx.opencdx.adr.repository;

import cdx.opencdx.adr.model.LogicalExpressionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The LogicalExpressionRepository interface extends the JpaRepository interface and provides methods for managing logical expressions.
 */
@Repository
public interface LogicalExpressionRepository extends JpaRepository<LogicalExpressionModel, Long> {
    /**
     * Checks if a logical expression with the given expression exists in the repository.
     *
     * @param expression The expression to check.
     * @return True if a logical expression with the given expression exists, false otherwise.
     */
    Boolean existsByExpression(String expression);

    /**
     * Finds a LogicalExpressionModel by the given expression.
     *
     * @param expression the expression to search for
     * @return the matching LogicalExpressionModel, or null if not found
     */
    LogicalExpressionModel findByExpression(String expression);

    /**
     * Saves the given LogicalExpressionModel to the repository or finds an existing one and returns it.
     *
     * @param logicalExpressionModel The LogicalExpressionModel to be saved or found
     * @return The saved or found LogicalExpressionModel
     */
    default LogicalExpressionModel saveOrFind(LogicalExpressionModel logicalExpressionModel) {
        if (existsByExpression(logicalExpressionModel.getExpression())) {
            return findByExpression(logicalExpressionModel.getExpression());
        }

        return save(logicalExpressionModel);
    }
}
