package cdx.opencdx.adr.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class NarrativeCircumstance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "timing_id")
    private Measure timing;

    @ElementCollection
    private List<String> purpose;

    private String text;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Measure getTiming() {
        return timing;
    }

    public void setTiming(Measure timing) {
        this.timing = timing;
    }

    public List<String> getPurpose() {
        return purpose;
    }

    public void setPurpose(List<String> purpose) {
        this.purpose = purpose;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}