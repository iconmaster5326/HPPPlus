
package com.iconmaster.hppplus.exception.parse;

import com.iconmaster.hppplus.SourceRange;
import com.iconmaster.hppplus.exception.HPPPlusException;

/**
 *
 * @author iconmaster
 */
public class EndExpectedException extends HPPPlusException {
    
    public EndExpectedException(SourceRange range,String begin) {
        super(range,"END expected after "+begin);
    }
    
}
