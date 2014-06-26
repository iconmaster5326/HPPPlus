
package com.iconmaster.hppplus.parse.element.operator;

import com.iconmaster.hppplus.Parseable;
import com.iconmaster.hppplus.SourceRange;
import com.iconmaster.hppplus.exception.HPPPlusException;
import com.iconmaster.hppplus.parse.Element;
import com.iconmaster.hppplus.tokenize.element.TokenOperator;
import java.util.ArrayList;

/**
 *
 * @author iconmaster
 */
public class ElementAssignment implements Element {
    public class AssignmentData {
        public Element lvalue = null;
        public Element rvalue = null;
        private AssignmentData(Element lvalue, Element rvalue) {
            this.lvalue = lvalue;
            this.rvalue = rvalue;
        }
    }
    
    private Element lvalue = null;
    private Element rvalue = null;
    private SourceRange range;
    
    public ElementAssignment() {
        
    }

    public ElementAssignment(Element lvalue,Element rvalue,SourceRange range) {
        this.lvalue = lvalue;
        this.rvalue = rvalue;
        this.range = range;
    }
    
    @Override
    public Object getParsedContent() {
        return new AssignmentData(lvalue,rvalue);
    }

    @Override
    public SourceRange getRange() {
        return range;
    }

    @Override
    public boolean isMatch(ArrayList<Parseable> parseList, int at) {
        if (at+2>=parseList.size()) {return false;}
        if (parseList.get(at+1) instanceof TokenOperator) {
            Parseable e = parseList.get(at+1);
            if (parseList.get(at) instanceof Element && parseList.get(at+2) instanceof Element && "=".equals(e.getParsedContent())) {
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
       Parseable opvalue = (Parseable) parseList.get(at+1);
       Parseable rvalue = (Parseable) parseList.get(at+2);
       return new ElementAssignment((Element)lvalue,(Element)rvalue,SourceRange.between(lvalue.getRange(), rvalue.getRange()));
    }

    @Override
    public int getDeletionLength() {
        return 3;
    }
    
    @Override
    public String toString() {
        StringBuilder cont = new StringBuilder("{= at "+range+":");
 
        cont.append("\n\t");
        cont.append(lvalue.toString().replace("\n", "\n\t"));
        
        cont.append("\n\t");
        cont.append(rvalue.toString().replace("\n", "\n\t"));

        cont.append("\n}");
        return cont.toString();
    }
}
