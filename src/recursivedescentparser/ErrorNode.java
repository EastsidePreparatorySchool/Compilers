package recursivedescentparser;

/**
 *
 * @author GM
 */
public class ErrorNode extends Node {

    String msg;
    ErrorNode(String msg) {
        super(Kind.ERROR);
        this.msg = msg;
    }

  
       @Override
    public String toString() {
        return "[error: " + msg + "]";
    }

    @Override
    public void dump(int recursionLevel) {
        System.out.println(Util.prefix(recursionLevel) + this);
    }


}
