/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilers;

import java.io.IOException;
import vx86.Examples;
import vx86.Program;
import vx86.Util;
import vx86.Vx86;

/**
 *
 * @author gmein
 */
public class Compilers {

    public static void main(String[] args) throws IOException {
        Util.debugOn();

        Vx86 vm = new Vx86();

        /*
        //
        // first, assemble and run a small "increment" program
        //
        Program program1 = Examples.example_increment();
        program1.resolveLabels();
        program1.dump();

        vm.setup(program1);
        vm.run();

        System.out.println("");
        System.out.println("");

        
        //
        // now, assemble and run the more complicated recursive "factorial" program
        //
        Program program2 = Examples.example_factorial();
        program2.resolveLabels();
        program2.dump();

        vm.setup(program2);
        vm.run();

         */
        //
        // now, compile an actual lolcode example
        //
        Program p = Compiler.compile(Compilers.class.getResourceAsStream("example1.lol"));
        if (p == null) {
            return;
        }

        //vm.setup(p);
        //vm.run();
    }

}
