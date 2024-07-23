package cdx.opencdx.adr.dto;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
@Slf4j
public class CsvDto {

    List<String> headers;
    ArrayList<ArrayList<FieldDto>> data;

    public CsvDto() {
        this.headers = new ArrayList<>();
        this.data = new ArrayList<>();
    }

    public void writeField(String header, int row, String value) {
        if(this.getColumn(header) == -1) {
            this.headers.add(header);
        }

        int column = this.getColumn(header);

        if(this.data.size() <= row ) {
            log.info("Adding new row: {}", row);
            log.info("Current data size: {}", this.data.size());
            this.data.add(row, new ArrayList<>());
            log.info("New data size: {}", this.data.size());
        }

        ArrayList<FieldDto> fieldDtos = this.data.get(row);
        if(fieldDtos.size() <= column + 2) {
         fieldDtos.ensureCapacity(column +2);
        }
        fieldDtos.set(column, new FieldDto(value));

    }

    private int getColumn(String header) {
        return this.headers.indexOf(header);
    }

    public String getHeaderRow() {
        StringBuilder headerRow = new StringBuilder();
        for(String header : this.headers) {
            headerRow.append(header).append(",");
        }
        return headerRow.toString();
    }

    public int getRowCount() {
        return this.data.size();
    }

    public String getRow(int row) {
        StringBuilder rowString = new StringBuilder();
        for(int i = 0; i < this.headers.size(); i++) {
            FieldDto field = this.data.get(row).get(i);
            if(field != null) {
                rowString.append(field).append(",");
            } else {
                rowString.append(",");
            }
        }

        return rowString.toString();
    }
}
