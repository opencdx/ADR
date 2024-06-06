package cdx.opencdx.adr.model;

import jakarta.persistence.*;

@Entity
@Table(name = "DimParticipant")
public class DimParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Adjust if needed
    private Integer participantID;

    @Column(name = "PractitionerValue")
    private String practitionerValue;

    @Column(name = "Code")
    private String code;

    // Getters and Setters

    public Integer getParticipantID() {
        return participantID;
    }

    public void setParticipantID(Integer participantID) {
        this.participantID = participantID;
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

