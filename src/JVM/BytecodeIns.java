package JVM;

import org.objectweb.asm.MethodVisitor;

public interface BytecodeIns {
    void apply(MethodVisitor mv);
}
