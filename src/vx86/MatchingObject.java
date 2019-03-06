/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vx86;

import vx86.Vx86.Inx;

/**
 *
 * @author gmein
 */
public class MatchingObject extends Operand {

    Inx name;

    public MatchingObject() {
        super();
        this.name = Inx.NONE;
    }

    public MatchingObject(Inx name) {
        this.name = name;
    }

    public MatchingObject(Vx86.Mode mode, Vx86.Reg reg, int number) {
        this.mode = mode;
        this.reg = reg;
        this.value = mode == Vx86.Mode.REGISTER ? 0 : number;
        this.name = Inx.NONE;
    }

    public MatchingObject(Vx86.Mode mode, Vx86.Reg reg, Object number) {
        this.mode = mode;
        this.reg = reg;
        this.value = mode == Vx86.Mode.REGISTER ? 0 : (Integer) number;
        this.name = Inx.NONE;
    }

    public MatchingObject(Vx86.Reg reg) {
        this.mode = Vx86.Mode.REGISTER;
        this.reg = reg;
        this.value = 0;
        this.name = Inx.NONE;
    }

    public MatchingObject(Vx86.Mode mode, int number) {
        this.mode = mode;
        this.reg = Vx86.Reg.NONE;
        this.value = number;
        this.name = Inx.NONE;
    }

    public MatchingObject(int number) {
        this.mode = Vx86.Mode.IMMEDIATE;
        this.reg = Vx86.Reg.NONE;
        this.value = number;
        this.name = Inx.NONE;
    }

    @Override
    public String toString() {
       if (this.name != Inx.NONE) {
           return "Inx."+this.name.toString();
       } else {
           return super.toString();
       }
    }
}
