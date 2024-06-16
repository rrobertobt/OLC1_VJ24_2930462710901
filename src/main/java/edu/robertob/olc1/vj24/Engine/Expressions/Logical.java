package edu.robertob.olc1.vj24.Engine.Expressions;

import edu.robertob.olc1.vj24.Engine.Base.Instruction;
import edu.robertob.olc1.vj24.Engine.Structs.*;

public class Logical extends Instruction {

    private Instruction leftCondition;
    private Instruction rightCondition;
    private Instruction uniqueCondition;
    private LogicalOperands operand;

    public Logical(Instruction leftCondition, Instruction rightCondition, LogicalOperands operand, int line, int column) {
        super(Types.BOOLEAN, line, column);
        this.leftCondition = leftCondition;
        this.rightCondition = rightCondition;
        this.operand = operand;
    }

    public Logical(Instruction uniqueCondition, LogicalOperands operand, int line, int column) {
        super(Types.BOOLEAN, line, column);
        this.uniqueCondition = uniqueCondition;
        this.operand = operand;
    }

    @Override
    public Object execute(Tree tree, SymbolTable table) {
        Object unique = null, left = null, right = null;
        Types leftType = null, rightType = null;
        if (uniqueCondition != null) {
            unique = uniqueCondition.execute(tree, table);
            if (unique instanceof JCError) {
                return unique;
            }
            Types uniqueType = uniqueCondition.getType();
            if (uniqueType != Types.BOOLEAN) {
                return new JCError("Semantico", "No se puede operar " + uniqueType + " con " + Types.BOOLEAN, this.line, this.column);
            }
        } else  {
            left = leftCondition.execute(tree, table);
            if (left instanceof JCError) {
                return left;
            }
            right = rightCondition.execute(tree, table);
            if (right instanceof JCError) {
                return right;
            }
            leftType = leftCondition.getType();
            rightType = rightCondition.getType();
            if (leftType != Types.BOOLEAN || rightType != Types.BOOLEAN) {
                return new JCError("Semantico", "No se puede operar " + leftType + " con " + rightType, this.line, this.column);
            }
        }

        switch (operand) {
            case AND:
                return (boolean) left && (boolean) right;
            case OR:
                return (boolean) left || (boolean) right;
            case NOT:
                return !(boolean) unique;
            case XOR:
                return (boolean) left ^ (boolean) right;
            default:
                return new JCError("Semantico", "Operador lógico no válido", this.line, this.column);
        }
    }
}
