package cdx.opencdx.adr.model;

import jakarta.persistence.*;

@Table(name = "practitioner")
@Entity
public class PractitionerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String practitionerValue;
    private String code;

    public PractitionerModel() {
    }

    public PractitionerModel(cdx.opencdx.grpc.data.Practitioner practitioner) {
        this.practitionerValue = practitioner.getPractitionerValue();
        this.code = practitioner.getCode();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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