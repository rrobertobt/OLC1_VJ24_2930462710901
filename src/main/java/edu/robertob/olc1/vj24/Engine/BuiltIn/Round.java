package edu.robertob.olc1.vj24.Engine.BuiltIn;

import edu.robertob.olc1.vj24.Engine.Base.Instruction;
import edu.robertob.olc1.vj24.Engine.Structs.JCError;
import edu.robertob.olc1.vj24.Engine.Structs.SymbolTable;
import edu.robertob.olc1.vj24.Engine.Structs.Tree;
import edu.robertob.olc1.vj24.Engine.Structs.Types;

// This class represent the built-in function round
// rules:
/*
* Esta función recibe como parámetro un valor numérico. Permite redondear los números decimales según las siguientes reglas:
● Si el decimal es mayor o igual a 0.5, se aproxima al entero superior.
● Si el decimal es menor que 0.5, se aproxima al número inferior.
* */
public class Round extends Instruction {
    private Instruction number;

    public Round(Instruction number, int line, int column){
        super(Types.INTEGER, line, column);
        this.number = number;
    }

    @Override
    public Object execute(Tree tree, SymbolTable table) {
        Object value = number.execute(tree, table);
        if (value instanceof Double) {
            double number = (double) value;
            return Math.round(number);
        } else {
            return new JCError("Semantica", "La función round solo acepta números decimales", this.line, this.column);
        }
    }
}
