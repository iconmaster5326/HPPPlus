
package com.iconmaster.hppplus.tokenize.element;

import com.iconmaster.hppplus.SourceRange;
import com.iconmaster.hppplus.tokenize.Token;
import com.iconmaster.hppplus.tokenize.Tokenizer;

/**
 *
 * @author iconmaster
 */
public class TotalMatchToken implements Token {
    protected String content;
    protected SourceRange range;

    @Override
    public boolean isMatch(Tokenizer tokenizer, char first) {
        return matchFirstChar(tokenizer, first);
    }

    @Override
    public Token match(Tokenizer tokenizer) {
        tokenizer.advanceIndex();
        StringBuilder word = new StringBuilder();
        int begin = tokenizer.getIndex();
        while (true) {
            if (!tokenizer.isEOF()) {
                char c = tokenizer.getChar();
                if (!matchLastChar(tokenizer, c)) {
                    word.append(c);
                    tokenizer.advanceIndex();
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        tokenizer.advanceIndex();
        return getToken(word.toString(),new SourceRange(begin,tokenizer.getIndex()));
    }

    @Override
    public Object getParsedContent() {
        return content;
    }

    @Override
    public SourceRange getRange() {
        return range;
    }
    
    public boolean matchFirstChar(Tokenizer tokenizer, char c) {
        return false;
    }
    
    public boolean matchLastChar(Tokenizer tokenizer, char c) {
        return false;
    }
    
    public Token getToken(String word,SourceRange range) {
        return null;
    }
    
    @Override
    public String toString() {
        return "["+getPrefix()+": "+content+" at "+range+"]";
    }
    
    public String getPrefix() {
        return "ELEMENT";
    }
}
