package cdx.opencdx.adr.model;

import cdx.opencdx.adr.repository.ANFRepo;
import cdx.opencdx.grpc.data.ANFStatement;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "dimanfstatement")
public class AnfStatementModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dimanfstatement_id_gen")
    @SequenceGenerator(name = "dimanfstatement_id_gen", sequenceName = "dimanfstatement_id_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "anfid", nullable = false)
    private UUID anfid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "time_id")
    private MeasureModel time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_of_record_id")
    private ParticipantModel subjectOfRecord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_of_information_id")
    private LogicalExpressionModel subjectOfInformation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private LogicalExpressionModel topic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private LogicalExpressionModel type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_circumstance_id")
    private PerformanceCircumstanceModel performanceCircumstance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_circumstance_id")
    private RequestCircumstanceModel requestCircumstance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "narrative_circumstance_id")
    private NarrativeCircumstanceModel narrativeCircumstance;

    @ManyToMany
    @JoinTable(name = "unionanfstatement_associatedstatement",
            joinColumns = @JoinColumn(name = "anf_statement_id"),
            inverseJoinColumns = @JoinColumn(name = "associated_statement_id"))
    private List<AssociatedStatementModel> associatedStatements = new LinkedList<>();

    @ManyToMany
    @JoinTable(name = "unionanfstatement_authors",
            joinColumns = @JoinColumn(name = "anf_statement_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<PractitionerModel> authors = new LinkedList<>();


    public AnfStatementModel(ANFStatement anfStatement, ANFRepo anfRepo) {
        this.anfid = UUID.fromString(anfStatement.getId());
        this.time = anfRepo.getMeasureRepository().save(new MeasureModel(anfStatement.getTime(), anfRepo));
        this.subjectOfRecord = anfRepo.getParticipantRepository().save(new ParticipantModel(anfStatement.getSubjectOfRecord(), anfRepo));
        this.subjectOfInformation = anfRepo.getLogicalExpressionRepository().save(new LogicalExpressionModel(anfStatement.getSubjectOfInformation(), anfRepo));
        this.topic = anfRepo.getLogicalExpressionRepository().save(new LogicalExpressionModel(anfStatement.getTopic(), anfRepo));
        this.type = anfRepo.getLogicalExpressionRepository().save(new LogicalExpressionModel(anfStatement.getType(), anfRepo));

        if(anfStatement.hasPerformanceCircumstance()) {
            this.performanceCircumstance = anfRepo.getPerformanceCircumstanceRepository().save(new PerformanceCircumstanceModel(anfStatement.getPerformanceCircumstance(), anfRepo));
        }
        if(anfStatement.hasRequestCircumstance()) {
            this.requestCircumstance = anfRepo.getRequestCircumstanceRepository().save(new RequestCircumstanceModel(anfStatement.getRequestCircumstance(), anfRepo));
        }
        if(anfStatement.hasNarrativeCircumstance()) {
            this.narrativeCircumstance = anfRepo.getNarrativeCircumstanceRepository().save(new NarrativeCircumstanceModel(anfStatement.getNarrativeCircumstance(), anfRepo));
        }
        this.associatedStatements = anfStatement.getAssociatedStatementList().stream()
                .map(associatedStatement -> anfRepo.getAssociatedStatementRespository().save(new AssociatedStatementModel(associatedStatement, anfRepo)))
                .toList();
        this.authors = anfStatement.getAuthorsList().stream().map(author -> anfRepo.getPractitionerRepository().save(new PractitionerModel(author, anfRepo)))
                .toList();
    }
}