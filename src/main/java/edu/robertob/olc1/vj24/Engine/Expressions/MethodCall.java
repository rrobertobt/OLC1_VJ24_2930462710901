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
            return new JCError("Semantica", "El metodo/función" + this.name + " no existe", this.line, this.column);
        }

        if (methodToCall instanceof MethodDeclaration method) {
            var callTable = new SymbolTable(tree.getGlobalTable());
            callTable.setName("EXEC_METODO@" + this.name + "@" + this.line + "@" + this.column);

            // check parameters
            if (method.getParamsSize() != this.parameters.size()) {
                return new JCError("Semantica", "Se esperaban " + method.getParamsSize() + " parametros, pero se recibieron " + this.parameters.size(), this.line, this.column);
            }

            for(int i = 0; i < this.parameters.size(); i++) {
                var mParamId = (String) method.getParams().get(i).get("id");
                var mParamType = (Types) method.getParams().get(i).get("type");
                var passedValue = this.parameters.get(i);

                // declare parameter as if it was a variable, it is like a temporary variable
                var paramDeclaration = new Declaration(mParamId, mParamType, passedValue, false, this.line, this.column);
                var paramResult = paramDeclaration.execute(tree, callTable);

                // error checks
                if (paramResult instanceof JCError) return paramResult;
            }

            // execute method
            var mResult = method.execute(tree, callTable);
            if (mResult instanceof JCError) return mResult;
            // return result if method is not of void type
            if (method.getType() != Types.VOID) {
                this.type = method.getType();
                return mResult;
            }
        }

        return null;
    }
}
