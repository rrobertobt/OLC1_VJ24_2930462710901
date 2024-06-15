package edu.robertob.olc1.vj24.Engine.Base;

import edu.robertob.olc1.vj24.Engine.Structs.Types;

public abstract class Instruction {
    public Types type;
    public int line;
    public int column;

    public Instruction(Types type, int line, int column) {
        this.type = type;
        this.line = line;
        this.column = column;
    }

    public abstract Object interpret();
}
