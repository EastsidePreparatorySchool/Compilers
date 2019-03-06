/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vx86;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author gmein
 */
public class Program {

    ArrayList<Instruction> instructions;
    StringMap strings;
    RuntimeSupport runtime;
    HashMap<String, Label> labels;
    HashMap<Integer, Label> labelLines;
    int tempLabels;
    int maxTempLabel;

    public Program(ArrayList<Instruction> list, RuntimeSupport runtime) {
        this.instructions = list;
        this.strings = new StringMap();
        this.runtime = runtime;
        this.labels = new HashMap<>();
        this.labelLines = new HashMap<>();
        this.tempLabels = 0;
        this.maxTempLabel = 0;
    }

    public int newStringId(String s) {
        return strings.newStringId(s);
    }

    public int getRuntimeAddress(String routine) {
        return runtime.getRuntimeAddress(routine);
    }

    public void defLabel(String name) {
        Label l = labels.get(name);
        if (l == null) {
            l = new Label(name);
            labels.put(name, l);
        }
        l.define(this.instructions.size());
        labelLines.put(this.instructions.size(), l);
        //Util.println("label " + name + " defined at " + l.line);
    }

    public int refLabel(String name) {
        Label l = labels.get(name);
        if (l == null) {
            l = new Label(name);
            labels.put(name, l);
        }
        l.addRef(this.instructions.size());
        //Util.println("label " + name + " referenced at " + this.instructions.size());
        return 0;
    }

    public int refTempLabel(int i) {
        maxTempLabel = Math.max(maxTempLabel, i);
        String l = "$temp" + (tempLabels + i);
        return refLabel(l);
    }

    public void defTempLabel(int i) {
        maxTempLabel = Math.max(maxTempLabel, i);
        String l = "$temp" + (tempLabels + i);
        defLabel(l);
    }

    public void resetTempLabels() {
        tempLabels += maxTempLabel;
    }

    public boolean hasLabel(String name) {
        Label l = labels.get(name);
        if (l == null) {
            return false;
        }
        return l.defined;
    }

    private void updateLabelLinesIndex() {
        HashMap<Integer, Label> newLines = new HashMap<>();
        for (Label l : this.labelLines.values()) {
            newLines.put(l.line, l);
        }
        this.labelLines = newLines;
    }

    public void resolveLabels() {
        //Util.println("Resolving labels and refs:");

        for (Label l : labels.values()) {

            for (Integer refLine : l.refs) {
                if (l.defined) {
                    Instruction ix = instructions.get(refLine);
                    ix.value = l.line - (refLine + 1);
                    //Util.println("Updating reference to " + l.name + "(" + l.line + ") at " + refLine + ": rel " + ix.value);
                } else {
                    System.err.println("Reference to undefined label " + l.name + " at " + refLine);
                    throw new IllegalArgumentException();
                }
            }
        }
    }

    public void add(Instruction ix) {
        this.instructions.add(ix);
    }

    public Instruction get(int line) {
        return this.instructions.get(line);
    }

    public int size() {
        return this.instructions.size();
    }

    public List<Instruction> subList(int a, int b) {
        return instructions.subList(a, b);
    }

    public void addAll(int line, Collection<Instruction> code) {
        this.instructions.addAll(line, code);

        for (Label l : this.labels.values()) {
            if (l.line > line) {
                l.line += code.size();
            }
            LinkedList<Integer> newRefs = new LinkedList<>();
            for (int i : l.refs) {
                newRefs.add(i >= line ? i + code.size() : i);
            }

            l.refs = newRefs;
        }

        updateLabelLinesIndex();
    }

    public void replace(int line, int size, Collection<Instruction> newCode) {
        for (int i = 0; i < size; i++) {
            remove(line);
            //Util.println("removed " + 1 + " lines of code");
            //resolveLabels();
            //dump();
        }

        addAll(line, newCode);

        //Util.println("At: " + line + ", replaced " + size + " lines of code with " + newCode.size());
        resolveLabels();
        //dump();
    }

    public void remove(int line) {
        this.instructions.remove(line);

        for (Label l : this.labels.values()) {
            if (l.line > line) {
                l.line--;
            }
            LinkedList<Integer> newRefs = new LinkedList<>();
            for (int i : l.refs) {
                if (i != line) {
                    newRefs.add(i > line ? i - 1 : i);
                }
            }

            l.refs = newRefs;
        }
        updateLabelLinesIndex();
    }

    public void dumpLabelsAndRefs() {
        Util.println("Labels and Refs:");
        for (Label l : this.labels.values()) {
            Util.println("Label " + l.name + ": " + l.line);
            for (int i : l.refs) {
                Util.println("  ref in " + i);
            }
        }
    }

    public void dumpFragment(int start, List<Instruction> fragment) {
        for (int line = start; line < start + fragment.size(); line++) {
            Instruction x = fragment.get(line - start);
            Label l = labelLines.get(line);
            if (l != null) {
                Util.println(l.name + ":");
            }
            if (x.name == Vx86.Inx.NONE) {
                Util.println("; " + Util.ANSI_BLUE + x.comment + Util.ANSI_RESET);
                continue;
            }
            Util.println(Util.rightJustify("" + line, 4) + ": " + x);
            // extra line after ret
            if (x.name == Vx86.Inx.RET) {
                Util.println("");
            }
        }
    }

    public void dumpPartial(int start, int length) {
        dumpFragment(start, instructions.subList(start, start + length));
    }

    public void dump() {
        dumpPartial(0, instructions.size());
    }
}
