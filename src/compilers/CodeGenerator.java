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
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTreeProperty;
import vx86.Instruction;
import vx86.Program;
import vx86.Util;
import vx86.Vx86;

/**
 *
 * @author gmein
 */
public class CodeGenerator extends LOLcodeBaseListener {

    Program p;
    ParseTreeProperty<Decoration> decs;

    public CodeGenerator(ParseTreeProperty<Decoration> decs) {
        this.p = new Program(new ArrayList<>(), new LOLDefaultRuntime());
        this.decs = decs;
    }

    //
    // helper functions
    //
    public boolean generateForFoldedExpr(ParserRuleContext ctx) {
        ExprDecoration dec = (ExprDecoration) decs.get(ctx);
        if (dec == null) {
            return false;
        }

        // two cases:
        // one, this expression was folded into another. Generate no code.
        if (dec.folded) {
            //Util.println("CF: Folded expr " + ctx.getText());
            return true;
        }

        // Two, this expression folded its children and now has a defined value. Load that value.
        if (dec.value != null) {
            //Util.println("CF: Loading folded value " + ctx.getText());
            loadLiteralFromDec(dec);
            return true;
        }

        return false;
    }

    void loadLiteralFromDec(ExprDecoration dec) {
        switch (dec.type) {
            case INTEGER:
                p.add(new Instruction(Vx86.Inx.MOV, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, (int) dec.value, "int constant " + dec.value));
                break;
            case STRING:
                p.add(new Instruction(Vx86.Inx.MOV, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, p.newStringId((String) dec.value), "string constant \"" + dec.value + "\""));
                break;
            case FLOAT:
                p.add(new Instruction(Vx86.Inx.MOV, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, Float.floatToIntBits((float) dec.value), "float constant " + dec.value));
                break;
            case BOOLEAN:
                p.add(new Instruction(Vx86.Inx.MOV, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, ((boolean) dec.value) ? 1 : 0, "boolean constant " + dec.value));
                break;
            default:
                // not sure how we are here
                throw new IllegalArgumentException();
        }

    }

    @Override
    public void enterProgram(LOLcodeParser.ProgramContext ctx) {
        GlobalScopeDecoration dec = GlobalScopeDecoration.find(decs, ctx);

        // code to label the main entry point
        p.defLabel("$main");

        // print start message
        p.defLabel("$start");
        p.add(new Instruction(Vx86.Inx.MOV, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, p.newStringId("LOLCode main program started"), "string id of start message"));
        p.add(new Instruction(Vx86.Inx.PUSH, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.NONE, Vx86.Reg.NONE, 0, "push argument"));
        p.add(new Instruction(Vx86.Inx.CALL, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, p.getRuntimeAddress("output"), "call $output"));

    }

    @Override
    public void exitProgram(LOLcodeParser.ProgramContext ctx) {
        // print start message
        p.add(new Instruction(Vx86.Inx.MOV, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, p.newStringId("LOLCode main program finished"), "string id of end message"));
        p.add(new Instruction(Vx86.Inx.PUSH, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.NONE, Vx86.Reg.NONE, 0, "push argument"));
        p.add(new Instruction(Vx86.Inx.CALL, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, p.getRuntimeAddress("output"), "call $output"));

        // exit
        p.add(new Instruction(Vx86.Inx.RET, Vx86.Mode.NONE, Vx86.Reg.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, 0, "return to host"));
    }

