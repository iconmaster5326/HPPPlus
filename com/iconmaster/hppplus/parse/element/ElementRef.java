
package com.iconmaster.hppplus.parse.element;

import com.iconmaster.hppplus.Parseable;
import com.iconmaster.hppplus.SourceRange;
import com.iconmaster.hppplus.exception.HPPPlusException;
import com.iconmaster.hppplus.exception.parse.SyntaxException;
import com.iconmaster.hppplus.parse.Element;
import com.iconmaster.hppplus.parse.Parser;
import com.iconmaster.hppplus.tokenize.Token;
import com.iconmaster.hppplus.tokenize.element.TokenChunk;
import com.iconmaster.hppplus.tokenize.element.TokenReference;
import com.iconmaster.hppplus.tokenize.element.TokenWord;
import java.util.ArrayList;

/**
 *
 * @author iconmaster
 */
public class ElementRef implements Element {
    public class CallData {
        public Element name = null;
        public String field = null;
        private CallData(Element name, String field) {
            this.name = name;
            this.field = field;
        }
    }
    
    private Element name = null;
    private String field = null;
    private SourceRange range;
    
    public ElementRef() {
        
    }

    public ElementRef(Element name, String field,SourceRange range) {
        this.name = name;
        this.field = field;
        this.range = range;
    }
    
    @Override
    public Object getParsedContent() {
        return new CallData(name,field);
    }

    @Override
    public SourceRange getRange() {
        return range;
    }

    @Override
    public boolean isMatch(ArrayList<Parseable> parseList, int at) {
        if (at+1>=parseList.size()) {return false;}
        return parseList.get(at+1) instanceof TokenReference;
    }

    @Override
    public Element match(ArrayList<Parseable> parseList, int at) throws HPPPlusException {
       Parseable name = (Parseable) parseList.get(at);
       Parseable args = (Parseable) parseList.get(at+1);
       
       
       ArrayList<Element> parsed;
       if (name instanceof TokenChunk) {
            parsed = (new Parser((ArrayList<Token>) name.getParsedContent())).parse();
       } else if (name instanceof Token) {
           ArrayList<Token> a = new ArrayList<>();
           a.add((Token) name);
           parsed = (new Parser(a)).parse();
       } else {
           parsed = new ArrayList<>();
           parsed.add((Element) name);
       }
       if (parsed.size()==1) {
           name = parsed.get(0);
       } else {
           throw new SyntaxException(name.getRange(), (Token) name);
       }
       
       return new ElementRef((Element) name,(String) args.getParsedContent(),SourceRange.between(name.getRange(), args.getRange()));
    }

    @Override
    public int getDeletionLength() {
        return 2;
    }
    
    @Override
    public String toString() {
        StringBuilder cont = new StringBuilder("{REF OF "+name+" at "+range+":");
        
        cont.append("\n\t");
        cont.append(field.toString().replace("\n", "\n\t"));

        cont.append("\n}");
        return cont.toString();
    }
}
