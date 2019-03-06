package recursivedescentparser;

/**
 *
 * @author GM
 */
public class ProgramNode extends Node {

    ProgramNode() {
        super(Kind.PROGRAM);
    }

    @Override
    public String toString() {
        return "[program]";
    }

    @Override
    public void dump(int recursionLevel) {
        System.out.println(Util.prefix(recursionLevel) + "[program");
        children.forEach(tn -> tn.dump(recursionLevel + 1));
        System.out.println(Util.prefix(recursionLevel) + "]");
    }

}
