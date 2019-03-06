/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilers;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import vx86.Instruction;
import vx86.MatchingObject;
import vx86.Program;
import vx86.Util;

/**
 *
 * @author gmein
 */
public class PeepholeEngine {

    public static class Pattern {

        LinkedList<Instruction> list;
        MatchingObject matchThis;
        MatchingObject matchThat;

        public Pattern() {
            list = new LinkedList<>();
        }

        public void add(Instruction x) {
            list.add(x);
        }

        public void clear() {
            matchThis = null;
            matchThat = null;
        }

        public boolean match(List<Instruction> sample, boolean trace) {
            Iterator<Instruction> p = this.list.iterator();
            Iterator<Instruction> s = sample.iterator();
            clear();

            while (p.hasNext() && s.hasNext()) {
                Instruction xp = p.next();
                Instruction xs = s.next();
              
                if (trace) {
                    Util.print(".");
                }
                if (!xp.matches(xs, matchThis, matchThat)) {
                    clear();
                    if (trace) {
                        Util.println("  no match");
                    }
                    return false;
                }

                if (matchThis == null) {
                    matchThis = xp.extractThis(xs);
                }
                if (matchThat == null) {
                    matchThat = xp.extractThat(xs);
                }
            }

            // whole pattern matched?
            if (!p.hasNext()) {
                if (trace) {
                    Util.println("  found");
                }
                return true;
            }

            if (trace) {
                Util.println("  no match");
            }
            matchThis = null;
            matchThat = null;
            return false;
        }

        public List<Instruction> fillFrom(Pattern src) {
            LinkedList<Instruction> list = new LinkedList<>();
            for (Instruction x : this.list) {
                list.add(x.fillThisAndThat(src.matchThis, src.matchThat));
            }
            
            return list;
        }

    }

    void process(Program p, PeepholeApplication pa) {
        for (int i = 0; i < p.size(); i++) {
            // peephole engine needs resolved labels to work, will resolve again later
            p.resolveLabels();
            if (pa.lineOfInterest == i) {
                Util.println("Watching matching of pattern " + pa.name + " at line " + pa.lineOfInterest);
            }
            pa.pattern.clear();
            if (pa.pattern.match(p.subList(i, i + Math.min(p.size() - i, pa.pattern.list.size())), i == pa.lineOfInterest)) {
                //Util.print("Match found for " + pa.name + " in " + i + ", replacing, variables: ");
                //Util.println(pa.pattern.matchThis + ", " + pa.pattern.matchThat);
                List<Instruction> filled = pa.substitution.fillFrom(pa.pattern);
                p.replace(i, pa.pattern.list.size(),filled);
                pa.pattern.clear();
            }
        }
    }

    void processAll(Program p, List<PeepholeApplication> lps) {
        int oldSize;
        do {
            oldSize = p.size();
            // process all patterns
            for (PeepholeApplication pa : lps) {
                process(p, pa);
                //Util.println("Done processing pattern " + pa.name);
                //p.dump();
            }
        } while (p.size() != oldSize); // until no more improvement
    }
}
