package JVM;

import JVM.BytecodeIns;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class LoadLitIns implements BytecodeIns, Opcodes {
    Object val;

    public LoadLitIns(Object val) {
        this.val = val;
    }

    @Override
    public void apply(MethodVisitor mv) {
        mv.visitLdcInsn(val);
    }
}
