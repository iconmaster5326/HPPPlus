
package com.iconmaster.hppplus.parse.element;

import com.iconmaster.hppplus.Parseable;
import com.iconmaster.hppplus.SourceRange;
import com.iconmaster.hppplus.exception.HPPPlusException;
import com.iconmaster.hppplus.parse.Element;
import com.iconmaster.hppplus.parse.Parser;
import com.iconmaster.hppplus.tokenize.Token;
import com.iconmaster.hppplus.tokenize.element.TokenChunk;
import java.util.ArrayList;

/**
 *
 * @author iconmaster
 */
public class ElementParen implements Element {
    private ArrayList<Element> value;
    private SourceRange range;
    
    public ElementParen() {
        
    }
    
    public ElementParen(ArrayList<Element> value, SourceRange range) {
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
        return parseList.get(at) instanceof TokenChunk;
    }

    @Override
    public Element match(ArrayList<Parseable> parseList, int at) throws HPPPlusException {
        Token token = (Token) parseList.get(at);
        ArrayList<Token> tokens = (ArrayList<Token>) token.getParsedContent();
        return new ElementParen((new Parser(tokens)).parse(),token.getRange());
    }

    @Override
    public int getDeletionLength() {
        return 1;
    }
    
    @Override
    public String toString() {
        StringBuilder cont = new StringBuilder("{PAREN at "+range+":");
        for (Element o : value) {
            cont.append("\n\t");
            cont.append(o.toString().replace("\n", "\n\t"));
        }
        cont.append("\n}");
        return cont.toString();
    }
}
