
package com.iconmaster.hppplus.tokenize.element;

import com.iconmaster.hppplus.SourceRange;
import com.iconmaster.hppplus.tokenize.Token;
import com.iconmaster.hppplus.tokenize.Tokenizer;

/**
 *
 * @author iconmaster
 */
public class TokenWhitespace extends SimpleMatchToken { 
    
    public TokenWhitespace() {
        
    }

    public TokenWhitespace(SourceRange range) {
        this.range = range;
    }
    
    @Override
    public boolean matchChar(Tokenizer tokenizer, char c) {
        return Character.isWhitespace(c) || c==',';
    }
    
    @Override
    public Token getToken(String word,SourceRange range) {
        return new TokenWhitespace(range);
        //return null;
    }
    
    @Override
    public String getPrefix() {
        return "SPACE";
    }
}
