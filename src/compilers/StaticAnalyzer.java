package compilers;

import lol.LOLcodeBaseListener;
import lol.LOLcodeParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.TerminalNode;
import vx86.Util;

/**
 *
 * @author gmein
 */
public class StaticAnalyzer extends LOLcodeBaseListener {

    // keep track here:
    // list of global variables encountered, with names
    // list of functions declared
    ParseTreeProperty<Decoration> decs = new ParseTreeProperty<>();

    int errors = 0;

    void error(String msg, ParserRuleContext ctx) {
        errors++;
        Util.println(Util.ANSI_RED
                + "SA Error: "
                + ctx.getStart().getLine() + ":" + ctx.getStart().getCharPositionInLine()
                + ": " + msg
                + ", " + ctx.getClass().getName()
                + Util.ANSI_RESET);
    }

    @Override
    public void enterLoop(LOLcodeParser.LoopContext ctx) {
        // put up new loop decoration
        String name = ctx.getToken(LOLcodeParser.IDENTIFIER, 0).getText();
        LoopDecoration dec = new LoopDecoration(ctx, name);
        decs.put(ctx, dec);

        // loop checking: the easy stuff
        if (!name.equals(ctx.getToken(LOLcodeParser.IDENTIFIER, 1).getText())) {
            error("Encountered mismatched loop labels", ctx);
            return;
        }
    }

    @Override
    public void enterProgram(LOLcodeParser.ProgramContext ctx) {
        // put up new scope
        GlobalScopeDecoration scope = new GlobalScopeDecoration(ctx);
        decs.put(ctx, scope);
        Variable v = new Variable("IT", Variable.Type.NULL);
        scope.storeVariable(v);
    }

    @Override
    public void enterFunc_decl(LOLcodeParser.Func_declContext ctx) {
        String name = ctx.getTokens(LOLcodeParser.IDENTIFIER).get(0).getText();

        // make a new scope
        //Util.println("Entering function declaration for " + name);
        FunctionDecoration dec = new FunctionDecoration(ctx, name);

        // now find the responsible scope and register the function
        GlobalScopeDecoration scope = GlobalScopeDecoration.find(decs, ctx);
        scope.addFunction(ctx.getToken(LOLcodeParser.IDENTIFIER, 0).getText(), dec);

        // register the new scope
        decs.put(ctx, dec);
    }

    @Override
    public void enterReturn_type(LOLcodeParser.Return_typeContext ctx) {
        FunctionDecoration dec = FunctionDecoration.findContaining(decs, ctx);
        String type = ((TerminalNode) ctx.getChild(1).getChild(0)).getText();
        dec.returnType = Variable.typeFromTypeName(type);
    }

    @Override
    public void exitFunc_decl(LOLcodeParser.Func_declContext ctx) {
        FunctionDecoration dec = FunctionDecoration.findContaining(decs, ctx);
        //Util.println("Exiting function declaration for " + dec.name + ", counted " + dec.numArgs + " arguments");

        // reverse the ordinals of the arguments
        dec.adjustArguments();
    }

    @Override
    public void enterVar_decl(LOLcodeParser.Var_declContext ctx) {
        // name is the 4th token (index 3) in this context
        String name = ctx.getToken(LOLcodeParser.IDENTIFIER, 0).getText();

        // type is the first token in the vartype subnode which is the child (not token) with index 3
        LOLcodeParser.VartypeContext ctxType = (LOLcodeParser.VartypeContext) ctx.getChild(3);
        String type = ctxType.getText().toLowerCase();

        // now find the responsible scope and register the variable
        ScopeDecoration scope = ScopeDecoration.find(decs, ctx);
        scope.addVariable(decs, ctx.getToken(LOLcodeParser.IDENTIFIER, 0).getText(), Variable.typeFromTypeName(type));

        //Util.println("registered variable " + name + " with type " + type);
    }

    @Override
    public void exitVar_decl(LOLcodeParser.Var_declContext ctx) {
        String name = ctx.getToken(LOLcodeParser.IDENTIFIER, 0).getText();
        Variable v = ScopeDecoration.findVar(decs, ctx, name);

        // does it have an initializer?
        if (ctx.getChildCount() == 7) {
            LOLcodeParser.ExprContext expr = (LOLcodeParser.ExprContext) ctx.children.get(6);
            ExprDecoration decInit = (ExprDecoration) decs.get(expr);
            if (v.type != decInit.type) {
                error("type mismatch in variable initialization", ctx);
                return;
            }
        }
    }

