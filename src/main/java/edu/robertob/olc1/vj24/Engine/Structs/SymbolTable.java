package edu.robertob.olc1.vj24.Engine.Structs;

import java.util.HashMap;

public class SymbolTable {
    private SymbolTable parentTable;
//    private LinkedList<Symbol> symbols;
    private HashMap<String, Object> symbols;
    private String name;

    public SymbolTable() {
        this.parentTable = null;
        this.symbols = new HashMap<>();
        this.name = "";
    }

    public SymbolTable(SymbolTable parentTable) {
        this.parentTable = parentTable;
        this.symbols = new HashMap<>();
        this.name = "";
    }

    public SymbolTable getParentTable() {
        return parentTable;
    }

    public void setParentTable(SymbolTable parentTable) {
        this.parentTable = parentTable;
    }

    public HashMap<String, Object> getSymbols() {
        return symbols;
    }

    public void setSymbols(HashMap<String, Object> symbols) {
        this.symbols = symbols;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
