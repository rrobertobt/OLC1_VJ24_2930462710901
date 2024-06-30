package edu.robertob.olc1.vj24.Engine.Structs;

import java.util.LinkedList;

public class ArrayValue {
    private Types type;
    private int dimension;
    private LinkedList<Object> values;
    private int size;

    public ArrayValue(Types type, int dimension, LinkedList<Object> values, int size) {
        this.type = type;
        this.dimension = dimension;
        this.values = values;
        this.size = size;
    }

    public Types getType() {
        return type;
    }

    public int getDimension() {
        return dimension;
    }

    public LinkedList<Object> getValues() {
        return values;
    }

    public int getLength() {
        return size;
    }
}
