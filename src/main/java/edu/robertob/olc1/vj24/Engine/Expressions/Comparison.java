package edu.robertob.olc1.vj24.Engine.Expressions;

import edu.robertob.olc1.vj24.Engine.Base.Instruction;
import edu.robertob.olc1.vj24.Engine.Structs.*;

public class Comparison extends Instruction {
    public Comparison(Types type, int line, int column) {
        super(type, line, column);
    }

    private Instruction leftCondition;
    private Instruction rightCondition;
    private ComparisonOperands operand;

    public Comparison(Instruction leftCondition, Instruction rightCondition, ComparisonOperands operand, int line, int column) {
        super(Types.BOOLEAN, line, column);
        this.leftCondition = leftCondition;
        this.rightCondition = rightCondition;
        this.operand = operand;
    }

    @Override
    public Object execute(Tree tree, SymbolTable table) {
        Object left = leftCondition.execute(tree, table);
        if (left instanceof JCError) {
            return left;
        }

        Object right = rightCondition.execute(tree, table);
        if (right instanceof JCError) {
            return right;
        }

        Types leftType = leftCondition.getType();
        Types rightType = rightCondition.getType();

        switch (leftType) {
            case Types.INTEGER:
                switch (rightType) {
                    case Types.INTEGER:
//                        return (int) left == (int) right;
                        switch (operand) {
                            case EQUALS:
                                return (int) left == (int) right;
                            case NOT_EQUALS:
                                return (int) left != (int) right;
                            case GREATER_THAN:
                                return (int) left > (int) right;
                            case LESS_THAN:
                                return (int) left < (int) right;
                            case GREATER_THAN_OR_EQUALS:
                                return (int) left >= (int) right;
                            case LESS_THAN_OR_EQUALS:
                                return (int) left <= (int) right;
                            default:
                                return new JCError("Semántico", "Operador relacional inválido", line, column);
                        }
                    case Types.DOUBLE:
//                        return (int) left == (double) right;
                        switch (operand) {
                            case EQUALS:
                                return (int) left == (double) right;
                            case NOT_EQUALS:
                                return (int) left != (double) right;
                            case GREATER_THAN:
                                return (int) left > (double) right;
                            case LESS_THAN:
                                return (int) left < (double) right;
                            case GREATER_THAN_OR_EQUALS:
                                return (int) left >= (double) right;
                            case LESS_THAN_OR_EQUALS:
                                return (int) left <= (double) right;
                            default:
                                return new JCError("Semántico", "Operador relacional inválido", line, column);
                        }
                    case Types.CHARACTER:
                        //return (int) left == (int)(char) right);
                        switch (operand) {
                            case EQUALS:
                                return (int) left == (int)(char)right;
                            case NOT_EQUALS:
                                return (int) left != (int)(char)right;
                            case GREATER_THAN:
                                return (int) left > (int)(char)right;
                            case LESS_THAN:
                                return (int) left < (int)(char)right;
                            case GREATER_THAN_OR_EQUALS:
                                return (int) left >= (int)(char)right;
                            case LESS_THAN_OR_EQUALS:
                                return (int) left <= (int)(char)right;
                            default:
                                return new JCError("Semántico", "Operador relacional inválido", line, column);
                        }
                    default:
                        return new JCError("Semántico", "No es posible comparar los tipos: " + leftType + " con " + rightType, line, column);
                }
            case Types.DOUBLE:
                switch (rightType) {
                    case Types.INTEGER:
//                        return (double) left == (int) right;
                        switch (operand) {
                            case EQUALS:
                                return (double) left == (int) right;
                            case NOT_EQUALS:
                                return (double) left != (int) right;
                            case GREATER_THAN:
                                return (double) left > (int) right;
                            case LESS_THAN:
                                return (double) left < (int) right;
                            case GREATER_THAN_OR_EQUALS:
                                return (double) left >= (int) right;
                            case LESS_THAN_OR_EQUALS:
                                return (double) left <= (int) right;
                            default:
                                return new JCError("Semántico", "Operador relacional inválido", line, column);
                        }
                    case Types.DOUBLE:
//                        return (double) left == (double) right;
                        switch (operand) {
                            case EQUALS:
                                return (double) left == (double) right;
                            case NOT_EQUALS:
                                return (double) left != (double) right;
                            case GREATER_THAN:
                                return (double) left > (double) right;
                            case LESS_THAN:
                                return (double) left < (double) right;
                            case GREATER_THAN_OR_EQUALS:
                                return (double) left >= (double) right;
                            case LESS_THAN_OR_EQUALS:
                                return (double) left <= (double) right;
                            default:
                                return new JCError("Semántico", "Operador relacional inválido", line, column);
                        }
                    case Types.CHARACTER:
//                        return (double) left == Character.getNumericValue((char) right);
                        switch (operand) {
                            case EQUALS:
                                return (double) left == (int)(char)right;
                            case NOT_EQUALS:
                                return (double) left != (int)(char)right;
                            case GREATER_THAN:
                                return (double) left > (int)(char)right;
                            case LESS_THAN:
                                return (double) left < (int)(char)right;
                            case GREATER_THAN_OR_EQUALS:
                                return (double) left >= (int)(char)right;
                            case LESS_THAN_OR_EQUALS:
                                return (double) left <= (int)(char)right;
                            default:
                                return new JCError("Semántico", "Operador relacional inválido", line, column);
                        }
                    default:
                        return new JCError("Semántico", "No es posible comparar los tipos: " + leftType + " con " + rightType, line, column);
                }
            case Types.BOOLEAN:
//                if (rightType == Types.BOOLEAN) {
//                    return left == right;
//                } else {
//                    return new JCError("Semántico", "No es posible comparar los tipos: " + leftType + " con " + rightType, line, column);
//                }
                // rewrite this, since we can accept for example, true>false, we need to convert true to 1 and false to 0
                switch (operand) {
                    case EQUALS:
                        return left.equals(right);
                    case NOT_EQUALS:
                        return !left.equals(right);
                    case GREATER_THAN: {
                        var leftValue = (boolean) left ? 1 : 0;
                        var rightValue = (boolean) right ? 1 : 0;
                        return leftValue > rightValue;
                    }
                    case LESS_THAN: {
                        var leftValue = (boolean) left ? 1 : 0;
                        var rightValue = (boolean) right ? 1 : 0;
                        return leftValue < rightValue;
                    }
                    case GREATER_THAN_OR_EQUALS: {
                        var leftValue = (boolean) left ? 1 : 0;
                        var rightValue = (boolean) right ? 1 : 0;
                        return leftValue >= rightValue;
                    }
                    case LESS_THAN_OR_EQUALS: {
                        var leftValue = (boolean) left ? 1 : 0;
                        var rightValue = (boolean) right ? 1 : 0;
                        return leftValue <= rightValue;
                    }
                    default:
                        return new JCError("Semántico", "Operador relacional inválido", line, column);
                                        }
            case Types.CHARACTER:
                switch (rightType) {
                    case Types.INTEGER:
//                        return (char) left == (int) right;
                        switch (operand) {
                            case EQUALS:
                                return (int)(char)left == (int) right;
                            case NOT_EQUALS:
                                return (int)(char)left != (int) right;
                            case GREATER_THAN:
                                return (int)(char)left > (int) right;
                            case LESS_THAN:
                                return (int)(char)left < (int) right;
                            case GREATER_THAN_OR_EQUALS:
                                return (int)(char)left >= (int) right;
                            case LESS_THAN_OR_EQUALS:
                                return (int)(char)left <= (int) right;
                            default:
                                return new JCError("Semántico", "Operador relacional inválido", line, column);
                        }
                    case Types.DOUBLE:
//                        return (char) left == (double) right;
                        switch (operand) {
                            case EQUALS:
                                return (int)(char)left == (double) right;
                            case NOT_EQUALS:
                                return (int)(char)left != (double) right;
                            case GREATER_THAN:
                                return (int)(char)left > (double) right;
                            case LESS_THAN:
                                return (int)(char)left < (double) right;
                            case GREATER_THAN_OR_EQUALS:
                                return (int)(char)left >= (double) right;
                            case LESS_THAN_OR_EQUALS:
                                return (int)(char)left <= (double) right;
                            default:
                                return new JCError("Semántico", "Operador relacional inválido", line, column);
                        }
                    case Types.CHARACTER:
//                        return (char) left == (char) right;
                        switch (operand) {
                            case EQUALS:
                                return (int)(char)left == (int)(char)right;
                            case NOT_EQUALS:
                                return (int)(char)left != (int)(char)right;
                            case GREATER_THAN:
                                return (int)(char)left > (int)(char)right;
                            case LESS_THAN:
                                return (int)(char)left < (int)(char)right;
                            case GREATER_THAN_OR_EQUALS:
                                return (int)(char)left >= (int)(char)right;
                            case LESS_THAN_OR_EQUALS:
                                return (int)(char)left <= (int)(char)right;
                            default:
                                return new JCError("Semántico", "Operador relacional inválido", line, column);
                        }
                    default:
                        return new JCError("Semántico", "No es posible comparar los tipos: " + leftType + " con " + rightType, line, column);
                }
            case STRING:
                switch (operand) {
                    case EQUALS:
                        return left.equals(right);
                    case NOT_EQUALS:
                        return !left.equals(right);
                    default:
                        return new JCError("Semántico", "Operador relacional inválido", line, column);
                }
            default:
                return new JCError("Semántico", "No es posible comparar los tipos: " + leftType + " con " + rightType, line, column);
        }
    }

