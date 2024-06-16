package edu.robertob.olc1.vj24.Engine.Structs;

public class SymbolVariable {
    private Types type;
    private String id;
    private String scopeName;
    private Object value;
    private boolean constant;
    private int line;
    private int column;

    public SymbolVariable(Types type, String id) {
        this.type = type;
        this.id = id;
    }

    public SymbolVariable(Types type, boolean constant,String id, Object value,int line, int column) {
        this.type = type;
        this.constant = constant;
        this.id = id;
        this.value = value;
        this.line = line;
        this.column = column;
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

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }

    public String getScopeName() {
        return scopeName;
    }

    public void setScopeName(String scopeName) {
        this.scopeName = scopeName;
    }
}
