package edu.robertob.olc1.vj24.Engine.BuiltIn;

import edu.robertob.olc1.vj24.Engine.Base.Instruction;
import edu.robertob.olc1.vj24.Engine.Structs.*;

import java.util.LinkedList;

public class Length extends Instruction {
    private String id;
    private Instruction value;

    public Length(String id, int line, int col) {
        super(Types.INTEGER, line, col);
        this.id = id;
    }

    public Length(Instruction value, int line, int col) {
        super(Types.INTEGER, line, col);
        this.value = value;
    }

    @Override
    public Object execute(Tree tree, SymbolTable table) {
        if (this.id == null) {
            var value = this.value.execute(tree, table);
            return switch (value) {
                case String s -> s.length();
                case ArrayValue arrayValue -> arrayValue.getValues().size();
                case DynamicList dynamicList -> dynamicList.getElements().size();
                // if it is a linked list, return the size of the linked list since we are accessing the length of the nested list (ex: list[0].length()) in a 2d list
                case LinkedList linkedList -> ((LinkedList) value).size();
                default ->
                        new JCError("Semantica", "No se puede obtener la longitud de un valor no iterable", this.line, this.column);
            };
        }
        var symbol = table.getSymbol(this.id);
        if (symbol == null) {
            return new JCError("Semantica", "Variable: " + this.id + " no existe", this.line, this.column);
        }
        if (symbol.getValue() instanceof String) {
            return ((String) symbol.getValue()).length();
        }
        if (symbol.getValue() instanceof ArrayValue) {
            return ((ArrayValue) symbol.getValue()).getValues().size();
        }
        if (symbol.getValue() instanceof DynamicList) {
            return ((DynamicList) symbol.getValue()).getElements().size();
        }
        return new JCError("Semantica", "No se puede obtener la longitud de un valor no iterable", this.line, this.column);
    }
}
