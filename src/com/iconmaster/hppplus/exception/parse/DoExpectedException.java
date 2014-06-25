
package com.iconmaster.hppplus.exception.parse;

import com.iconmaster.hppplus.SourceRange;
import com.iconmaster.hppplus.exception.HPPPlusException;

/**
 *
 * @author iconmaster
 */
public class DoExpectedException extends HPPPlusException {
    
    public DoExpectedException(SourceRange range,String begin) {
        super(range,"DO expected after "+begin);
    }
    
}
