
package com.iconmaster.hppplus.exception.tokenize;

import com.iconmaster.hppplus.SourceRange;
import com.iconmaster.hppplus.exception.HPPPlusException;

/**
 *
 * @author iconmaster
 */
public class UnknownSymbolException extends HPPPlusException {
    private String unknownChar;
    
    public UnknownSymbolException(SourceRange range, char unknownChar) {
        super(range,"Unknown symbol "+unknownChar);
    }
    
}
