package cdx.opencdx.adr.model;
import jakarta.persistence.*;


@Entity
@Table(name = "DimMeasure")
public class DimMeasure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Adjust if needed
    private Integer measureID;

    @Column(name = "UpperBound")
    private String upperBound;

    @Column(name = "LowerBound")
    private String lowerBound;

    @Column(name = "IncludeUpperBound")
    private Boolean includeUpperBound;

    @Column(name = "IncludeLowerBound")
    private Boolean includeLowerBound;

    @Column(name = "Semantic")
    private String semantic;

    @Column(name = "Resolution")
    private String resolution;

    // Getters and Setters

    public Integer getMeasureID() {
        return measureID;
    }

    public void setMeasureID(Integer measureID) {
        this.measureID = measureID;
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

