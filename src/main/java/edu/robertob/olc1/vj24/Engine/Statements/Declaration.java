package edu.robertob.olc1.vj24.Engine.Statements;

import edu.robertob.olc1.vj24.Engine.Base.Instruction;
import edu.robertob.olc1.vj24.Engine.Expressions.Primitive;
import edu.robertob.olc1.vj24.Engine.Structs.*;

import java.util.LinkedList;

public class Declaration extends Instruction {

    private String id;
    private Instruction value;
    private boolean constant;
    private boolean isDynamicList;

    private int dimension; // Only for arrays
    private LinkedList<Instruction> values; // Only for arrays
    private LinkedList<LinkedList<Instruction>> matrixValues; // Only for matrices

    public Declaration(String id, Types type, Instruction value, boolean constant, int line, int column) {
        super(type, line, column);
        this.id = id;
        this.constant = constant;
        this.value = value;
        this.dimension = 0;
    }

    public Declaration(String id, Types type, Instruction value, boolean constant, int line, int column, boolean isDynamicList) {
        super(type, line, column);
        this.id = id;
        this.constant = constant;
        this.value = value;
        this.dimension = 0;
        this.isDynamicList = isDynamicList;
    }

    public Declaration(String id, Types type, int dimension, LinkedList<Instruction> values, boolean constant, int line, int column) {
        super(type, line, column);
        this.id = id;
        this.constant = constant;
        this.value = null;
        this.dimension = dimension;
        this.values = values;
    }

    public Declaration(String id, Types type, LinkedList<LinkedList<Instruction>> values2D, int dimension, boolean constant, int line, int column) {
        super(type, line, column);
        this.id = id;
        this.constant = constant;
        this.value = null;
        this.dimension = dimension;
        this.matrixValues = values2D;
    }


    @Override
    public Object execute(Tree tree, SymbolTable table) {
        if (dimension == 0) {
            if (this.constant && this.value == null)
                return new JCError("Semantica", "Variable constante " + this.id + " debe tener un valor", this.line, this.column);

            if (isDynamicList) {
                var symbol = new SymbolVariable(this.type, this.constant, this.id, new DynamicList(this.type, this.id, this.line, this.column), this.line, this.column, true);
                boolean created = table.setSymbol(symbol);
                if (!created) {
                    return new JCError("Semantica", "Variable " + this.id + " ya existe", this.line, this.column);
                }
                return null;
            }

            if (this.value == null) {
                switch (this.type) {
                    case INTEGER:
                        this.value = new Primitive(Types.INTEGER, 0, this.line, this.column);
                        break;
                    case DOUBLE:
                        this.value = new Primitive(Types.DOUBLE, 0.0, this.line, this.column);
                        break;
                    case STRING:
                        this.value = new Primitive(Types.STRING, "", this.line, this.column);
                        break;
                    case BOOLEAN:
                        this.value = new Primitive(Types.BOOLEAN, false, this.line, this.column);
                        break;
                    case CHARACTER:
                        this.value = new Primitive(Types.CHARACTER, '0', this.line, this.column);
                        break;
                }
            }

            var result = this.value.execute(tree, table);

            if (result instanceof JCError) return result;

            if (this.value.getType() != this.type)
                return new JCError("Semantica", "No se puede asignar el valor de tipo " + this.value.getType() + " a una variable de tipo " + this.type, this.line, this.column);

            if (this.id.equals("new")) {
                return new JCError("Semantica", "No se puede declarar una variable con el nombre 'new'", this.line, this.column);
            }

            var symbol = new SymbolVariable(this.type, this.constant, this.id, result, this.line, this.column);


            boolean created = table.setSymbol(symbol);
            if (!created) {
                return new JCError("Semantica", "Variable " + this.id + " ya existe", this.line, this.column);
            }
        } else if (dimension == 1) {
            // Handle 1D array declaration
            LinkedList<Object> evaluatedValues = new LinkedList<>();
            for (Instruction val : values) {
                var evalValue = val.execute(tree, table);
                if (evalValue instanceof JCError) return evalValue;
                evaluatedValues.add(evalValue);
            }

            if (!isValidVector(evaluatedValues)) {
                return new JCError("Semantica", "Inicializacion de arreglo/vector invalida", line, column);
            }

            var symbol = new SymbolVariable(this.type, this.constant, this.id,
                    new ArrayValue(this.type, dimension, evaluatedValues, evaluatedValues.size()),
                    this.line, this.column);

            boolean created = table.setSymbol(symbol);
            if (!created) {
                return new JCError("Semantica", "Variable " + this.id + " ya existe", this.line, this.column);
            }
        } else if (dimension == 2) {
            // Handle 2D array declaration
            LinkedList<Object> evaluatedValues = new LinkedList<>();
            for (LinkedList<Instruction> row : matrixValues) {
                LinkedList<Object> rowValues = new LinkedList<>();
                for (Instruction val : row) {
                    var evalValue = val.execute(tree, table);
                    if (evalValue instanceof JCError) return evalValue;
                    rowValues.add(evalValue);
                }
                evaluatedValues.add(rowValues);
            }

            if (!isValidMatrix(evaluatedValues)) {
                return new JCError("Semantica", "Inicializacion de arreglo/vector invalida, revisa los valores y sus tipos", line, column);
            }

            var symbol = new SymbolVariable(this.type, this.constant, this.id,
                    new ArrayValue(this.type, dimension, evaluatedValues, evaluatedValues.size()),
                    this.line, this.column);

            boolean created = table.setSymbol(symbol);
            if (!created) {
                return new JCError("Semantica", "Variable " + this.id + " ya existe", this.line, this.column);
            }
        }

        return null;
    }

    public String getId() {
        return id;
    }

    private boolean isValidVector(LinkedList<Object> values) {
        for (Object value : values) {
            if (!value.getClass().getSimpleName().equalsIgnoreCase(type.name())) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidMatrix(LinkedList<Object> values) {
        int rowSize = -1;
        for (Object row : values) {
            if (!(row instanceof LinkedList<?>)) {
                return false;
            }
            LinkedList<?> rowList = (LinkedList<?>) row;
            if (rowSize == -1) {
                rowSize = rowList.size();
            } else if (rowList.size() != rowSize) {
                return false;
            }
            for (Object value : rowList) {
                if (!value.getClass().getSimpleName().equalsIgnoreCase(type.name())) {
                    return false;
                }
            }
        }
        return true;
    }
}
