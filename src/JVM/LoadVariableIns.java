package JVM;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class LoadVariableIns implements BytecodeIns, Opcodes {
    int index;

    public LoadVariableIns(int index) {
        this.index = index;
    }

    @Override
    public void apply(MethodVisitor mv) {
        mv.visitVarInsn(ILOAD, index);
    }
}
