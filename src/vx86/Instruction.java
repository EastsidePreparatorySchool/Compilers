/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vx86;

import vx86.Vx86.Inx;
import vx86.Vx86.Mode;
import vx86.Vx86.Reg;

/**
 *
 * @author gmein
 */
public class Instruction {

    public Inx name;
    public Mode modeDest;
    public Mode modeSrc;
    public Reg regDest;
    public Reg regSrc;
    public Object value;
    public String comment;

    public Instruction(Inx name, Mode opDest, Reg dest, Mode opSrc, Reg src, int val) {
        this.name = name;
        this.modeSrc = opSrc;
        this.modeDest = opDest;
        this.regSrc = src;
        this.regDest = dest;
        this.value = val;
    }

    public Instruction(Inx name, Mode opDest, Reg dest, Mode opSrc, Reg src, Object val) {
        this.name = name;
        this.modeSrc = opSrc;
        this.modeDest = opDest;
        this.regSrc = src;
        this.regDest = dest;
        this.value = val;
    }

    public Instruction(Inx name, Mode opDest, Reg dest, Mode opSrc, Reg src, int val, String comment) {
        this.name = name;
        this.modeSrc = opSrc;
        this.modeDest = opDest;
        this.regSrc = src;
        this.regDest = dest;
        this.value = val;
        this.comment = comment;
    }

    public Instruction(Inx name, Mode opDest, Reg dest, Mode opSrc, Reg src, Object val, String comment) {
        this.name = name;
        this.modeSrc = opSrc;
        this.modeDest = opDest;
        this.regSrc = src;
        this.regDest = dest;
        this.value = val;
        this.comment = comment;
    }

    public void dump(Integer line) {
        if (name == Vx86.Inx.NONE) {
            Util.println("; " + Util.ANSI_BLUE + comment + Util.ANSI_RESET);
            line++;
            ;
        } else {
            Util.println(Util.rightJustify("" + line, 4) + ": " + this);
        }
        line++;
        // extra line after ret
        if (name == Vx86.Inx.RET) {
            Util.println("");

        }
    }

