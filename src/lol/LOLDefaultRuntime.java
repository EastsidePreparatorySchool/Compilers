/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lol;

import java.util.Scanner;
import vx86.Util;
import vx86.Vx86;
import static vx86.Vx86.RUNTIME_BASE;
import vx86.RuntimeSupport;

/**
 *
 * @author gmein
 */
public class LOLDefaultRuntime implements RuntimeSupport {

    @Override
    public boolean isRuntimeRoutine(int addr) {
        return addr >= RUNTIME_BASE;
    }

    public static enum RuntimeEntry {
        none(0),
        output(1),
        input(2),
        intToString(3),
        intToFloat(4),
        floatToInt(5),
        floatToString(6),
        stringToInt(7),
        stringToFloat(8),
        concat(9),
        strcmp(10),
        booleanToString(11),
        stringToBoolean(12);

        private int addr;

        private RuntimeEntry(int v) {
            addr = v;
        }

        public int getAddress() {
            return RUNTIME_BASE + addr;
        }
    };

    private final RuntimeEntry[] routines = RuntimeEntry.values();

    @Override
    public int getRuntimeAddress(String routine) {
        return RuntimeEntry.valueOf(routine).getAddress();
    }

    private int argument(int n) {
        return n * 4;
    }

    @Override
    public void invokeRoutine(Vx86 vm, int addr) {
        int stack = 0;
        int args = 0;
        int id = 0;
        String s = null;

        addr -= RUNTIME_BASE;
        if (addr < 0 || addr >= routines.length) {
            System.err.println("invalid runtime call " + addr + " at " + (vm.eip - 1));
            throw new IllegalArgumentException();
        }

        switch (routines[addr]) {
            case output:
                //Util.println("");
                //Util.print(Util.ANSI_GREEN + "Vx86 output :");
                id = vm.peekStackVar(argument(1));
                s = vm.strings.getString(id);
                if (s == null) {
                    Util.println("Unknown string id " + id);
                    throw new IllegalArgumentException();
                }
                System.out.println();
                System.out.println(Util.ANSI_BLUE + s + Util.ANSI_RESET);
                vm.writeRegister(Vx86.Reg.EAX, id);
                args = 1;
                break;

            case input:
                Util.println("");
                Util.print(Util.ANSI_GREEN + "Program waiting for input:" + Util.ANSI_BLUE);
                System.out.println();
                Scanner sc = new Scanner(System.in);
                s = sc.nextLine();
                id = vm.strings.newStringId(s);
                vm.writeRegister(Vx86.Reg.EAX, id);
                args = 0;
                break;

            case intToString:
                //Util.println("");
                //Util.println(Util.ANSI_GREEN + "Vx86: $intToString called" + Util.ANSI_RESET);
                id = vm.strings.newStringId(Integer.toString(vm.peekStackVar(argument(1))));
                vm.writeRegister(Vx86.Reg.EAX, id);
                args = 1;
                break;

            case floatToString:
                //Util.println("");
                //Util.println(Util.ANSI_GREEN + "Vx86: $intToString called" + Util.ANSI_RESET);
                id = vm.strings.newStringId(Float.toString(Float.intBitsToFloat(vm.peekStackVar(argument(1)))));
                vm.writeRegister(Vx86.Reg.EAX, id);
                args = 1;
                break;

            case concat:
                //Util.println("");
                //Util.println(Util.ANSI_GREEN + "Vx86: $concat called" + Util.ANSI_RESET);
                args = vm.peekStackVar(argument(1));
                s = "";
                for (int i = args + 1; i > 1; i--) {
                    id = vm.peekStackVar(argument(i));
                    if (s == null) {
                        Util.println("Unknown string id " + id);
                        throw new IllegalArgumentException();
                    }
                    s += vm.strings.getString(id);
                }
                id = vm.strings.newStringId(s);
                vm.writeRegister(Vx86.Reg.EAX, id);
                args++; // had number of strings, too
                break;
            default:
                Util.println("");
                Util.println("invalid runtime call " + addr + "[" + routines[addr] + "] at " + (vm.eip - 1));
                throw new IllegalArgumentException();
        }

        // proper return, clearing stack
        stack = vm.readRegister(Vx86.Reg.ESP);
        if (stack == vm.memory.length) {
            return;
        }
        vm.eip = vm.readSrc(Vx86.Mode.INDIRECT, Vx86.Reg.ESP, 0);
        stack += 4 + args * 4;
        vm.writeRegister(Vx86.Reg.ESP, stack);
    }

}
