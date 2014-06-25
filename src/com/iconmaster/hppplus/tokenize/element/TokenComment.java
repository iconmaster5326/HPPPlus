
package com.iconmaster.hppplus.tokenize.element;

import com.iconmaster.hppplus.SourceRange;
import com.iconmaster.hppplus.tokenize.Token;
import com.iconmaster.hppplus.tokenize.Tokenizer;

/**
 *
 * @author iconmaster
 */
public class TokenComment extends TotalMatchToken { 
    
    public TokenComment() {
        
    }

    public TokenComment(String word, SourceRange range) {
        this.content = word;
        this.range =range;
    }
    
    @Override
    public boolean matchFirstChar(Tokenizer tokenizer, char c) {
        if (c=='/') {
            tokenizer.advanceIndex();
            if (tokenizer.getChar()=='/') {
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
    public boolean matchLastChar(Tokenizer tokenizer, char c) {
        return c=='\n';
    }
    
    @Override
    public Token getToken(String word,SourceRange range) {
        return new TokenComment(word,range);
    }
    
    @Override
    public String getPrefix() {
        return "COMMENT";
    }
}