    @Override
    public void enterArg_decl(LOLcodeParser.Arg_declContext ctx) {
        // find declaring scope
        FunctionDecoration dec = FunctionDecoration.findContaining(decs, ctx);

        // register argument as variable
        String name = ctx.getToken(LOLcodeParser.IDENTIFIER, 0).getText();
        String type = ctx.getChild(1).getChild(0).getText();
        dec.addParameter(name, Variable.typeFromTypeName(type));
    }

    @Override
    public void enterVar_ref(LOLcodeParser.Var_refContext ctx) {
        // put up the type, and any constant it might have
        ExprDecoration dec = new ExprDecoration(ctx);
        decs.put(ctx, dec);

        Variable v = ScopeDecoration.findVar(decs, ctx, ctx.getToken(LOLcodeParser.IDENTIFIER, 0).getText());
        dec.type = v.type;
    }

    @Override
    public void enterAtom(LOLcodeParser.AtomContext ctx) {
        // put up the type, and any constant it might have
        ExprDecoration dec = new ExprDecoration(ctx);
        decs.put(ctx, dec);
    }

    @Override
    public void exitAtom(LOLcodeParser.AtomContext ctx) {
        // put up the type, and any constant it might have
        ExprDecoration dec = (ExprDecoration) decs.get(ctx);
        setTypeFromChildren(ctx);
    }

    @Override
    public void enterOutput_args(LOLcodeParser.Output_argsContext ctx) {
        // put up the type, and any constant it might have
        OutputArgListDecoration dec = new OutputArgListDecoration(ctx);
        decs.put(ctx, dec);
    }

    @Override
    public void exitInput(LOLcodeParser.InputContext ctx) {
        String name = ctx.getToken(LOLcodeParser.IDENTIFIER, 0).getText();
        Variable v = ScopeDecoration.findVar(decs, ctx, name);
        if (v == null) {
            error("Unknown variable for GIMMEH: " + name, ctx);
            return;
        }
    }

    @Override
    public void exitOutput_arg(LOLcodeParser.Output_argContext ctx) {
        // put up the type, and any constant it might have
        ExprDecoration dec = new ExprDecoration(ctx);
        decs.put(ctx, dec);
        setTypeFromChildren(ctx);

        // count this argument in the list
        OutputArgListDecoration decList = OutputArgListDecoration.find(decs, ctx);
        decList.numArgs++;
    }

    @Override
    public void exitExpr(LOLcodeParser.ExprContext ctx) {
        // put up the type, and any constant it might have
        ExprDecoration dec = new ExprDecoration(ctx);
        decs.put(ctx, dec);

        setTypeFromChildren(ctx);
    }

    @Override
    public void enterLiteral_value(LOLcodeParser.Literal_valueContext ctx) {
        // put up the type, and the constant
        ExprDecoration dec = new ExprDecoration(ctx);
        decs.put(ctx, dec);

        TerminalNode tn = (TerminalNode) ctx.children.get(0);
        switch (tn.getSymbol().getType()) {
            case LOLcodeParser.INTEGER:
                dec.type = Variable.Type.INTEGER;
                dec.value = Integer.parseInt(tn.getText());
                break;
            case LOLcodeParser.STRING:
                dec.type = Variable.Type.STRING;
                String s = tn.getText();
                dec.value = s.substring(1, s.length() - 1);
                break;
            case LOLcodeParser.FLOAT:
                dec.type = Variable.Type.FLOAT;
                dec.value = Float.parseFloat(tn.getText());
                break;
            case LOLcodeParser.BOOLEAN:
                dec.type = Variable.Type.BOOLEAN;
                dec.value = tn.getText().equalsIgnoreCase("win");
                break;
            default:
                error("unknown type for literal value, should not be possible", ctx);
                return;
        }
    }

    @Override
    public void enterDiff(LOLcodeParser.DiffContext ctx) {
        // put up the type, and any constant it might have
        ExprDecoration dec = new ExprDecoration(ctx);
        decs.put(ctx, dec);
    }

    @Override
    public void exitDiff(LOLcodeParser.DiffContext ctx) {
        // get decoration, promote types
        ExprDecoration dec = (ExprDecoration) decs.get(ctx);
        setTypeFromChildren(ctx);

        // has to be numeric
        if (dec.type != Variable.Type.FLOAT && dec.type != Variable.Type.INTEGER) {
            error("Illegal type for diff", ctx);
            return;
        }
    }

