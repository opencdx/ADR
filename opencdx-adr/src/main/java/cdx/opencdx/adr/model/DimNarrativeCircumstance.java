package cdx.opencdx.adr.model;

import jakarta.persistence.*;
@Entity
@Table(name = "DimNarrativeCircumstance")
public class DimNarrativeCircumstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Adjust if needed
    private Integer narrativeCircumstanceID;

    @ManyToOne
    @JoinColumn(name = "TimingMeasureID")
    private DimMeasure timingMeasure;

    @Column(name = "Purpose")
    private String purpose;

    @Column(name = "Text")
    private String text;

    // Getters and Setters (omitted for brevity)

    public Integer getNarrativeCircumstanceID() {
        return narrativeCircumstanceID;
    }

    public void setNarrativeCircumstanceID(Integer narrativeCircumstanceID) {
        this.narrativeCircumstanceID = narrativeCircumstanceID;
    }

    public DimMeasure getTimingMeasure() {
        return timingMeasure;
    }

    public void setTimingMeasure(DimMeasure timingMeasure) {
        this.timingMeasure = timingMeasure;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
