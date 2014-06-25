
package com.iconmaster.hppplus.exception.parse;

import com.iconmaster.hppplus.SourceRange;
import com.iconmaster.hppplus.exception.HPPPlusException;

/**
 *
 * @author iconmaster
 */
public class ThenExpectedException extends HPPPlusException {
    
    public ThenExpectedException(SourceRange range,String begin) {
        super(range,"THEN expected after "+begin);
    }
    
}
