
package com.iconmaster.hppplus.tokenize.element;

import com.iconmaster.hppplus.SourceRange;
import com.iconmaster.hppplus.tokenize.Token;
import com.iconmaster.hppplus.tokenize.Tokenizer;

/**
 *
 * @author iconmaster
 */
public class TokenNumber extends SimpleMatchToken { 
    
    public TokenNumber() {
        
    }

    public TokenNumber(String word, SourceRange range) {
        this.content = word;
        this.range =range;
    }
    
    @Override
    public boolean matchChar(Tokenizer tokenizer, char c) {
        return Character.isDigit(c) || c=='.';
    }
    
    @Override
    public Token getToken(String word,SourceRange range) {
        return new TokenNumber(word,range);
    }
    
    @Override
    public String getPrefix() {
        return "NUM";
    }
}