    public boolean validate() {
        if ((this.modeDest == Mode.MEMORY && this.modeSrc == Mode.MEMORY)
                || (this.modeDest == Mode.INDIRECT && this.modeSrc == Mode.INDIRECT)
                || (this.modeDest == Mode.INDIRECT && this.modeSrc == Mode.MEMORY)
                || (this.modeDest == Mode.MEMORY && this.modeSrc == Mode.INDIRECT)
                || (this.modeDest == Mode.NONE)
                || (this.modeDest == Mode.IMMEDIATE)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String strSrc = opToString(modeSrc, regSrc, value);
        String strDest = opToString(modeDest, regDest, value);

        String strOp = "";
        if (strSrc != null && strDest != null) {
            strOp = strDest + "," + strSrc;
        } else if (strDest != null) {
            strOp = strDest;
        } else if (strSrc != null) {
            strOp = strSrc;
        }

        return Util.leftJustify(name.toString(), 5)
                + Util.leftJustify(strOp, 20)
                + ((comment == null) ? "" : "; " + comment);
    }

    private String opToString(Mode opMode, Reg reg, Object val) {
        switch (opMode) {
            case REGISTER:
                return reg.toString();
            case IMMEDIATE:
                return val.toString();
            case MEMORY:
                return "[" + val + "]";
            case INDIRECT:
                int v = (Integer) val;
                return "[" + reg.toString() + (v != 0 ? (v < 0 ? "" : "+") + val.toString() : "") + "]";
            default:
        }
        return null;
    }

    // invoked on pattern instructions only, to match real instruction
    public boolean matches(Instruction other, MatchingObject thisOp, MatchingObject thatOp) {
        if ((this.name == other.name || this.name == Inx.ANY
                || (this.name == Inx.THIS&& (thisOp == null || thisOp.name == other.name))
                || (this.name == Inx.THAT&& (thatOp == null || thatOp.name == other.name)))
                && (this.modeDest == other.modeDest || this.modeDest == Mode.ANY
                || (this.modeDest == Mode.THIS && (thisOp == null || thisOp.mode == other.modeDest))
                || (this.modeDest == Mode.THAT && (thatOp == null || thatOp.mode == other.modeDest)))
                && (this.modeSrc == other.modeSrc || this.modeSrc == Mode.ANY
                || (this.modeSrc == Mode.THIS && (thisOp == null || thisOp.mode == other.modeSrc))
                || (this.modeSrc == Mode.THAT && (thatOp == null || thatOp.mode == other.modeSrc)))
                && (this.regDest == other.regDest || this.regDest == Reg.ANY
                || (this.regDest == Reg.THIS && (thisOp == null || thisOp.reg == other.regDest))
                || (this.regDest == Reg.THAT && (thatOp == null || thatOp.reg == other.regDest)))
                && (this.regSrc == other.regSrc || this.regSrc == Reg.ANY
                || (this.regSrc == Reg.THIS && (thisOp == null || thisOp.reg == other.regSrc))
                || (this.regSrc == Reg.THAT && (thatOp == null || thatOp.reg == other.regSrc)))
                && (this.value.equals(other.value) || this.value.equals("ANY") 
                || (this.value.equals("THIS") && (thisOp == null || other.value.equals(thisOp.value)))
                || (this.value.equals("THAT") && (thatOp == null || other.value.equals(thatOp.value))))) {
            return true;
        }
        return false;

    }

    //invoked on pattern instruction
    public MatchingObject extractThis(Instruction x) {
        if (this.name == Inx.THIS) {
            return new MatchingObject(x.name);
        }
        if (this.modeDest == Mode.THIS) {
            return new MatchingObject(x.modeDest, x.regDest, (Integer) x.value);
        }
        if (this.modeSrc == Mode.THIS) {
            return new MatchingObject(x.modeSrc, x.regSrc, (Integer) x.value);
        }
        if (this.regSrc == Reg.THIS) {
            return new MatchingObject(x.modeSrc, x.regSrc, (Integer) x.value);
        }
        if (this.regDest == Reg.THIS) {
            return new MatchingObject(x.modeDest, x.regDest, (Integer) x.value);
        }
        if (this.value.equals("THIS")) {
            if (x.modeDest == Mode.IMMEDIATE || x.modeDest == Mode.MEMORY || x.modeDest == Mode.INDIRECT) {
                return new MatchingObject(x.modeDest, x.regDest, (Integer) x.value);
            } else {
                return new MatchingObject(x.modeSrc, x.regSrc, (Integer) x.value);
            }
        }

        return null;
    }

    // invoked on pattern instruction
    public MatchingObject extractThat(Instruction x) {
        if (this.name == Inx.THAT) {
            return new MatchingObject(x.name);
        }
        if (this.modeDest == Mode.THAT) {
            return new MatchingObject(x.modeDest, x.regDest, (Integer) x.value);
        }
        if (this.modeSrc == Mode.THAT) {
            return new MatchingObject(x.modeSrc, x.regSrc, (Integer) x.value);
        }
        if (this.regSrc == Reg.THAT) {
            return new MatchingObject(x.modeSrc, x.regSrc, (Integer) x.value);
        }
        if (this.regDest == Reg.THAT) {
            return new MatchingObject(x.modeDest, x.regDest, (Integer) x.value);
        }
        if (this.value.equals("THAT")) {
            if (x.modeDest == Mode.IMMEDIATE || x.modeDest == Mode.MEMORY || x.modeDest == Mode.INDIRECT) {
                return new MatchingObject(x.modeDest, x.regDest, (Integer) x.value);
            } else {
                return new MatchingObject(x.modeSrc, x.regSrc, (Integer) x.value);
            }
        }

        return null;
    }

    public Instruction fillThisAndThat(MatchingObject thisObj, MatchingObject thatObj) {
        Instruction result = new Instruction(this.name, this.modeDest, this.regDest, this.modeSrc, this.regSrc, this.value, this.comment);
        // this
        if (this.name == Inx.THIS) {
            result.name =  thisObj.name;
        }
        if (this.modeDest == Mode.THIS) {
            Operand dest = (Operand) thisObj;
            result.modeDest = dest.mode;
        }
        if (this.modeSrc == Mode.THIS) {
            Operand src = (Operand) thisObj;
            result.modeSrc = src.mode;
        }
        if (this.regSrc == Reg.THIS) {
            Operand src = (Operand) thisObj;
            result.regSrc = src.reg;
        }
        if (this.regDest == Reg.THIS) {
            Operand dest = (Operand) thisObj;
            result.regDest = dest.reg;
        }
        if (this.value.equals("THIS")) {
            Operand v = (Operand) thisObj;
            result.value = v.value;
        }

        // that
        if (this.name == Inx.THAT) {
            result.name = thatObj.name;
        }
        if (this.modeDest == Mode.THAT) {
            Operand dest = (Operand) thatObj;
            result.modeDest = dest.mode;
        }
        if (this.modeSrc == Mode.THAT) {
            Operand src = (Operand) thatObj;
            result.modeSrc = src.mode;
        }
        if (this.regSrc == Reg.THAT) {
            Operand src = (Operand) thatObj;
            result.regSrc = src.reg;
        }
        if (this.regDest == Reg.THAT) {
            Operand dest = (Operand) thatObj;
            result.regDest = dest.reg;
        }
        if (this.value.equals("THAT")) {
            Operand v = (Operand) thatObj;
            result.value = v.value;
        }
        
        return result;
    }
}
