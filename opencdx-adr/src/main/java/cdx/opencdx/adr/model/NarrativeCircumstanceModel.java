package cdx.opencdx.adr.model;

import cdx.opencdx.adr.repository.ANFRepo;
import jakarta.persistence.*;

import java.util.List;

@Table(name = "factnarrativecircumstance")
@Entity
public class NarrativeCircumstanceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "timing_id")
    private MeasureModel timing;

    @ElementCollection
    private List<String> purpose;

    private String text;

    public NarrativeCircumstanceModel() {
    }

    public NarrativeCircumstanceModel(cdx.opencdx.grpc.data.NarrativeCircumstance narrativeCircumstance, ANFRepo anfRepo) {
        if(narrativeCircumstance.hasTiming()) {
            this.timing = anfRepo.getMeasureRepository().save(new MeasureModel(narrativeCircumstance.getTiming()));
        }
        this.purpose = narrativeCircumstance.getPurposeList();
        this.text = narrativeCircumstance.getText();
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MeasureModel getTiming() {
        return timing;
    }

    public void setTiming(MeasureModel timing) {
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