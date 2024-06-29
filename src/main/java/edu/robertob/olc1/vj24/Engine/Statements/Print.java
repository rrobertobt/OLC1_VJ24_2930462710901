package edu.robertob.olc1.vj24.Engine.Statements;

import edu.robertob.olc1.vj24.Engine.Base.Instruction;
import edu.robertob.olc1.vj24.Engine.Structs.JCError;
import edu.robertob.olc1.vj24.Engine.Structs.SymbolTable;
import edu.robertob.olc1.vj24.Engine.Structs.Tree;
import edu.robertob.olc1.vj24.Engine.Structs.Types;

public class Print extends Instruction {
    private Instruction expression;

    public Print(Instruction expression, int line, int column) {
        super(Types.VOID, line, column);
        this.expression = expression;
    }

    @Override
    public Object execute(Tree tree, SymbolTable table) {
        var result = this.expression.execute(tree, table);
        if (result instanceof JCError) {
            return result;
        }
        if (result != null) tree.print(result.toString());
        if (result == null) tree.print("print() recibio valor indefinido");
        return null;
    }

}
