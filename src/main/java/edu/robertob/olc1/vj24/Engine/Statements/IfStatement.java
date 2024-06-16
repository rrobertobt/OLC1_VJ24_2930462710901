package edu.robertob.olc1.vj24.Engine.Statements;

import edu.robertob.olc1.vj24.Engine.Base.Instruction;
import edu.robertob.olc1.vj24.Engine.Structs.JCError;
import edu.robertob.olc1.vj24.Engine.Structs.SymbolTable;
import edu.robertob.olc1.vj24.Engine.Structs.Tree;
import edu.robertob.olc1.vj24.Engine.Structs.Types;

import java.util.LinkedList;

public class IfStatement extends Instruction {

    private Instruction condition;
    private LinkedList<Instruction> ifBody;

    public IfStatement(Instruction condition, LinkedList<Instruction> ifBody, int line, int column) {
        super(Types.VOID, line, column);
        this.condition = condition;
        this.ifBody = ifBody;
    }

    @Override
    public Object execute(Tree tree, SymbolTable table) {
        var resultCondition = condition.execute(tree, table);
        if (resultCondition instanceof JCError) return resultCondition;

        if (condition.getType() != Types.BOOLEAN) {
            return new JCError("Semantica", "La condición de un bloque if debe ser de tipo booleano", line, column);
        }

        var newTable = new SymbolTable(table);
        table.getChildren().add(newTable);
        this.scopeTable = newTable;
        newTable.setName("IF@"+line+":"+column);
        if((boolean) resultCondition){
            for (var instruction : ifBody) {
                var result = instruction.execute(tree, newTable);
                if (result instanceof JCError) return result;
            }
        }
        return null;
    }
}