    @Override
    public void enterProduct(LOLcodeParser.ProductContext ctx) {
        // put up the type, and any constant it might have
        ExprDecoration dec = new ExprDecoration(ctx);
        decs.put(ctx, dec);
    }

    @Override
    public void enterSum(LOLcodeParser.SumContext ctx) {
        // put up the type, and any constant it might have
        ExprDecoration dec = new ExprDecoration(ctx);
        decs.put(ctx, dec);
    }

    @Override
    public void exitProduct(LOLcodeParser.ProductContext ctx) {
        // get decoration, promote types
        ExprDecoration dec = (ExprDecoration) decs.get(ctx);
        setTypeFromChildren(ctx);

        // has to be numeric
        if (dec.type != Variable.Type.FLOAT && dec.type != Variable.Type.INTEGER) {
            error("Illegal type for product", ctx);
            return;
        }
    }

    @Override
    public void exitSum(LOLcodeParser.SumContext ctx) {
        // get decoration, promote types
        ExprDecoration dec = (ExprDecoration) decs.get(ctx);
        setTypeFromChildren(ctx);

        // has to be numeric
        if (dec.type != Variable.Type.FLOAT && dec.type != Variable.Type.INTEGER) {
            error("Illegal type for sum", ctx);
            return;
        }
    }

    @Override
    public void enterNaked_arg(LOLcodeParser.Naked_argContext ctx) {
        // put up the type, and any constant it might have
        ExprDecoration dec = new ExprDecoration(ctx);
        decs.put(ctx, dec);
    }

    @Override
    public void exitNaked_arg(LOLcodeParser.Naked_argContext ctx) {
        // get decoration, promote types
        ExprDecoration dec = (ExprDecoration) decs.get(ctx);
        setTypeFromChildren(ctx);
    }

    @Override
    public void enterFoldable_arg(LOLcodeParser.Foldable_argContext ctx) {
        // put up the type, and any constant it might have
        ExprDecoration dec = new ExprDecoration(ctx);
        decs.put(ctx, dec);
    }

    @Override
    public void exitFoldable_arg(LOLcodeParser.Foldable_argContext ctx) {
        // get decoration, promote types
        ExprDecoration dec = (ExprDecoration) decs.get(ctx);
        setTypeFromChildren(ctx);
    }

    @Override
    public void exitFunc_call(LOLcodeParser.Func_callContext ctx) {
    }

    private void setTypeFromChildren(ParserRuleContext ctx) {
        // put up the type, and any constant it might have
        ExprDecoration dec = (ExprDecoration) decs.get(ctx);
        //Util.println("Setting type for " + ctx.getClass().getName());

        // type upward propagation: if all subnodes have same type, make that ours
        for (int i = 0; i < ctx.getChildCount(); i++) {
            ParseTree child = ctx.getChild(i);
            ExprDecoration decChild = (ExprDecoration) decs.get(child);
            if (decChild == null) {
                continue;
            }
            if (dec.type == Variable.Type.NULL) {
                // our expression is still undecided, record the child node's type here
                dec.type = decChild.type;
            } else {
                // our type is already set, is it the same?
                if (dec.type != decChild.type) {
                    error("Type mismatch", ctx);
                    return;
                }
            }
        }
        if (dec.type == Variable.Type.NULL) {
            error("Not able to establish type", ctx);
            return;
        }
    }

    @Override
    public void enterSame(LOLcodeParser.SameContext ctx) {
        // put up the type, and any constant it might have
        ExprDecoration dec = new ExprDecoration(ctx);
        decs.put(ctx, dec);
    }

    @Override
    public void exitSame(LOLcodeParser.SameContext ctx) {
        // get decoration, promote types
        ExprDecoration dec = (ExprDecoration) decs.get(ctx);

        // check that both args have same type
        ExprDecoration decArg1 = (ExprDecoration) decs.get(ctx.getChild(2));
        ExprDecoration decArg2 = (ExprDecoration) decs.get(ctx.getChild(4));

        if (decArg1.type != decArg2.type) {
            error("Illegal type for comparison", ctx);
            return;
        }

        dec.type = Variable.Type.BOOLEAN;
    }

    @Override
    public void exitVar_assignment(LOLcodeParser.Var_assignmentContext ctx) {
        String name = ctx.getChild(0).getChild(0).getText();
        Variable v = ScopeDecoration.findVar(decs, ctx, name);
        if (v == null) {
            error("Unknown variable for assignment", ctx);
            return;
        }
        ExprDecoration decExpr = (ExprDecoration) decs.get(ctx.getChild(2));
        if (v.type != decExpr.type) {
            error("Type mismatch for assignment", ctx);
            return;
        }
    }

