
package com.iconmaster.hppplus.exception.link;

import com.iconmaster.hppplus.exception.parse.*;
import com.iconmaster.hppplus.SourceRange;
import com.iconmaster.hppplus.exception.HPPPlusException;

/**
 *
 * @author iconmaster
 */
public class InvalidLocallyException extends HPPPlusException {
    
    public InvalidLocallyException(SourceRange range,String thing) {
        super(range,thing+" is only valid in the global context");
    }
    
}
