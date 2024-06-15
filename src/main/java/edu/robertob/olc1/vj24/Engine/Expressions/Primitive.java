package edu.robertob.olc1.vj24.Engine.Expressions;

import edu.robertob.olc1.vj24.Engine.Base.Instruction;
import edu.robertob.olc1.vj24.Engine.Structs.SymbolTable;
import edu.robertob.olc1.vj24.Engine.Structs.Tree;
import edu.robertob.olc1.vj24.Engine.Structs.Types;

public class Primitive extends Instruction {
    public Object value;

    public Primitive(Types type, Object value, int line, int column) {
        super(type, line, column);
        this.value = value;
    }

    @Override
    public Object execute(Tree tree, SymbolTable table) {
        return this.value;
    }
}
