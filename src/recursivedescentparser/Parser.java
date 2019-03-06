package recursivedescentparser;

/**
 *
 * @author GM
 */
public class Parser {

    int recLevel;

    Node parse(String s) {
        Tokenizer tokens = new Tokenizer(s);
        ProgramNode parseTree = new ProgramNode();
        Node n;

        Util.debug = true;

        while (tokens.hasNext()) {
            // see whether the top level is a complaint
            n = matchComplaint(tokens);
            if (n != null) {
                parseTree.addRightChild(n);
                continue;
            }

            // if nothing was matched, then this is an error
            parseTree.addRightChild(new ErrorNode("parser: unexpected token " + tokens.next()));
        }

        return parseTree;
    }

    Node matchComplaint(Tokenizer tokens) {
        Util.debugOut("matching complaint", tokens);
        Tokenizer.State state = tokens.saveState();

        if (tokens.match("@Siri") && tokens.match(1, ",") 
                && tokens.match(2, "@I") && tokens.match(3, "@hate") && tokens.match(4, "@you")) {
            tokens.next();
            tokens.next();
            tokens.next();
            tokens.next();
            tokens.next();
            return new ComplaintNode("you suck");
        }

        Util.debugOut("no match for complaint", null);
        tokens.restoreState(state);
        return null;
    }

    
    
    
}
