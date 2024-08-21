package cdx.opencdx.adr.service.impl;

import cdx.opencdx.adr.dto.*;
import cdx.opencdx.adr.model.AnfStatementModel;
import cdx.opencdx.adr.model.CalculatedConcept;
import cdx.opencdx.adr.model.MeasureModel;
import cdx.opencdx.adr.model.TinkarConceptModel;
import cdx.opencdx.adr.repository.ANFStatementRepository;
import cdx.opencdx.adr.repository.CalculatedConceptRepository;
import cdx.opencdx.adr.service.*;
import cdx.opencdx.adr.utils.ANFHelper;
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
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * The QueryServiceImpl class is an implementation of the QueryService interface.
 * It provides methods for processing queries and writing the results as CSV content.
 */
@Slf4j
@Service
public class QueryServiceImpl implements QueryService {
    private final ANFStatementRepository aNFStatementRepository;

    private final ANFHelper anfRepo;
    private final CsvService csvService;
    private final MeasureOperationService measureOperationService;
    private final TextOperationService textOperationService;
    private final CalculatedConceptRepository calculatedConceptRepository;
    private final FormulaService formulaService;
    private final ConceptService conceptService;


    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Creates a new instance of QueryServiceImpl with the given ANFRepo and CsvService objects.
     *
     * @param anfRepo                     the ANFRepo object used for querying ANF data
     * @param csvService                  the CsvService object used for csv operations
     * @param measureOperationService     the MeasureOperationService object used for measure operations
     * @param textOperationService        the TextOperationService object used for text operations
     * @param calculatedConceptRepository the CalculatedConceptRepository object used for calculated concept operations
     * @param formulaService              the FormulaService object used for formula operations
     * @param aNFStatementRepository      the ANFStatementRepository object used for ANF statement operations
     * @param conceptService              the ConceptService object used for concept operations
     */
    public QueryServiceImpl(ANFHelper anfRepo, CsvService csvService, MeasureOperationService measureOperationService, TextOperationService textOperationService, CalculatedConceptRepository calculatedConceptRepository, FormulaService formulaService,
                            ANFStatementRepository aNFStatementRepository, ConceptService conceptService) {
        this.anfRepo = anfRepo;
        this.csvService = csvService;
        this.measureOperationService = measureOperationService;
        this.textOperationService = textOperationService;
        this.calculatedConceptRepository = calculatedConceptRepository;
        this.formulaService = formulaService;
        this.aNFStatementRepository = aNFStatementRepository;
        this.conceptService = conceptService;
    }

    /**
     * Processes the given ADRQuery and writes the results to the provided PrintWriter.
     *
     * @param adrQuery The ADRQuery to process.
     * @param writer The PrintWriter to write the results to.
     */
    @Override
    public void processQuery(ADRQuery adrQuery, PrintWriter writer) {

        List<CalculatedConcept> allByThreadName = this.calculatedConceptRepository.findAllByThreadName(Thread.currentThread().getName());
        if(!allByThreadName.isEmpty()) {
            this.calculatedConceptRepository.deleteAll(allByThreadName);
        }

        ProcessingResults processingResults = processQuery(adrQuery.getQueries());

        List<AnfStatementModel> results = processingResults.anfStatements;

        List<UUID> list = processingResults.conceptIds;
        List<String> csvContent = prepareCsvContent(list, results, adrQuery.getUnitOutput());
        csvContent.forEach(writer::println);
    }

