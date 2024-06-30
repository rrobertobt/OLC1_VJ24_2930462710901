package edu.robertob.olc1.vj24.Engine.Statements;

import edu.robertob.olc1.vj24.Engine.Base.Instruction;
import edu.robertob.olc1.vj24.Engine.Structs.*;

public class DynamicListMutation extends Instruction {
    public enum MutationType {
        ADD,
        REMOVE
    }

    private String id;
    private Instruction index;
    private Instruction value;
    private MutationType mutationType;

    public DynamicListMutation(String id, Instruction index, Instruction value, MutationType mutationType, int line, int col) {
        super(Types.VOID, line, col);
        this.id = id;
        this.index = index;
        this.mutationType = mutationType;
        this.value = value;
    }

    @Override
    public Object execute(Tree tree, SymbolTable table) {
        var symbol = table.getSymbol(this.id);
        if (symbol == null) {
            return new JCError("Semantica", "Variable: " + this.id + " no existe", this.line, this.column);
        }

        if (!symbol.isDynamicList()) {
            return new JCError("Semantica", "Variable: " + this.id + " no es una lista dínamica, es: " + symbol.getType(), this.line, this.column);
        }

        var list = ((DynamicList) symbol.getValue()).getElements();
        var listType = ((DynamicList) symbol.getValue()).getType();

        switch (this.mutationType) {
            case ADD:
                var value = this.value.execute(tree, table);
                if (value instanceof JCError) return value;
                if (listType != ((Instruction) this.value).getType()) {
                    return new JCError("Semantica", "No se puede agregar un valor de tipo " + ((Instruction) this.value).getType() + " a una lista de tipo " + listType, this.line, this.column);
                }
                list.add(value);
                break;
            case REMOVE:
                var index = this.index.execute(tree, table);
                if (index instanceof JCError) return index;
                if ((index instanceof Integer)) {
                    Object toReturn;
                    try {
                        toReturn = list.get((int) index);
                    } catch (IndexOutOfBoundsException e) {
                        return new JCError("Semantica", "Indice de lista fuera de rango para lista dinámica", this.line, this.column);
                    }
                    this.type = listType;
                    Object removed;
                    removed = list.remove((int) index);
                    return removed;
                }
                return new JCError("Semantica", "Indice de lista debe ser entero", this.line, this.column);
        }
        return null;
    }
}
