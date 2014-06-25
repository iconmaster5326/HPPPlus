
package com.iconmaster.hppplus.parse.element;

import com.iconmaster.hppplus.Parseable;
import com.iconmaster.hppplus.SourceRange;
import com.iconmaster.hppplus.exception.HPPPlusException;
import com.iconmaster.hppplus.parse.Element;
import com.iconmaster.hppplus.tokenize.element.TokenSeperator;
import com.iconmaster.hppplus.tokenize.element.TokenWhitespace;
import java.util.ArrayList;

/**
 *
 * @author iconmaster
 */
public class ElementWhitespace implements Element {
    private SourceRange range;
    
    public ElementWhitespace() {
        
    }
    
    @Override
    public Object getParsedContent() {
        return null;
    }

    @Override
    public SourceRange getRange() {
        return range;
    }

    @Override
    public boolean isMatch(ArrayList<Parseable> parseList, int at) {
        return parseList.get(at) instanceof TokenWhitespace;
    }

    @Override
    public Element match(ArrayList<Parseable> parseList, int at) throws HPPPlusException {
        return null;
    }

    @Override
    public int getDeletionLength() {
        return 1;
    }
    
    @Override
    public String toString() {
        return "{SPACE at "+range+"}";
    }
}
