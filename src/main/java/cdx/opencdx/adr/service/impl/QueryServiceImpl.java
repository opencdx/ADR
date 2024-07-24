package cdx.opencdx.adr.service.impl;

import cdx.opencdx.adr.dto.JoinOperation;
import cdx.opencdx.adr.dto.Query;
import cdx.opencdx.adr.model.AnfStatementModel;
import cdx.opencdx.adr.model.TinkarConceptModel;
import cdx.opencdx.adr.repository.ANFRepo;
import cdx.opencdx.adr.service.CsvService;
import cdx.opencdx.adr.service.QueryService;
import cdx.opencdx.adr.utils.CsvBuilder;
import cdx.opencdx.adr.utils.ListUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.*;

@Slf4j
@Service
public class QueryServiceImpl implements QueryService {

    private final ANFRepo anfRepo;
    private final CsvService csvService;
    @PersistenceContext
    private EntityManager entityManager;

    public record ProcessingResults(List<UUID> conceptIds, List<AnfStatementModel> anfStatements) {
    }


    public QueryServiceImpl(ANFRepo anfRepo, CsvService csvService) {
        this.anfRepo = anfRepo;
        this.csvService = csvService;
    }

    @Override
    public void processQuery(List<Query> queries, PrintWriter writer) {
        log.info("Processing query: {}", queries);

        ProcessingResults processingResults = processQuery(queries);

        List<AnfStatementModel> results = processingResults.anfStatements;
        log.info("Found {} anf statements", results.size());

        List<UUID> list = processingResults.conceptIds;
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

    private ProcessingResults processQuery(List<Query> queries) {

        if (queries.size() % 2 == 0) {
            throw new IllegalArgumentException("Malformed Query syntax");
        }

        queries.forEach(query -> {
            if(query.getConceptId() != null) {
                query.setAnfStatements(this.runQuery(query));
                query.setConceptIds(this.getAnfStatementUuids(query.getAnfStatements()));
            }
        });

        if(queries.size() == 1) {
            return new ProcessingResults(queries.get(0).getConceptIds(), queries.get(0).getAnfStatements());
        }
        boolean keepProcessing = true;
        int index = 1;

        do {
            if(queries.get(index).getJoinOperation().equals(JoinOperation.AND)) {
                ProcessingResults results = processAndResults(queries.get(index - 1), queries.get(index + 1));
                queries.get(index+1).setConceptIds(results.conceptIds);
                queries.get(index+1).setAnfStatements(results.anfStatements);
            } else if(queries.get(index).getJoinOperation().equals(JoinOperation.OR)){
                ProcessingResults results = processOrResults(queries.get(index - 1), queries.get(index + 1));
                queries.get(index+1).setConceptIds(results.conceptIds);
                queries.get(index+1).setAnfStatements(results.anfStatements);
            } else {
                throw new IllegalArgumentException("Malformed Query syntax");
            }

            if(index + 2 >= queries.size()) {
                keepProcessing = false;
            } else {
                index += 2;
            }

        } while(keepProcessing);

        return new ProcessingResults(queries.get(queries.size() - 1).getConceptIds(), queries.get(queries.size() - 1).getAnfStatements());
    }

    private ProcessingResults processOrResults(Query query1, Query query2) {
        List<UUID> uuids = new ArrayList<>(query1.getConceptIds());
        uuids.addAll(query2.getConceptIds());
        List<AnfStatementModel> anfStatements = new ArrayList<>(query1.getAnfStatements());
        anfStatements.addAll(query2.getAnfStatements());

        return new ProcessingResults(uuids, anfStatements);
    }

    private ProcessingResults processAndResults(Query query1, Query query2) {
        List<UUID> uuids = ListUtils.union(query1.getConceptIds(), query2.getConceptIds());
        List<AnfStatementModel> anfStatements = new ArrayList<>(query1.getAnfStatements());
        anfStatements.addAll(query2.getAnfStatements());

        return new ProcessingResults(uuids, anfStatements);
    }

    private List<AnfStatementModel>  runQuery(Query query) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TinkarConceptModel> criteriaQuery = cb.createQuery(TinkarConceptModel.class);
        Root<TinkarConceptModel> root = criteriaQuery.from(TinkarConceptModel.class);

        criteriaQuery.where(cb.equal(root.get("conceptId"), query.getConceptId()));

        return entityManager.createQuery(criteriaQuery).getResultList().stream()
                .map(TinkarConceptModel::getAnfStatements)
                .collect(ArrayList::new, List::addAll, List::addAll);
    }

}