    @Override
    public String generateAstDotFormat(Tree tree, String previousContent) {

        String nodeLeft = "n" + tree.getGraphNodeCounter();
        String nodeOperation = "n" + tree.getGraphNodeCounter();
        String nodeRight = "n" + tree.getGraphNodeCounter();

        String result = previousContent + "->" + nodeLeft + "\n";
        result += previousContent + "->" + nodeOperation + "\n";
        result += previousContent + "->" + nodeRight + "\n";

        result += nodeLeft + "[label=\"EXP\"];\n";
        result += nodeOperation + "[label=\"" + this.operand + "\"];\n";
        result += nodeRight + "[label=\"EXP\"];\n";
        result += this.leftCondition.generateAstDotFormat(tree, nodeLeft);
        result += this.rightCondition.generateAstDotFormat(tree, nodeRight);

        return result;
    }

    private Object equals(Object left, Object right) {
        Types leftType = leftCondition.getType();
        Types rightType = rightCondition.getType();

        /*
         * possible combinations:
         * int with int
         * int with double
         * int with char
         * double with int
         * double with double
         * double with char
         * boolean with boolean
         * char with int
         * char with double
         * char with char
         * string with string
         * */

        switch (leftType) {
            case Types.INTEGER:
                switch (rightType) {
                    case Types.INTEGER:
                        return (int) left == (int) right;
                    case Types.DOUBLE:
                        return (int) left == (double) right;
                    case Types.CHARACTER:
                        return (int) left == (char) right;
                    default:
                        return new JCError("Semántico", "No es posible comparar los tipos: " + leftType + " con " + rightType, line, column);
                }
            case Types.DOUBLE:
                switch (rightType) {
                    case Types.INTEGER:
                        return (double) left == (int) right;
                    case Types.DOUBLE:
                        return (double) left == (double) right;
                    case Types.CHARACTER:
                        return (double) left == (char) right;
                    default:
                        return new JCError("Semántico", "No es posible comparar los tipos: " + leftType + " con " + rightType, line, column);
                }
            case Types.BOOLEAN:
                if (rightType == Types.BOOLEAN) {
                    return left == right;
                } else {
                    return new JCError("Semántico", "No es posible comparar los tipos: " + leftType + " con " + rightType, line, column);
                }
            case Types.CHARACTER:
                switch (rightType) {
                    case Types.INTEGER:
                        return (char) left == (int) right;
                    case Types.DOUBLE:
                        return (char) left == (double) right;
                    case Types.CHARACTER:
                        return (char) left == (char) right;
                    default:
                        return new JCError("Semántico", "No es posible comparar los tipos: " + leftType + " con " + rightType, line, column);
                }
            case STRING:
                if (rightType == Types.STRING) {
                    return left.equals(right);
                } else {
                    return new JCError("Semántico", "No es posible comparar los tipos: " + leftType + " con " + rightType, line, column);
                }
            default:
                return new JCError("Semántico", "No es posible comparar los tipos: " + leftType + " con " + rightType, line, column);
        }
    }
}
