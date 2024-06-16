package edu.robertob.olc1.vj24.Engine.Expressions;

import edu.robertob.olc1.vj24.Engine.Base.Instruction;
import edu.robertob.olc1.vj24.Engine.Structs.*;

public class Arithmetic extends Instruction {

    private Instruction firstOperand;
    private Instruction secondOperand;
    private ArithmeticOperands operation;
    private Instruction uniqueOperand;

    // Constructor to handle negation (ex: -5)
    public Arithmetic(Instruction uniqueOperand, ArithmeticOperands operation, int line, int column) {
        super(Types.INTEGER, line, column);
        this.operation = operation;
        this.uniqueOperand = uniqueOperand;
    }

    // Constructor to handle any other operation
    public Arithmetic(Instruction firstOperand, Instruction secondOperand, ArithmeticOperands operation, int line, int column) {
        super(Types.INTEGER, line, column);
        this.firstOperand = firstOperand;
        this.secondOperand = secondOperand;
        this.operation = operation;
    }

    @Override
    public Object execute(Tree tree, SymbolTable table) {
        // Variables to store the operands and check for errors
        Object leftOperand = null, rightOperand = null, unique = null;
        if (this.uniqueOperand != null) {
            unique = this.uniqueOperand.execute(tree, table);
            if (unique instanceof JCError) {
                return unique;
            }
        } else {
            leftOperand = this.firstOperand.execute(tree, table);
            if (leftOperand instanceof JCError) {
                return leftOperand;
            }
            rightOperand = this.secondOperand.execute(tree, table);
            if (rightOperand instanceof JCError) {
                return rightOperand;
            }
        }

        // Switch to handle the operation
        switch (this.operation) {
            case SUM -> {
                return this.add(leftOperand, rightOperand);
            }
            case SUB -> {
                return this.sub(leftOperand, rightOperand);
            }
            case MUL -> {
                return this.multiply(leftOperand, rightOperand);
            }
            case DIV -> {
                return this.divide(leftOperand, rightOperand);
            }
            case NEG -> {
                return this.negate(unique);
            }
            case MOD -> {
                return this.modulate(leftOperand, rightOperand);
            }
            case POW -> {
                return this.power(leftOperand, rightOperand);
            }
            default -> {
                return new JCError("Semantico", "Operacion no soportada: " + this.operation, this.line, this.column);
            }
        }
    }

