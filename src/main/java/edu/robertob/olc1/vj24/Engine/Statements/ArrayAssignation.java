package edu.robertob.olc1.vj24.Engine.Statements;

import edu.robertob.olc1.vj24.Engine.Base.Instruction;
import edu.robertob.olc1.vj24.Engine.Structs.*;

import java.util.LinkedList;

public class ArrayAssignation extends Instruction {
    private String id;
    private Instruction index1;
    private Instruction index2;
    private Instruction newValue;

    public ArrayAssignation(String id, Instruction index1, Instruction index2, Instruction newValue, int line, int column) {
        super(Types.VOID, line, column);
        this.id = id;
        this.index1 = index1;
        this.index2 = index2;
        this.newValue = newValue;
    }

    @Override
    public Object execute(Tree tree, SymbolTable table) {
        var symbol = table.getSymbol(id);
        if (symbol == null) {
            return new JCError("Semantica", "Variable " + id + " no existe", this.line, this.column);
        }

        if (!(symbol.getValue() instanceof ArrayValue)) {
            return new JCError("Semantica", "Variable: " + this.id + " no es un vector", this.line, this.column);
        }

//        LinkedList vector = (LinkedList) symbol.getValue();
        ArrayValue vector = (ArrayValue) symbol.getValue();
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

        var newValueResult = this.newValue.execute(tree, table);
        if (newValueResult instanceof JCError) return newValueResult;

        if (this.index2 == null) {
            if (symbol.getType() != this.newValue.getType())
                return new JCError("Semantica", "No se puede asignar el valor de tipo " + this.newValue.getType() + " a un vector de tipo " + symbol.getType(), this.line, this.column);

            if(symbol.isConstant())
                return new JCError("Semantica", "Variable constante " + id + " no puede ser re-asignada", this.line, this.column);

            vector.getValues().set(index1, newValueResult);
        } else {
            if (vector.getDimension() == 1) {
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

            if (symbol.getType() != this.newValue.getType())
                return new JCError("Semantica", "No se puede asignar el valor de tipo " + this.newValue.getType() + " a un vector de tipo " + symbol.getType(), this.line, this.column);

            if(symbol.isConstant())
                return new JCError("Semantica", "Variable constante " + id + " no puede ser re-asignada", this.line, this.column);

            LinkedList<Object> innerVector = (LinkedList<Object>) vector.getValues().get(index1);
            innerVector.set(index2, newValueResult);
        }

        return null;
    }
}