    @Override
    public void enterFunc_decl(LOLcodeParser.Func_declContext ctx) {
        FunctionDecoration dec = FunctionDecoration.findContaining(decs, ctx);
        String name = ctx.getTokens(LOLcodeParser.IDENTIFIER).get(0).getText();

        // first, a jump, because in our running program, we want to skip this function as it is being declared
        p.add(new Instruction(Vx86.Inx.JMP, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, p.refLabel(name + "$after"), "skipping function \"" + name + "\""));

        // now the actual function itself, first a label and the frame
        p.defLabel(name);
        p.add(new Instruction(Vx86.Inx.PUSH, Vx86.Mode.REGISTER, Vx86.Reg.EBP, Vx86.Mode.NONE, Vx86.Reg.NONE, 0));
        p.add(new Instruction(Vx86.Inx.MOV, Vx86.Mode.REGISTER, Vx86.Reg.EBP, Vx86.Mode.REGISTER, Vx86.Reg.ESP, 0, "establish new frame pointer"));

        // now room for local variables
        p.add(new Instruction(Vx86.Inx.SUB, Vx86.Mode.REGISTER, Vx86.Reg.ESP, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, dec.getNumVariables() * 4, "room for locals"));
    }

    @Override
    public void exitFunc_decl(LOLcodeParser.Func_declContext ctx) {
        FunctionDecoration dec = FunctionDecoration.findContaining(decs, ctx);
        String name = ctx.getTokens(LOLcodeParser.IDENTIFIER).get(0).getText();

        p.defLabel(name + "$ret");
        p.add(new Instruction(Vx86.Inx.MOV, Vx86.Mode.REGISTER, Vx86.Reg.ESP, Vx86.Mode.REGISTER, Vx86.Reg.EBP, 0, "scratch locals"));
        p.add(new Instruction(Vx86.Inx.POP, Vx86.Mode.REGISTER, Vx86.Reg.EBP, Vx86.Mode.NONE, Vx86.Reg.NONE, 0, "restore previous frame pointer"));
        p.add(new Instruction(Vx86.Inx.RET, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, dec.numArgs * 4, "return from function"));
        p.defLabel(name + "$after");

    }

    @Override
    public void exitOutput_arg(LOLcodeParser.Output_argContext ctx) {
        // if this, an arg to the visible function, is not a string, convert it into one
        ExprDecoration dec = (ExprDecoration) decs.get(ctx);
        if (dec.type != Variable.Type.STRING) {
            switch (dec.type) {
                case INTEGER:
                    p.add(new Instruction(Vx86.Inx.PUSH, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.NONE, Vx86.Reg.NONE, 0, "push arg for conversion"));
                    p.add(new Instruction(Vx86.Inx.CALL, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, p.getRuntimeAddress("intToString"), "call $intToString"));
                    break;
                case FLOAT:
                    p.add(new Instruction(Vx86.Inx.PUSH, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.NONE, Vx86.Reg.NONE, 0, "push arg for conversion"));
                    p.add(new Instruction(Vx86.Inx.CALL, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, p.getRuntimeAddress("floatToString"), "call $floatToString"));
                    break;
                case BOOLEAN:
                    p.add(new Instruction(Vx86.Inx.PUSH, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.NONE, Vx86.Reg.NONE, 0, "push arg for conversion"));
                    p.add(new Instruction(Vx86.Inx.CALL, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, p.getRuntimeAddress("booleanToString"), "call $booleanToString"));
                    break;
                default:
                    break;
            }
        }
        p.add(new Instruction(Vx86.Inx.PUSH, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.NONE, Vx86.Reg.NONE, 0, "push arg for output"));

    }

    @Override
    public void exitNaked_arg(LOLcodeParser.Naked_argContext ctx) {
        p.add(new Instruction(Vx86.Inx.PUSH, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.NONE, Vx86.Reg.NONE, 0, "push arg"));
    }

    @Override
    public void exitFoldable_arg(LOLcodeParser.Foldable_argContext ctx) {
        if (generateForFoldedExpr(ctx)) {
            return;
        }
        p.add(new Instruction(Vx86.Inx.PUSH, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.NONE, Vx86.Reg.NONE, 0, "push arg"));
    }

    @Override
    public void enterOutput_args(LOLcodeParser.Output_argsContext ctx) {
    }

