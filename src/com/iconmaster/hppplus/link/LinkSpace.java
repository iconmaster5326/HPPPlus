
package com.iconmaster.hppplus.link;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author iconmaster
 */
public class LinkSpace {
    protected HashMap<String,VariablePrototype> vars = new HashMap<>();
    protected ArrayList<Statement> statements;
    
    public LinkSpace(ArrayList<Statement> statements) {
        this.statements = statements;
    }
}
