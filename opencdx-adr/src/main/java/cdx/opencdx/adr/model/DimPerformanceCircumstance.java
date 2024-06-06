package cdx.opencdx.adr.model;

import jakarta.persistence.*;

@Entity
@Table(name = "DimPerformanceCircumstance")
public class DimPerformanceCircumstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Adjust if needed
    private Integer performanceCircumstanceID;

    @ManyToOne // Many-to-One relationship with DimMeasure
    @JoinColumn(name = "TimingMeasureID")
    private DimMeasure timingMeasure;

    @Column(name = "Purpose")
    private String purpose;

    @Column(name = "Status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "ResultMeasureID")
    private DimMeasure resultMeasure;

    @Column(name = "HealthRisk")
    private String healthRisk;

    @ManyToOne
    @JoinColumn(name = "NormalRangeMeasureID")
    private DimMeasure normalRangeMeasure;

    // Getters and Setters

    public Integer getPerformanceCircumstanceID() {
        return performanceCircumstanceID;
    }

    public void setPerformanceCircumstanceID(Integer performanceCircumstanceID) {
        this.performanceCircumstanceID = performanceCircumstanceID;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DimMeasure getResultMeasure() {
        return resultMeasure;
    }

    public void setResultMeasure(DimMeasure resultMeasure) {
        this.resultMeasure = resultMeasure;
    }

    public String getHealthRisk() {
        return healthRisk;
    }

    public void setHealthRisk(String healthRisk) {
        this.healthRisk = healthRisk;
    }

    public DimMeasure getNormalRangeMeasure() {
        return normalRangeMeasure;
    }

    public void setNormalRangeMeasure(DimMeasure normalRangeMeasure) {
        this.normalRangeMeasure = normalRangeMeasure;
    }
}

