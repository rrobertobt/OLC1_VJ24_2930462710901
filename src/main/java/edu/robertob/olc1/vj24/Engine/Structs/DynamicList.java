package edu.robertob.olc1.vj24.Engine.Structs;

import java.util.LinkedList;

// This class represents a dynamic list in our custom language
//
public class DynamicList {
    private String id;
    private LinkedList<Object> elements;
    private Types type;
    private int line;
    private int column;

    public DynamicList(Types type, String id, int line, int column) {
        this.elements = new LinkedList<>();
        this.type = type;
        this.id = id;
        this.line = line;
        this.column = column;
    }

    public void addElement(Object element) {
        this.elements.add(element);
    }

    public LinkedList<Object> getElements() {
        return this.elements;
    }

    public Types getType() {
        return this.type;
    }

    public String getId() {
        return id;
    }
}
