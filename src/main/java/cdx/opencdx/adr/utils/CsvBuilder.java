package cdx.opencdx.adr.utils;

import cdx.opencdx.adr.dto.Cell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * CsvBuilder is a class that allows you to build and manipulate CSV (Comma Separated Values) data.
 */
public class CsvBuilder {
    /**
     * Private variable to store the CSV data.
     * It is a list of lists of strings, representing the rows and columns of the CSV.
     */
    private final List<List<Cell>> data;
    /**
     * A private variable that maps header names to their corresponding indices in the CsvBuilder.
     */
    private final Map<String, Integer> headerToIndex;
    /**
     * This variable represents a list of headers.
     * Headers are the names of the columns in a CSV (Comma-Separated Values) file.
     * The order of the headers in this list corresponds to the order of the columns in the CSV file.
     * Each header is a string.
     * This list is private, which means it can only be accessed within the containing class.
     */
    private List<String> headers;

    /**
     * CsvBuilder is a class that allows you to build and manipulate CSV (Comma Separated Values) data.
     * <p>
     * Example usage:
     * <pre>{@code
     * CsvBuilder csvBuilder = new CsvBuilder();
     * csvBuilder.addHeader("Name");
     * csvBuilder.addHeader("Age");
     * csvBuilder.setCell(0, "Name", "John Doe");
     * csvBuilder.setCell(0, "Age", "30");
     * csvBuilder.addRow();
     * csvBuilder.setCell(1, "Name", "Alice Smith");
     * csvBuilder.setCell(1, "Age", "25");
     * String csv = csvBuilder.toString();
     * }</pre>
     * This will create a CSV string with two rows and two columns:
     * <pre>
     * Name,Age
     * John Doe,30
     * Alice Smith,25
     * </pre>
     * </p>
     */
    public CsvBuilder() {
        headers = new ArrayList<>();
        data = new ArrayList<>();
        headerToIndex = new HashMap<>();
    }

    /**
     * Adds a new header to the CsvBuilder.
     *
     * @param headerName the name of the header to be added
     * @throws IllegalArgumentException if the headerName already exists in the headers list
     */
    public void addHeader(String headerName) {
        if (!headers.contains(headerName)) {
            headers.add(headerName);
            headerToIndex.put(headerName, headers.size() - 1);
            for (List<Cell> row : data) {
                row.add(Cell.builder().build());
            }
        } else {
            throw new IllegalArgumentException("Duplicate header name: " + headerName);
        }
    }


    public int getHeaderCount() {
        return headers.size();
    }

    /**
     * Sets the value of a cell in the CSV data.
     *
     * @param rowIndex   the index of the row where the cell is located
     * @param columnName the name of the column where the cell is located
     * @param value      the value to be set in the cell
     * @throws IllegalArgumentException if the rowIndex is negative or the columnName is a duplicate
     */
    public void setCell(int rowIndex, String columnName, Cell value) {
        if (rowIndex < 0) {
            throw new IllegalArgumentException("Row index cannot be negative.");
        }

        Integer columnIndex = headerToIndex.get(columnName);
        if (columnIndex == null) {
            this.addHeader(columnName);
            columnIndex = headerToIndex.get(columnName);
        }

        while (data.size() <= rowIndex) {
            data.add(new ArrayList<>(headers.size()));
        }

        List<Cell> row = data.get(rowIndex);
        while (row.size() < headers.size()) {
            row.add(Cell.builder().build());
        }

        row.set(columnIndex, value);
    }

    /**
     * Retrieves the value at the specified cell in the CsvBuilder.
     *
     * @param rowIndex    the index of the row
     * @param columnIndex the index of the column
     * @return the value at the specified cell or null if the row index or column index is out of bounds
     */
    public Cell getCell(int rowIndex, int columnIndex) {
        if (rowIndex < data.size() && columnIndex < headers.size()) {
            return data.get(rowIndex).get(columnIndex);
        } else {
            return null;
        }
    }

    /**
     * Retrieves the number of rows in the data.
     *
     * @return The number of rows in the data.
     */
    public int getRowCount() {
        return data.size();
    }

    /**
     * Retrieves the value of a cell in the specified row with the given header name.
     *
     * @param rowIndex   the index of the row to retrieve
     * @param headerName the name of the header to retrieve
     * @return the value of the cell with the specified row and header name, or null if the header name does not exist
     */
    public Cell getCell(int rowIndex, String headerName) {
        int columnIndex = headers.indexOf(headerName);
        if (columnIndex != -1) {
            return getCell(rowIndex, columnIndex);
        } else {
            return null;
        }
    }

    /**
     * Returns the headers of the CSV file as a comma-separated string.
     *
     * @return the headers of the CSV file as a string
     */
    public String getHeaderString() {
        StringBuilder headerRow = new StringBuilder();
        for (String header : this.headers) {
            headerRow.append(header).append(",");
        }
        return headerRow.toString();
    }

    /**
     * Retrieves the list of headers for the CSV data.
     *
     * @return a list of headers as strings.
     */
    public List<String> getHeaders() {
        return headers;
    }


    /**
     * Sets the headers of the CSV file.
     *
     * @param headers the list of header names
     */
    public void setHeaders(List<String> headers) {
        this.headers = headers;
        for (int i = 0; i < headers.size(); i++) {
            headerToIndex.put(headers.get(i), i);
        }
    }

    /**
     * Returns the row at the specified index as a string, with each cell separated by a comma.
     *
     * @param rowIndex the index of the row to retrieve
     * @return a string representation of the row at the specified index
     */
    public String getRow(int rowIndex) {
        StringBuilder rowString = new StringBuilder();
        for (int i = 0; i < this.headers.size(); i++) {
            rowString.append(getCell(rowIndex, i)).append(",");
        }

        return rowString.toString();
    }

    /**
     * Returns a string representation of the CsvBuilder object.
     * The string contains the headers and data of the CsvBuilder object, separated by commas and newlines.
     *
     * @return a string representation of the CsvBuilder object
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.join(",", headers)).append("\n");
        for (List<Cell> row : data) {
            sb.append(row.stream().map(Cell::getValue).collect(Collectors.joining(","))).append("\n");
        }
        return sb.toString();
    }
}