    public Object add(Object leftOperand, Object rightOperand) {
        var leftType = this.firstOperand.getType();
        var rightType = this.secondOperand.getType();
        switch (leftType) {
            case Types.INTEGER -> {
                switch (rightType) {
                    case Types.INTEGER -> {
                        this.type = Types.INTEGER;
                        return (int) leftOperand + (int) rightOperand;
                    }
                    case Types.DOUBLE -> {
                        this.type = Types.DOUBLE;
                        return (int) leftOperand + (double) rightOperand;
                    }
                    case Types.STRING -> {
                        this.type = Types.STRING;
                        return leftOperand.toString() + rightOperand.toString();
                    }
                    case Types.CHARACTER -> {
                        // this should result in an integer, so lets convert the character to its ASCII value
                        this.type = Types.INTEGER;
                        return (int) leftOperand + (int) (char) rightOperand;
                    }
                    default -> {
                        return new JCError("Semantico", "No se puede sumar " + leftType + " con " + rightType, this.line, this.column);
                    }
                }
            }
            case Types.DOUBLE -> {
                switch (rightType) {
                    case Types.INTEGER -> {
                        this.type = Types.DOUBLE;
                        return Double.parseDouble(leftOperand.toString()) + Integer.parseInt(rightOperand.toString());
                    }
                    case Types.DOUBLE -> {
                        this.type = Types.DOUBLE;
                        return (double) leftOperand + (double) rightOperand;
                    }
                    case Types.STRING -> {
                        this.type = Types.STRING;
                        return leftOperand.toString() + rightOperand.toString();
                    }
                    case Types.CHARACTER -> {
                        // this should result in a double, so lets convert the character to its ASCII value
                        this.type = Types.DOUBLE;
                        return (double) leftOperand + (int)(char) rightOperand;
                    }
                    default -> {
                        return new JCError("Semantico", "No se puede sumar " + leftType + " con " + rightType, this.line, this.column);
                    }
                }
            }
            case Types.BOOLEAN -> {
                switch (rightType) {
                    case Types.STRING -> {
                        this.type = Types.STRING;
                        return leftOperand.toString() + rightOperand.toString();
                    }
                    default -> {
                        return new JCError("Semantico", "No se puede sumar " + leftType + " con " + rightType, this.line, this.column);
                    }
                }
            }
            case Types.CHARACTER -> {
                switch (rightType) {
                    case Types.INTEGER -> {
                        // this should result in an integer, so lets convert the character to its ASCII value
                        this.type = Types.INTEGER;
                        return (int) (char) leftOperand + (int) rightOperand;
                    }
                    case Types.DOUBLE -> {
                        // this should result in a double, so lets convert the character to its ASCII value
                        this.type = Types.DOUBLE;
                        return (int) (char) leftOperand + (double) rightOperand;
                    }
                    case Types.STRING -> {
                        this.type = Types.STRING;
                        return (char) leftOperand + rightOperand.toString();
                    }
                    case Types.CHARACTER -> {
                        this.type = Types.STRING;
                        return String.valueOf(leftOperand) + rightOperand;
                    }
                    default -> {
                        return new JCError("Semantico", "No se puede sumar " + leftType + " con " + rightType, this.line, this.column);
                    }
                }
            }
            case Types.STRING -> {
                switch (rightType) {
                    case Types.STRING -> {
                        this.type = Types.STRING;
                        return leftOperand.toString() + rightOperand.toString();
                    }
                    case Types.CHARACTER -> {
                        this.type = Types.STRING;
                        return leftOperand.toString() + (char) rightOperand;
                    }
                    default -> {
                        return new JCError("Semantico", "No se puede sumar " + leftType + " con " + rightType, this.line, this.column);
                    }
                }
            }
            default -> {
                return new JCError("Semantico", "No se puede sumar " + leftType + " con " + rightType, this.line, this.column);
            }
        }
    }

    public Object sub(Object leftOperand, Object rightOperand) {
        var leftType = this.firstOperand.getType();
        var rightType = this.secondOperand.getType();

        switch (leftType) {
            case Types.INTEGER -> {
                switch (rightType) {
                    case Types.INTEGER -> {
                        this.type = Types.INTEGER;
                        return (int) leftOperand - (int) rightOperand;
                    }
                    case Types.DOUBLE -> {
                        this.type = Types.DOUBLE;
                        return (int) leftOperand - (double) rightOperand;
                    }
                    case Types.CHARACTER -> {
                        this.type = Types.INTEGER;
                        return (int) leftOperand - (int) (char) rightOperand;
                    }
                    default -> {
                        return new JCError("Semantico", "No se puede restar " + leftType + " con " + rightType, this.line, this.column);
                    }
                }
            }
            case Types.DOUBLE -> {
                switch (rightType) {
                    case Types.INTEGER, CHARACTER -> {
                        this.type = Types.DOUBLE;
                        return (double) leftOperand - (int) rightOperand;
                    }
                    case Types.DOUBLE -> {
                        this.type = Types.DOUBLE;
                        return (double) leftOperand - (double) rightOperand;
                    }
                    default -> {
                        return new JCError("Semantico", "No se puede restar " + leftType + " con " + rightType, this.line, this.column);
                    }
                }
            }
            case CHARACTER -> {
                switch (rightType) {
                    case Types.INTEGER -> {
                        this.type = Types.INTEGER;
                        return (int) leftOperand - (int) rightOperand;
                    }
                    case Types.DOUBLE -> {
                        this.type = Types.DOUBLE;
                        return (int) leftOperand - (double) rightOperand;
                    }
                    default -> {
                        return new JCError("Semantico", "No se puede restar " + leftType + " con " + rightType, this.line, this.column);
                    }
                }
            }
            default -> {
                return new JCError("Semantico", "No se puede restar " + leftType + " con " + rightType, this.line, this.column);
            }
        }
    }

