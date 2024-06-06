package cdx.opencdx.adr.model;

import jakarta.persistence.*;

@Entity
@Table(name = "DimPractitioner")
public class DimPractitioner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Adjust if needed
    private Integer practitionerID;

    @Column(name = "PractitionerValue")
    private String practitionerValue;

    @Column(name = "Code")
    private String code;

    // Getters and Setters

    public Integer getPractitionerID() {
        return practitionerID;
    }

    public void setPractitionerID(Integer practitionerID) {
        this.practitionerID = practitionerID;
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
}
