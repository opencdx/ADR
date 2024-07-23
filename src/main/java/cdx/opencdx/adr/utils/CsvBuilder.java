package cdx.opencdx.adr.utils;

import cdx.opencdx.adr.dto.FieldDto;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class CsvBuilder {
    private List<String> headers;
    private List<List<String>> data;
    private Map<String, Integer> headerToIndex;

    public CsvBuilder() {
        headers = new ArrayList<>();
        data = new ArrayList<>();
        headerToIndex = new HashMap<>();
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
        for (int i = 0; i < headers.size(); i++) {
            headerToIndex.put(headers.get(i), i);
        }
    }

    public void addHeader(String headerName) {
        if (!headers.contains(headerName)) {
            headers.add(headerName);
            headerToIndex.put(headerName, headers.size() - 1);
            for (List<String> row : data) {
                row.add("");
            }
        } else {
            throw new IllegalArgumentException("Duplicate header name: " + headerName);
        }
    }

    public void setCell(int rowIndex, String columnName, String value) {
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

        List<String> row = data.get(rowIndex);
        while (row.size() < headers.size()) {
            row.add("");
        }

        row.set(columnIndex, value);
    }

    public String getCell(int rowIndex, int columnIndex) {
        if (rowIndex < data.size() && columnIndex < headers.size()) {
            return data.get(rowIndex).get(columnIndex);
        } else {
            return null;
        }
    }

    public int getRowCount() {
        return data.size();
    }

    public String getCell(int rowIndex, String headerName) {
        int columnIndex = headers.indexOf(headerName);
        if (columnIndex != -1) {
            return getCell(rowIndex, columnIndex);
        } else {
            return null;
        }
    }

    public String getHeaders() {
        StringBuilder headerRow = new StringBuilder();
        for(String header : this.headers) {
            headerRow.append(header).append(",");
        }
        return headerRow.toString();
    }

    public String getRow(int rowIndex) {
        StringBuilder rowString = new StringBuilder();
        for(int i = 0; i < this.headers.size(); i++) {
            rowString.append(getCell(rowIndex, i)).append(",");
        }

        return rowString.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.join(",", headers)).append("\n");
        for (List<String> row : data) {
            sb.append(String.join(",", row)).append("\n");
        }
        return sb.toString();
    }
}
