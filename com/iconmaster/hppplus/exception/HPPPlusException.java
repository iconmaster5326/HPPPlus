
package com.iconmaster.hppplus.exception;

import com.iconmaster.hppplus.SourceRange;

/**
 *
 * @author iconmaster
 */
public class HPPPlusException extends Exception {
    protected final SourceRange range;
    
    public HPPPlusException(SourceRange range) {
        this(range,"Unknown HPP+ exception");
    }
    
    public HPPPlusException(SourceRange range,String message) {
        super(message);
        this.range = range;
    }

    public SourceRange getRange() {
        return range;
    }
    
    @Override
    public String getMessage() {
        return "Error at "+range+": "+super.getMessage();
    }
}
