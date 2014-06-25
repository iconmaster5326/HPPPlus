
package com.iconmaster.hppplus.tokenize.element;

import com.iconmaster.hppplus.SourceRange;
import com.iconmaster.hppplus.tokenize.Token;
import com.iconmaster.hppplus.tokenize.Tokenizer;
import java.util.ArrayList;

/**
 *
 * @author iconmaster
 */
public class TokenIndex extends RecursiveMatchToken { 
    
    public TokenIndex() {
        
    }

    public TokenIndex(ArrayList<Token> tokens, SourceRange range) {
        this.tokens = tokens;
        this.range =range;
    }
    
    @Override
    public boolean matchFirstChar(Tokenizer tokenizer,char c) {
        return c=='[';
    }
    
    @Override
    public boolean matchLastChar(Tokenizer tokenizer,char c) {
        return c==']';
    }
    
    @Override
    public Token getToken(ArrayList<Token> tokens,SourceRange range) {
        return new TokenIndex(tokens,range);
    }
    
    @Override
    public String getPrefix() {
        return "INDEX";
    }
}
