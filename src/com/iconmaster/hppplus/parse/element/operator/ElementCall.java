
package com.iconmaster.hppplus.parse.element.operator;

import com.iconmaster.hppplus.Parseable;
import com.iconmaster.hppplus.SourceRange;
import com.iconmaster.hppplus.exception.HPPPlusException;
import com.iconmaster.hppplus.parse.Element;
import com.iconmaster.hppplus.parse.Parser;
import com.iconmaster.hppplus.tokenize.Token;
import com.iconmaster.hppplus.tokenize.element.TokenChunk;
import com.iconmaster.hppplus.tokenize.element.TokenWord;
import java.util.ArrayList;

/**
 *
 * @author iconmaster
 */
public class ElementCall implements Element {
    public class CallData {
        public String name = null;
        public ArrayList<Element> args = null;
        private CallData(String lvalue, ArrayList<Element> rvalue) {
            this.name = lvalue;
            this.args = rvalue;
        }
    }
    
    private String name = null;
    private ArrayList<Element> args = null;
    private SourceRange range;
    
    public ElementCall() {
        
    }

    public ElementCall(String lvalue,ArrayList<Element> rvalue,SourceRange range) {
        this.name = lvalue;
        this.args = rvalue;
        this.range = range;
    }
    
    @Override
    public Object getParsedContent() {
        return new CallData(name,args);
    }

    @Override
    public SourceRange getRange() {
        return range;
    }

    @Override
    public boolean isMatch(ArrayList<Parseable> parseList, int at) {
        if (at+1>=parseList.size()) {return false;}
        return parseList.get(at) instanceof TokenWord && parseList.get(at+1) instanceof TokenChunk;
    }

    @Override
    public Element match(ArrayList<Parseable> parseList, int at) throws HPPPlusException {
       Parseable name = (Parseable) parseList.get(at);
       Parseable args = (Parseable) parseList.get(at+1);
       return new ElementCall((String) name.getParsedContent(),(new Parser((ArrayList<Token>) args.getParsedContent())).parse(),SourceRange.between(name.getRange(), args.getRange()));
    }

    @Override
    public int getDeletionLength() {
        return 2;
    }
    
    @Override
    public String toString() {
        StringBuilder cont = new StringBuilder("{CALL TO "+name+" at "+range+":");
        
        cont.append("\n\t");
        cont.append(args.toString().replace("\n", "\n\t"));

        cont.append("\n}");
        return cont.toString();
    }
}
