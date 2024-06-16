package edu.robertob.olc1.vj24.Engine.Structs;

import edu.robertob.olc1.vj24.Engine.Base.Instruction;

import java.util.LinkedList;

public class Tree {
    private LinkedList<Instruction> instructions;
    private String console;
    private SymbolTable globalTable;
    public LinkedList<JCError> errors;

    public Tree(LinkedList<Instruction> instructions) {
        this.instructions = instructions;
        this.console = "";
        this.globalTable = new SymbolTable("");
        this.errors = new LinkedList<>();
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

    public void setErrors(LinkedList<JCError> errors) {
        this.errors = errors;
    }

    // Dedicated print method for the console
    public void print(String value) {
        this.console += value + "\n";
    }
}
