package edu.robertob.olc1.vj24.Engine.Statements;

import edu.robertob.olc1.vj24.Engine.Base.Instruction;
import edu.robertob.olc1.vj24.Engine.Structs.JCError;
import edu.robertob.olc1.vj24.Engine.Structs.SymbolTable;
import edu.robertob.olc1.vj24.Engine.Structs.Tree;
import edu.robertob.olc1.vj24.Engine.Structs.Types;

import java.util.LinkedList;

public class MatchStatement extends Instruction {

    private Instruction expression;
    private MatchCase defaultCase;
    private LinkedList<MatchCase> cases;

    public MatchStatement(Instruction expression, LinkedList<MatchCase> cases, MatchCase defaultCase, int line, int column) {
        super(Types.VOID, line, column);
        this.expression = expression;
        this.cases = cases;
        this.defaultCase = defaultCase;
    }

    @Override
    public Object execute(Tree tree, SymbolTable table) {
        var comparingValue = expression.execute(tree, table);

        if (comparingValue instanceof JCError) return comparingValue;

        if (cases != null) {
            for (MatchCase matchCase : cases) {
                var caseValue = matchCase.getExpressionToCompare().execute(tree, table);
                if (caseValue instanceof JCError) return caseValue;

                if (comparingValue == caseValue) {
                    return matchCase.execute(tree, table);
                }
            }
        }

        if (defaultCase != null) {
            return defaultCase.execute(tree, table);
        }

        return null;
    }

    @Override
    public String generateAstDotFormat(Tree tree, String previousContent) {
        return "";
    }
}
