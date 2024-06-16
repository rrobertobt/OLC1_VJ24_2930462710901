package edu.robertob.olc1.vj24.Engine.Statements;

import edu.robertob.olc1.vj24.Engine.Base.Instruction;
import edu.robertob.olc1.vj24.Engine.Structs.SymbolTable;
import edu.robertob.olc1.vj24.Engine.Structs.Tree;
import edu.robertob.olc1.vj24.Engine.Structs.Types;

public class Continue extends Instruction {

    public Continue(int line, int column) {
        super(Types.VOID, line, column);
    }

    @Override
    public Object execute(Tree tree, SymbolTable table) {
        return this;
    }
}
