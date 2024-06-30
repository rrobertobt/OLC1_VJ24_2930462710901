package edu.robertob.olc1.vj24.Engine.Statements;

import edu.robertob.olc1.vj24.Engine.Base.Instruction;
import edu.robertob.olc1.vj24.Engine.Structs.JCError;
import edu.robertob.olc1.vj24.Engine.Structs.SymbolTable;
import edu.robertob.olc1.vj24.Engine.Structs.Tree;
import edu.robertob.olc1.vj24.Engine.Structs.Types;

import java.util.LinkedList;

public class ForStatement extends Instruction {

    private Instruction init;
    private Instruction condition;
    private Instruction increment;
    private LinkedList<Instruction> body;

    public ForStatement(Instruction init, Instruction condition, Instruction increment, LinkedList<Instruction> body, int line, int column) {
        super(Types.VOID, line, column);
        this.init = init;
        this.condition = condition;
        this.increment = increment;
        this.body = body;
    }

    @Override
    public Object execute(Tree tree, SymbolTable table) {
        var newTable = new SymbolTable(table);

        // Check initialization
        var initResult = init.execute(tree, newTable);
        if (initResult instanceof JCError) return initResult;

        // Check condition
        var conditionResult = condition.execute(tree, newTable);
        if (conditionResult instanceof JCError) return conditionResult;
        if (condition.getType() != Types.BOOLEAN) {
            return new JCError("Semantica", "La condición de un bloque 'for' debe ser de tipo booleano", line, column);
        }

        while ((boolean) condition.execute(tree, newTable)) {
            newTable.setName("FOR@"+line+":"+column);

            for (var instruction : body) {
                if (instruction instanceof Declaration) {
                    newTable.getSymbols().remove(((Declaration) instruction).getId().toLowerCase());
                }

                var result = instruction.execute(tree, newTable);
                if (result instanceof JCError) return result;
                if (result instanceof Break) {
                    return null;
                }

                if (result instanceof Continue) {
                    break;
                }
            }
            var update = increment.execute(tree, newTable);
            if (update instanceof JCError) return update;
        }
        return null;
    }

    @Override
    public String generateAstDotFormat(Tree tree, String previousContent) {
        return "";
    }
}
