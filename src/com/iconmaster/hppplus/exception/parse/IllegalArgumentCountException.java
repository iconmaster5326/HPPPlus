
package com.iconmaster.hppplus.exception.parse;

import com.iconmaster.hppplus.SourceRange;
import com.iconmaster.hppplus.exception.HPPPlusException;

/**
 *
 * @author iconmaster
 */
public class IllegalArgumentCountException extends HPPPlusException {
    
    public IllegalArgumentCountException(SourceRange range,Object func, int need, int got) {
        super(range,"Incorrect number of arguments to "+func+": "+need+" expected, got "+got);
    }
    
}
