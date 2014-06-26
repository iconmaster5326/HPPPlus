
package com.iconmaster.hppplus.exception.parse;

import com.iconmaster.hppplus.Parseable;
import com.iconmaster.hppplus.SourceRange;
import com.iconmaster.hppplus.exception.HPPPlusException;

/**
 *
 * @author iconmaster
 */
public class SyntaxException extends HPPPlusException {
    private String unknownChar;
    
    public SyntaxException(SourceRange range,Parseable token) {
        super(range,"Syntax error on token "+token);
    }
    
}
