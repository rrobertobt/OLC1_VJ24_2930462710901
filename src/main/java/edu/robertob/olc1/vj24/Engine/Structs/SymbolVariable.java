package edu.robertob.olc1.vj24.Engine.Structs;

public class SymbolVariable {
    private Types type;
    private String id;
    private Object value;
    private boolean constant;

    public SymbolVariable(Types type, String id) {
        this.type = type;
        this.id = id;
    }

    public SymbolVariable(Types type, boolean constant,String id, Object value) {
        this.type = type;
        this.constant = constant;
        this.id = id;
        this.value = value;
    }

    public Types getType() {
        return type;
    }

    public void setType(Types type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean isConstant() {
        return constant;
    }
}
