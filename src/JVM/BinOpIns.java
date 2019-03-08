package JVM;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import lol.LOLcodeParser;

public class BinOpIns implements BytecodeIns, Opcodes {

    LOLcodeParser.SumContext op;

    public BinOpIns(LOLcodeParser.SumContext op) {
        this.op = op;
    }

    @Override
    public void apply(MethodVisitor mv) {
        mv.visitInsn(IADD);
    }
}
