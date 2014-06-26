
package com.iconmaster.hppplus.link;

import java.util.HashMap;

/**
 *
 * @author iconmaster
 */
public class LinkSpaceGlobal extends LinkSpace implements Directable {
    private final HashMap<String,FunctionPrototype> functions = new HashMap<>();
    
    public LinkSpaceGlobal() {
        super();
    }
    
    public void addFunction(FunctionPrototype func) {
        functions.put(func.getName(), func);
    }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("[G. LINKSPACE: ");
        s.append("\n\tVariables:");
        for (VariablePrototype var : this.vars.values()) {
            s.append("\n\t\t");
            s.append(var.toString().replace("\n", "\n\t"));
        }
        s.append("\n\tFunctions:");
        for (FunctionPrototype func : this.functions.values()) {
            s.append("\n\t\t");
            s.append(func.toString().replace("\n", "\n\t"));
        }
        s.append("\n]");
        return s.toString();
    }
}
