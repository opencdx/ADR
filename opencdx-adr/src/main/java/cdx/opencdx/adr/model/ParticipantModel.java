package cdx.opencdx.adr.model;

import jakarta.persistence.*;

@Table(name = "dimparticipant")
@Entity
public class ParticipantModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String practitionerValue;
    private String code;

    public ParticipantModel() {
    }

    public ParticipantModel(cdx.opencdx.grpc.data.Participant participant) {
        this.practitionerValue = participant.getPractitionerValue();
        this.code = participant.getCode();
    }

    // Getters and Setters


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
}