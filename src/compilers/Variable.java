/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilers;

/**
 *
 * @author gmein
 */
public class Variable {

    String name;
    int ordinal;
    Type type;
    boolean global;

    public Variable(String n, Type t) {
        this.name = n;
        this.ordinal = 0;
        this.type = t;
        global = false;
    }

    public static enum Type {
        INTEGER, FLOAT, BOOLEAN, STRING, NULL;
    }
    
    public int getOffset() {
        return ordinal*4;
    }

    public static Variable.Type typeFromTypeName(String typeName) {
        // todo: there is a better way to do this, with token numbers out of the grammar
        Variable.Type t;
        typeName = typeName.toLowerCase();
        switch (typeName) {
            case "numbr":
                t = Variable.Type.INTEGER;
                break;
            case "numbar":
                t = Variable.Type.FLOAT;
                break;
            case "yarn":
                t = Variable.Type.STRING;
                break;
            case "troof":
                t = Variable.Type.BOOLEAN;
                break;
            case "null":
            default:
                t = Variable.Type.NULL;
        }
        return t;
    }

}
