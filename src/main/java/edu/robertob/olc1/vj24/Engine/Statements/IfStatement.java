package edu.robertob.olc1.vj24.Engine.Statements;

import edu.robertob.olc1.vj24.Engine.Base.Instruction;
import edu.robertob.olc1.vj24.Engine.Structs.JCError;
import edu.robertob.olc1.vj24.Engine.Structs.SymbolTable;
import edu.robertob.olc1.vj24.Engine.Structs.Tree;
import edu.robertob.olc1.vj24.Engine.Structs.Types;

import java.util.LinkedList;

public class IfStatement extends Instruction {

    private Instruction condition;
    //    private LinkedList<Instruction> ifBody;
    private LinkedList<ConditionBlock> conditionBlocks;
    private LinkedList<Instruction> elseBody;

//    public IfStatement(Instruction condition, LinkedList<Instruction> ifBody, int line, int column) {
//        super(Types.VOID, line, column);
//        this.condition = condition;
//        this.ifBody = ifBody;
//    }
//
//    public IfStatement(Instruction condition, LinkedList<Instruction> ifBody, LinkedList<Instruction> elseBody, int line, int column) {
//        super(Types.VOID, line, column);
//        this.condition = condition;
//        this.ifBody = ifBody;
//        this.elseBody = elseBody;
//    }

    public IfStatement(LinkedList<ConditionBlock> conditionBlocks, int line, int column) {
        super(Types.VOID, line, column);
        this.conditionBlocks = conditionBlocks;
    }

    public IfStatement(LinkedList<ConditionBlock> conditionBlocks, LinkedList<Instruction> elseBody, int line, int column) {
        super(Types.VOID, line, column);
        this.conditionBlocks = conditionBlocks;
        this.elseBody = elseBody;
    }

    @Override
    public Object execute(Tree tree, SymbolTable table) {
        for (ConditionBlock block : conditionBlocks) {
            var resultCondition = block.getCondition().execute(tree, table);
            if (resultCondition instanceof JCError) return resultCondition;

            if (block.getCondition().getType() != Types.BOOLEAN) {
                return new JCError("Semantica", "La condición de un bloque if debe ser de tipo booleano", line, column);
            }

            if ((boolean) resultCondition) {
                var newTable = new SymbolTable(table);
                table.getChildren().add(newTable);
                newTable.setName("IF@" + line + ":" + column);

                for (var instruction : block.getBody()) {
                    var result = instruction.execute(tree, newTable);
                    if (result instanceof JCError || result instanceof Break || result instanceof Continue)
                        return result;
                }
                return null; // If one block is executed, no further blocks or else should be executed
            }
        }

        if (elseBody != null) {
            SymbolTable elseTable = new SymbolTable(table);
            table.getChildren().add(elseTable);
            elseTable.setName("ELSE@" + line + ":" + column);

            for (var instruction : elseBody) {
                var result = instruction.execute(tree, elseTable);
                if (result instanceof JCError || result instanceof Break || result instanceof Continue) return result;
            }
        }

        return null;

//        var resultCondition = condition.execute(tree, table);
//        if (resultCondition instanceof JCError) return resultCondition;
//
//        if (condition.getType() != Types.BOOLEAN) {
//            return new JCError("Semantica", "La condición de un bloque if debe ser de tipo booleano", line, column);
//        }
//
//        var newTable = new SymbolTable(table);
//        table.getChildren().add(newTable);
//        this.scopeTable = newTable;
//        newTable.setName("IF@"+line+":"+column);

//        if (elseBody == null) {
//            if((boolean) resultCondition){
//                for (var instruction : ifBody) {
//                    var result = instruction.execute(tree, newTable);
//                    if (result instanceof JCError || result instanceof Break || result instanceof Continue) return result;
//                }
//            }
//        } else {
//            if ((boolean) resultCondition) {
//                for (var instruction : ifBody) {
//                    var result = instruction.execute(tree, newTable);
//                    if (result instanceof JCError || result instanceof Break || result instanceof Continue) return result;
//                }
//            } else {
//                for (var instruction : elseBody) {
//                    var result = instruction.execute(tree, newTable);
//                    if (result instanceof JCError || result instanceof Break || result instanceof Continue) return result;
//                }
//            }
//        }

//        if ((boolean) resultCondition) {
//            for (var instruction : ifBody) {
//                var result = instruction.execute(tree, newTable);
//                if (result instanceof JCError || result instanceof Break || result instanceof Continue) return result;
//            }
//        } else if (elseBody != null) {
//            // Create new symbol table for the else body
//            SymbolTable elseTable = new SymbolTable(table);
//            table.getChildren().add(elseTable);
//            elseTable.setName("ELSE@" + line + ":" + column);
//
//            // Execute the else body with the new symbol table
//            for (var instruction : elseBody) {
//                var result = instruction.execute(tree, elseTable);
//                if (result instanceof JCError || result instanceof Break || result instanceof Continue) return result;
//            }
//        }
    }

    public static class ConditionBlock {
        private Instruction condition;
        private LinkedList<Instruction> body;

        public ConditionBlock(Instruction condition, LinkedList<Instruction> body) {
            this.condition = condition;
            this.body = body;
        }


        public Instruction getCondition() {
            return condition;
        }

        public LinkedList<Instruction> getBody() {
            return body;
        }
    }

    public static LinkedList<ConditionBlock> buildConditionBlockList(Instruction ifCondition, LinkedList<Instruction> ifBody, LinkedList<ConditionBlock> elseIfBlocks) {
        LinkedList<ConditionBlock> conditionBlocks = new LinkedList<>();
        conditionBlocks.add(new ConditionBlock(ifCondition, ifBody));
        conditionBlocks.addAll(elseIfBlocks);
        return conditionBlocks;
    }
}
