package recursivedescentparser;

/**
 *
 * @author GM
 */
public class Util {

    public static boolean debug = false;

    public static String prefix(int recLevel) {
        return "                                      ".substring(0, recLevel);
    }

    public static void debugOut(String s, Tokenizer tokens) {
        if (tokens != null) {
            System.out.println(prefix(tokens.recLevel) + s + ": " + tokens);
        } else {
            System.out.println(s );
        }
    }
    
 
}
