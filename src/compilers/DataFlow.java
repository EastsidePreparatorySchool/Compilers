/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import vx86.Instruction;
import vx86.Operand;
import vx86.Program;
import vx86.Vx86;

/**
 *
 * @author gmein
 */
public class DataFlow {

    // objectives:
    // 1) eliminate needless push/pop
    // 2) eliminate needless moving reg to reg
    // 3) further out eliminate needless update of local variables
    //
    HashMap<Operand, Operand> data;     // maps storage locations to source inputs - what really end up in what location
    LinkedList<Operand> stack;          // replica of the stack, what is really on it
    HashMap<Vx86.Reg, Operand> regs;    // during replacement code gen, keep track of what is in which register

    DataFlow() {
        data = new HashMap<>();
        stack = new LinkedList<>();
        regs = new HashMap<>();

    }

    private void push(Operand o) {
        stack.addFirst(o);
    }

    private Operand pop() {
        return stack.removeFirst();
    }

    public void clear() {
        data.clear();
        stack.clear();
        regs.clear();
    }

    int optimize(Program p, int start) {
        Operand dest;
        Operand src;
        Operand newSrc;
        Operand srcOrigin;
        int line;

        // go through list from start to end on until hard stop
        loop:
        for (line = start; start < p.size(); line++) {
            Instruction x = p.get(line);
            dest = new Operand(x.modeDest, x.regDest, x.value);
            src = new Operand(x.modeSrc, x.regSrc, x.value);

            switch (x.name) {
                case MOV:
                    if ((src.mode == Vx86.Mode.REGISTER && src.reg == Vx86.Reg.EBP)
                            || (dest.mode == Vx86.Mode.REGISTER && dest.reg == Vx86.Reg.EBP)) {
                        break loop;
                    }
                    // if the src came from somewhere else, substitute that
                    srcOrigin = data.getOrDefault(src, src);
                    data.put(dest, srcOrigin);
                    //Util.println("df: " + dest + ":" + srcOrigin);
                    break;
                case PUSH:
                    if (dest.mode == Vx86.Mode.REGISTER && dest.reg == Vx86.Reg.EBP) {
                        break loop;
                    }
                    src = new Operand(x.modeDest, x.regDest, x.value);
                    // if the src came from somewhere else, substitute that
                    srcOrigin = data.getOrDefault(src, src);
                    push(srcOrigin);
                    //Util.println("df: push" + src);
                    break;

                case POP:
                    if (dest.mode == Vx86.Mode.REGISTER && dest.reg == Vx86.Reg.EBP) {
                        // registers other than eax are not important once we come to the end of a function
                        data.remove(new Operand(Vx86.Reg.EBX));
                        data.remove(new Operand(Vx86.Reg.ECX));
                        data.remove(new Operand(Vx86.Reg.EDX));
                        break loop;
                    }
                    src = pop();
                    dest = new Operand(x.modeDest, x.regDest, x.value);
                    data.put(dest, src);
                    //Util.println("df: pop " + dest + ":" + src);
                    break;

                case CALL:
                    // registers are not important once we come to a call
                    data.remove(new Operand(Vx86.Reg.EAX));
                    data.remove(new Operand(Vx86.Reg.EBX));
                    data.remove(new Operand(Vx86.Reg.ECX));
                    data.remove(new Operand(Vx86.Reg.EDX));
                    break loop;

                default:
                    break loop;
            }
        }

        ArrayList<Instruction> newCode = new ArrayList<>();
        ArrayList<Entry<Operand, Operand>> state = new ArrayList(data.entrySet());

        // dump contents of stack and registers
        //Util.println("");
//        for (Entry<Operand, Operand> e : data.entrySet()) {
//            src = e.getValue();
//            dest = e.getKey();
//            //Util.println("df locations: " + dest + ": " + src);
//        }
//        for (int i = 0; i < stack.size(); i++) {
//            src = stack.get(stack.size() - i - 1);
//            //Util.println("df stack: [top+" + i + "]: " + src);
//        }
        //Util.println("");
        //
        // replacement code generation
        //
        // generate code to push values on stack from recorded data
        while (!stack.isEmpty()) {
            src = stack.removeLast();
            if (src.mode != Vx86.Mode.REGISTER) {
                // to do : find available register
                newCode.add(new Instruction(Vx86.Inx.MOV, Vx86.Mode.REGISTER, Vx86.Reg.EAX, src.mode, src.reg, src.value));
                regs.put(Vx86.Reg.EAX, src);
                src = new Operand(Vx86.Reg.EAX);
            }
            newCode.add(new Instruction(Vx86.Inx.PUSH, src.mode, src.reg, Vx86.Mode.NONE, Vx86.Reg.NONE, 0));
        }

        // generate code to load all regs and stack from recorded data
        // pass one - stuff that needs to go into memory
        for (Entry<Operand, Operand> e : data.entrySet()) {
            src = e.getValue();
            dest = e.getKey();
            // where will this be going?
            if (dest.mode != Vx86.Mode.REGISTER) {
                // value is going into memory. Does it come from a register?
                if (src.mode != Vx86.Mode.REGISTER) {
                    // no. does some register already contain it, though?
                    if (!regs.containsValue(src)) {
                        // no. load it into a (todo: an available) register, and name that the source
                        newCode.add(new Instruction(Vx86.Inx.MOV, Vx86.Mode.REGISTER, Vx86.Reg.EAX, src.mode, src.reg, src.value));
                        regs.put(Vx86.Reg.EAX, src);
                        src = new Operand(Vx86.Reg.EAX);
                    } else {
                        // yes. find the register that contains it, and name it the source
                        for (Vx86.Reg r : regs.keySet()) {
                            if (regs.get(r).equals(src)) {
                                src = new Operand(r);
                                break;
                            }
                        }
                    }
                }
                // at this point, we can be sure that src is not a memory access
                newCode.add(new Instruction(Vx86.Inx.MOV, dest.mode, dest.reg, src.mode, src.reg, dest.value));
            }
        }
        // pass two - registers themselves
        ArrayList<Entry<Operand, Operand>> list = new ArrayList<>(data.entrySet());
        list.sort((a,b)->a.getKey().toString().compareTo(b.getKey().toString())); // sort registers alphabetically
        for (Entry<Operand, Operand> e : list) {
            src = e.getValue();
            dest = e.getKey();
            if (dest.mode == Vx86.Mode.REGISTER
                    && !(src.mode == Vx86.Mode.REGISTER && src.reg == dest.reg)
                    && !src.equals(regs.get(dest.reg))) {
                newCode.add(new Instruction(Vx86.Inx.MOV, dest.mode, dest.reg, src.mode, src.reg, src.value));
            }
        }

        if (line - start > 0) {
            //Util.println("Old code:");
            //p.dumpPartial(start, line - start);
            //Util.println("Proposed new code:");
            //p.dumpFragment(start, newCode);

            // replace code
            if (newCode.size() < line - start) {
                p.replace(start, line - start, newCode);
                line = start + newCode.size();
                //Util.println(Util.ANSI_GREEN + "code improved!");
            } else {
                //Util.println("Code undisturbed.");
            }
        }
        //Util.print(Util.ANSI_RED + "skipping:");
        //p.dumpPartial(line, 1);
        //Util.println(Util.ANSI_RESET);
        return line - start;
    }

    //
    // to do:
    // 1) keep track of register values during code generation
    // 2) get rid of move eax, eax
    //
}
