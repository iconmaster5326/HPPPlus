
package com.iconmaster.hppplus.tokenize.element;

import com.iconmaster.hppplus.SourceRange;
import com.iconmaster.hppplus.tokenize.Token;
import com.iconmaster.hppplus.tokenize.Tokenizer;

/**
 *
 * @author iconmaster
 */
public class TokenReference extends SimpleMatchToken { 
    private final TokenWord matcher = new TokenWord();
    
    public TokenReference() {
        
    }

    public TokenReference(String word, SourceRange range) {
        this.content = word;
        this.range =range;
    }
    
    @Override
    public boolean matchFirstChar(Tokenizer tokenizer, char c) {
        if (c=='.') {
            tokenizer.advanceIndex();
            if (matcher.matchFirstChar(tokenizer, tokenizer.getChar())) {
                //tokenizer.advanceIndex(-1);
                return true;
            } else {
                tokenizer.advanceIndex(-1);
                return false;
            }
        } else {
            return false;
        }
    }
    
    @Override
    public boolean matchChar(Tokenizer tokenizer, char c) {
        return matcher.matchChar(tokenizer, c);
    }
    
    @Override
    public Token getToken(String word,SourceRange range) {
        return new TokenReference(word,range);
    }
    
    @Override
    public String getPrefix() {
        return "REF";
    }
}
