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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * The QueryServiceImpl class is an implementation of the QueryService interface.
 * It provides methods for processing queries and writing the results as CSV content.
 */
@Slf4j
@Service
public class QueryServiceImpl implements QueryService {

    private final ANFRepo anfRepo;
    private final CsvService csvService;
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Creates a new instance of QueryServiceImpl with the given ANFRepo and CsvService objects.
     *
     * @param anfRepo    the ANFRepo object used for querying ANF data
     * @param csvService the CsvService object used for csv operations
     */
    public QueryServiceImpl(ANFRepo anfRepo, CsvService csvService) {
        this.anfRepo = anfRepo;
        this.csvService = csvService;
    }

    /**
     * Processes the given list of queries and writes the results as CSV content to the provided PrintWriter.
     *
     * @param queries The list of queries to process.
     * @param writer  The PrintWriter to write the CSV content to.
     */
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

    /**
     * Prepares the content of a CSV file.
     *
     * @param uuids   the list of UUIDs.
     * @param results the list of AnfStatementModel objects.
     * @return the list of strings representing the content of the CSV file.
     */
    private List<String> prepareCsvContent(List<UUID> uuids, List<AnfStatementModel> results) {
        CsvBuilder csvDto = this.csvService.buildCsvDto(uuids, results);
        List<String> csvContent = new ArrayList<>();
        csvContent.add(csvDto.getHeaders());
        for (int i = 0; i < csvDto.getRowCount(); i++) {
            csvContent.add(csvDto.getRow(i));
        }
        return csvContent;
    }

    /**
     * Returns a list of UUIDs extracted from the subjectOfRecord.partId property
     * of the given list of AnfStatementModel objects.
     *
     * @param results the list of AnfStatementModel objects from which to extract the UUIDs
     * @return a list of UUIDs extracted from the subjectOfRecord.partId property
     */
    private List<UUID> getAnfStatementUuids(List<AnfStatementModel> results) {
        return results.stream()
                .map(anf -> anf.getSubjectOfRecord().getPartId())
                .distinct()
                .toList();
    }

    /**
     * Retrieves a list of AnfStatementModels based on the given Predicate and CriteriaQuery.
     *
     * @param predicate the Predicate to filter the AnfStatementModels
     * @param query     the CriteriaQuery to perform the query with
     * @return a list of AnfStatementModels matching the given Predicate and CriteriaQuery
     */
    private List<AnfStatementModel> getAnfStatementModels(Predicate predicate, CriteriaQuery<TinkarConceptModel> query) {
        query.where(predicate);

        log.info("Executing query: {}", query);

        return entityManager.createQuery(query).getResultList().stream()
                .map(TinkarConceptModel::getAnfStatements)
                .collect(ArrayList::new, List::addAll, List::addAll);
    }

    /**
     * Processes the given list of queries.
     *
     * @param queries the list of queries to be processed
     * @return the processing results
     */
    private ProcessingResults processQuery(List<Query> queries) {
        validateQueryList(queries);
        processQueries(queries);

        if (queries.size() == 1) {
            return prepareResult(queries.get(0));
        }

        int index = 1;
        while (index + 2 < queries.size()) {
            processByJoinOperation(queries, index);
            index += 2;
        }
        return prepareResult(queries.get(queries.size() - 1));
    }

    /**
     * Validates a list of Query objects.
     *
     * @param queries The list of Query objects to be validated.
     * @throws IllegalArgumentException If the size of the queries list is even.
     */
    private void validateQueryList(List<Query> queries) {
        if (queries.size() % 2 == 0) {
            throw new IllegalArgumentException("Malformed Query syntax");
        }
    }

    /**
     * Processes a list of queries by setting ANF statements and concept IDs for each query
     *
     * @param queries the list of queries to process
     */
    private void processQueries(List<Query> queries) {
        queries.forEach(query -> {
            if (query.getConceptId() != null) {
                query.setAnfStatements(this.runQuery(query));
                query.setConceptIds(this.getAnfStatementUuids(query.getAnfStatements()));
            }
        });
    }

