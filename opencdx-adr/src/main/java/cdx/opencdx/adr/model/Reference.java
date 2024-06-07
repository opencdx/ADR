package cdx.opencdx.adr.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Reference {
    @Id
    private String id;

    private String type;

    // Getters and Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}