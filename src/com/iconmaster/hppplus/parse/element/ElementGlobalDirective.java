
package com.iconmaster.hppplus.parse.element;

import com.iconmaster.hppplus.Parseable;
import com.iconmaster.hppplus.SourceRange;
import com.iconmaster.hppplus.exception.HPPPlusException;
import com.iconmaster.hppplus.parse.Element;
import com.iconmaster.hppplus.tokenize.Token;
import com.iconmaster.hppplus.tokenize.element.TokenDirective;
import java.util.ArrayList;

/**
 *
 * @author iconmaster
 */
public class ElementGlobalDirective implements Element {
    private String value;
    private SourceRange range;
    
    public ElementGlobalDirective() {
        
    }
    
    public ElementGlobalDirective(String value, SourceRange range) {
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
        return parseList.get(at) instanceof TokenDirective && ((String)((Token)parseList.get(at)).getParsedContent()).startsWith("@");
    }

    @Override
    public Element match(ArrayList<Parseable> parseList, int at) throws HPPPlusException {
        Token token = (Token) parseList.get(at);
        return new ElementGlobalDirective(((String) token.getParsedContent()).substring(1),token.getRange());
    }

    @Override
    public int getDeletionLength() {
        return 1;
    }
    
    @Override
    public String toString() {
        return "{@@: "+value+" at "+range+"}";
    }
}