    @Override
    public void exitOutput_args(LOLcodeParser.Output_argsContext ctx) {
        OutputArgListDecoration dec = (OutputArgListDecoration) decs.get(ctx);
        // check number of args, if more than one, insert a concat
        if (dec.numArgs > 1) {
            p.add(new Instruction(Vx86.Inx.MOV, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, dec.numArgs, "#arguments to concat"));
            p.add(new Instruction(Vx86.Inx.PUSH, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.NONE, Vx86.Reg.NONE, 0));
            p.add(new Instruction(Vx86.Inx.CALL, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, p.getRuntimeAddress("concat"), "call $concat"));
            p.add(new Instruction(Vx86.Inx.PUSH, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.NONE, Vx86.Reg.NONE, 0, "push string for output"));
        }
    }

    @Override
    public void exitOutput(LOLcodeParser.OutputContext ctx) {
        p.add(new Instruction(Vx86.Inx.CALL, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, p.getRuntimeAddress("output"), "call $output"));
    }

    @Override
    public void enterLiteral_value(LOLcodeParser.Literal_valueContext ctx) {
        if (generateForFoldedExpr(ctx)) {
            return;
        }
        // put up the type, and any constant it might have
        ExprDecoration dec = (ExprDecoration) decs.get(ctx);
        loadLiteralFromDec(dec);
    }

    @Override
    public void exitDiff(LOLcodeParser.DiffContext ctx) {
        if (generateForFoldedExpr(ctx)) {
            return;
        }
        p.add(new Instruction(Vx86.Inx.POP, Vx86.Mode.REGISTER, Vx86.Reg.EBX, Vx86.Mode.NONE, Vx86.Reg.NONE, 0, "diff arg 1"));
        p.add(new Instruction(Vx86.Inx.POP, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.NONE, Vx86.Reg.NONE, 0, "diff arg 2"));
        p.add(new Instruction(Vx86.Inx.SUB, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.REGISTER, Vx86.Reg.EBX, 0));
    }

    @Override
    public void exitProduct(LOLcodeParser.ProductContext ctx) {
        if (generateForFoldedExpr(ctx)) {
            return;
        }
        p.add(new Instruction(Vx86.Inx.POP, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.NONE, Vx86.Reg.NONE, 0, "prod arg 1"));
        p.add(new Instruction(Vx86.Inx.POP, Vx86.Mode.REGISTER, Vx86.Reg.EBX, Vx86.Mode.NONE, Vx86.Reg.NONE, 0, "prod arg 2"));
        p.add(new Instruction(Vx86.Inx.MUL, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.REGISTER, Vx86.Reg.EBX, 0));
    }

    @Override
    public void exitVar_decl(LOLcodeParser.Var_declContext ctx) {
        Variable v = ScopeDecoration.findVar(decs, ctx, ctx.getToken(LOLcodeParser.IDENTIFIER, 0).getText());

        // does it have an initializer?
        if (ctx.getChildCount() != 7) {
            // if not, load 0 into eax
            p.add(new Instruction(Vx86.Inx.MOV, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, 0));
        }

        // store whatever is in EAX into the variable
        if (v.global) {
            p.add(new Instruction(Vx86.Inx.MOV, Vx86.Mode.MEMORY, Vx86.Reg.NONE, Vx86.Mode.REGISTER, Vx86.Reg.EAX, v.getOffset(), "initial value for \"" + v.name + "\""));
        } else {
            p.add(new Instruction(Vx86.Inx.MOV, Vx86.Mode.INDIRECT, Vx86.Reg.EBP, Vx86.Mode.REGISTER, Vx86.Reg.EAX, v.getOffset(), "initial value for \"" + v.name + "\""));
        }
    }

    @Override
    public void exitExpr(LOLcodeParser.ExprContext ctx) {
        generateForFoldedExpr(ctx);
    }

