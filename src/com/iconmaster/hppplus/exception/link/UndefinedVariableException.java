
package com.iconmaster.hppplus.exception.link;

import com.iconmaster.hppplus.exception.parse.*;
import com.iconmaster.hppplus.SourceRange;
import com.iconmaster.hppplus.exception.HPPPlusException;

/**
 *
 * @author iconmaster
 */
public class UndefinedVariableException extends HPPPlusException {
    
    public UndefinedVariableException(SourceRange range,Object thing) {
        super(range,"Undefined variable "+thing);
    }
    
}
