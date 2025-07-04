package edu.robertob.olc1.vj24.Engine.Structs;

public class JCError {
    private String type;
    private String description;
    private int line;
    private int column;

    public JCError(String type, String description, int line, int column) {
        this.type = type;
        this.description = description;
        this.line = line;
        this.column = column;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public String toString(){
        return "!> Error: " + this.type + " - " + this.description + " en: " + this.line + " column: " + this.column;
    }
}