    @Override
    public void enterVar_ref(LOLcodeParser.Var_refContext ctx) {
        if (generateForFoldedExpr(ctx)) {
            return;
        }
        Variable v = ScopeDecoration.findVar(decs, ctx, ctx.getToken(LOLcodeParser.IDENTIFIER, 0).getText());
        if (v.global) {
            p.add(new Instruction(Vx86.Inx.MOV, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.MEMORY, Vx86.Reg.NONE, v.getOffset(), "load \"" + v.name + "\""));
        } else {
            p.add(new Instruction(Vx86.Inx.MOV, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.INDIRECT, Vx86.Reg.EBP, v.getOffset(), "load \"" + v.name + "\""));
        }
    }

    @Override
    public void exitInput(LOLcodeParser.InputContext ctx) {
        Variable v = ScopeDecoration.findVar(decs, ctx, ctx.getToken(LOLcodeParser.IDENTIFIER, 0).getText());
        if (v.type != Variable.Type.STRING) {
            Util.println("Type mismatch for GIMMEH: " + v.name);
            throw new IllegalArgumentException();
        }

        p.add(new Instruction(Vx86.Inx.CALL, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, p.getRuntimeAddress("input"), "call $input"));
        p.add(new Instruction(Vx86.Inx.MOV, Vx86.Mode.INDIRECT, Vx86.Reg.EBP, Vx86.Mode.REGISTER, Vx86.Reg.EAX, v.getOffset(), "store \"" + v.name + "\""));

    }

    @Override
    public void exitVar_assignment(LOLcodeParser.Var_assignmentContext ctx) {
        String name = ctx.getChild(0).getChild(0).getText();
        Variable v = ScopeDecoration.findVar(decs, ctx, name);
        if (v.global) {
            p.add(new Instruction(Vx86.Inx.MOV, Vx86.Mode.MEMORY, Vx86.Reg.NONE, Vx86.Mode.REGISTER, Vx86.Reg.EAX, v.getOffset(), "store \"" + v.name + "\""));
        } else {
            p.add(new Instruction(Vx86.Inx.MOV, Vx86.Mode.INDIRECT, Vx86.Reg.EBP, Vx86.Mode.REGISTER, Vx86.Reg.EAX, v.getOffset(), "store \"" + v.name + "\""));
        }
    }

    @Override
    public void exitFunc_call(LOLcodeParser.Func_callContext ctx) {
        String name = ctx.getChild(2).getText();
        FunctionDecoration dec = FunctionDecoration.findByName(decs, ctx, name);
        p.add(new Instruction(Vx86.Inx.CALL, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, p.refLabel(dec.name), "call \"" + dec.name + "\""));
    }

    @Override
    public void exitSame(LOLcodeParser.SameContext ctx) {
        // get type of operands
        ExprDecoration decArg1 = (ExprDecoration) decs.get(ctx.getChild(2));

        switch (decArg1.type) {
            case INTEGER:
                p.add(new Instruction(Vx86.Inx.POP, Vx86.Mode.REGISTER, Vx86.Reg.EBX, Vx86.Mode.NONE, Vx86.Reg.NONE, 0, "same arg 1"));
                p.add(new Instruction(Vx86.Inx.POP, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.NONE, Vx86.Reg.NONE, 0, "same arg 2"));
                p.add(new Instruction(Vx86.Inx.SUB, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.REGISTER, Vx86.Reg.EBX, 0, "subtract to compare"));
                p.add(new Instruction(Vx86.Inx.JZ, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, p.refTempLabel(1), "0 (equal)? go to inc to become 1 (true)"));
                p.add(new Instruction(Vx86.Inx.MOV, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, -1, "goes through inc and becomes 0 (false)"));
                p.defTempLabel(1);
                p.add(new Instruction(Vx86.Inx.INC, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.NONE, Vx86.Reg.NONE, 0));
        }
        p.resetTempLabels();
    }

    @Override
    public void enterInner_statement(LOLcodeParser.Inner_statementContext ctx) {
        p.add(new Instruction(Vx86.Inx.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, 0,
                "statement: " + ctx.getText().replace("\n", " ")));
    }

