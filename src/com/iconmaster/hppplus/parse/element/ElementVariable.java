
package com.iconmaster.hppplus.parse.element;

import com.iconmaster.hppplus.HPPPlus;
import com.iconmaster.hppplus.Parseable;
import com.iconmaster.hppplus.SourceRange;
import com.iconmaster.hppplus.exception.HPPPlusException;
import com.iconmaster.hppplus.parse.Element;
import com.iconmaster.hppplus.tokenize.Token;
import com.iconmaster.hppplus.tokenize.element.TokenWord;
import java.util.ArrayList;

/**
 *
 * @author iconmaster
 */
public class ElementVariable implements Element {
    private String value;
    private SourceRange range;
    
    public ElementVariable() {
        
    }
    
    public ElementVariable(String value, SourceRange range) {
        this.value = value;
        this.range = range;
    }
    
    @Override
    public Object getParsedContent() {
        return value;
    }

    @Override
    public SourceRange getRange() {
        return range;
    }

    @Override
    public boolean isMatch(ArrayList<Parseable> parseList, int at) {
        return parseList.get(at) instanceof TokenWord && !HPPPlus.isReservedWord((String)((Parseable)parseList.get(at)).getParsedContent());
    }

    @Override
    public Element match(ArrayList<Parseable> parseList, int at) throws HPPPlusException {
        Token token = (Token) parseList.get(at);
        return new ElementVariable((String) token.getParsedContent(),token.getRange());
    }

    @Override
    public int getDeletionLength() {
        return 1;
    }
    
    @Override
    public String toString() {
        return "{VAR: "+value+" at "+range+"}";
    }
}
