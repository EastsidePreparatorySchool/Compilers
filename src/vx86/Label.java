/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vx86;

import java.util.LinkedList;

/**
 *
 * @author gmein
 */
public class Label {

    public String name;
    public Integer line;
    public boolean defined;
    public LinkedList<Integer> refs;

    Label(String name) {
        this.name = name;
        this.defined = false;
        this.refs = new LinkedList<>();
    }

  
    
    public void define(int line) {
        if (defined) {
            System.err.println("label defined more than once: "+this.name+", "+this.line+", "+line);
        }
        
        this.defined = true;
        this.line = line;
    }

    public void addRef(int line) {
        refs.add(line);
    }
}
