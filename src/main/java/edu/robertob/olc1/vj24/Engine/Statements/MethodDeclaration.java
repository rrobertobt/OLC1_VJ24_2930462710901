package edu.robertob.olc1.vj24.Engine.Statements;

import edu.robertob.olc1.vj24.Engine.Base.Instruction;
import edu.robertob.olc1.vj24.Engine.Structs.JCError;
import edu.robertob.olc1.vj24.Engine.Structs.SymbolTable;
import edu.robertob.olc1.vj24.Engine.Structs.Tree;
import edu.robertob.olc1.vj24.Engine.Structs.Types;

import java.util.LinkedList;

public class MethodDeclaration extends Instruction {

    private String id;
    //params for later LinkedList<HashMap> parameters;
    private LinkedList<Instruction> methodBody;


    public MethodDeclaration(
            Types type,
            String id,
            //params for later LinkedList<HashMap> parameters,
            LinkedList<Instruction> methodBody,
            int line, int column) {
        super(type, line, column);
        this.id = id;
        //this.parameters = parameters;
        this.methodBody = methodBody;
    }

    @Override
    public Object execute(Tree tree, SymbolTable table) {
        for (Instruction i : methodBody) {
            if (i != null) {
                var result = i.execute(tree, table);
                // checks
                if (result instanceof JCError) {
                    return result;
                }
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }

    public String getId() {
        return id;
    }
}
