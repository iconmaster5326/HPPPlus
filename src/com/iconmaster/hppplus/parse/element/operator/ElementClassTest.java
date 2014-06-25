
package com.iconmaster.hppplus.parse.element.operator;

import com.iconmaster.hppplus.Parseable;
import com.iconmaster.hppplus.SourceRange;
import com.iconmaster.hppplus.exception.HPPPlusException;
import com.iconmaster.hppplus.parse.Element;
import com.iconmaster.hppplus.tokenize.element.TokenWord;
import java.util.ArrayList;

/**
 *
 * @author iconmaster
 */
public class ElementClassTest implements Element {
    public class ClassTestData {
        public Parseable lvalue = null;
        public Parseable rvalue = null;
        private ClassTestData(Parseable lvalue, Parseable rvalue) {
            this.lvalue = lvalue;
            this.rvalue = rvalue;
        }
    }
    
    private Parseable lvalue = null;
    private Parseable rvalue = null;
    private SourceRange range;
    
    public ElementClassTest() {
        
    }

    public ElementClassTest(Parseable lvalue,Parseable rvalue,SourceRange range) {
        this.lvalue = lvalue;
        this.rvalue = rvalue;
        this.range = range;
    }
    
    @Override
    public Object getParsedContent() {
        return new ClassTestData(lvalue,rvalue);
    }

    @Override
    public SourceRange getRange() {
        return range;
    }

    @Override
    public boolean isMatch(ArrayList<Parseable> parseList, int at) {
        if (at+2>=parseList.size()) {return false;}
        if (parseList.get(at+1) instanceof TokenWord) {
            Parseable e = parseList.get(at+1);
            if (parseList.get(at) instanceof Element && parseList.get(at+2) instanceof Element && "IS".equalsIgnoreCase((String) e.getParsedContent())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Element match(ArrayList<Parseable> parseList, int at) throws HPPPlusException {
        //Token token = (Token) parseList.get(at);
        //return new ElementOperator(op,token.getRange());
       Parseable lvalue = (Parseable) parseList.get(at);
       Parseable rvalue = (Parseable) parseList.get(at+2);
       return new ElementClassTest(lvalue,rvalue,SourceRange.between(lvalue.getRange(), rvalue.getRange()));
    }

    @Override
    public int getDeletionLength() {
        return 3;
    }
    
    @Override
    public String toString() {
        StringBuilder cont = new StringBuilder("{CLASS TEST at "+range+":");
 
        cont.append("\n\t");
        cont.append(lvalue.toString().replace("\n", "\n\t"));
        
        cont.append("\n\t");
        cont.append(rvalue.toString().replace("\n", "\n\t"));

        cont.append("\n}");
        return cont.toString();
    }
}
