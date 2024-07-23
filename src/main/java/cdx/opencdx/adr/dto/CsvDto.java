package cdx.opencdx.adr.dto;

import java.util.ArrayList;
import java.util.List;

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

        ArrayList<FieldDto> fieldDtos = this.data.get(row);
        if(fieldDtos == null) {
            fieldDtos = new ArrayList<>();
            this.data.add(row, fieldDtos);
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
