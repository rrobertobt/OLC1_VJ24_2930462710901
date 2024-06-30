package edu.robertob.olc1.vj24.Engine.BuiltIn;

import edu.robertob.olc1.vj24.Engine.Base.Instruction;
import edu.robertob.olc1.vj24.Engine.Structs.JCError;
import edu.robertob.olc1.vj24.Engine.Structs.SymbolTable;
import edu.robertob.olc1.vj24.Engine.Structs.Tree;
import edu.robertob.olc1.vj24.Engine.Structs.Types;

public class ToString extends Instruction {

    private Instruction value;

    public ToString(Instruction value, int line, int column) {
        super(Types.STRING, line, column);
        this.value = value;
    }

    @Override
    public Object execute(Tree tree, SymbolTable table) {
        var value = this.value.execute(tree, table);
        if ((value instanceof Integer) || (value instanceof Double) || (value instanceof Boolean) || (value instanceof String) || (value instanceof Character)) {
            return value.toString();
        }
        return new JCError("Semantica", "No se puede convertir el valor a string", this.line, this.column);
    }
}
