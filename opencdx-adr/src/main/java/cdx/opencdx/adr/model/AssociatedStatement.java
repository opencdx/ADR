package cdx.opencdx.adr.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class AssociatedStatement {
    @Id
    private String id;

    private String semantic;

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSemantic() {
        return semantic;
    }

    public void setSemantic(String semantic) {
        this.semantic = semantic;
    }
}