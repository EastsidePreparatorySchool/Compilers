/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import lol.LOLcodeLexer;
import lol.LOLcodeParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import vx86.Program;
import vx86.Util;

/**
 *
 * @author gmein
 */
public class Compiler {

    public static boolean dataflow = true;

    static Program compile(InputStream is) throws IOException {
        int length;

        CharStream cs = CharStreams.fromStream(is);
        LOLcodeLexer lexer = new LOLcodeLexer(cs);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        LOLcodeParser parser = new LOLcodeParser(tokens);

        LOLcodeParser.ProgramContext tree = Parser.parse(lexer, parser, tokens);
        if (tree == null) {
            return null;
        }
        Util.println("Parsing successful.");

        // dump parse tree
//        Dumper dumper = new Dumper();
//        ParseTreeWalker.DEFAULT.walk(dumper, tree);
//        System.out.println("\n\n");
        // decorate tree
        StaticAnalyzer checker = new StaticAnalyzer();
        ParseTreeWalker.DEFAULT.walk(checker, tree);
        if (checker.errors != 0) {
            return null;
        }
        Util.println("Static analysis successful.");

        ConstantFolder cf = new ConstantFolder(checker.decs);
        ParseTreeWalker.DEFAULT.walk(cf, tree);
        Util.println("Constant folding complete, folded " + cf.total + " expressions");

        // now create a code generator, passing on decorations from checker phase
        CodeGenerator cgen = new CodeGenerator(checker.decs);
        ParseTreeWalker.DEFAULT.walk(cgen, tree);
        Program p = cgen.p;
        if (p == null) {
            return null;
        }
        length = p.size();
        Util.println("Code generation successful, size: " + length + " lines.");
        p.resolveLabels();
        //p.dump();

        if (dataflow) {
            Util.println("Starting data flow analysis:");
            DataFlow df = new DataFlow();
            for (int i = 0; i < p.size(); i++) {
                p.resolveLabels();
                df.clear();
                i += df.optimize(p, i);
            }
            Util.println("Dataflow analysis done, reduced code from " + length + " to " + p.size() + " instructions");
            p.resolveLabels();
//        p.dump();
        }

        Util.println("Starting Peephole optimizations ... ");
        length = p.size();
        PeepholeEngine pe = new PeepholeEngine();
        List<PeepholeApplication> patterns = PeepholeApplication.generateAllPatterns();
        pe.processAll(p, patterns);
        Util.println("Peephole optimization done, reduced code from " + length + " to " + p.size() + " lines");

        // resolve again as peephole optimizations might have shifted jump targets
        p.resolveLabels();
        p.dump();
        return p;
    }
}
