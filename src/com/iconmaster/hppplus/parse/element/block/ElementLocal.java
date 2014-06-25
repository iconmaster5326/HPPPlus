
package com.iconmaster.hppplus.parse.element.block;

import com.iconmaster.hppplus.SourceRange;
import com.iconmaster.hppplus.exception.HPPPlusException;
import com.iconmaster.hppplus.exception.parse.SyntaxException;
import com.iconmaster.hppplus.parse.Element;
import com.iconmaster.hppplus.parse.Parser;
import com.iconmaster.hppplus.parse.element.ElementVariable;
import com.iconmaster.hppplus.parse.element.operator.ElementAssignment;
import com.iconmaster.hppplus.parse.element.operator.ElementCast;
import com.iconmaster.hppplus.tokenize.Token;
import com.iconmaster.hppplus.tokenize.element.TokenWord;
import java.util.ArrayList;

/**
 *
 * @author iconmaster
 */
public class ElementLocal implements Element {
    private SourceRange range;
    private ArrayList<Element> vars;
    private int blockSize;
    
    public ElementLocal() {
        
    }
    
    public ElementLocal(ArrayList<Element> vars,int blockSize,SourceRange range) {
        this.range = range;
        this.vars = vars;
        this.blockSize = blockSize;
    }

    @Override
    public boolean isMatch(ArrayList parseList, int at) {
        if (at>=parseList.size()) {return false;}
        if (parseList.get(at) instanceof TokenWord) {
            Token e = (Token) parseList.get(at);
            return "LOCAL".equalsIgnoreCase((String) e.getParsedContent());
        }
        return false;
    }

    @Override
    public Element match(ArrayList parseList, int at) throws HPPPlusException {
        int end = -2;
        for (int i=at+1;i<parseList.size();i++) {
            if (!(parseList.get(i) instanceof ElementVariable || parseList.get(i) instanceof ElementAssignment || parseList.get(i) instanceof ElementCast)) {
                end = i-1;
                break;
            }
        }
        if (end==-2) {
            throw new SyntaxException(((Token)parseList.get(at)).getRange(),(Token)parseList.get(at));
        }
        
        ArrayList toParse = new ArrayList();
        for (int i=at+1;i<=end;i++) {
            toParse.add(parseList.get(i));
        }
        Parser parser = new Parser(toParse);
        ArrayList<Element> output = parser.parse();
        return new ElementLocal(output,end-at+1,SourceRange.between(((Token)parseList.get(at)).getRange(), ((Element)parseList.get(end)).getRange()));
    }

    @Override
    public int getDeletionLength() {
        return blockSize;
    }

    @Override
    public SourceRange getRange() {
        return range;
    }
    
    @Override
    public String toString() {
        StringBuilder cont = new StringBuilder("{LOCAL at "+range+":");
        for (Object o : vars) {
            cont.append("\n\t");
            cont.append(o.toString().replace("\n", "\n\t"));
        }
        cont.append("\n}");
        return cont.toString();
    }

    @Override
    public Object getParsedContent() {
        return vars;
    }
}
