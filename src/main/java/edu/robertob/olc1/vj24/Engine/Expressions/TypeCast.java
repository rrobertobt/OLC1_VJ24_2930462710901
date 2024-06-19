package edu.robertob.olc1.vj24.Engine.Expressions;

import edu.robertob.olc1.vj24.Engine.Base.Instruction;
import edu.robertob.olc1.vj24.Engine.Structs.JCError;
import edu.robertob.olc1.vj24.Engine.Structs.SymbolTable;
import edu.robertob.olc1.vj24.Engine.Structs.Tree;
import edu.robertob.olc1.vj24.Engine.Structs.Types;

public class TypeCast extends Instruction {

    private Instruction expression;
    private Types typeToCast;

    public TypeCast(Instruction expression, Types typeToCast, int line, int column) {
        super(typeToCast, line, column);
        this.expression = expression;
        this.typeToCast = typeToCast;
    }

    @Override
    public Object execute(Tree tree, SymbolTable table) {
        Object value = expression.execute(tree, table);

        if (value instanceof JCError) {
            return value;
        }

        switch (typeToCast) {
            case Types.DOUBLE -> {
                System.out.println("Casting to double");
                switch (expression.getType()){
                    case Types.INTEGER -> {
                        value = (double) (int) value;
                        return value;
                    }
                    case Types.CHARACTER -> {
                        value = (double) (char) value;
                        return value;
                    }
                    default -> {
                        return new JCError("Semantica", "No se puede castear de " + expression.getType() + " hacia " + typeToCast, line, column);
                    }
                }
            }
            case Types.INTEGER -> {
                System.out.println("Casting to integer");
                switch (expression.getType()){
                    case Types.DOUBLE -> {
                        value = (int) (double) value;
                        return value;
                    }
                    case Types.CHARACTER -> {
                        value = (int) (char) value;
                        return value;
                    }
                    default -> {
                        return new JCError("Semantica", "No se puede castear de " + expression.getType() + " hacia " + typeToCast, line, column);
                    }
                }

            }
            case Types.CHARACTER -> {
                System.out.println("Casting to character");
                switch (expression.getType()) {
                    case Types.INTEGER -> {
                        value = (char) (int) value;
                        return value;
                    }
                    default -> {
                        return new JCError("Semantica", "No se puede castear de " + expression.getType() + " hacia " + typeToCast, line, column);
                    }
                }
            }
        }

        return value;
    }
}
