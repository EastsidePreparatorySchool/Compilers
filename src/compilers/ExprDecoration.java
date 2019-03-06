/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilers;

import lol.LOLcodeParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;

/**
 *
 * @author gmein
 */
public class ExprDecoration extends Decoration {

    Variable.Type type;
    Object value;
    boolean folded;

    ExprDecoration(ParseTree ctx) {
        super(ctx);
        type = Variable.Type.NULL;
        value = null;
        folded = false;
    }

    static ExprDecoration get(ParseTreeProperty<Decoration> decs, ParseTree ctx) {
        return (ExprDecoration) decs.get(ctx);
    }

}
