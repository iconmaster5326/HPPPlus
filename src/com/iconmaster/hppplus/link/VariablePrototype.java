
package com.iconmaster.hppplus.link;

/**
 *
 * @author iconmaster
 */
public class VariablePrototype implements Directable {
    private final String name;
    
    public VariablePrototype(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return "["+name+"]";
    }

    String getName() {
        return name;
    }
}
