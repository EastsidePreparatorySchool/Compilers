package recursivedescentparser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

//
// @author GM
//
// returns:
//   keywords (exact spelling!) as "@....."
//   numbers as "#...."
//   identifiers as "$...."
//   single-character arithmetic symbols as themselves
//
public class Tokenizer implements Iterable<String> {

    String s;
    ArrayList<String> als;
    int tokenIndex;
    int recLevel = 0;
    HashSet<String> keywords = new HashSet<>(Arrays.asList(new String[]{
        "Siri",
        "what", "is", "times",
        "the", "weather", "forecast",
        "remind", "me", "in", "minutes", "seconds", "hours",
        "I", "hate", "you"
    }));

    Tokenizer(String s) {
        this.s = s;
        this.als = new ArrayList<>();
        scan();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Tokens: ");
        for (int i = tokenIndex; i < als.size(); i++) {
            result.append(als.get(i));
            result.append(" ");
        }
        return result.toString();
    }

    final void scan() {
        boolean identifierInProgress = false;
        String identifier = "";

        boolean numberInProgress = false;
        boolean seenDecimalPoint = false;
        String number = "";

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '0':
                case '1':
                case '2':
                case '3':
                case '4':
                case '5':
                case '6':
                case '7':
                case '8':
                case '9':
                    if (identifierInProgress) {
                        identifier += c;
                        break;
                    }
                    if (numberInProgress && !seenDecimalPoint) {
                        number += c;
                        numberInProgress = true;
                        break;
                    }
                    if (numberInProgress && seenDecimalPoint) {
                        number += c;
                        break;
                    }
                    // number starts here
                    number = Character.toString(c);
                    numberInProgress = true;
                    seenDecimalPoint = false;
                    break;

                case '.':
                    if (identifierInProgress) {
                        identifier += c;
                        break;
                    }
                    if (numberInProgress) {
                        if (seenDecimalPoint) {
                            System.out.println("syntax error: number with more than one decimal point");
                            throw new IllegalArgumentException();
                        } else {
                            seenDecimalPoint = true;
                            number += c;
                        }
                    }
                    break;

                case '+':
                case '-':
                case '*':
                case '/':
                case '(':
                case ')':
                case ' ':
                case ',':
                case '=':
                case '>':
                case '<':
                    // first, finish numbers or identifiers in progress
                    if (numberInProgress) {
                        als.add("#" + number);
                        numberInProgress = false;
                        seenDecimalPoint = false;
                    } else if (identifierInProgress) {
                        if (keywords.contains(identifier)) {
                            als.add("@" + identifier);
                        } else {
                            als.add("$" + identifier);
                        }
                        identifierInProgress = false;
                    }

                    // handle >= and <=
                    if ((c == '>' || c == '<') && ((i + 1) < s.length())) {
                        if (s.charAt(i + 1) == '=') {
                            i++;
                            als.add(c + "=");
                            break;
                        }
                    }
                    // except for spaces, these are operators
                    if (c != ' ') {
                        als.add(Character.toString(c));
                    }
                    break;

                default:
                    if (numberInProgress) {
                        // we don't like characters after numbers
                        System.out.println("illegal character in number");
                        throw new IllegalArgumentException();
                    }
                    if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
                        if (!identifierInProgress) {
                            identifierInProgress = true;
                            identifier = "";
                        }
                        identifier += c;
                    } else {
                        System.out.println("illegal character " + c);
                        throw new IllegalArgumentException();
                    }
                    break;

            } //switch

        } //for
        if (numberInProgress) {
            als.add("#" + number);
        } else if (identifierInProgress) {
            if (keywords.contains(identifier)) {
                als.add("@" + identifier);
            } else {
                als.add("$" + identifier);
            }
        }

    }

    // state can be saved and restored
    public class State {

        int index = tokenIndex;
        int level = recLevel++;
    }

    // save iterator state
    public State saveState() {
        return new State();
    }

    // restore iterator state
    public void restoreState(State s) {
        tokenIndex = s.index;
        recLevel = s.level;
    }

    // this is a symmetrical method to restore, it can be used as a positive exit
    public void maintainState() {
        recLevel--;
    }

    //
    // return an iterator based on the member variable tokenIndex
    // this can be saved and restored (see above)
    // note that this implementation breaks Java conventions: 
    // two separate iterators will have the same state
    //
    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {
            @Override
            public boolean hasNext() {
                return (tokenIndex < als.size());
            }

            @Override
            public String next() {
                if (!hasNext()) {
                    return null;
                }
                return als.get(tokenIndex++);
            }
        };
    }

    
    //
    // you can call iterator methods directly on the tokenizer. 
    //
    
    public boolean hasNext() {
        return (tokenIndex < als.size());
    }

    public String next() {
        if (!hasNext()) {
            return null;
        }
        return als.get(tokenIndex++);
    }

    //
    // look at tokens without advancing iterator
    //
    public String peek(int index) {
        if (index + tokenIndex < als.size()) {
            return als.get(index + tokenIndex);
        }

        return null;
    }

    public String peek() {
        return peek(0);
    }

    //
    // match tries to match the next token, but does not consume it. Use next() after success.
    //
    
    boolean match(int index, String token) {
        String s = peek(index);
        if (s == null) {
            return false;
        }
        return s.startsWith(token);
    }

    // simpler method looks only at next token
    boolean match(String token) {
        return match(0, token);
    }

}
