
package com.iconmaster.hppplus.parse.element.block;

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
public class ElementReturn implements Element {
    private SourceRange range;
    private Element value;
    
    public ElementReturn() {
        
    }
    
    public ElementReturn(Element value,SourceRange range) {
        this.range = range;
        this.value = value;
    }

    @Override
    public boolean isMatch(ArrayList parseList, int at) {
        if (parseList.get(at) instanceof TokenWord) {
            Token e = (Token) parseList.get(at);
            return "RETURN".equalsIgnoreCase((String) e.getParsedContent());
        }
        return false;
    }

    @Override
    public Element match(ArrayList parseList, int at) throws HPPPlusException {
        Element value = null;
        SourceRange range;
        if (at+1<parseList.size()) {
            value = (Element) parseList.get(at+1);
            range = SourceRange.between(((Token) parseList.get(at)).getRange(), value.getRange());
        } else {
            range = ((Token) parseList.get(at)).getRange();
        }
        return new ElementReturn(value,range);
    }

    @Override
    public int getDeletionLength() {
        return value == null ? 1 : 2;
    }

    @Override
    public SourceRange getRange() {
        return range;
    }
    
    @Override
    public String toString() {
        if (value!= null) {
            return "{RETURN: "+value+" at "+range+"}";
        } else {
            return "{RETURN: at "+range+"}";
        }
    }

    @Override
    public Object getParsedContent() {
        return value;
    }
    
}
