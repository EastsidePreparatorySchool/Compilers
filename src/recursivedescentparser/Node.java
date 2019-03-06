package recursivedescentparser;

import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author GM
 */
public abstract class Node implements Iterable<Node> {

    protected LinkedList<Node> children;
    Kind kind;
    Node parent;

    public enum Kind {
        // parse node kinds for the example in lesson 1
        PROGRAM,
        COMPLAINT,
        WEATHER,
        MULTIPLICATION,
        CONSTANT,
        REMINDER, 
        ERROR
    }

    protected Node(Kind k) {
        kind = k;
        children = new LinkedList<>();
        parent = null;
    }

 

    @Override
    final public Iterator<Node> iterator() {
        return children.iterator();
    }

    final void addLeftChild(Node tn) {
        children.addFirst(tn);
    }

    final void addRightChild(Node tn) {
        children.addLast(tn);
    }

    void dump(int recursionLevel) {
        System.out.println(Util.prefix(recursionLevel) + "[" + kind.toString());
        children.forEach((tn) -> tn.dump(recursionLevel + 1));
        System.out.println(Util.prefix(recursionLevel) + "]");
    }

    void dump() {
        dump(0);
    }

}
