
package com.iconmaster.hppplus.tokenize.element;

import com.iconmaster.hppplus.SourceRange;
import com.iconmaster.hppplus.tokenize.Token;
import com.iconmaster.hppplus.tokenize.Tokenizer;

/**
 *
 * @author iconmaster
 */
public class TokenString extends TotalMatchToken { 
    
    public TokenString() {
        
    }

    public TokenString(String word, SourceRange range) {
        this.content = word;
        this.range =range;
    }
    
    @Override
    public boolean matchFirstChar(Tokenizer tokenizer, char c) {
        return c=='"';
    }
    
    @Override
    public boolean matchLastChar(Tokenizer tokenizer, char c) {
        return c=='"';
    }
    
    @Override
    public Token getToken(String word,SourceRange range) {
        return new TokenString(word,range);
    }
    
    @Override
    public String getPrefix() {
        return "STRING";
    }
}
