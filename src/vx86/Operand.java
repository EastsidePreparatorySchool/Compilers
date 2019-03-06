/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vx86;

import java.util.Objects;

/**
 *
 * @author gmein
 */
public class Operand {

    public Vx86.Mode mode;
    public Vx86.Reg reg;
    public int value;

    public Operand() {
        this.mode = Vx86.Mode.NONE;
        this.reg = Vx86.Reg.NONE;
        this.value = 0;
    }

    public Operand(Vx86.Mode mode, Vx86.Reg reg, int number) {
        this.mode = mode;
        this.reg = reg;
        this.value = mode == Vx86.Mode.REGISTER ? 0 : number;
    }

    public Operand(Vx86.Mode mode, Vx86.Reg reg, Object number) {
        this.mode = mode;
        this.reg = reg;
        this.value = mode == Vx86.Mode.REGISTER ? 0 : (Integer) number;
    }

    public Operand(Vx86.Reg reg) {
        this.mode = Vx86.Mode.REGISTER;
        this.reg = reg;
        this.value = 0;
    }

    public Operand(Vx86.Mode mode, int number) {
        this.mode = mode;
        this.reg = Vx86.Reg.NONE;
        this.value = number;
    }

    public Operand(int number) {
        this.mode = Vx86.Mode.IMMEDIATE;
        this.reg = Vx86.Reg.NONE;
        this.value = number;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof Operand)) {
            return false;
        }

        Operand o = (Operand) obj;
        return o.reg == reg && o.mode == mode && o.value == value;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.mode);
        hash = 97 * hash + Objects.hashCode(this.reg);
        hash = 97 * hash + this.value;
        return hash;
    }

    @Override
    public String toString() {
        switch (this.mode) {
            case REGISTER:
                return this.reg.toString();
            case IMMEDIATE:
                return Integer.toString(value);
            case MEMORY:
                return "[" + value + "]";
            case INDIRECT:
                return "[" + this.reg.toString() + "+" + value + "]";
            default:
                return "";
        }
    }
}
