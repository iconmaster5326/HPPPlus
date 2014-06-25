
package com.iconmaster.hppplus.tokenize.element;

import com.iconmaster.hppplus.SourceRange;
import com.iconmaster.hppplus.tokenize.Token;
import com.iconmaster.hppplus.tokenize.Tokenizer;

/**
 *
 * @author iconmaster
 */
public class TokenSeperator extends SimpleMatchToken { 
    
    public TokenSeperator() {
        
    }

    public TokenSeperator(SourceRange range) {
        this.range = range;
    }
    
    @Override
    public boolean matchChar(Tokenizer tokenizer, char c) {
        return c==';';
    }
    
    @Override
    public Token getToken(String word,SourceRange range) {
        return new TokenSeperator(range);
    }
    
    @Override
    public String getPrefix() {
        return "SEP";
    }
}
