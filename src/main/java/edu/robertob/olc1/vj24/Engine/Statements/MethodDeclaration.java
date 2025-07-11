package edu.robertob.olc1.vj24.Engine.Statements;

import edu.robertob.olc1.vj24.Engine.Base.Instruction;
import edu.robertob.olc1.vj24.Engine.Structs.JCError;
import edu.robertob.olc1.vj24.Engine.Structs.SymbolTable;
import edu.robertob.olc1.vj24.Engine.Structs.Tree;
import edu.robertob.olc1.vj24.Engine.Structs.Types;

import java.util.HashMap;
import java.util.LinkedList;

public class MethodDeclaration extends Instruction {

    private String id;
    LinkedList<HashMap> params;
    private LinkedList<Instruction> methodBody;
    private final String[] reservedMethodNames = {
            "round",
            "print",
            "length",
            "tostring",
            "find",
            // not really a method name, but it's a reserved word
            "new"
    };


    public MethodDeclaration(Types type, String id, LinkedList<HashMap> params, LinkedList<Instruction> methodBody, int line, int column) {
        super(type, line, column);
        this.id = id;
        this.params = params;
        this.methodBody = methodBody;
    }

    @Override
    public Object execute(Tree tree, SymbolTable table) {
        for (Instruction i : methodBody) {
//            System.out.println(i.getClass());
//            System.out.println("EXECUTING METHOD for loop");
            if (i != null) {
                var result = i.execute(tree, table);
                // checks
                if (result instanceof JCError) {
                    return result;
                }
                // if the method returns at some point before the end, further instructions are not executed
                if (result instanceof ReturnIns) {
                    return ((ReturnIns) result).getExpressionToReturn().execute(tree, table);
                }
                if (result instanceof Break) {
                    return result;
                }
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    @Override
    public String generateAstDotFormat(Tree tree, String previousContent) {
        String nativeNode = "n" + tree.getGraphNodeCounter();
        String nodeValue = "n" + tree.getGraphNodeCounter();

        String result = previousContent + "->" + nativeNode + "\n";

        result += nativeNode + "[label=\"" + "METODO" + "\"]\n";
        result += nodeValue + "[label=\"" + this.id + "\"]\n";

        result += nativeNode + "->" + nodeValue + "\n";

        for (Instruction i : methodBody) {
            result += i.generateAstDotFormat(tree, nodeValue);
        }
        return result;
    }

    public String getId() {
        return id;
    }

    public LinkedList<HashMap> getParams() {
        return params;
    }

    public LinkedList<Instruction> getMethodBody() {
        return methodBody;
    }

    public int getParamsSize() {
        return params.size();
    }

    public boolean isReservedMethod() {
        for (String reservedMethodName : reservedMethodNames) {
            if (reservedMethodName.equals(this.id)) {
                return true;
            }
        }
        return false;
    }


}
