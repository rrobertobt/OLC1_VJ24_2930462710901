package edu.robertob.olc1.vj24.Engine.Expressions;

import edu.robertob.olc1.vj24.Engine.Base.Instruction;
import edu.robertob.olc1.vj24.Engine.Structs.SymbolTable;
import edu.robertob.olc1.vj24.Engine.Structs.Tree;
import edu.robertob.olc1.vj24.Engine.Structs.Types;

public class Comparison extends Instruction {
    public Comparison(Types type, int line, int column) {
        super(type, line, column);
    }

    @Override
    public Object execute(Tree tree, SymbolTable table) {
        return null;
    }
}
