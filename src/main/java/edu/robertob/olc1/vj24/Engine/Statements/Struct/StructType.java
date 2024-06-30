package edu.robertob.olc1.vj24.Engine.Statements.Struct;

import edu.robertob.olc1.vj24.Engine.Structs.Types;

public class StructType {
    private Types primitiveType;
    private String anotherStruct;

    public StructType(Types primitiveType) {
        this.primitiveType = primitiveType;
    }

    public StructType(String anotherStruct) {
        this.anotherStruct = anotherStruct;
    }

    public Types getPrimitiveType() {
        return primitiveType;
    }

    public String getAnotherStruct() {
        return anotherStruct;
    }

    public boolean isPrimitive() {
        return primitiveType != null;
    }
}

