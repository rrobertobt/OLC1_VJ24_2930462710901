package edu.robertob.olc1.vj24.Engine.Expressions;

import edu.robertob.olc1.vj24.Engine.Base.Instruction;
import edu.robertob.olc1.vj24.Engine.Structs.JCError;
import edu.robertob.olc1.vj24.Engine.Structs.SymbolTable;
import edu.robertob.olc1.vj24.Engine.Structs.Tree;
import edu.robertob.olc1.vj24.Engine.Structs.Types;

public class SymbolAccess extends Instruction {
    private String id;

    public SymbolAccess(String id, int line, int col) {
        super(Types.VOID, line, col);
        this.id = id;
    }

    @Override
    public Object execute(Tree tree, SymbolTable table) {
        var value = table.getSymbol(this.id);
        if (value == null) {
            return new JCError("Semantica", "Variable: " + this.id + " no existe", this.line, this.column);
        }
        this.type = value.getType();
        return value.getValue();
    }
}
