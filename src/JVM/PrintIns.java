package JVM;

import JVM.BytecodeIns;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class PrintIns implements BytecodeIns, Opcodes {
    int index;

    public PrintIns(int index) {
        this.index = index;
    }

    @Override
    public void apply(MethodVisitor mv) {
        mv.visitFieldInsn(GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        // only works for ints
        mv.visitVarInsn(ILOAD, index);
        mv.visitMethodInsn(INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);
   }
}
