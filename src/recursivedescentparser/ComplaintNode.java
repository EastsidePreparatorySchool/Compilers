package recursivedescentparser;

/**
 *
 * @author GM
 */
public class ComplaintNode extends Node {

    String msg;
    ComplaintNode(String msg) {
        super(Kind.COMPLAINT);
        this.msg = msg;
    }

  
       @Override
    public String toString() {
        return "[complaint: " + msg + "]";
    }

    @Override
    public void dump(int recursionLevel) {
        System.out.println(Util.prefix(recursionLevel) + this);
    }


}
