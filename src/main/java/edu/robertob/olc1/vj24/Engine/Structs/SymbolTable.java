package edu.robertob.olc1.vj24.Engine.Structs;

import java.util.HashMap;

public class SymbolTable {
    private SymbolTable parentTable;
    private HashMap<String, Object> symbols;
    private String name;

    public SymbolTable(String name) {
        this.parentTable = null;
        this.symbols = new HashMap<>();
        this.name = name;
    }

    public boolean setSymbol(SymbolVariable symbol) {
        SymbolVariable search = (SymbolVariable) this.symbols.get(symbol.getId().toLowerCase());
        if (search == null) {
            this.symbols.put(symbol.getId().toLowerCase(), symbol);
            return true;
        }
        return false;
    }

    public SymbolVariable getSymbol(String id) {
        for (SymbolTable table = this; table != null; table = table.getParentTable()) {
            SymbolVariable symbol = (SymbolVariable) table.symbols.get(id.toLowerCase());
            if (symbol != null) return symbol;
        }
        return null;
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
