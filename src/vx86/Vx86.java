package vx86;

import java.util.HashMap;

/**
 *
 * The Virtual x86 - Just enough x86 for our subset of LOLCODE
 *
 * @author gmein
 */
public class Vx86 {

    public static final int MEMSIZE = 1024 * 1024;
    public static final int INITIAL_ESP = 1024 * 1024;
    public static final int RUNTIME_BASE = 2 * 1024 * 1024;

    public static enum Reg {

        NONE,
        EAX,
        EBX,
        ECX,
        EDX,
        ESP,
        EBP,
        FLAGS,
        ANY, // for searching/matching
        THIS, // for searching/matching
        THAT      // for searching/matching
    }

    public static enum Mode {
        NONE,
        REGISTER,
        IMMEDIATE,
        MEMORY,
        INDIRECT,
        ANY, // for searching/matching
        THIS, // for searching/matching
        THAT        // for searching/matching
    }

    public static enum Inx {
        NONE,
        MOV,
        PUSH,
        POP,
        CALL,
        RET,
        ADD,
        SUB,
        MUL,
        DIV,
        CMP,
        INC,
        DEC,
        FADD,
        FSUB,
        FMUL,
        FDIV,
        FCMP,
        JZ,
        JNZ,
        JE,
        JNE,
        JC,
        JNC,
        JGE,
        JG,
        JL,
        JLE,
        JMP,
        ANY, // for searching/matching
        THIS, // for searching/matching
        THAT        // for searching/matching
    }

    //
    // machine state
    //
    public Program program;
    public StringMap strings;
    public RuntimeSupport runtime;

    public int eip;
    public HashMap<Reg, Integer> regs;
    public int[] memory;
    public boolean cf;
    public boolean zf;

    public String status() {
        return "["
                + regs.get(Reg.EAX) + ","
                + regs.get(Reg.EBX) + ","
                + regs.get(Reg.ECX) + ","
                + regs.get(Reg.EDX) + ","
                + "s:" + regs.get(Reg.ESP) + ","
                + "b:" + regs.get(Reg.EBP) + ","
                + (cf ? "c" : "") + (zf ? "z" : "")
                + "]";
    }

    public void setup(Program program) {
        this.regs = new HashMap<>();
        this.strings = program.strings;
        this.runtime = program.runtime;
        this.program = program;

        regs.put(Reg.EAX, 0);
        regs.put(Reg.EBX, 0);
        regs.put(Reg.ECX, 0);
        regs.put(Reg.EDX, 0);
        regs.put(Reg.EBP, 0);

        memory = new int[10000 / 4];
    }

    public void writeRegister(Reg reg, int value) {
        if (regs.get(reg) == null) {
            System.err.println("Vx86: unknown register " + (eip - 1));
            throw new IllegalArgumentException();
        }

        regs.put(reg, value);
    }

    public int readRegister(Reg reg) {
        Integer value = regs.get(reg);
        if (value == null) {
            System.err.println("Vx86: unknown register " + (eip - 1));
            throw new IllegalArgumentException();
        }

        return value;
    }

    public void writeMemory(int address, int val) {
        if (address % 4 != 0) {
            System.err.println("Vx86: unaligned memory acces at " + (eip - 1));
            throw new IllegalArgumentException();
        }
        memory[address / 4] = val;
    }

    public int readMemory(int address) {
        if (address % 4 != 0) {
            System.err.println("Vx86: unaligned memory access at " + (eip - 1));
            throw new IllegalArgumentException();
        }
        return memory[address / 4];
    }

    public int readSrc(Mode opSrc, Reg reg, int val) {
        switch (opSrc) {
            case REGISTER:
                return readRegister(reg);
            case IMMEDIATE:
                return val;
            case MEMORY:
                return readMemory(val);
            case INDIRECT:
                return readMemory(readRegister(reg) + val);
            default:
                System.err.println("Vx86: unknown opmode at " + (eip - 1));
                throw new IllegalArgumentException();
        }
    }

