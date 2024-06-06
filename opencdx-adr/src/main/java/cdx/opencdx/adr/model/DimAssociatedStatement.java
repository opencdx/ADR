package cdx.opencdx.adr.model;

import jakarta.persistence.*;

@Entity
@Table(name = "DimAssociatedStatement")
public class DimAssociatedStatement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Adjust if needed
    private Integer associatedStatementID;

    @Column(name = "Semantic")
    private String semantic;

    // Getters and Setters

    public Integer getAssociatedStatementID() {
        return associatedStatementID;
    }

    public void setAssociatedStatementID(Integer associatedStatementID) {
        this.associatedStatementID = associatedStatementID;
    }

    public String getSemantic() {
        return semantic;
    }

    public void setSemantic(String semantic) {
        this.semantic = semantic;
    }
}
