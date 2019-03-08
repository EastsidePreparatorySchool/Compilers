package JVM;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.Queue;

public class BytecodeGenerator implements Opcodes {
    public byte[] generate(Queue<BytecodeIns> insQueue, String name, int numVars) {
        ClassWriter cw = new ClassWriter(0);
        MethodVisitor mv;
        cw.visit(V1_8, ACC_PUBLIC + ACC_SUPER, name,null,"java/lang/Object",null);

        mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null);
        for (BytecodeIns ins : insQueue) {
            ins.apply(mv);
        }
        mv.visitInsn(RETURN);
        // TODO: actually find these values
        mv.visitMaxs(100, numVars); // stack and # of variables
        mv.visitEnd();

        return cw.toByteArray();
    }
}
