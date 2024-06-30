package edu.robertob.olc1.vj24.Engine.BuiltIn;

import edu.robertob.olc1.vj24.Engine.Base.Instruction;
import edu.robertob.olc1.vj24.Engine.Structs.*;

public class Find extends Instruction {

    private String id;
    private Instruction valueToFind;

    public Find(String id, Instruction valueToFind, int line, int column) {
        super(Types.BOOLEAN, line, column);
        this.id = id;
        this.valueToFind = valueToFind;
    }

    @Override
    public Object execute(Tree tree, SymbolTable table) {
        var value = valueToFind.execute(tree, table);
        var symbol = table.getSymbol(id);

        if (symbol == null) {
            return new JCError("Semantica", "No se encontro la variable " + id + "para llamada find()", line, column);
        }

        if (symbol.getValue() instanceof ArrayValue || symbol.getValue() instanceof DynamicList) {
            if (symbol.getValue() instanceof ArrayValue array) {
                for (var element : array.getValues()) {
                    if (element.equals(value)) {
                        return true;
                    }
                }
                return false;
            } else {
                var list = (DynamicList) symbol.getValue();
                for (var element : list.getElements()) {
                    if (element.equals(value)) {
                        return true;
                    }
                }
                return false;
            }
        } else {
            return new JCError("Semantica", "La variable " + id + " no es una lista", line, column);
        }
    }

    @Override
    public String generateAstDotFormat(Tree tree, String previousContent) {
        return "";
    }
}
