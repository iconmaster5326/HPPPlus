
package com.iconmaster.hppplus.exception.tokenize;

import com.iconmaster.hppplus.SourceRange;
import com.iconmaster.hppplus.exception.HPPPlusException;

/**
 *
 * @author iconmaster
 */
public class UnexpectedEOFException extends HPPPlusException {
    
    public UnexpectedEOFException(SourceRange range) {
        super(range,"Unexpected end of file");
    }
    
}