    /**
     * Prepares the content for a CSV file based on the given UUIDs, results, and unit output.
     *
     * @param uuids      The list of UUIDs.
     * @param results    The list of AnfStatementModel objects.
     * @param unitOutput The unit output.
     * @return The prepared CSV content as a list of strings.
     */
    private List<String> prepareCsvContent(List<UUID> uuids, List<AnfStatementModel> results,UnitOutput unitOutput) {
        CsvBuilder csvDto = this.csvService.buildCsvDto(uuids, results, unitOutput);
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
        while (index + 1 < queries.size()) {
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
            query.setAnfStatements(this.runQuery(query));
            query.setConceptIds(this.getAnfStatementUuids(query.getAnfStatements()));
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

        uuids = uuids.stream().distinct().toList();
        anfStatements = anfStatements.stream().distinct().toList();

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
        List<UUID> uuids = ListUtils.intersection(query1.getConceptIds(), query2.getConceptIds());
        List<AnfStatementModel> anfStatements = new ArrayList<>();

        anfStatements.addAll(query1.getAnfStatements().stream().filter(anf -> uuids.contains(anf.getSubjectOfRecord().getPartId())).toList());
        anfStatements.addAll(query2.getAnfStatements().stream().filter(anf -> uuids.contains(anf.getSubjectOfRecord().getPartId())).toList());

        return new ProcessingResults(uuids, anfStatements);
    }

    /**
     * Executes a simple query to retrieve a list of {@link AnfStatementModel}.
     *
     * @param query the query object containing the concept id to filter the results
     * @return a list of {@link AnfStatementModel} matching the query criteria
     */
    private List<AnfStatementModel> runSimpleQuery(Query query) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<TinkarConceptModel> criteriaQuery = cb.createQuery(TinkarConceptModel.class);
        Root<TinkarConceptModel> root = criteriaQuery.from(TinkarConceptModel.class);

        criteriaQuery.where(root.get("conceptId").in(this.conceptService.getFocusConcepts(query.getConcept())));

        return entityManager.createQuery(criteriaQuery).getResultList().stream()
                .map(TinkarConceptModel::getAnfStatements)
                .collect(ArrayList::new, List::addAll, List::addAll);
    }

    /**
     * Checks the operation based on the given value.
     *
     * @param operation the operation to be checked
     * @param value the value parameter to be checked, can be a MeasureModel or a String
     *
     * @return true if the operation is successfully checked, false otherwise
     */
    private boolean check(ComparisonOperation operation, Object operationValue, TinkarConceptModel operationUnit, Object value) {
        if(value instanceof MeasureModel measure) {
            return this.measureOperationService.measureOperation(operation, (Double)operationValue, operationUnit,  measure);
        } else if(value instanceof String text) {
            return this.textOperationService.textOperation(operation, (String) operationValue, text);
        } return false;
    }

    /**
     * Executes a query and returns a list of AnfStatementModel objects filtered based on query conditions.
     *
     * @param query The query object representing the conditions to filter the AnfStatementModel objects.
     * @return A list of AnfStatementModel objects that match the conditions specified in the query.
     */
    private List<AnfStatementModel> runQuery(Query query) {
        List<AnfStatementModel> simpleQueryResults = null;
        if(query.getConcept() != null && query.getConcept().getConceptId() != null) {
           simpleQueryResults = runSimpleQuery(query);
        } else if(query.getGroup() != null) {
            ProcessingResults results = this.processQuery(query.getGroup());
            simpleQueryResults = results.anfStatements;
        } else if(query.getFormula() != null) {
            simpleQueryResults = this.formulaService.evaluateFormula(query.getFormula());
        } else {
            log.info("Returning Empty Query Results");
            return Collections.emptyList();
        }
        return this.processOperational(query, simpleQueryResults);
    }

    /**
     * Represents the result of processing certain data.
     */
    public record ProcessingResults(List<UUID> conceptIds, List<AnfStatementModel> anfStatements) {
    }

    private List<AnfStatementModel> processOperational(Query query, List<AnfStatementModel> simpleQueryResults) {
        if (query.getOperation() == null) {
            log.info("Returning Simple Query Results: {}", simpleQueryResults.size());
            return simpleQueryResults;
        }
        log.info("Processing Operational Query Results: {} on Operation: {}", simpleQueryResults.size(),query.getOperation());
        return simpleQueryResults.stream().filter(anf -> {
            if (anf.getPerformanceCircumstance() != null && anf.getPerformanceCircumstance().getResult() != null) {
                log.info("Processing Performance Circumstance Operation Double: {}", query.getOperationDouble());
                return this.check(query.getOperation(), query.getOperationDouble(), query.getOperationUnit(), anf.getPerformanceCircumstance().getResult());
            } else if (anf.getRequestCircumstance() != null && anf.getRequestCircumstance().getRequestedResult() != null) {
                log.info("Processing Request Circumstance Operation Double: {}", query.getOperationDouble());
                return this.check(query.getOperation(), query.getOperationDouble(), query.getOperationUnit(), anf.getRequestCircumstance().getRequestedResult());
            } else if (anf.getNarrativeCircumstance() != null && anf.getNarrativeCircumstance().getText() != null) {
                log.info("Processing Narrative Circumstance Operation Text: {}", query.getOperationText());
                return this.check(query.getOperation(), query.getOperationText(), query.getOperationUnit(), anf.getNarrativeCircumstance().getText());
            }
            return false;
        }).toList();
    }
}
