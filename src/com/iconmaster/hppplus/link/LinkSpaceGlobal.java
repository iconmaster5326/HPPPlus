
package com.iconmaster.hppplus.link;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author iconmaster
 */
public class LinkSpaceGlobal extends LinkSpace implements Directable {
    private HashMap<String,FunctionPrototype> functions = new HashMap<>();
    
    public LinkSpaceGlobal() {
        super(new ArrayList<Statement>());
    }
    
    public void addFunction(FunctionPrototype func) {
        functions.put(func.getName(), func);
    }
}
