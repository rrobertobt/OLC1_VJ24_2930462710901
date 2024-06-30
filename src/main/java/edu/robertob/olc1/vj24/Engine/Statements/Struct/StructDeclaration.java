package edu.robertob.olc1.vj24.Engine.Statements.Struct;

import edu.robertob.olc1.vj24.Engine.Base.Instruction;
import edu.robertob.olc1.vj24.Engine.Structs.JCError;
import edu.robertob.olc1.vj24.Engine.Structs.SymbolTable;
import edu.robertob.olc1.vj24.Engine.Structs.Tree;
import edu.robertob.olc1.vj24.Engine.Structs.Types;

import java.util.LinkedList;

public class StructDeclaration extends Instruction {
    private String id;
    private LinkedList<StructField> fields;
    private StructDefinition definition;

    public StructDeclaration(String id, LinkedList<StructField> fields, int line, int column) {
        super(Types.STRUCT, line, column);
        this.id = id;
        this.fields = fields;
        this.line = line;
        this.column = column;
    }

    @Override
    public Object execute(Tree tree, SymbolTable table) {
        var def = new StructDefinition(id, fields);
        this.definition = def;
//        var added = table.setStruct(def);
//        if (!added) {
//            return new JCError("Semantica", "Ya existe una estructura con el nombre " + id, this.line, this.column);
//        }
//        System.out.println("Struct " + id + " added");
        return null;
    }

    public StructDefinition getDefinition() {
        return definition;
    }
}
