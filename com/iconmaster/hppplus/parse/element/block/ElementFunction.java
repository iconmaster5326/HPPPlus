
package com.iconmaster.hppplus.parse.element.block;

import com.iconmaster.hppplus.HPPPlus;
import com.iconmaster.hppplus.Parseable;
import com.iconmaster.hppplus.SourceRange;
import com.iconmaster.hppplus.exception.HPPPlusException;
import com.iconmaster.hppplus.exception.parse.EndExpectedException;
import com.iconmaster.hppplus.exception.parse.SyntaxException;
import com.iconmaster.hppplus.parse.Element;
import com.iconmaster.hppplus.parse.Parser;
import com.iconmaster.hppplus.tokenize.Token;
import com.iconmaster.hppplus.tokenize.element.TokenWord;
import java.util.ArrayList;

/**
 *
 * @author iconmaster
 */
public class ElementFunction implements Element {
    public class FunctionData {
        public Element name;
        public ArrayList<Element> block;
        private FunctionData(Element name,ArrayList<Element> block) {
            this.name = name;
            this.block = block;
        }
    }
    
    private SourceRange range;
    private Element name;
    private ArrayList<Element> block;
    private int blockSize;
    
    public ElementFunction() {
        
    }

    public ElementFunction(Element name,ArrayList<Element> block,int blockSize,SourceRange range) {
        this.name = name;
        this.block = block;
        this.blockSize = blockSize;
        this.range = range;
    }
    
    @Override
    public Object getParsedContent() {
        return new FunctionData(name,block);
    }

    @Override
    public SourceRange getRange() {
        return range;
    }

    @Override
    public boolean isMatch(ArrayList<Parseable> parseList, int at) {
        if (at+2>=parseList.size()) {return false;}
        if (parseList.get(at) instanceof TokenWord) {
            Token e = (Token) parseList.get(at);
            return "FUNCTION".equalsIgnoreCase((String) e.getParsedContent());
        }
        return false;
    }

    @Override
    public Element match(ArrayList<Parseable> parseList, int at) throws HPPPlusException {
        if (parseList.get(at+1) instanceof Token) {
            throw new SyntaxException(((Element)parseList.get(at+2)).getRange(), (Token) parseList.get(at+1));
        }
        
        int layer = 1;
        int end = -2;
        for (int i=at+2;i<parseList.size();i++) {
            if (parseList.get(i) instanceof TokenWord && HPPPlus.isBlockBeginner((String)((TokenWord)parseList.get(i)).getParsedContent())) {
                layer++;
            }
            
            if (parseList.get(i) instanceof TokenWord && "END".equalsIgnoreCase((String)((TokenWord)parseList.get(i)).getParsedContent())) {
                layer--;
            }
            
            if (layer==0) {
                end = i-1;
                break;
            }
        }
        
        if (end==-2) {
            throw new EndExpectedException(((Token)parseList.get(at)).getRange(),"FUNCTION");
        }
        
        ArrayList toParse = new ArrayList();
        for (int i=at+2;i<=end;i++) {
            toParse.add(parseList.get(i));
        }
        Parser parser = new Parser(toParse);
        ArrayList<Element> output = parser.parse();
        return new ElementFunction((Element) parseList.get(at+1),output,2+end-at,SourceRange.between(((Token)parseList.get(at)).getRange(), ((Token)parseList.get(end+1)).getRange()));
    }

    @Override
    public int getDeletionLength() {
        return blockSize;
    }

    @Override
    public String toString() {
        StringBuilder cont = new StringBuilder("{FUNCTION ("+name+") at "+range+":");
        for (Object o : block) {
            cont.append("\n\t");
            cont.append(o.toString().replace("\n", "\n\t"));
        }
        cont.append("\n}");
        return cont.toString();
    }
}
