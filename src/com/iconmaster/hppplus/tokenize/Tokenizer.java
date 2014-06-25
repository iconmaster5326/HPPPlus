
package com.iconmaster.hppplus.tokenize;

import com.iconmaster.hppplus.SourceRange;
import com.iconmaster.hppplus.exception.HPPPlusException;
import com.iconmaster.hppplus.exception.tokenize.UnknownSymbolException;
import com.iconmaster.hppplus.tokenize.element.*;
import java.util.ArrayList;

/**
 *
 * @author iconmaster
 */
public class Tokenizer {
    private static final ArrayList<Token> handlers = new ArrayList<>();
    
    private int index = 0;
    private final CharSequence input;
    
    public Tokenizer(CharSequence input) {
        this.input = input;
    }
    
    public static void registerHandler(Token t) {
        handlers.add(t);
    }
    
    public static void registerDefaultHandlers() {
        registerHandler(new TokenComment());
        registerHandler(new TokenReference());
        registerHandler(new TokenWord());
        registerHandler(new TokenNumber());
        registerHandler(new TokenString());
        registerHandler(new TokenOperator());
        registerHandler(new TokenSeperator());
        registerHandler(new TokenWhitespace());
        registerHandler(new TokenChunk());
        registerHandler(new TokenIndex());
        registerHandler(new TokenList());
        registerHandler(new TokenDirective());
    }
    
    public ArrayList<Token> tokenize() throws HPPPlusException {
        ArrayList<Token> a = new ArrayList<>();
        while (!isEOF()) {
            Token token = nextToken();
            if (token!=null) {
                a.add(token);
            }
        }
        return a;
    }
    
    public Token nextToken() throws HPPPlusException {
            Token handler = getHandler(getChar());
            if (handler == null) {
                throw new UnknownSymbolException(new SourceRange(index,index),getChar());
            } else {
                Token result = handler.match(this);
                if (result!=null) {
                    return result;
                }
            }
            advanceIndex();
            return null;
    }
    
    public void advanceIndex() {
        advanceIndex(1);
    }
    
    public void advanceIndex(int amt) {
        index+=amt;
    }
    
    public char getChar() {
        return input.charAt(index);
    }
    
    public boolean isEOF() {
        return index >= input.length();
    }
    
    private Token getHandler(char c) {
        for (Token t : handlers) {
            if (t.isMatch(this, c)) {
                return t;
            }
        }
        return null;
    }

    public int getIndex() {
        return index;
    }
}
