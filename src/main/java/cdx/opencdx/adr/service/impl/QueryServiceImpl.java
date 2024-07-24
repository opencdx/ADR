package cdx.opencdx.adr.service.impl;

import cdx.opencdx.adr.dto.JoinOperation;
import cdx.opencdx.adr.dto.Query;
import cdx.opencdx.adr.model.AnfStatementModel;
import cdx.opencdx.adr.model.TinkarConceptModel;
import cdx.opencdx.adr.repository.ANFRepo;
import cdx.opencdx.adr.service.CsvService;
import cdx.opencdx.adr.service.QueryService;
import cdx.opencdx.adr.utils.CsvBuilder;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class QueryServiceImpl implements QueryService {

    private final ANFRepo anfRepo;
    private final CsvService csvService;
    @PersistenceContext
    private EntityManager entityManager;

    public QueryServiceImpl(ANFRepo anfRepo, CsvService csvService) {
        this.anfRepo = anfRepo;
        this.csvService = csvService;
    }

    @Override
    public void processQuery(List<Query> queries, PrintWriter writer) {
        log.info("Processing query: {}", queries);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TinkarConceptModel> query = cb.createQuery(TinkarConceptModel.class);
        Root<TinkarConceptModel> root = query.from(TinkarConceptModel.class);
        List<AnfStatementModel> results = getAnfStatementModels(buildQuery(queries, cb, root), query);
        log.info("Found {} anf statements", results.size());
        List<UUID> list = getAnfStatementUuids(results);
        List<String> csvContent = prepareCsvContent(list, results);
        csvContent.forEach(writer::println);
    }

    private List<String> prepareCsvContent(List<UUID> uuids, List<AnfStatementModel> results) {
        CsvBuilder csvDto = this.csvService.buildCsvDto(uuids, results);
        List<String> csvContent = new ArrayList<>();
        csvContent.add(csvDto.getHeaders());
        for (int i = 0; i < csvDto.getRowCount(); i++) {
            csvContent.add(csvDto.getRow(i));
        }
        return csvContent;
    }

    private List<UUID> getAnfStatementUuids(List<AnfStatementModel> results) {
        return results.stream()
                .map(anf -> anf.getSubjectOfRecord().getPartId())
                .distinct()
                .toList();
    }

    private List<AnfStatementModel> getAnfStatementModels(Predicate predicate, CriteriaQuery<TinkarConceptModel> query) {
        query.where(predicate);

        log.info("Executing query: {}", query);

        return entityManager.createQuery(query).getResultList().stream()
                .map(TinkarConceptModel::getAnfStatements)
                .collect(ArrayList::new, List::addAll, List::addAll);
    }

    private Predicate buildQuery(List<Query> query, CriteriaBuilder cb, Root<TinkarConceptModel> root) {
        query.forEach(item -> {
            if (item.getConceptId() != null) {
                log.info("Adding query for conceptId: {}", item.getConceptId());
                item.setPredicate(cb.equal(root.get("conceptId"), item.getConceptId()));
            }
        });

        if (query.size() == 1) {
            log.info("Returning single query: {}", query.get(0).getPredicate());
            return query.get(0).getPredicate();
        }

        if (query.get(1).getJoinOperation() != null && query.get(1).getJoinOperation().equals(JoinOperation.AND)) {
            log.info("Returning AND query: {}, {}", query.get(0).getConceptId(), query.get(2).getConceptId());


            return cb.and(query.get(0).getPredicate(), query.get(2).getPredicate());
        } else {
            log.info("Returning OR query: {}, {}", query.get(0).getConceptId(), query.get(2).getConceptId());
            return cb.or(query.get(0).getPredicate(), query.get(2).getPredicate());
        }
    }


}
