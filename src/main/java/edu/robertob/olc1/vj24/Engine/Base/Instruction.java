package edu.robertob.olc1.vj24.Engine.Base;

import edu.robertob.olc1.vj24.Engine.Structs.SymbolTable;
import edu.robertob.olc1.vj24.Engine.Structs.Tree;
import edu.robertob.olc1.vj24.Engine.Structs.Types;

public abstract class Instruction {
    public Types type;
    public int line;
    public int column;
    public SymbolTable scopeTable;

    public Instruction(Types type, int line, int column) {
        this.type = type;
        this.line = line;
        this.column = column;
    }

    public abstract Object execute(Tree tree, SymbolTable table);

    public abstract String generateAstDotFormat(Tree tree, String previousContent);

    public Types getType(){
        return this.type;
    }

    public SymbolTable getScopeTable() {
        return scopeTable;
    }

    public void setScopeTable(SymbolTable scopeTable) {
        this.scopeTable = scopeTable;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }
}
