/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilers;

import java.util.HashMap;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

/**
 *
 * @author gmein
 */
public class GlobalScopeDecoration extends StorageScopeDecoration {

    HashMap<String, FunctionDecoration> funcs;
    int numVars;

    GlobalScopeDecoration(ParseTree ctx) {
        super(ctx);
        funcs = new HashMap<>();
        numVars = 0;
    }

    static GlobalScopeDecoration find(ParseTreeProperty<Decoration> decs, ParseTree ctx) {
        Decoration dec = Decoration.find(decs, ctx, GlobalScopeDecoration.class);
        if (dec != null) {
            return (GlobalScopeDecoration) dec;
        }
        return null;
    }

    @Override
    public void storeVariable(Variable v) {
        this.numVars++;
        v.ordinal = numVars;
        v.global = true;
        this.vars.put(v.name, v);
    }

    public void addFunction(String name, FunctionDecoration fdec) {
        funcs.put(name, fdec);
    }

    static FunctionDecoration findFunc(ParseTreeProperty<Decoration> decs, ParseTree ctx, String name) {
        GlobalScopeDecoration scope = GlobalScopeDecoration.find(decs, ctx);
        if (scope == null) {
            return null;
        }

        // does it have our function?
        FunctionDecoration fdec = scope.funcs.get(name);
        if (fdec != null) {
            // yes! return the variable
            return fdec;
        }

        // nothing found
        return null;
    }
}
