
package com.iconmaster.hppplus.parse.element.block;

import com.iconmaster.hppplus.HPPPlus;
import com.iconmaster.hppplus.Parseable;
import com.iconmaster.hppplus.SourceRange;
import com.iconmaster.hppplus.exception.HPPPlusException;
import com.iconmaster.hppplus.exception.parse.DoExpectedException;
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
public class ElementWhile implements Element {
    public class WhileData {
        private Element cond;
        private ArrayList<Element> block;
        public WhileData(Element cond,ArrayList<Element> thenb) {
            this.cond = cond;
            this.block = block;
        }
    }
        
    private SourceRange range;
    private Element cond;
    private ArrayList<Element> block;
    private int blockSize;
    
    public ElementWhile() {
        
    }
    
    public ElementWhile(Element cond,ArrayList<Element> block,int blockSize,SourceRange range) {
        this.range = range;
        this.cond = cond;
        this.block = block;
        this.blockSize = blockSize;
    }

    @Override
    public boolean isMatch(ArrayList parseList, int at) {
        if (at+3>=parseList.size()) {return false;}
        if (parseList.get(at) instanceof TokenWord) {
            Token e = (Token) parseList.get(at);
            return "WHILE".equalsIgnoreCase((String) e.getParsedContent());
        }
        return false;
    }

    @Override
    public Element match(ArrayList parseList, int at) throws HPPPlusException {
        if (parseList.get(at+2) instanceof Token) {
           Token e = (Token) parseList.get(at+2);
           if (!"DO".equalsIgnoreCase((String) e.getParsedContent())) {
               throw new DoExpectedException(e.getRange(),"WHILE");
           }
        } else {
            throw new DoExpectedException(((Element)parseList.get(at+2)).getRange(),"WHILE");
        }
        
        if (parseList.get(at+1) instanceof Token) {
            throw new SyntaxException(((Element)parseList.get(at+2)).getRange(),(Token)parseList.get(at+1));
        }
        
        int layer = 1;
        int end = -2;
        for (int i=at+3;i<parseList.size();i++) {
            if (parseList.get(i) instanceof TokenWord && HPPPlus.isBlockBeginner((String)((Parseable)parseList.get(i)).getParsedContent())) {
                layer++;
            }
            
            if (parseList.get(i) instanceof TokenWord && "END".equalsIgnoreCase((String)((Parseable)parseList.get(i)).getParsedContent())) {
                layer--;
            }
            
            if (layer==0) {
                end = i-1;
                break;
            }
        }
        
        if (end==-2) {
            throw new EndExpectedException(((Element)parseList.get(at+2)).getRange(),"DO");
        }
        
        ArrayList toParse = new ArrayList();
        for (int i=at+3;i<=end;i++) {
            toParse.add(parseList.get(i));
        }
        Parser parser = new Parser(toParse);
        ArrayList<Element> output = parser.parse();
        return new ElementWhile((Element) parseList.get(at+1),output,3+end-at,SourceRange.between(((Token)parseList.get(at)).getRange(), ((Token)parseList.get(end+1)).getRange()));
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
        StringBuilder cont = new StringBuilder("{WHILE ("+cond+") at "+range+":");
        for (Object o : block) {
            cont.append("\n\t");
            cont.append(o.toString().replace("\n", "\n\t"));
        }
        cont.append("\n}");
        return cont.toString();
    }

    @Override
    public Object getParsedContent() {
        return new WhileData(cond,block);
    }
}
