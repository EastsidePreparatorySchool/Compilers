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
public class Decoration {

    ParseTree ctx;

    public Decoration(ParseTree ctx) {
        this.ctx = ctx; // must have pointer back to parse tree
    }

    static Decoration find(ParseTreeProperty<Decoration> decs, ParseTree ctx, Class thisClass) {

        while (ctx != null) {
            Decoration dec = decs.get(ctx);
            if (dec != null && thisClass.isInstance(dec)) {
                return dec;
            }
            ctx = ctx.getParent();
        }
        return null;
    }
}
