package cdx.opencdx.adr.service.impl;

import cdx.opencdx.adr.dto.Query;
import cdx.opencdx.adr.model.AnfStatementModel;
import cdx.opencdx.adr.model.MeasureModel;
import cdx.opencdx.adr.model.ParticipantModel;
import cdx.opencdx.adr.model.TinkarConceptModel;
import cdx.opencdx.adr.repository.ANFRepo;
import cdx.opencdx.adr.service.QueryService;
import cdx.opencdx.adr.utils.CsvBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class QueryServiceImpl implements QueryService {

    @PersistenceContext
    private EntityManager entityManager;

    private final ANFRepo anfRepo;

    public QueryServiceImpl(ANFRepo anfRepo) {
        this.anfRepo = anfRepo;
    }

    @Override
    public void processQuery(Query queryDto, PrintWriter writer) {
        log.info("Processing query: {}", queryDto);

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TinkarConceptModel> query = cb.createQuery(TinkarConceptModel.class);
        Root<TinkarConceptModel> root = query.from(TinkarConceptModel.class);

        query.where(cb.equal(root.get("conceptId"), queryDto.getConceptId()));

        log.info("Executing query: {}", query);

        List<AnfStatementModel> results = entityManager.createQuery(query).getResultList().stream()
                .map(TinkarConceptModel::getAnfStatements)
                .collect(ArrayList::new, List::addAll, List::addAll);

        log.info("Found {} anf statements", results.size());

        List<UUID> list = results.stream().map(anf -> anf.getSubjectOfRecord().getPartId()).distinct().toList();

        CsvBuilder csvDto = buildCsvDto(list);

        writer.println(csvDto.getHeaders());
        for(int i =0 ; i < csvDto.getRowCount(); i++) {
            writer.println(csvDto.getRow(i));
        }
    }

    private CsvBuilder buildCsvDto(List<UUID> list) {
        CsvBuilder csvDto = new CsvBuilder();
        AtomicInteger row = new AtomicInteger();
        list.stream().forEach(uuid -> {
            csvDto.setCell( row.get(), "Participant ID", uuid.toString());

            ArrayList<AnfStatementModel> anfs = this.anfRepo.getParticipantRepository().findAllByPartId(uuid).stream().map(ParticipantModel::getDimanfstatements).collect(ArrayList::new, List::addAll, List::addAll);

            anfs.forEach(anf -> {
                if(anf.getTopic() != null) {
                    if(anf.getPerformanceCircumstance() != null && anf.getPerformanceCircumstance().getResult() != null) {
                        csvDto.setCell(row.get(), anf.getTopic().getTinkarConcept().getConceptName(),  this.buildValue(anf.getPerformanceCircumstance().getResult()) );
                    } else if(anf.getRequestCircumstance() != null) {
                        csvDto.setCell(row.get(), anf.getTopic().getTinkarConcept().getConceptName(),  this.buildValue(anf.getRequestCircumstance().getRequestedResult()));
                    } else if(anf.getNarrativeCircumstance() != null && anf.getNarrativeCircumstance().getText() != null) {
                        csvDto.setCell(row.get(),anf.getTopic().getTinkarConcept().getConceptName(), anf.getNarrativeCircumstance().getText());
                    }
                }
            });
            row.getAndIncrement();
        });

        return csvDto;
    }

    private String buildValue(MeasureModel measure)  {
        if(measure == null) {
            return "\"\"";
        }
        StringBuilder sb = new StringBuilder();

        if(Boolean.TRUE.equals(measure.getIncludeLowerBound()) && Boolean.TRUE.equals(measure.getIncludeUpperBound())
        && measure.getLowerBound() != null && measure.getUpperBound() != null && measure.getUpperBound().equals(measure.getLowerBound())) {
            sb.append(measure.getLowerBound());

            if (measure.getUnit() != null) {
                sb.append(" ").append(measure.getUnit().getConceptName());
            }
        } else {
            if (Boolean.TRUE.equals(measure.getIncludeLowerBound())) {
                if (measure.getLowerBound() == Double.NEGATIVE_INFINITY) {
                    sb.append("INF");
                } else {
                    sb.append(measure.getLowerBound());
                }
            }
            if (measure.getIncludeLowerBound() && measure.getIncludeUpperBound()) {
                sb.append(" - ");
            }
            if (Boolean.TRUE.equals(measure.getIncludeUpperBound())) {
                if (measure.getUpperBound() == Double.POSITIVE_INFINITY) {
                    sb.append("INF");
                } else {
                    sb.append(measure.getUpperBound());
                }
            }

            if (measure.getUnit() != null) {
                sb.append(" ").append(measure.getUnit().getConceptName());
            }
        }

        return sb.toString();
    }

}
