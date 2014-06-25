
package com.iconmaster.hppplus.tokenize.element;

import com.iconmaster.hppplus.SourceRange;
import com.iconmaster.hppplus.exception.HPPPlusException;
import com.iconmaster.hppplus.exception.tokenize.UnexpectedEOFException;
import com.iconmaster.hppplus.tokenize.Token;
import com.iconmaster.hppplus.tokenize.Tokenizer;
import java.util.ArrayList;

/**
 *
 * @author iconmaster
 */
public class RecursiveMatchToken implements Token {
    protected ArrayList<Token> tokens;
    protected SourceRange range;

    @Override
    public boolean isMatch(Tokenizer tokenizer, char first) {
        return matchFirstChar(tokenizer,first);
    }

    @Override
    public Token match(Tokenizer tokenizer) throws HPPPlusException {
        ArrayList<Token> a = new ArrayList<>();
        tokenizer.advanceIndex();
        int begin = tokenizer.getIndex();
        while (true) {
            if (!tokenizer.isEOF()) {
                char c = tokenizer.getChar();
                if (!matchLastChar(tokenizer,c)) {
                    Token token = tokenizer.nextToken();
                    if (token!=null) {
                        a.add(token);
                    }
                    //tokenizer.advanceIndex();
                } else {
                    break;
                }
            } else {
                throw new UnexpectedEOFException(new SourceRange(begin,tokenizer.getIndex()));
            }
        }
        tokenizer.advanceIndex();
        return getToken(a,new SourceRange(begin,tokenizer.getIndex()));
    }

    @Override
    public Object getParsedContent() {
        return tokens;
    }

    @Override
    public SourceRange getRange() {
        return range;
    }
    
    public boolean matchFirstChar(Tokenizer tokenizer,char c) {
        return false;
    }
    
    public boolean matchLastChar(Tokenizer tokenizer,char c) {
        return false;
    }
    
    public Token getToken(ArrayList<Token> tokens,SourceRange range) {
        return null;
    }
    
    @Override
    public String toString() {
        StringBuilder cont = new StringBuilder("["+getPrefix()+" at "+range+":");
        for (Object o : tokens) {
            cont.append("\n\t");
            cont.append(o.toString().replace("\n", "\n\t"));
        }
        cont.append("\n]");
        return cont.toString();
    }
    
    public String getPrefix() {
        return "ELEMENT";
    }
}
