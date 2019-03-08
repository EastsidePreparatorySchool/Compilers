package JVM;

import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class AssignmentIns implements BytecodeIns, Opcodes {
    int index;

    public AssignmentIns(int index) {
        this.index = index;
    }

    @Override
    public void apply(MethodVisitor mv) {
        // assuming that the value we want is on top of the stack
        mv.visitVarInsn(ISTORE, index);
    }
}