    @Override
    public void enterLoop_action(LOLcodeParser.Loop_actionContext ctx) {
        LoopDecoration dec = LoopDecoration.find(decs, ctx);

        String varName = ctx.getChild(2).getChild(0).getText();
        Variable v = ScopeDecoration.findVar(decs, ctx, varName);
        if (v == null) {
            error("Unknown variable \"" + varName + "\" for loop \"" + dec.name + "\"", ctx);
            return;
        }
        if (v.type != Variable.Type.INTEGER) {
            error("Variable \"" + varName + "\" for loop \"" + dec.name + "\" is not numbr", ctx);
            return;
        }

        dec.v = v;
        dec.nerfin = (ctx.getToken(LOLcodeParser.NERFIN, 0) != null);
    }

    @Override
    public void exitLoop_action(LOLcodeParser.Loop_actionContext ctx) {
    }

    @Override
    public void enterLoop_condition(LOLcodeParser.Loop_conditionContext ctx) {

    }

    @Override
    public void exitLoop_condition(LOLcodeParser.Loop_conditionContext ctx) {
        LoopDecoration dec = LoopDecoration.find(decs, ctx);

        // check that condition is boolean
        LOLcodeParser.ExprContext ctxExpr = (LOLcodeParser.ExprContext) ctx.getChild(1);
        ExprDecoration decExpr = (ExprDecoration) decs.get(ctxExpr);
        if (decExpr.type != Variable.Type.BOOLEAN) {
            error("Non-boolean expression for loop condition in loop \"" + dec.name + "\"", ctx);
            return;
        }

        dec.until = (ctx.getToken(LOLcodeParser.TIL, 0) != null);
        dec.ctxCondition = ctx;
    }

    @Override
    public void enterLoop_end(LOLcodeParser.Loop_endContext ctx) {
    }

    @Override
    public void exitLoop_end(LOLcodeParser.Loop_endContext ctx) {
    }

    @Override
    public void exitReturn_statement(LOLcodeParser.Return_statementContext ctx) {
        // find continaing scope
        FunctionDecoration dec = FunctionDecoration.findContaining(decs, ctx);

        LOLcodeParser.ExprContext ctxExpr = (LOLcodeParser.ExprContext) ctx.getChild(2);
        ExprDecoration decExpr = (ExprDecoration) decs.get(ctxExpr);
        if (decExpr.type != dec.returnType) {
            error("Type mismatch for return value", ctx);
            return;
        }

    }

    @Override
    public void exitStatement_affecting_it(LOLcodeParser.Statement_affecting_itContext ctx) {
        // after these statements, update the pseudo-variable "it"
        String name;
        Variable v;

        Variable it = ScopeDecoration.findVar(decs, ctx, "IT");

        ParserRuleContext ctxChild = (ParserRuleContext) ctx.getChild(0);
        if (ctxChild instanceof LOLcodeParser.Var_declContext) {
            name = ctxChild.getToken(LOLcodeParser.IDENTIFIER, 0).getText();
            v = ScopeDecoration.findVar(decs, ctx, name);
            it.type = v.type;
        } else if (ctxChild instanceof LOLcodeParser.Var_assignmentContext) {
            name = ctxChild.getChild(0).getChild(0).getText();
            v = ScopeDecoration.findVar(decs, ctx, name);
            it.type = v.type;
        } else if (ctxChild instanceof LOLcodeParser.InputContext) {
            it.type = Variable.Type.STRING;
        } else if (ctxChild instanceof LOLcodeParser.OutputContext) {
            it.type = Variable.Type.STRING;
        } else if (ctxChild instanceof LOLcodeParser.ExprContext) {
            ExprDecoration decExpr = (ExprDecoration) decs.get(ctxChild);
            it.type = decExpr.type;
        } else if (ctxChild instanceof LOLcodeParser.Func_callContext) {
            name = ctxChild.getToken(LOLcodeParser.IDENTIFIER, 0).getText();
            FunctionDecoration decFunc = GlobalScopeDecoration.findFunc(decs, ctx, name);
            it.type = decFunc.returnType;
        } else {
            error("Error maintaining \"it\", not sure hwo we got here", ctx);
            return;
        }
    }

}
