package cdx.opencdx.adr.model;

import cdx.opencdx.adr.repository.ANFRepo;
import cdx.opencdx.grpc.data.LogicalExpression;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "dimlogicalexpression")
public class LogicalExpressionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "expression", length = Integer.MAX_VALUE)
    private String expression;

    public LogicalExpressionModel(LogicalExpression logicalExpression, ANFRepo anfRepo) {
        this.expression = logicalExpression.getExpression();
    }
}