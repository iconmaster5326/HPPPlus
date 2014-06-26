
package com.iconmaster.hppplus.link;

import com.iconmaster.hppplus.SourceRange;
import com.iconmaster.hppplus.exception.HPPPlusException;
import com.iconmaster.hppplus.exception.link.UndefinedVariableException;
import com.iconmaster.hppplus.exception.link.DuplicateVariableException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author iconmaster
 */
public class LinkSpace {
    protected HashMap<String,VariablePrototype> vars = new HashMap<>();
    protected ArrayList<Statement> statements = new ArrayList<>();
    
    public LinkSpace() {
        
    }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder("[LINKSPACE: ");
        s.append("\n\tVariables:");
        for (VariablePrototype var : this.vars.values()) {
            s.append("\n\t\t");
            s.append(var.toString().replace("\n", "\n\t"));
        }
        s.append("\n\tStatements:");
        for (Statement func : this.statements) {
            s.append("\n\t\t");
            s.append(func.toString().replace("\n", "\n\t"));
        }
        s.append("\n]");
        return s.toString();
    }

    public void addVar(VariablePrototype var) {
        vars.put(var.getName(),var);
    }
    
    public void addVarEnsureUnique(VariablePrototype var,SourceRange range) throws HPPPlusException {
        if (vars.get(var.getName())==null) {
            addVar(var);
        } else {
            throw new DuplicateVariableException(range,var);
        }
    }
    
    public void addVarEnsureDefined(VariablePrototype var,SourceRange range) throws HPPPlusException {
        if (vars.get(var.getName())!=null) {
            addVar(var);
        } else {
            throw new UndefinedVariableException(range,var);
        }
    }
    
    public void addStatement(Statement s) {
        statements.add(s);
    }
}
