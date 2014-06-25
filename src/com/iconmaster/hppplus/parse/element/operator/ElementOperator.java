
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
public class ElementOperator implements Element {
    public class OperatorData {
        public String op;
        public Parseable lvalue = null;
        public Parseable rvalue = null;
        private OperatorData(Parseable lvalue, Parseable rvalue, String op) {
            this.op = op;
            this.lvalue = lvalue;
            this.rvalue = rvalue;
        }
    }
    
    private final String op;
    private Parseable lvalue = null;
    private Parseable rvalue = null;
    private SourceRange range;
    
    public ElementOperator(String op) {
        this.op = op;
    }

    public ElementOperator(Parseable lvalue,String op,Parseable rvalue,SourceRange range) {
        this.op = op;
        this.lvalue = lvalue;
        this.rvalue = rvalue;
        this.range = range;
    }
    
    @Override
    public Object getParsedContent() {
        return new OperatorData(lvalue,rvalue,op);
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
            if (parseList.get(at) instanceof Element && parseList.get(at+2) instanceof Element && op.equals(e.getParsedContent())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Element match(ArrayList<Parseable> parseList, int at) throws HPPPlusException {
       Parseable lvalue = (Parseable) parseList.get(at);
       Parseable opvalue = (Parseable) parseList.get(at+1);
       Parseable rvalue = (Parseable) parseList.get(at+2);
       return new ElementOperator(lvalue, (String) opvalue.getParsedContent(),rvalue,SourceRange.between(lvalue.getRange(), rvalue.getRange()));
    }

    @Override
    public int getDeletionLength() {
        return 3;
    }
    
    @Override
    public String toString() {
        StringBuilder cont = new StringBuilder("{"+op+" at "+range+":");
 
        cont.append("\n\t");
        cont.append(lvalue.toString().replace("\n", "\n\t"));
        
        cont.append("\n\t");
        cont.append(rvalue.toString().replace("\n", "\n\t"));

        cont.append("\n}");
        return cont.toString();
    }
}