    public void writeDest(Mode opDest, Reg reg, int val, int data) {
        switch (opDest) {
            case REGISTER:
                writeRegister(reg, data);
                break;
            case MEMORY:
                writeMemory(val, data);
                break;
            case INDIRECT:
                writeMemory(readRegister(reg) + val, data);
                break;
            default:
                System.err.println("Vx86: unknown opmode at " + (eip - 1));
                throw new IllegalArgumentException();
        }
    }

    public void push(int value) {
        int esp = readRegister(Reg.ESP);
        esp -= 4;
        writeRegister(Reg.ESP, esp);
        writeDest(Mode.INDIRECT, Reg.ESP, 0, value);
    }

    public int pop() {
        int data = readSrc(Mode.INDIRECT, Reg.ESP, 0);
        int esp = readRegister(Reg.ESP);
        esp += 4;
        writeRegister(Reg.ESP, esp);
        return data;
    }

    public int peekStackVar(int offset) {
        int data = readSrc(Mode.INDIRECT, Reg.ESP, offset);
        return data;
    }

    public void run() {
        int src = 0;
        int dest = 0;
        int data = 0;
        int stack = 0;

        eip = 0;
        int preveip = -1;
        regs.put(Reg.ESP, memory.length * 4);

        while (eip >= 0 && eip < program.instructions.size()) {
            //dumpRegisters();
            Instruction ix = program.instructions.get(eip);
            if (ix.name != Inx.NONE) {
                if (eip != preveip + 1) {
                    Util.println("");
                }
                String seip = Util.rightJustify("" + eip, 4);
                Util.print(Util.leftJustify(seip + ":" + status(), 36));
                Util.print(Util.leftJustify(ix.toString(), 60));
            }
            preveip = eip;
            eip++;
            switch (ix.name) {
                // mov x y - copies y (either a constant value or the content of a register) into register x
                case MOV:
                    data = readSrc(ix.modeSrc, ix.regSrc, (Integer) ix.value);
                    writeDest(ix.modeDest, ix.regDest, (Integer) ix.value, data);
                    break;

                case PUSH:
                    data = readSrc(ix.modeDest, ix.regDest, (Integer) ix.value);
                    push(data);
                    break;

                case POP:
                    data = pop();
                    writeDest(ix.modeDest, ix.regDest, (Integer) ix.value, data);
                    break;

                case CALL:
                    push(eip);
                // intentional fall-through
                case JMP:
                    if (ix.modeDest == Mode.IMMEDIATE && (Integer) ix.value > 10000) {
                        runtime.invokeRoutine(this, (Integer) ix.value);
                        Util.print(Util.rightJustify(Util.ANSI_RESET, 100));

                    } else {
                        data = readSrc(ix.modeDest, ix.regDest, (Integer) ix.value);
                        eip += data;
                    }
                    break;

                case RET:
                    // is stack empty? return to 
                    stack = readRegister(Reg.ESP);
                    if (stack == memory.length * 4) {
                        Util.println("");
                        Util.println(Util.ANSI_GREEN + "VM: Program run finished" + Util.ANSI_RESET);
                        return;
                    }
                    eip = pop();
                    stack = readRegister(Reg.ESP);
                    data = readSrc(ix.modeDest, ix.regDest, (Integer) ix.value);
                    stack += data;
                    writeRegister(Reg.ESP, stack);
                    break;

                case ADD:
                    src = readSrc(ix.modeSrc, ix.regSrc, (Integer) ix.value);
                    dest = readSrc(ix.modeDest, ix.regDest, (Integer) ix.value);
                    data = dest + src;
                    zf = data == 0;
                    cf = src > dest;
                    writeDest(ix.modeDest, ix.regDest, (Integer) ix.value, data);
                    break;

                case SUB:
                    src = readSrc(ix.modeSrc, ix.regSrc, (Integer) ix.value);
                    dest = readSrc(ix.modeDest, ix.regDest, (Integer) ix.value);
                    data = dest - src;
                    zf = data == 0;
                    cf = src > dest;
                    writeDest(ix.modeDest, ix.regDest, (Integer) ix.value, data);
                    break;

                case CMP:
                    src = readSrc(ix.modeSrc, ix.regSrc, (Integer) ix.value);
                    dest = readSrc(ix.modeDest, ix.regDest, (Integer) ix.value);
                    data = dest - src;
                    zf = data == 0;
                    cf = src > dest;
                    break;

                case MUL:
                    src = readSrc(ix.modeSrc, ix.regSrc, (Integer) ix.value);
                    dest = readSrc(ix.modeDest, ix.regDest, (Integer) ix.value);
                    data = dest * src;
                    zf = data == 0;
                    cf = dest > data;
                    writeDest(ix.modeDest, ix.regDest, (Integer) ix.value, data);
                    break;

                case DIV:
                    src = readSrc(ix.modeSrc, ix.regSrc, (Integer) ix.value);
                    dest = readSrc(ix.modeDest, ix.regDest, (Integer) ix.value);
                    if (src == 0) {
                        System.err.println("Vx86: Divide by Zero at " + (eip - 1));
                        throw new IllegalArgumentException();
                    }
                    data = dest / src;
                    zf = data == 0;
                    cf = false;
                    writeDest(ix.modeDest, ix.regDest, (Integer) ix.value, data);
                    break;

                case INC:
                    dest = readSrc(ix.modeDest, ix.regDest, (Integer) ix.value);
                    dest++;
                    zf = dest == 0;
                    cf = dest == Integer.MIN_VALUE;
                    writeDest(ix.modeDest, ix.regDest, (Integer) ix.value, dest);
                    break;

                case DEC:
                    dest = readSrc(ix.modeDest, ix.regDest, (Integer) ix.value);
                    dest--;
                    zf = dest == 0;
                    cf = dest == Integer.MAX_VALUE;
                    writeDest(ix.modeDest, ix.regDest, (Integer) ix.value, dest);
                    break;

                case JZ:
                case JE:
                    if (zf) {
                        data = readSrc(ix.modeDest, ix.regDest, (Integer) ix.value);
                        eip += data;
                    }
                    break;

                case JNZ:
                case JNE:
                    if (!zf) {
                        data = readSrc(ix.modeDest, ix.regDest, (Integer) ix.value);
                        eip += data;
                    }
                    break;

                case JC:
                    if (cf) {
                        data = readSrc(ix.modeDest, ix.regDest, (Integer) ix.value);
                        eip = data;
                    }
                    break;

                case JNC:
                    if (!cf) {
                        data = readSrc(ix.modeDest, ix.regDest, (Integer) ix.value);
                        eip += data;
                    }
                    break;

                case JG:
                    if (!cf && !zf) {
                        data = readSrc(ix.modeDest, ix.regDest, (Integer) ix.value);
                        eip += data;
                    }
                    break;

                case JGE:
                    if (!cf || zf) {
                        data = readSrc(ix.modeDest, ix.regDest, (Integer) ix.value);
                        eip += data;
                    }
                    break;

                case JL:
                    if (cf && !zf) {
                        data = readSrc(ix.modeDest, ix.regDest, (Integer) ix.value);
                        eip += data;
                    }
                    break;

                case JLE:
                    if (cf || zf) {
                        data = readSrc(ix.modeDest, ix.regDest, (Integer) ix.value);
                        eip += data;
                    }
                    break;

                case NONE:
                    break;

                default:
                    System.err.println("Unknown instruction at " + (eip - 1));
                    throw new IllegalArgumentException();
            } //switch
            Util.println(status());
        } // while
        System.err.println("outside of program area at " + (eip - 1));
        throw new IllegalArgumentException();
    }
}
