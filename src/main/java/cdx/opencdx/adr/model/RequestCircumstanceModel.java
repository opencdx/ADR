package cdx.opencdx.adr.model;

import cdx.opencdx.adr.repository.ANFRepo;
import cdx.opencdx.grpc.data.RequestCircumstance;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "factrequestcircumstance")
public class RequestCircumstanceModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timing_id")
    private MeasureModel timing;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "priority_id")
    private LogicalExpressionModel priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requested_result_id")
    private MeasureModel requestedResult;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repetition_id")
    private RepetitionModel repetition;

    @ManyToMany
    @JoinTable(
            name = "unionrequestcircumstance_conditionaltrigger",
            joinColumns = @JoinColumn(name = "request_circumstance_id"),
            inverseJoinColumns = @JoinColumn(name = "conditional_trigger_id"))
    private List<AssociatedStatementModel> conditionalTrigger = new LinkedList<>();

    @ManyToMany
    @JoinTable(
            name = "unionrequestcircumstance_purpose",
            joinColumns = @JoinColumn(name = "request_circumstance_id"),
            inverseJoinColumns = @JoinColumn(name = "purpose_id"))
    private List<LogicalExpressionModel> purposes = new LinkedList<>();


    @ManyToMany
    @JoinTable(
            name = "unionrequestcircumstance_requestedparticipant",
            joinColumns = @JoinColumn(name = "request_circumstance_id"),
            inverseJoinColumns = @JoinColumn(name = "requested_participant_id"))
    private List<ReferenceModel> requestedParticipant = new LinkedList<>();

    public RequestCircumstanceModel(RequestCircumstance request, ANFRepo anfRepo) {
        this.timing = anfRepo.getMeasureRepository().save(new MeasureModel(request.getTiming(),anfRepo));
        this.priority = anfRepo.getLogicalExpressionRepository().save(new LogicalExpressionModel(request.getPriority(),anfRepo));
        this.requestedResult = anfRepo.getMeasureRepository().save(new MeasureModel(request.getRequestedResult(),anfRepo));
        this.repetition = anfRepo.getRepetitionRepository().save(new RepetitionModel(request.getRepetition(),anfRepo));
        this.conditionalTrigger = request.getConditionalTriggerList().stream().map(trigger -> anfRepo.getAssociatedStatementRespository().save(new AssociatedStatementModel(trigger,anfRepo))).toList();
        this.purposes = request.getPurposeList().stream().map(purpose -> anfRepo.getLogicalExpressionRepository().save(new LogicalExpressionModel(purpose,anfRepo))).toList();
        this.requestedParticipant = request.getRequestedParticipantList().stream().map(participant -> anfRepo.getReferenceRepository().save(new ReferenceModel(participant,anfRepo))).toList();
    }
}