
package com.iconmaster.hppplus.tokenize.element;

import com.iconmaster.hppplus.SourceRange;
import com.iconmaster.hppplus.tokenize.Token;
import com.iconmaster.hppplus.tokenize.Tokenizer;

/**
 *
 * @author iconmaster
 */
public class TokenOperator extends SimpleMatchToken { 
    private static final char[] symbols = new char[] {'+','-','=','/','*','^','%','|','&','<','>','!','#'};
    
    public TokenOperator() {
        
    }

    public TokenOperator(String word, SourceRange range) {
        this.content = word;
        this.range =range;
    }
    
    @Override
    public boolean matchChar(Tokenizer tokenizer, char c) {
        for (char symbol : symbols) {
            if (symbol == c) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public Token getToken(String word,SourceRange range) {
        return new TokenOperator(word,range);
    }
    
    @Override
    public String getPrefix() {
        return "OP";
    }
}
