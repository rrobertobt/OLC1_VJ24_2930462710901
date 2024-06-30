package edu.robertob.olc1.vj24.Engine.Statements;

import edu.robertob.olc1.vj24.Engine.Base.Instruction;
import edu.robertob.olc1.vj24.Engine.Structs.JCError;
import edu.robertob.olc1.vj24.Engine.Structs.SymbolTable;
import edu.robertob.olc1.vj24.Engine.Structs.Tree;
import edu.robertob.olc1.vj24.Engine.Structs.Types;

public class SymbolAssignation extends Instruction {

    private String id;
    private Instruction newValue;

    public SymbolAssignation(String id, Instruction newValue, int line, int column) {
        super(Types.VOID, line, column);
        this.id = id;
        this.newValue = newValue;
    }

    @Override
    public Object execute(Tree tree, SymbolTable table) {
        var symbol = table.getSymbol(id);
        if (symbol == null) {
            return new JCError("Semantica", "Variable " + id + " no existe", this.line, this.column);
        }

        var result = this.newValue.execute(tree, table);
        if (result instanceof JCError) return result;

        if (symbol.getType() != this.newValue.getType())
            return new JCError("Semantica", "No se puede asignar el valor de tipo " + this.newValue.getType() + " a una variable de tipo " + symbol.getType(), this.line, this.column);

        if(symbol.isConstant())
            return new JCError("Semantica", "Variable constante " + id + " no puede ser re-asignada", this.line, this.column);

        symbol.setValue(result);
        return null;
    }
}
