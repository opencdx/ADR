package cdx.opencdx.adr.model;

import jakarta.persistence.*;


@Table(name = "dimmeasure")
@Entity
public class MeasureModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String upperBound;
    private String lowerBound;
    private Boolean includeUpperBound;
    private Boolean includeLowerBound;
    private String semantic;
    private String resolution;

    public MeasureModel() {
    }

    public MeasureModel(cdx.opencdx.grpc.data.Measure measure) {
        this.upperBound = measure.getUpperBound();
        this.lowerBound = measure.getLowerBound();
        this.includeUpperBound = measure.getIncludeUpperBound();
        this.includeLowerBound = measure.getIncludeLowerBound();
        this.semantic = measure.getSemantic();
        this.resolution = measure.getResolution();
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(String upperBound) {
        this.upperBound = upperBound;
    }

    public String getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(String lowerBound) {
        this.lowerBound = lowerBound;
    }

    public Boolean getIncludeUpperBound() {
        return includeUpperBound;
    }

    public void setIncludeUpperBound(Boolean includeUpperBound) {
        this.includeUpperBound = includeUpperBound;
    }

    public Boolean getIncludeLowerBound() {
        return includeLowerBound;
    }

    public void setIncludeLowerBound(Boolean includeLowerBound) {
        this.includeLowerBound = includeLowerBound;
    }

    public String getSemantic() {
        return semantic;
    }

    public void setSemantic(String semantic) {
        this.semantic = semantic;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }
}