
package com.iconmaster.hppplus.tokenize.element;

import com.iconmaster.hppplus.SourceRange;
import com.iconmaster.hppplus.tokenize.Token;
import com.iconmaster.hppplus.tokenize.Tokenizer;

/**
 *
 * @author iconmaster
 */
public class TokenWord extends SimpleMatchToken { 
    
    public TokenWord() {
        
    }

    public TokenWord(String word, SourceRange range) {
        this.content = word;
        this.range =range;
    }
    
    @Override
    public boolean matchFirstChar(Tokenizer tokenizer, char c) {
        return Character.isAlphabetic(c) || c=='_';
    }
    
    @Override
    public boolean matchChar(Tokenizer tokenizer, char c) {
        return Character.isAlphabetic(c) || Character.isDigit(c) || c=='_';
    }
    
    @Override
    public Token getToken(String word,SourceRange range) {
        return new TokenWord(word,range);
    }
    
    @Override
    public String getPrefix() {
        return "WORD";
    }
}
