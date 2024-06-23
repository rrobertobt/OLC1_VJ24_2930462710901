package edu.robertob.olc1.vj24.Engine.Statements;

import edu.robertob.olc1.vj24.Engine.Base.Instruction;
import edu.robertob.olc1.vj24.Engine.Structs.JCError;
import edu.robertob.olc1.vj24.Engine.Structs.SymbolTable;
import edu.robertob.olc1.vj24.Engine.Structs.Tree;
import edu.robertob.olc1.vj24.Engine.Structs.Types;

import java.util.LinkedList;

public class WhileStatement extends Instruction {

    public Instruction condition;
    public LinkedList<Instruction> whileBody;

    public WhileStatement(Instruction condition, LinkedList<Instruction> whileBody, int line, int column) {
        super(Types.VOID, line, column);
        this.condition = condition;
        this.whileBody = whileBody;
    }

    @Override
    public Object execute(Tree tree, SymbolTable table) {
        var resultCondition = condition.execute(tree, table);
        if (resultCondition instanceof JCError) return resultCondition;

        if (condition.getType() != Types.BOOLEAN) {
            return new JCError("Semantica", "La condición de un bloque while debe ser de tipo booleano", line, column);
        }

        var newTable = new SymbolTable(table);
        table.getChildren().add(newTable);
        this.scopeTable = newTable;
        newTable.setName("WHILE@"+line+":"+column);
        while((boolean) resultCondition){
            for (var instruction : whileBody) {
                if (instruction instanceof Declaration) {
                    newTable.getSymbols().remove(((Declaration) instruction).getId().toLowerCase());
                }

                var result = instruction.execute(tree, newTable);
                if (result instanceof JCError) return result;
                if (result instanceof Break) {
                    return null; // Exit the loop
                }

                // Check for continue
                if (result instanceof Continue) {
                    break; // Exit the current iteration and evaluate the condition again
                }

            }
            resultCondition = condition.execute(tree, table);
            if (resultCondition instanceof JCError) return resultCondition;
        }
        return null;
    }
}
