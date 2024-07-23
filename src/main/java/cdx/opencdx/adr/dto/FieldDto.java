package cdx.opencdx.adr.dto;

public class FieldDto {
    private boolean isString;
    private String value;
    private double number;

    public FieldDto(String value) {
        this.value = value;
        this.isString = true;
    }

    public FieldDto(double number) {
        this.number = number;
        this.isString = false;
    }

    @Override
    public String toString() {
        if(isString) {
            return "\"" + value + "\"";
        } else {
            return String.valueOf(number);
        }
    }
}
