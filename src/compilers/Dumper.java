/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compilers;

import lol.LOLcodeBaseListener;
import lol.LOLcodeParser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import vx86.Util;

/**
 *
 * @author gmein
 */
public class Dumper extends LOLcodeBaseListener {

 
    int level;
    ParseTree parent;


    private String spaces(int level) {
        String s = "                                                                                                             ";
        if (s.length() > 2 * level) {
            return s.substring(0, 2 * level);
        } else {
            return s;
        }
    }

    @Override
    public void enterEveryRule(ParserRuleContext ctx) {
        if (ctx.getClass() == LOLcodeParser.SeparatorContext.class) {
            return;
        }
        System.out.println("");
        System.out.print(spaces(level));
        String s = ctx.getClass().getName();
        System.out.print(s.substring(s.indexOf("$") + 1));
        level++;
        parent = ctx;
    }

    @Override
    public void exitEveryRule(ParserRuleContext ctx) {
        if (ctx.getClass() == LOLcodeParser.SeparatorContext.class) {
            return;
        }
        level--;
        System.out.println("");
        System.out.print(spaces(level));
        String s = ctx.getClass().getName();
        System.out.print("/" + s.substring(s.indexOf("$") + 1));
        parent = ctx.getParent();
    }

    @Override
    public void visitTerminal(TerminalNode node) {
        if (node.getParent().getClass() == LOLcodeParser.SeparatorContext.class) {
            return;
        }
        if (parent != node.getParent()) {
            System.out.println(spaces(level));
            parent = node.getParent();
        }
        System.out.print(" " + Util.ANSI_BLUE + node.getText() + Util.ANSI_RESET);
    }

}
