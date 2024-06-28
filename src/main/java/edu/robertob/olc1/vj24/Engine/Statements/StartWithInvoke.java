package edu.robertob.olc1.vj24.Engine.Statements;

import edu.robertob.olc1.vj24.Engine.Base.Instruction;
import edu.robertob.olc1.vj24.Engine.Structs.JCError;
import edu.robertob.olc1.vj24.Engine.Structs.SymbolTable;
import edu.robertob.olc1.vj24.Engine.Structs.Tree;
import edu.robertob.olc1.vj24.Engine.Structs.Types;

// Class that handles the 'start_with method()' instruction that indicates and starts the execution of a so-called "main" method in this custom language.

public class StartWithInvoke extends Instruction {

    private String id;

    public StartWithInvoke(String id, int line, int column) {
        super(Types.VOID, line, column);
        this.id = id;
    }

    @Override
    public Object execute(Tree tree, SymbolTable table) {
        var methodToInvoke = tree.getMethod(id);
        if (methodToInvoke == null) {
            return new JCError("Semantica", "El metodo utilizado no existe", this.line, this.column);
        }

        var globalTableCtx = new SymbolTable(tree.getGlobalTable());
        globalTableCtx.setName("INIT");
        tree.getGlobalTable().getChildren().add(globalTableCtx);

        var result = methodToInvoke.execute(tree, globalTableCtx);
        if (result instanceof JCError) return result;

        return null;
    }
}
