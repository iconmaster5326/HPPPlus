
package com.iconmaster.hppplus.link;

import java.util.ArrayList;

/**
 *
 * @author iconmaster
 */
public class FunctionPrototype implements Directable {
    private String name;
    private ArrayList<VariablePrototype> vars;
    
    public FunctionPrototype(String name,ArrayList<VariablePrototype> vars) {
        this.name = name;
        this.vars = vars;
    }

    public String getName() {
        return name;
    }
            
}
