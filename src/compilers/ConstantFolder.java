/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilers;

import java.util.ArrayList;
import lol.LOLDefaultRuntime;
import lol.LOLcodeBaseListener;
import lol.LOLcodeParser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import vx86.Program;

/**
 *
 * @author gmein
 */
public class ConstantFolder extends LOLcodeBaseListener {

    ParseTreeProperty<Decoration> decs;
    public int total;

    public ConstantFolder(ParseTreeProperty<Decoration> decs) {
        this.decs = decs;
        this.total = 0;
    }

    void foldSingleChild(ParseTree ctx) {
        // get decorations generated in static
        ExprDecoration decSum = ExprDecoration.get(decs, ctx);
        ExprDecoration decLeft = ExprDecoration.get(decs, ctx.getChild(0));

        // see whether values are defined, and get them
        if (decLeft.value != null) {
            decSum.value = decLeft.value;
            decLeft.folded = true;
        }
    }

    @Override
    public void exitSum(LOLcodeParser.SumContext ctx) {
        // get decorations generated in static
        ExprDecoration decSum = ExprDecoration.get(decs, ctx);
        ExprDecoration decLeft = ExprDecoration.get(decs, ctx.getChild(LOLcodeParser.Foldable_argContext.class, 0));
        ExprDecoration decRight = ExprDecoration.get(decs, ctx.getChild(LOLcodeParser.Foldable_argContext.class, 1));

        // see whether values are defined, and get them
        switch (decSum.type) {
            case INTEGER:
                if (decLeft.value != null && decRight.value != null) {
                    decSum.value = (Integer) decLeft.value + (Integer) decRight.value;
                    decLeft.folded = true;
                    decRight.folded = true;
                    this.total++;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void exitDiff(LOLcodeParser.DiffContext ctx) {
        // get decorations generated in static
        ExprDecoration dec = ExprDecoration.get(decs, ctx);
        ExprDecoration decLeft = ExprDecoration.get(decs, ctx.getChild(LOLcodeParser.Foldable_argContext.class, 0));
        ExprDecoration decRight = ExprDecoration.get(decs, ctx.getChild(LOLcodeParser.Foldable_argContext.class, 1));

        // see whether values are defined, and get them
        switch (dec.type) {
            case INTEGER:
                if (decLeft.value != null && decRight.value != null) {
                    dec.value = (Integer) decLeft.value - (Integer) decRight.value;
                    decLeft.folded = true;
                    decRight.folded = true;
                    this.total++;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void exitProduct(LOLcodeParser.ProductContext ctx) {
        // get decorations generated in static
        ExprDecoration dec = ExprDecoration.get(decs, ctx);
        ExprDecoration decLeft = ExprDecoration.get(decs, ctx.getChild(LOLcodeParser.Foldable_argContext.class, 0));
        ExprDecoration decRight = ExprDecoration.get(decs, ctx.getChild(LOLcodeParser.Foldable_argContext.class, 1));

        // see whether values are defined, and get them
        switch (dec.type) {
            case INTEGER:
                if (decLeft.value != null && decRight.value != null) {
                    dec.value = (Integer) decLeft.value *  (Integer) decRight.value;
                    decLeft.folded = true;
                    decRight.folded = true;
                    this.total++;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void exitFoldable_arg(LOLcodeParser.Foldable_argContext ctx) {
        foldSingleChild(ctx);
    }

    @Override
    public void exitExpr(LOLcodeParser.ExprContext ctx) {
        foldSingleChild(ctx);
    }

    @Override
    public void exitAtom(LOLcodeParser.AtomContext ctx) {
        foldSingleChild(ctx);
    }

}