    public Object multiply(Object leftOperand, Object rightOperand) {
        var leftType = this.firstOperand.getType();
        var rightType = this.secondOperand.getType();

        switch (leftType) {
            case Types.INTEGER -> {
                switch (rightType) {
                    case Types.INTEGER -> {
                        this.type = Types.INTEGER;
                        return (int) leftOperand * (int) rightOperand;
                    }
                    case Types.DOUBLE -> {
                        this.type = Types.DOUBLE;
                        return (int) leftOperand * (double) rightOperand;
                    }
                    case Types.CHARACTER -> {
                        this.type = Types.INTEGER;
                        return (int) leftOperand * (int) (char) rightOperand;
                    }
                    default -> {
                        return new JCError("Semantico", "No se puede multiplicar " + leftType + " con " + rightType, this.line, this.column);
                    }
                }
            }
            case Types.DOUBLE -> {
                switch (rightType) {
                    case Types.INTEGER -> {
                        this.type = Types.DOUBLE;
                        return Double.parseDouble(leftOperand.toString()) * Integer.parseInt(rightOperand.toString());
                    }
                    case Types.DOUBLE -> {
                        this.type = Types.DOUBLE;
                        return (double) leftOperand * (double) rightOperand;
                    }
                    case CHARACTER -> {
                        this.type = Types.DOUBLE;
                        return Double.parseDouble(leftOperand.toString()) * (int)(char) rightOperand;
                    }
                    default -> {
                        return new JCError("Semantico", "No se puede multiplicar " + leftType + " con " + rightType, this.line, this.column);
                    }
                }
            }
            case CHARACTER -> {
                switch (rightType) {
                    case Types.INTEGER -> {
                        this.type = Types.INTEGER;
//                        return Character.getNumericValue((char) leftOperand) * (int) rightOperand;
                        return (int) (char) leftOperand * (int) rightOperand;
                    }
                    case Types.DOUBLE -> {
                        this.type = Types.DOUBLE;
//                        return Character.getNumericValue((char) leftOperand) * (double) rightOperand;
                        return (int) (char) leftOperand * (double) rightOperand;
                    }
                    default -> {
                        return new JCError("Semantico", "No se puede multiplicar " + leftType + " con " + rightType, this.line, this.column);
                    }
                }
            }
            default -> {
                return new JCError("Semantico", "No se puede multiplicar " + leftType + " con " + rightType, this.line, this.column);
            }
        }
    }

    public Object divide(Object leftOperand, Object rightOperand) {
        var leftType = this.firstOperand.getType();
        var rightType = this.secondOperand.getType();
        // in the case of division, it will always return a double
        switch (leftType) {
            case Types.INTEGER -> {
                switch (rightType) {
                    case Types.INTEGER -> {
                        this.type = Types.DOUBLE;
                        // also check for division by zero
                        if ((int) rightOperand == 0) {
                            return new JCError("Semantico", "No se puede dividir entre 0", this.line, this.column);
                        }
                        return (double) (int) leftOperand / (int) rightOperand;
                    }
                    case Types.DOUBLE -> {
                        this.type = Types.DOUBLE;
                        System.out.println(rightOperand);
                        if (Double.parseDouble(rightOperand.toString()) == 0) {
                            return new JCError("Semantico", "No se puede dividir entre 0", this.line, this.column);
                        }
                        return (double) ((int) leftOperand / (double) rightOperand);
                    }
                    case Types.CHARACTER -> {
                        this.type = Types.DOUBLE;
                        if ((int) (char) rightOperand == 0) {
                            return new JCError("Semantico", "No se puede dividir entre 0", this.line, this.column);
                        }
                        return (double) (int) leftOperand / (int) (char) rightOperand;
                    }
                    default -> {
                        return new JCError("Semantico", "No se puede dividir " + leftType + " con " + rightType, this.line, this.column);
                    }
                }
            }
            case Types.DOUBLE -> {
                switch (rightType) {
                    case Types.INTEGER ->{
                        this.type = Types.DOUBLE;
                        if ((int) rightOperand == 0) {
                            return new JCError("Semantico", "No se puede dividir entre 0", this.line, this.column);
                        }
                        return (double) leftOperand / (int) rightOperand;
                    }
                    case Types.DOUBLE -> {
                        this.type = Types.DOUBLE;
                        if ((double) rightOperand == 0) {
                            return new JCError("Semantico", "No se puede dividir entre 0", this.line, this.column);
                        }
                        return (double) leftOperand / (double) rightOperand;
                    }
                    case Types.CHARACTER -> {
                        this.type = Types.DOUBLE;
                        if ((int) (char) rightOperand == 0) {
                            return new JCError("Semantico", "No se puede dividir entre 0", this.line, this.column);
                        }
                        return (double) leftOperand / (int) (char) rightOperand;
                    }
                    default -> {
                        return new JCError("Semantico", "No se puede dividir " + leftType + " con " + rightType, this.line, this.column);
                    }
                }
            }
            case CHARACTER -> {
                switch (rightType) {
                    case Types.INTEGER -> {
                        this.type = Types.DOUBLE;
                        if ((int) rightOperand == 0) {
                            return new JCError("Semantico", "No se puede dividir entre 0", this.line, this.column);
                        }
                        return (double) (int) leftOperand / (int) rightOperand;
                    }
                    case Types.DOUBLE -> {
                        this.type = Types.DOUBLE;
                        if ((double) rightOperand == 0) {
                            return new JCError("Semantico", "No se puede dividir entre 0", this.line, this.column);
                        }
                        return (double) (int) leftOperand / (double) rightOperand;
                    }
                    default -> {
                        return new JCError("Semantico", "No se puede dividir " + leftType + " con " + rightType, this.line, this.column);
                    }
                }
            }
            default -> {
                return new JCError("Semantico", "No se puede dividir " + leftType + " con " + rightType, this.line, this.column);
            }
        }
    }

