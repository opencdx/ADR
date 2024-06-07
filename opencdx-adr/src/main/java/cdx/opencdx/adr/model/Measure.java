package cdx.opencdx.adr.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;


@Entity
public class Measure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String upperBound;
    private String lowerBound;
    private Boolean includeUpperBound;
    private Boolean includeLowerBound;
    private String semantic;
    private String resolution;

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