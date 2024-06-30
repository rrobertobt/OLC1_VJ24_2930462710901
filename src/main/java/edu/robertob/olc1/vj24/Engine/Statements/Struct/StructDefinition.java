package edu.robertob.olc1.vj24.Engine.Statements.Struct;

import java.util.LinkedList;

public class StructDefinition {
    private String structName;
    private LinkedList<StructField> fields;

    public StructDefinition(String structName, LinkedList<StructField> fields) {
        this.structName = structName;
        this.fields = fields;
    }

    public String getStructName() {
        return structName;
    }

    public void setStructName(String structName) {
        this.structName = structName;
    }

    public LinkedList<StructField> getFields() {
        return fields;
    }

    public void setFields(LinkedList<StructField> fields) {
        this.fields = fields;
    }
}
