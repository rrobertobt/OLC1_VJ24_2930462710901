package edu.robertob.olc1.vj24.Engine.Statements;

import edu.robertob.olc1.vj24.Engine.Base.Instruction;
import edu.robertob.olc1.vj24.Engine.Expressions.Primitive;
import edu.robertob.olc1.vj24.Engine.Structs.*;

public class Declaration extends Instruction {

    private String id;
    private Instruction value;
    private boolean constant;

    public Declaration(String id, Types type, Instruction value, boolean constant, int line, int column) {
        super(type, line, column);
        this.id = id;
        this.constant = constant;
        this.value = value;
    }

    @Override
    public Object execute(Tree tree, SymbolTable table) {
        if (this.constant && this.value == null)
            return new JCError("Semantica", "Variable constante " + this.id + " debe tener un valor", this.line, this.column);

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

        var symbol = new SymbolVariable(this.type, this.constant, this.id, result, this.line, this.column);


        boolean created = table.setSymbol(symbol);
        if (!created) {
            return new JCError("Semantica", "Variable " + this.id + " ya existe", this.line, this.column);
        }
//        if (!table.setSymbol(symbol))
//            return new JCError("Semantica", "La variable " + this.id + " ya esta definida", this.line, this.column);

        return null;
    }

    public String getId() {
        return id;
    }
}
