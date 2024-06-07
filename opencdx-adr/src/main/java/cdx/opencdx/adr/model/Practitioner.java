package cdx.opencdx.adr.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Practitioner {
    @Id
    private String id;

    private String practitionerValue;
    private String code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPractitionerValue() {
        return practitionerValue;
    }

    public void setPractitionerValue(String practitionerValue) {
        this.practitionerValue = practitionerValue;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    // Getters and Setters
}