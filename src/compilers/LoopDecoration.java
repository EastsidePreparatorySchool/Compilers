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
public class LoopDecoration extends Decoration {

    String name;
    LOLcodeParser.Loop_conditionContext ctxCondition;
    Variable v;
    boolean nerfin;
    boolean until;

    LoopDecoration(ParserRuleContext ctx, String name) {
        super(ctx);
        this.name = name;
    }

    static LoopDecoration find(ParseTreeProperty<Decoration> decs, ParseTree ctx) {
        Decoration dec = Decoration.find(decs, ctx, LoopDecoration.class);
        if (dec != null) {
            return (LoopDecoration) dec;
        }
        return null;
    }
}
