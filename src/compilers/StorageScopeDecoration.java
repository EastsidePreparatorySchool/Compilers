/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilers;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

/**
 *
 * @author gmein
 */
public abstract class StorageScopeDecoration extends ScopeDecoration {

    StorageScopeDecoration(ParseTree ctx) {
        super(ctx);
    }

    abstract void storeVariable(Variable v);

    static StorageScopeDecoration find(ParseTreeProperty<Decoration> decs, ParseTree ctx) {
        Decoration dec = Decoration.find(decs, ctx, StorageScopeDecoration.class);
        if (dec != null) {
            return (StorageScopeDecoration) dec;
        }
        return null;
    }

}
