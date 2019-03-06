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
public class OutputArgListDecoration extends Decoration {
    int numArgs;

    OutputArgListDecoration(ParseTree ctx) {
        super(ctx);
        this.numArgs = 0;
    }
    
      static OutputArgListDecoration find(ParseTreeProperty<Decoration> decs, ParseTree ctx) {
        Decoration dec = Decoration.find(decs, ctx, OutputArgListDecoration.class);
        if (dec != null) {
            return (OutputArgListDecoration) dec;
        }
        return null;
    }
}