    @Override
    public void enterTop_level_statement(LOLcodeParser.Top_level_statementContext ctx) {
        p.add(new Instruction(Vx86.Inx.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, 0,
                "statement: " + ctx.getText().replace("\n", " ")));
    }

    @Override
    public void enterLoop_condition(LOLcodeParser.Loop_conditionContext ctx) {
        LoopDecoration dec = LoopDecoration.find(decs, ctx);
        // just provide a label to jump back to from end of loop
        p.defLabel("$loopstart$" + dec.name);
    }

    @Override
    public void exitLoop_condition(LOLcodeParser.Loop_conditionContext ctx) {
        LoopDecoration dec = LoopDecoration.find(decs, ctx);
        if (dec.ctxCondition != null) {
            // expr has already been generated
            // now just check for true/false and skip out of loop if false
            p.add(new Instruction(Vx86.Inx.CMP, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, 0, "done?"));
            if (dec.until) {
                p.add(new Instruction(Vx86.Inx.JNE, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, p.refLabel("$loopend$" + dec.name), "done, leave loop"));
            } else {
                p.add(new Instruction(Vx86.Inx.JE, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, p.refLabel("$loopend$" + dec.name), "done, leave loop"));
            }
        }
    }

    @Override
    public void exitLoop_end(LOLcodeParser.Loop_endContext ctx) {
        LoopDecoration dec = LoopDecoration.find(decs, ctx);
        // if there is an action on a variable, do it now
        if (dec.v != null) {
            if (dec.nerfin) {
                p.add(new Instruction(Vx86.Inx.DEC, Vx86.Mode.INDIRECT, Vx86.Reg.EBP, Vx86.Mode.NONE, Vx86.Reg.NONE, dec.v.getOffset(), "nerfin yr " + dec.v.name));
            } else {
                p.add(new Instruction(Vx86.Inx.INC, Vx86.Mode.INDIRECT, Vx86.Reg.EBP, Vx86.Mode.NONE, Vx86.Reg.NONE, dec.v.getOffset(), "uppin yr " + dec.v.name));
            }
        }

        // jump to start, and provide end label
        p.add(new Instruction(Vx86.Inx.JMP, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, p.refLabel("$loopstart$" + dec.name), "jump to loop start"));
        p.defLabel("$loopend$" + dec.name);
    }

    @Override
    public void exitReturn_statement(LOLcodeParser.Return_statementContext ctx) {
        FunctionDecoration dec = FunctionDecoration.findContaining(decs, ctx);
        p.add(new Instruction(Vx86.Inx.JMP, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, p.refLabel(dec.name + "$ret"), "jump to common return point"));
    }

    @Override
    public void exitStatement_affecting_it(LOLcodeParser.Statement_affecting_itContext ctx) {
        // after these statements, update the pseudo-variable "it"

        Variable it = ScopeDecoration.findVar(decs, ctx, "IT");
        p.add(new Instruction(Vx86.Inx.MOV, Vx86.Mode.MEMORY, Vx86.Reg.NONE, Vx86.Mode.REGISTER, Vx86.Reg.EAX, it.getOffset(), "update variable \"it\""));
    }

    @Override
    public void exitSum(LOLcodeParser.SumContext ctx) {
        if (generateForFoldedExpr(ctx)) {
            return;
        }
        p.add(new Instruction(Vx86.Inx.POP, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.NONE, Vx86.Reg.NONE, 0, "sum arg 1"));
        p.add(new Instruction(Vx86.Inx.POP, Vx86.Mode.REGISTER, Vx86.Reg.EBX, Vx86.Mode.NONE, Vx86.Reg.NONE, 0, "sum arg 2"));
        p.add(new Instruction(Vx86.Inx.ADD, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.REGISTER, Vx86.Reg.EBX, 0));

    }

    // blank instruction to copy
    //  p.add(new Instruction(Vx86.Inx.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, 0));
}
