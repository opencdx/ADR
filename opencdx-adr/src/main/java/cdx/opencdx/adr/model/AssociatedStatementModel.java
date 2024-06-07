package cdx.opencdx.adr.model;

import jakarta.persistence.*;

@Table(name = "associatedstatement")
@Entity
public class AssociatedStatementModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String semantic;

    public AssociatedStatementModel() {
    }

    public AssociatedStatementModel(cdx.opencdx.grpc.data.AssociatedStatement associatedStatement) {
        this.semantic = associatedStatement.getSemantic();
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getSemantic() {
        return semantic;
    }

    public void setSemantic(String semantic) {
        this.semantic = semantic;
    }
}