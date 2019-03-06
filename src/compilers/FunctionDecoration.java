/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilers;

import java.util.LinkedList;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

/**
 *
 * @author gmein
 */
public class FunctionDecoration extends StorageScopeDecoration {

    public int numArgs;
    public int numVars;
    public LinkedList<Variable> args;

    public String name;
    public Variable.Type returnType;

    FunctionDecoration(ParseTree ctx, String name) {
        super(ctx);
        this.name = name;
        this.numArgs = 0;
        this.numVars = 0;
        this.returnType = Variable.Type.NULL;
        this.args = new LinkedList<>();
    }
    
    public void adjustArguments() {
        for (Variable v:args) {
            v.ordinal = this.numArgs - v.ordinal + 1;
        }
    }

    public void addParameter(String name, Variable.Type type) {
        if (!name.equalsIgnoreCase("it") && vars.containsKey(name)) {
            throw new IllegalArgumentException();
        }

        Variable var = new Variable(name, type);
        var.ordinal = numArgs;
        var.global = false;
        this.vars.put(name, var);
        this.args.add(var);
        this.numArgs++;
    }

    @Override
    public void storeVariable(Variable v) {
        this.numVars++;
        v.ordinal = -numVars;
        v.global = false;
        this.vars.put(v.name, v);
    }

    static FunctionDecoration findContaining(ParseTreeProperty<Decoration> decs, ParseTree ctx) {
        Decoration dec = Decoration.find(decs, ctx, FunctionDecoration.class);
        if (dec != null) {
            return (FunctionDecoration) dec;
        }
        return null;
    }

    static FunctionDecoration findByName(ParseTreeProperty<Decoration> decs, ParseTree ctx, String name) {
        return GlobalScopeDecoration.findFunc(decs, ctx, name);
    }

}
