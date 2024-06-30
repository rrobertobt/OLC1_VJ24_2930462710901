package edu.robertob.olc1.vj24.Engine.Expressions;

import edu.robertob.olc1.vj24.Engine.Base.Instruction;
import edu.robertob.olc1.vj24.Engine.Structs.*;

import java.util.LinkedList;

public class ArrayAccess extends Instruction {
    private String id;
    private Instruction index1;
    private Instruction index2;

    public ArrayAccess(String id, Instruction index1, Instruction index2, int line, int col) {
        super(Types.VOID, line, col);
        this.id = id;
        this.index1 = index1;
        this.index2 = index2;
    }

    @Override
    public Object execute(Tree tree, SymbolTable table) {
        var symbol = table.getSymbol(this.id);
        if (symbol == null) {
            return new JCError("Semantica", "Variable: " + this.id + " no existe", this.line, this.column);
        }

//        if (!(symbol.getValue() instanceof ArrayValue)) {
//            return new JCError("Semantica", "Variable: " + this.id + " no es un vector", this.line, this.column);
//        }
//
//        // dynamic lists handling
//        if (!(symbol.isDynamicList()) && !(symbol.getValue() instanceof ArrayValue)) {
//            return new JCError("Semantica", "Variable: " + this.id + " no es una lista dinamica", this.line, this.column);
//        }

        // this means we are accessing an array and not a list
        if (symbol.getValue() instanceof ArrayValue && !symbol.isDynamicList()) {
            ArrayValue vector = (ArrayValue) symbol.getValue();
            int dimension = vector.getDimension();
            if (this.index1 == null) {
                return new JCError("Semantica", "Indice del vector no puede ser nulo", this.line, this.column);
            }

            var index1Value = this.index1.execute(tree, table);
            if (index1Value instanceof JCError) return index1Value;
            if (!(index1Value instanceof Integer)) {
                return new JCError("Semantica", "Indice del vector debe ser entero", this.line, this.column);
            }

            int index1 = (Integer) index1Value;

            if (index1 < 0 || index1 >= vector.getValues().size()) {
                return new JCError("Semantica", "Indice del vector fuera de rango", this.line, this.column);
            }


            if (this.index2 == null) {
                this.type = symbol.getType();
                return vector.getValues().get(index1);
            } else {
                if (dimension == 1) {
                    return new JCError("Semantica", "Vector unidimensional no puede tener dos indices", this.line, this.column);
                }

                var index2Value = this.index2.execute(tree, table);
                if (index2Value instanceof JCError) return index2Value;
                if (!(index2Value instanceof Integer)) {
                    return new JCError("Semantica", "Indice del vector debe ser entero", this.line, this.column);
                }

                int index2 = (Integer) index2Value;

                if (index2 < 0 || index2 >= vector.getValues().size()) {
                    return new JCError("Semantica", "Indice del vector fuera de rango", this.line, this.column);
                }

                if (vector.getValues().get(index1) instanceof LinkedList vector2) {
//                ArrayValue vector2 = (ArrayValue) vector.getValues().get(index1);
//                LinkedList<Object> vector2 = (LinkedList<Object>) vector.getValues().get(index1);
                    if (index2 < 0 || index2 >= vector2.size()) {
                        return new JCError("Semantica", "Indice del vector fuera de rango", this.line, this.column);
                    }
                    this.type = symbol.getType();
                    return vector2.get(index2);
                } else {
                    return new JCError("Semantica", "Variable no es un vector", this.line, this.column);
                }
            }
        } else if (symbol.isDynamicList()) {
            DynamicList list = (DynamicList) symbol.getValue();
            if (this.index1 == null) {
                return new JCError("Semantica", "Indice de la lista no puede ser nulo", this.line, this.column);
            }

            var index1Value = this.index1.execute(tree, table);
            if (index1Value instanceof JCError) return index1Value;
            if (!(index1Value instanceof Integer)) {
                return new JCError("Semantica", "Indice de la lista debe ser entero", this.line, this.column);
            }

            int index1 = (Integer) index1Value;

            if (index1 < 0 || index1 >= list.getElements().size()) {
                return new JCError("Semantica", "Indice de la lista fuera de rango", this.line, this.column);
            }

            this.type = symbol.getType();
            return list.getElements().get(index1);

        } else {
            return new JCError("Semantica", "Variable no es un array o lista, usar el operador [] no es valido", this.line, this.column);
        }
//        return null;
    }

}
