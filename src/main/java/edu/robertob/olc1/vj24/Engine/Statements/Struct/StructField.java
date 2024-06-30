package edu.robertob.olc1.vj24.Engine.Statements.Struct;

import java.util.LinkedHashMap;
import java.util.List;

public class StructField {
    private String fieldName;
    private StructType type;

    public StructField(String fieldName, StructType type) {
        this.fieldName = fieldName;
        this.type = type;
    }

    public String getFieldName() {
        return fieldName;
    }

    public StructType getType() {
        return type;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public void setType(StructType type) {
        this.type = type;
    }

    public static StructField getField(String fieldName, List<StructField> fields) {
        for (StructField field : fields) {
            if (field.getFieldName().equals(fieldName)) {
                return field;
            }
        }
        return null;
    }

}
