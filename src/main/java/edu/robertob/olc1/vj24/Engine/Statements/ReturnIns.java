package edu.robertob.olc1.vj24.Engine.Statements;

import edu.robertob.olc1.vj24.Engine.Base.Instruction;
import edu.robertob.olc1.vj24.Engine.Structs.SymbolTable;
import edu.robertob.olc1.vj24.Engine.Structs.Tree;
import edu.robertob.olc1.vj24.Engine.Structs.Types;

public class ReturnIns extends Instruction {

        private Instruction expressionToReturn;

        public ReturnIns(Instruction expressionToReturn, int line, int column) {
            super(Types.VOID, line, column);
            this.expressionToReturn = expressionToReturn;
            if (this.expressionToReturn != null) {
                this.type = expressionToReturn.type;
            }
        }

    @Override
    public Object execute(Tree tree, SymbolTable table) {
        if (expressionToReturn == null){
            return new Break(line, column);
        }
//        return expressionToReturn.execute(tree, table);
        return this;
    }

    public Instruction getExpressionToReturn() {
        return expressionToReturn;
    }
}
