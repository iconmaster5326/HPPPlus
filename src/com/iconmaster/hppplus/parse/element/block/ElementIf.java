
package com.iconmaster.hppplus.parse.element.block;

import com.iconmaster.hppplus.HPPPlus;
import com.iconmaster.hppplus.Parseable;
import com.iconmaster.hppplus.SourceRange;
import com.iconmaster.hppplus.exception.HPPPlusException;
import com.iconmaster.hppplus.exception.parse.EndExpectedException;
import com.iconmaster.hppplus.exception.parse.SyntaxException;
import com.iconmaster.hppplus.exception.parse.ThenExpectedException;
import com.iconmaster.hppplus.parse.Element;
import com.iconmaster.hppplus.parse.Parser;
import com.iconmaster.hppplus.tokenize.Token;
import com.iconmaster.hppplus.tokenize.element.TokenWord;
import java.util.ArrayList;


/**
 *
 * @author iconmaster
 */
public class ElementIf implements Element {
    public class IfData {
        private Element cond;
        private ArrayList<Element> thenb;
        private ArrayList<Element> elseb;
        public IfData(Element cond,ArrayList<Element> thenb,ArrayList<Element> elseb) {
            this.cond = cond;
            this.thenb = thenb;
            this.elseb = elseb;
        }
    }
        
    private SourceRange range;
    private Element cond;
    private ArrayList<Element> thenb;
    private ArrayList<Element> elseb;
    private int blockSize;
    
    
    public ElementIf() {
        
    }
    
    public ElementIf(Element cond,ArrayList<Element> thenb,ArrayList<Element> elseb,int blockSize,SourceRange range) {
        this.range = range;
        this.cond = cond;
        this.thenb = thenb;
        this.elseb = elseb;
        this.blockSize = blockSize;
    }

    @Override
    public boolean isMatch(ArrayList<Parseable> parseList, int at) {
        if (at+3>=parseList.size()) {return false;}
        if (parseList.get(at) instanceof TokenWord) {
            Token e = (Token) parseList.get(at);
            return "IF".equalsIgnoreCase((String) e.getParsedContent());
        }
        return false;
    }

    @Override
    public Element match(ArrayList<Parseable> parseList, int at) throws HPPPlusException {
        if (parseList.get(at+2) instanceof Token) {
           Token e = (Token) parseList.get(at+2);
           if (!"THEN".equalsIgnoreCase((String) e.getParsedContent())) {
               throw new ThenExpectedException(e.getRange(),"IF");
           }
        } else {
            throw new ThenExpectedException(((Element)parseList.get(at+2)).getRange(),"IF");
        }
        
        if (parseList.get(at+1) instanceof Token) {
            throw new SyntaxException(((Element)parseList.get(at+2)).getRange(),(Token)parseList.get(at+1));
        }
        
        int layer = 1;
        int mainend = -2;
        int elsebegin = -2;
        for (int i=at+3;i<parseList.size();i++) {
            if (parseList.get(i) instanceof TokenWord && HPPPlus.isBlockBeginner((String)((TokenWord)parseList.get(i)).getParsedContent())) {
                layer++;
            }
            
            if (parseList.get(i) instanceof TokenWord && "END".equalsIgnoreCase((String)((TokenWord)parseList.get(i)).getParsedContent())) {
                layer--;
            }
            
            if (layer==1 && parseList.get(i) instanceof TokenWord && "ELSE".equalsIgnoreCase((String)((TokenWord)parseList.get(i)).getParsedContent())) {
                elsebegin = i;
            }
            
            if (layer==0) {
                mainend = i-1;
                break;
            }
        }
        
        if (mainend==-2) {
            throw new EndExpectedException(((Parseable)parseList.get(at+2)).getRange(),"THEN");
        }
        
        if (elsebegin==-2) {
            ArrayList toParse = new ArrayList();
            for (int i=at+3;i<=mainend;i++) {
                toParse.add(parseList.get(i));
            }
            Parser parser = new Parser(toParse);
            ArrayList<Element> output = parser.parse();
            return new ElementIf((Element) parseList.get(at+1),output,new ArrayList<Element>(),3+mainend-at,SourceRange.between(((Token)parseList.get(at)).getRange(), ((Token)parseList.get(mainend+1)).getRange()));
        } else {
            ArrayList parseThen = new ArrayList();
            for (int i=at+3;i<=elsebegin-1;i++) {
                parseThen.add(parseList.get(i));
            }
            Parser parserThen = new Parser(parseThen);
            ArrayList<Element> outputThen = parserThen.parse();
            
            ArrayList parseElse = new ArrayList();
            for (int i=elsebegin+1;i<=mainend;i++) {
                parseElse.add(parseList.get(i));
            }
            Parser parserElse = new Parser(parseElse);
            ArrayList<Element> outputElse = parserElse.parse();
            
            return new ElementIf((Element) parseList.get(at+1),outputThen,outputElse,3+mainend-at,SourceRange.between(((Token)parseList.get(at)).getRange(), ((Token)parseList.get(mainend+1)).getRange()));
        }
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
        StringBuilder cont = new StringBuilder("{IF ("+cond+") at "+range+":");
        for (Object o : thenb) {
            cont.append("\n\t");
            cont.append(o.toString().replace("\n", "\n\t"));
        }
        cont.append("\nELSE:");
        for (Object o : elseb) {
            cont.append("\n\t");
            cont.append(o.toString().replace("\n", "\n\t"));
        }
        cont.append("\n}");
        return cont.toString();
    }
    
    @Override
    public Object getParsedContent() {
        return new IfData(cond,thenb,elseb);
    }
}
