package edu.robertob.olc1.vj24.Engine.Structs;

import edu.robertob.olc1.vj24.Engine.Base.Instruction;
import edu.robertob.olc1.vj24.Engine.Statements.MethodDeclaration;
import edu.robertob.olc1.vj24.Engine.Statements.Struct.StructDefinition;

import java.util.HashMap;
import java.util.LinkedList;

public class Tree {
    private LinkedList<Instruction> instructions;
    private String console;
    private SymbolTable globalTable;
    public LinkedList<JCError> errors;
    private HashMap<String, StructDefinition> structs;
    private LinkedList<Instruction> methods;

    public Tree(LinkedList<Instruction> instructions) {
        this.instructions = instructions;
        this.console = "";
        this.globalTable = new SymbolTable("");
        this.errors = new LinkedList<>();
        this.structs = new HashMap<>();
        this.methods = new LinkedList<>();
    }

    public void addMethod(Instruction method) {
        this.methods.add((MethodDeclaration) method);
    }

    public boolean setStruct(StructDefinition struct) {
        if (structs.containsKey(struct.getStructName())) {
            return false;
        }
        structs.put(struct.getStructName(), struct);
        return true;
    }

    public StructDefinition getStruct(String id) {
        return structs.get(id);
    }

    public Instruction getMethod(String id) {
        for (Instruction i : this.methods) {
            if (i instanceof MethodDeclaration method) {
                if (method.getId().equalsIgnoreCase(id)) {
                    return method;
                }
            }
        }
        return null;
    }

    public LinkedList<Instruction> getInstructions() {
        return instructions;
    }

    public void setInstructions(LinkedList<Instruction> instructions) {
        this.instructions = instructions;
    }

    public String getConsole() {
        return console;
    }

    public void setConsole(String console) {
        this.console = console;
    }

    public SymbolTable getGlobalTable() {
        return globalTable;
    }

    public void setGlobalTable(SymbolTable globalTable) {
        this.globalTable = globalTable;
    }

    public LinkedList<JCError> getErrors() {
        return errors;
    }

    public LinkedList<Instruction> getMethods() {
        return methods;
    }

    public void setErrors(LinkedList<JCError> errors) {
        this.errors = errors;
    }

    // Dedicated print method for the console
    public void print(String value) {
        this.console += value + "\n";
    }
}
