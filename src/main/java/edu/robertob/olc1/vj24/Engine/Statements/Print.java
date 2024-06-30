package edu.robertob.olc1.vj24.Engine.Statements;

import edu.robertob.olc1.vj24.Engine.Base.Instruction;
import edu.robertob.olc1.vj24.Engine.Structs.JCError;
import edu.robertob.olc1.vj24.Engine.Structs.SymbolTable;
import edu.robertob.olc1.vj24.Engine.Structs.Tree;
import edu.robertob.olc1.vj24.Engine.Structs.Types;

public class Print extends Instruction {
    private Instruction expression;

    public Print(Instruction expression, int line, int column) {
        super(Types.VOID, line, column);
        this.expression = expression;
    }

    @Override
    public Object execute(Tree tree, SymbolTable table) {
        var result = this.expression.execute(tree, table);
        if (result instanceof JCError) {
            return result;
        }
        if (result != null) tree.print(result.toString());
        if (result == null) tree.print("print() recibio valor indefinido");
        return null;
    }

    @Override
    public String generateAstDotFormat(Tree tree, String previousContent) {
        String nodePP = "n" + tree.getGraphNodeCounter();
        String nodeP = "n" + tree.getGraphNodeCounter();
        String nodeOpenPar = "n" + tree.getGraphNodeCounter();
        String nodeClosePar = "n" + tree.getGraphNodeCounter();
        String nodeExpression = "n" + tree.getGraphNodeCounter();
        String nodeSemiColon = "n" + tree.getGraphNodeCounter();

        String result = nodePP + "[label=\"FUNC PRINT\"]\n";
        result += previousContent + "->" + nodePP + "\n";

        result += nodeP + "[label=\"println\"]\n";
        result += nodeOpenPar + "[label=\"(\"]\n";
        result += nodeExpression + "[label=\"EXPRESION\"]\n";
        result += nodeClosePar + "[label=\")\"]\n";
        result += nodeSemiColon + "[label=\";\"]\n";

        result += nodePP + "->" + nodeP + "\n";
        result += nodePP + "->" + nodeOpenPar + "\n";
        result += nodePP + "->" + nodeExpression + "\n";
        result += nodePP + "->" + nodeClosePar + "\n";
        result += nodePP + "->" + nodeSemiColon + "\n";

        result += this.expression.generateAstDotFormat(tree, nodeExpression);

        return result;
    }

}
