
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
public class ElementUnaryOperator implements Element {
    public class UnaryOperatorData {
        public String op;
        public Parseable rvalue = null;
        private UnaryOperatorData(Parseable rvalue, String op) {
            this.op = op;
            this.rvalue = rvalue;
        }
    }
    
    private final String op;
    private Parseable rvalue = null;
    private SourceRange range;
    
    public ElementUnaryOperator(String op) {
        this.op = op;
    }

    public ElementUnaryOperator(String op,Parseable rvalue,SourceRange range) {
        this.op = op;
        this.rvalue = rvalue;
        this.range = range;
    }
    
    @Override
    public Object getParsedContent() {
        return new UnaryOperatorData(rvalue,op);
    }

    @Override
    public SourceRange getRange() {
        return range;
    }

    @Override
    public boolean isMatch(ArrayList<Parseable> parseList, int at) {
        if (at+1>=parseList.size()) {return false;}
        if (parseList.get(at) instanceof TokenOperator) {
            Parseable e = parseList.get(at);
            if (parseList.get(at+1) instanceof Element && op.equals(e.getParsedContent())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Element match(ArrayList<Parseable> parseList, int at) throws HPPPlusException {
       Parseable opvalue = (Parseable) parseList.get(at);
       Parseable rvalue = (Parseable) parseList.get(at+1);
       return new ElementUnaryOperator((String) opvalue.getParsedContent(),rvalue,SourceRange.between(opvalue.getRange(), rvalue.getRange()));
    }

    @Override
    public int getDeletionLength() {
        return 2;
    }
    
    @Override
    public String toString() {
        StringBuilder cont = new StringBuilder("{UNARY "+op+" at "+range+":");
        
        cont.append("\n\t");
        cont.append(rvalue.toString().replace("\n", "\n\t"));

        cont.append("\n}");
        return cont.toString();
    }
}