    public Object modulate(Object leftOperand, Object rightOperand) {
        var leftType = this.firstOperand.getType();
        var rightType = this.secondOperand.getType();

        switch (leftType) {
            case Types.INTEGER -> {
                switch (rightType) {
                    case Types.INTEGER, Types.DOUBLE -> {
                        this.type = Types.DOUBLE;
                        return (int) leftOperand % (int) rightOperand;
                    }
                    default -> {
                        return new JCError("Semantico", "No se puede modular " + leftType + " con " + rightType, this.line, this.column);
                    }
                }
            }
            case Types.DOUBLE -> {
                switch (rightType) {
                    case Types.INTEGER, Types.DOUBLE -> {
                        this.type = Types.DOUBLE;
                        return (double) leftOperand % (int) rightOperand;
                    }
                    default -> {
                        return new JCError("Semantico", "No se puede modular " + leftType + " con " + rightType, this.line, this.column);
                    }
                }
            }
            default -> {
                return new JCError("Semantico", "No se puede modular " + leftType + " con " + rightType, this.line, this.column);
            }
        }
    }

    public Object power(Object leftOperand, Object rightOperand) {
        var leftType = this.firstOperand.getType();
        var rightType = this.secondOperand.getType();

        switch (leftType) {
            case Types.INTEGER -> {
                switch (rightType) {
                    case Types.INTEGER -> {
                        this.type = Types.INTEGER;
                        return (int) Math.pow((int) leftOperand, (int) rightOperand);
                    }
                    case Types.DOUBLE -> {
                        this.type = Types.DOUBLE;
                        return Math.pow((int) leftOperand, (double) rightOperand);
                    }
                    default -> {
                        return new JCError("Semantico", "No se puede elevar " + leftType + " con " + rightType, this.line, this.column);
                    }
                }
            }
            case Types.DOUBLE -> {
                switch (rightType) {
                    case Types.INTEGER, Types.DOUBLE -> {
                        this.type = Types.DOUBLE;
                        return Math.pow((double) leftOperand, (int) rightOperand);
                    }
                    default -> {
                        return new JCError("Semantico", "No se puede elevar " + leftType + " con " + rightType, this.line, this.column);
                    }
                }
            }
            default -> {
                return new JCError("Semantico", "No se puede elevar " + leftType + " con " + rightType, this.line, this.column);
            }
        }
    }

    public static boolean isIntegerValue(double value) {
        return value == Math.floor(value) && !Double.isInfinite(value);
    }

    public Object negate(Object operand) {
        var type = this.uniqueOperand.getType();
        switch (type) {
            case Types.INTEGER:
                this.type = Types.INTEGER;
                return (int) operand * -1;
            case Types.DOUBLE:
                this.type = Types.DOUBLE;
                return (double) operand * -1;
            default:
                return new JCError("Semantico", "No se puede negar el tipo: " + type, this.line, this.column);
        }
    }
}
