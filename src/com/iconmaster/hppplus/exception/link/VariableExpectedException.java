
package com.iconmaster.hppplus.exception.link;

import com.iconmaster.hppplus.exception.parse.*;
import com.iconmaster.hppplus.SourceRange;
import com.iconmaster.hppplus.exception.HPPPlusException;

/**
 *
 * @author iconmaster
 */
public class VariableExpectedException extends HPPPlusException {
    
    public VariableExpectedException(SourceRange range,Object thing) {
        super(range,"Variable name expected, got "+thing);
    }
    
}
