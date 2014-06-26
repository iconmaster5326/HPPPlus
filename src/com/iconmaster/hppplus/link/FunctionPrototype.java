
package com.iconmaster.hppplus.link;

import com.iconmaster.hppplus.exception.HPPPlusException;
import com.iconmaster.hppplus.parse.Element;
import java.util.ArrayList;

/**
 *
 * @author iconmaster
 */
public class FunctionPrototype implements Directable {
    private final String name;
    private final ArrayList<VariablePrototype> vars;
    private final LinkSpace body;
    
    public FunctionPrototype(String name,ArrayList<VariablePrototype> vars,ArrayList<Element> block) throws HPPPlusException {
        this.name = name;
        this.vars = vars;
        
        body = (new Linker(block)).linkBlock(vars);
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
            s.append(var.toString().replace("\n", "\n\t"));
            s.append(" ");
        }
        s.append("\b");
        s.append("\n\tCode:");
        s.append(body.toString().replace("\n", "\n\t"));
        s.append("\n]");
        return s.toString();
    }
            
}
