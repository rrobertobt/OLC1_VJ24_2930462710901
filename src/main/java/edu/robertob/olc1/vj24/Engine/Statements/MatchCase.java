package edu.robertob.olc1.vj24.Engine.Statements;

import edu.robertob.olc1.vj24.Engine.Base.Instruction;
import edu.robertob.olc1.vj24.Engine.Structs.SymbolTable;
import edu.robertob.olc1.vj24.Engine.Structs.Tree;

import java.util.LinkedList;

public class MatchCase {

    private Instruction expressionToCompare;
    private LinkedList<Instruction> caseBody;
    int line, column;

    public MatchCase(Instruction expressionToCompare, LinkedList<Instruction> caseBody, int line, int column) {
        this.expressionToCompare = expressionToCompare;
        this.caseBody = caseBody;
        this.line = line;
        this.column = column;
    }

    // this casebody-less constructor is used for the default case
    public MatchCase(LinkedList<Instruction> caseBody, int line, int column) {
        this.caseBody = caseBody;
        this.line = line;
        this.column = column;
    }

    public Object execute(Tree tree, SymbolTable table){
        SymbolTable localTable = new SymbolTable(table);
        for (Instruction instruction : caseBody) {
            Object result = instruction.execute(tree, localTable);
            if (result instanceof ReturnIns || result instanceof Break) {
                return result;
            }
        }
        return null;
    }

    public Instruction getExpressionToCompare() {
        return expressionToCompare;
    }

}
