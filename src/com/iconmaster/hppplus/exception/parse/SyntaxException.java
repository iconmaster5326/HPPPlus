
package com.iconmaster.hppplus.exception.parse;

import com.iconmaster.hppplus.SourceRange;
import com.iconmaster.hppplus.exception.HPPPlusException;
import com.iconmaster.hppplus.tokenize.Token;

/**
 *
 * @author iconmaster
 */
public class SyntaxException extends HPPPlusException {
    private String unknownChar;
    
    public SyntaxException(SourceRange range,Token token) {
        super(range,"Syntax error on token "+token);
    }
    
}
