
package com.iconmaster.hppplus.link;

import java.util.ArrayList;

/**
 *
 * @author iconmaster
 */
public class FunctionPrototype implements Directable {
    private final String name;
    private final ArrayList<VariablePrototype> vars;
    
    public FunctionPrototype(String name,ArrayList<VariablePrototype> vars) {
        this.name = name;
        this.vars = vars;
    }

    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("[");
        s.append(name);
        s.append(": ");
        for (VariablePrototype var : this.vars) {
            s.append(var);
            s.append(" ");
        }
        s.append("\b");
        s.append("]");
        return s.toString();
    }
            
}
