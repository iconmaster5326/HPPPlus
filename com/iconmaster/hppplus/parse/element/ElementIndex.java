
package com.iconmaster.hppplus.parse.element;

import com.iconmaster.hppplus.Parseable;
import com.iconmaster.hppplus.SourceRange;
import com.iconmaster.hppplus.exception.HPPPlusException;
import com.iconmaster.hppplus.exception.parse.SyntaxException;
import com.iconmaster.hppplus.parse.Element;
import com.iconmaster.hppplus.parse.Parser;
import com.iconmaster.hppplus.tokenize.Token;
import com.iconmaster.hppplus.tokenize.element.TokenChunk;
import com.iconmaster.hppplus.tokenize.element.TokenIndex;
import java.util.ArrayList;

/**
 *
 * @author iconmaster
 */
public class ElementIndex implements Element {
    public class IndexData {
        public Parseable lvalue = null;
        public ArrayList<Element> args = null;
        private IndexData(Parseable lvalue, ArrayList<Element> rvalue) {
            this.lvalue = lvalue;
            this.args = rvalue;
        }
    }
    
    private Parseable lvalue = null;
    private ArrayList<Element> args = null;
    private SourceRange range;
    
    public ElementIndex() {
        
    }

    public ElementIndex(Parseable lvalue,ArrayList<Element> rvalue,SourceRange range) {
        this.lvalue = lvalue;
        this.args = rvalue;
        this.range = range;
    }
    
    @Override
    public Object getParsedContent() {
        return new IndexData(lvalue,args);
    }

    @Override
    public SourceRange getRange() {
        return range;
    }

    @Override
    public boolean isMatch(ArrayList<Parseable> parseList, int at) {
        if (at+1>=parseList.size()) {return false;}
        return parseList.get(at+1) instanceof TokenIndex;
    }

    @Override
    public Element match(ArrayList<Parseable> parseList, int at) throws HPPPlusException {
       Parseable lvalue = (Parseable) parseList.get(at);
       Parseable args = (Parseable) parseList.get(at+1);
       
       ArrayList<Element> parsed;
       if (lvalue instanceof TokenChunk) {
            parsed = (new Parser((ArrayList<Token>) lvalue.getParsedContent())).parse();
       } else if (lvalue instanceof Token) {
           ArrayList<Token> a = new ArrayList<>();
           a.add((Token) lvalue);
           parsed = (new Parser(a)).parse();
       } else {
           parsed = new ArrayList<>();
           parsed.add((Element) lvalue);
       }
       if (parsed.size()==1) {
           lvalue = parsed.get(0);
       } else {
           throw new SyntaxException(lvalue.getRange(), (Token) lvalue);
       }
       
       return new ElementIndex(lvalue,(new Parser((ArrayList<Token>) args.getParsedContent())).parse(),SourceRange.between(lvalue.getRange(), args.getRange()));
    }

    @Override
    public int getDeletionLength() {
        return 2;
    }
    
    @Override
    public String toString() {
        StringBuilder cont = new StringBuilder("{INDEX TO "+lvalue+" at "+range+":");
        
        cont.append("\n\t");
        cont.append(args.toString().replace("\n", "\n\t"));

        cont.append("\n}");
        return cont.toString();
    }
}
