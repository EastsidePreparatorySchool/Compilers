package compilers;

import JVM.PrintIns;
import JVM.LoadLitIns;
import JVM.DeclarationIns;
import JVM.BytecodeIns;
import JVM.AssignmentIns;
import JVM.BytecodeGenerator;
import JVM.LoadVariableIns;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import lol.LOLcodeBaseListener;
import lol.LOLcodeParser;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

public class CodeGeneratorJVM extends LOLcodeBaseListener {

    HashMap<String, Integer> globals = new HashMap<>(); // name to register
    Queue<BytecodeIns> instructions = new ArrayDeque<>();
    int currentIndex = 0;

    public void save(String fileName) {
        String baseName = fileName.split("\\.")[0];
        final byte[] byteCode = new BytecodeGenerator().generate(instructions, baseName, currentIndex);
        fileName = baseName + ".class";
        System.out.println("saving " + instructions.size() + " instructions to file "+fileName);
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            OutputStream os = new FileOutputStream(file);
            os.write(byteCode);
            os.flush();
            os.close();
        } catch (Exception e) {
            System.out.println("Exception writing jvm class file " + fileName + ": " + e);
        }
    }

    @Override
    public void exitVar_decl(LOLcodeParser.Var_declContext ctx) {
        String name = ctx.getText();
        globals.put(name, currentIndex);
        instructions.add(new DeclarationIns(currentIndex));
    }

//    @Override
//    public void exitUnary_op_call(LOLCodeParser.Unary_op_callContext ctx) {
//        if (ctx.unary_op().VISIBLE() != null) {
//            int index = globals.get(ctx.expr().getText()); // TODO: only works for variables
//            instructions.add(new PrintIns(index));
//        }
//    }
//
//    @Override
//    public void exitAssignment(LOLcodeParser.AssignmentContext ctx) {
//        int index = globals.get(ctx.variable().getText());
//        instructions.add(new AssignmentIns(index));
//    }
//
//    @Override
//    public void exitNumber(LOLCodeParser.NumberContext ctx) {
//        instructions.add(new LoadLitIns(Integer.valueOf(ctx.getText())));
//    }
//
//    @Override
//    public void exitBin_op_call(LOLCodeParser.Bin_op_callContext ctx) {
//        instructions.add(new BinOpIns(ctx.bin_op()));
//    }
//
    @Override
    public void exitAtom(LOLcodeParser.AtomContext ctx) {
        if (ctx.getText() != null) {
            int index = globals.get(ctx.getText());
            instructions.add(new LoadVariableIns(index));
        }
    }
}
