/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vx86;

import lol.LOLDefaultRuntime;
import java.util.ArrayList;

/**
 *
 * @author gmein
 */
public class Examples {

    public static Program example_increment() {
        ArrayList<Instruction> p = new ArrayList<>();
        RuntimeSupport runtime = new LOLDefaultRuntime();
        Program program = new Program(p, runtime);

        p.add(new Instruction(Vx86.Inx.PUSH, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, program.newStringId("Hello World! 3+1 is:"), "argument for increment"));
        p.add(new Instruction(Vx86.Inx.CALL, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, program.getRuntimeAddress("output"), "call $output"));
        p.add(new Instruction(Vx86.Inx.PUSH, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, 3, "argument for increment"));
        p.add(new Instruction(Vx86.Inx.CALL, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, program.refLabel("increment"), "call increment"));
        p.add(new Instruction(Vx86.Inx.PUSH, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.NONE, Vx86.Reg.NONE, 0, "push result"));
        p.add(new Instruction(Vx86.Inx.CALL, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, program.getRuntimeAddress("intToString"), "call $output"));
        p.add(new Instruction(Vx86.Inx.PUSH, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.NONE, Vx86.Reg.NONE, 0, "push result"));
        p.add(new Instruction(Vx86.Inx.CALL, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, program.getRuntimeAddress("output"), "call $output"));
        p.add(new Instruction(Vx86.Inx.RET, Vx86.Mode.NONE, Vx86.Reg.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, 0, "return to host"));

        program.defLabel("increment");
        p.add(new Instruction(Vx86.Inx.PUSH, Vx86.Mode.REGISTER, Vx86.Reg.EBP, Vx86.Mode.NONE, Vx86.Reg.NONE, 0));
        p.add(new Instruction(Vx86.Inx.MOV, Vx86.Mode.REGISTER, Vx86.Reg.EBP, Vx86.Mode.REGISTER, Vx86.Reg.ESP, 0, "establish new frame pointer"));
        p.add(new Instruction(Vx86.Inx.MOV, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.INDIRECT, Vx86.Reg.EBP, 8, "load first parameter"));
        p.add(new Instruction(Vx86.Inx.ADD, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, 1, "add 1"));
        p.add(new Instruction(Vx86.Inx.POP, Vx86.Mode.REGISTER, Vx86.Reg.EBP, Vx86.Mode.NONE, Vx86.Reg.NONE, 0, "restore previous frame pointer"));
        p.add(new Instruction(Vx86.Inx.RET, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, 4, "return from function"));

        return program;
    }

    public static Program example_factorial() {
        ArrayList<Instruction> p = new ArrayList<>();
        RuntimeSupport runtime = new LOLDefaultRuntime();
        Program program = new Program(p, runtime);

        p.add(new Instruction(Vx86.Inx.PUSH, Vx86.Mode.IMMEDIATE, Vx86.Reg.EAX, Vx86.Mode.NONE, Vx86.Reg.NONE, program.newStringId("Hello World! 10! is:")));
        p.add(new Instruction(Vx86.Inx.CALL, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, program.getRuntimeAddress("output"), "call $output"));
        p.add(new Instruction(Vx86.Inx.PUSH, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, 10, "main program calculates 10! and prints it"));
        p.add(new Instruction(Vx86.Inx.CALL, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, program.refLabel("factorial"), "call factorial"));
        p.add(new Instruction(Vx86.Inx.PUSH, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.NONE, Vx86.Reg.NONE, 0));
        p.add(new Instruction(Vx86.Inx.CALL, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, program.getRuntimeAddress("intToString"), "call $intToString"));
        p.add(new Instruction(Vx86.Inx.PUSH, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.NONE, Vx86.Reg.NONE, 0));
        p.add(new Instruction(Vx86.Inx.CALL, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, program.getRuntimeAddress("output"), "call $output"));
        p.add(new Instruction(Vx86.Inx.RET, Vx86.Mode.NONE, Vx86.Reg.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, 0));

        program.defLabel("factorial");
        p.add(new Instruction(Vx86.Inx.PUSH, Vx86.Mode.REGISTER, Vx86.Reg.EBP, Vx86.Mode.NONE, Vx86.Reg.NONE, 0, "factorial starts here"));
        p.add(new Instruction(Vx86.Inx.MOV, Vx86.Mode.REGISTER, Vx86.Reg.EBP, Vx86.Mode.REGISTER, Vx86.Reg.ESP, 0));
        p.add(new Instruction(Vx86.Inx.MOV, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.INDIRECT, Vx86.Reg.EBP, 8));
        p.add(new Instruction(Vx86.Inx.CMP, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, 1, "done?"));
        p.add(new Instruction(Vx86.Inx.JNE, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, program.refLabel("factorial$temp1")));
        p.add(new Instruction(Vx86.Inx.JMP, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, program.refLabel("factorial$ret")));

        program.defLabel("factorial$temp1");
        p.add(new Instruction(Vx86.Inx.MOV, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.INDIRECT, Vx86.Reg.EBP, 8));
        p.add(new Instruction(Vx86.Inx.SUB, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, 1));
        p.add(new Instruction(Vx86.Inx.PUSH, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.NONE, Vx86.Reg.NONE, 0));
        p.add(new Instruction(Vx86.Inx.CALL, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, program.refLabel("factorial"), "recurse"));
        p.add(new Instruction(Vx86.Inx.MUL, Vx86.Mode.REGISTER, Vx86.Reg.EAX, Vx86.Mode.INDIRECT, Vx86.Reg.EBP, 8));

        program.defLabel("factorial$ret");
        p.add(new Instruction(Vx86.Inx.POP, Vx86.Mode.REGISTER, Vx86.Reg.EBP, Vx86.Mode.NONE, Vx86.Reg.NONE, 0));
        p.add(new Instruction(Vx86.Inx.RET, Vx86.Mode.IMMEDIATE, Vx86.Reg.NONE, Vx86.Mode.NONE, Vx86.Reg.NONE, 4));

        return program;
    }

}
