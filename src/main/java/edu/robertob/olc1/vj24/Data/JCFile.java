/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.robertob.olc1.vj24.Data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

import edu.robertob.olc1.vj24.Engine.Structs.JCError;
import edu.robertob.olc1.vj24.Engine.Structs.SymbolTable;
import edu.robertob.olc1.vj24.Engine.Structs.Tree;
import jflex.generator.*;

/**
 *
 * @author robertob
 * Esta clase se encarga de manejar los archivos que se abren en el editor
 */
public class JCFile {
    private String name;
    private String systemPath;
    private String content;
    private boolean saved;
    private boolean isNew;
    private int index;
    private String consoleOutput;
    private LinkedList<JCError> errors = new LinkedList<>();
    private SymbolTable globalTable;
    private Tree currentTree;

    public JCFile(String name, String systemPath, String content, boolean saved, int index) {
        this.name = name;
        this.systemPath = systemPath;
        this.content = content;
        this.saved = saved;
        this.index = index;
    }

    public JCFile(String name, String systemPath, String content, boolean saved, boolean isNew, int index) {
        this.name = name;
        this.systemPath = systemPath;
        this.content = content;
        this.saved = saved;
        this.isNew = isNew;
        this.index = index;
    }

    public void saveFile(String content) {
        // Guardar el archivo en el sistema usando el systemPath
        this.content = content;
        this.saved = true;
        File file = new File(this.systemPath);
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSystemPath() {
        return systemPath;
    }

    public void setSystemPath(String systemPath) {
        this.systemPath = systemPath;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSaved() {
        return saved;
    }

    public void setSaved(boolean saved) {
        this.saved = saved;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getConsoleOutput() {
        return consoleOutput;
    }

    public void setConsoleOutput(String consoleOutput) {
        this.consoleOutput = consoleOutput;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setIsNew(boolean isNew) {
        this.isNew = isNew;
    }

    public void setErrors(LinkedList<JCError> errors) {
        this.errors = errors;
    }

    public LinkedList<JCError> getErrors() {
        return this.errors;
    }

    public void setGlobalTable(SymbolTable globalTable) {
        this.globalTable = globalTable;
    }

    public SymbolTable getGlobalTable() {
        return this.globalTable;
    }

    public void setCurrentTree(Tree tree) {
        this.currentTree = tree;
    }

    public Tree getCurrentTree() {
        return this.currentTree;
    }
}