    /**
     * Prepares the result using the given query.
     *
     * @param query The query object containing concept IDs and ANF statements.
     * @return The processing results object containing the concept IDs and ANF statements.
     */
    private ProcessingResults prepareResult(Query query) {
        return new ProcessingResults(query.getConceptIds(), query.getAnfStatements());
    }

    /**
     * Processes the given list of queries based on the join operation at the specified index.
     *
     * @param queries the list of queries
     * @param index   the index of the join operation in the list of queries
     */
    private void processByJoinOperation(List<Query> queries, int index) {
        JoinOperation joinOperation = queries.get(index).getJoinOperation();
        ProcessingResults results;

        if (joinOperation.equals(JoinOperation.AND)) {
            results = processAndResults(queries.get(index - 1), queries.get(index + 1));
        } else if (joinOperation.equals(JoinOperation.OR)) {
            results = processOrResults(queries.get(index - 1), queries.get(index + 1));
        } else {
            throw new IllegalArgumentException("Malformed Query syntax");
        }

        updateQueryWithResults(queries.get(index + 1), results);
    }

    /**
     * Updates a given Query object with the results from ProcessingResults.
     *
     * @param query   The Query object to be updated.
     * @param results The ProcessingResults object containing the results to update the Query object with.
     */
    private void updateQueryWithResults(Query query, ProcessingResults results) {
        query.setConceptIds(results.conceptIds);
        query.setAnfStatements(results.anfStatements);
    }

    /**
     * Combines the concept IDs and ANF statements from two given query objects into a single ProcessingResults object.
     *
     * @param query1 The first Query object containing concept IDs and ANF statements to be combined.
     * @param query2 The second Query object containing concept IDs and ANF statements to be combined.
     * @return A ProcessingResults object containing the combined concept IDs and ANF statements.
     */
    private ProcessingResults processOrResults(Query query1, Query query2) {
        List<UUID> uuids = new ArrayList<>(query1.getConceptIds());
        uuids.addAll(query2.getConceptIds());
        List<AnfStatementModel> anfStatements = new ArrayList<>(query1.getAnfStatements());
        anfStatements.addAll(query2.getAnfStatements());

        return new ProcessingResults(uuids, anfStatements);
    }

    /**
     * Process and combine the provided queries to produce the processing results.
     *
     * @param query1 The first query to process.
     * @param query2 The second query to process.
     * @return The processing results obtained by combining the concept IDs and ANF statements from the queries.
     */
    private ProcessingResults processAndResults(Query query1, Query query2) {
        List<UUID> uuids = ListUtils.union(query1.getConceptIds(), query2.getConceptIds());
        List<AnfStatementModel> anfStatements = new ArrayList<>(query1.getAnfStatements());
        anfStatements.addAll(query2.getAnfStatements());

        return new ProcessingResults(uuids, anfStatements);
    }

    /**
     * Runs a query to retrieve a list of {@link AnfStatementModel} based on the given {@link Query}.
     *
     * @param query the query object used to filter the data
     * @return a list of AnfStatementModel objects that match the given query
     */
    private List<AnfStatementModel> runQuery(Query query) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TinkarConceptModel> criteriaQuery = cb.createQuery(TinkarConceptModel.class);
        Root<TinkarConceptModel> root = criteriaQuery.from(TinkarConceptModel.class);

        criteriaQuery.where(cb.equal(root.get("conceptId"), query.getConceptId()));

        return entityManager.createQuery(criteriaQuery).getResultList().stream()
                .map(TinkarConceptModel::getAnfStatements)
                .collect(ArrayList::new, List::addAll, List::addAll);
    }

    public record ProcessingResults(List<UUID> conceptIds, List<AnfStatementModel> anfStatements) {
    }

}
