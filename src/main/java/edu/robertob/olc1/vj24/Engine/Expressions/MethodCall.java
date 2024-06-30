package edu.robertob.olc1.vj24.Engine.Expressions;

import edu.robertob.olc1.vj24.Engine.Base.Instruction;
import edu.robertob.olc1.vj24.Engine.Statements.Declaration;
import edu.robertob.olc1.vj24.Engine.Statements.MethodDeclaration;
import edu.robertob.olc1.vj24.Engine.Structs.JCError;
import edu.robertob.olc1.vj24.Engine.Structs.SymbolTable;
import edu.robertob.olc1.vj24.Engine.Structs.Tree;
import edu.robertob.olc1.vj24.Engine.Structs.Types;

import java.util.LinkedList;

public class MethodCall extends Instruction {

    private String name;
    private LinkedList<Instruction> parameters;

    public MethodCall(String name, LinkedList<Instruction> parameters, int line, int column) {
        super(Types.VOID, line, column);
        this.name = name;
        this.parameters = parameters;
    }

    @Override
    public Object execute(Tree tree, SymbolTable table) {
        var methodToCall = tree.getMethod(this.name);
        if (methodToCall == null) {
            return new JCError("Semantica", "El metodo/función " + this.name + " no existe", this.line, this.column);
        }

        if (methodToCall instanceof MethodDeclaration method) {
            var callTable = new SymbolTable(tree.getGlobalTable());
            callTable.setName("EXEC_METODO@" + this.name + "@" + this.line + "@" + this.column);

            // check parameters
            if (method.getParamsSize() != this.parameters.size()) {
                return new JCError("Semantica", "Se esperaban " + method.getParamsSize() + " parametros, pero se recibieron " + this.parameters.size(), this.line, this.column);
            }

            for (int i = 0; i < this.parameters.size(); i++) {
                var mParamId = (String) method.getParams().get(i).get("id");
                var mParamType = (Types) method.getParams().get(i).get("type");
                var passedValue = this.parameters.get(i);

                // declare parameter as if it was a variable, it is like a temporary variable but with the same scope as the method and without value set
                var paramDeclaration = new Declaration(mParamId, mParamType, null, false, this.line, this.column);
                var paramResult = paramDeclaration.execute(tree, callTable);

                // execute parameter to get its value with the current scope, not the method scope
                // since we can't set the value of the parameter in the declaration, because it could be a method call and we need to execute it first
                if (paramResult instanceof JCError) return paramResult;
                var paramValueResult = passedValue.execute(tree, table);

                if (paramValueResult instanceof JCError) return paramValueResult;
                // error checks
                var createdVar = callTable.getSymbol(mParamId);

                if ((createdVar != null) && createdVar.getType() != passedValue.getType())
                    return new JCError("Semantica", "Tipo de argumento: " + passedValue.getType() + " no coincide con el tipo de parametro esperado: " + createdVar.getType(), this.line, this.column);

                createdVar.setValue(paramValueResult);
            }
            // execute method
            var mResult = method.execute(tree, callTable);
            if (mResult instanceof JCError) return mResult;
            if (method.getType() != Types.VOID) {
                this.type = method.getType();
                return mResult;
            }
        }

        return null;
    }

    @Override
    public String generateAstDotFormat(Tree tree, String previousContent) {
        String nativeNode = "n" + tree.getGraphNodeCounter();
        String nodeValue = "n" + tree.getGraphNodeCounter();

        String result = previousContent + "->" + nativeNode + "\n";

        result += nativeNode + "[label=\"" + "LLAMADA METODO" + "\"]\n";
        result += nodeValue + "[label=\"" + this.name + "\"]\n";

        result += nativeNode + "->" + nodeValue + "\n";
        return result;

//        return "";
    }
}
