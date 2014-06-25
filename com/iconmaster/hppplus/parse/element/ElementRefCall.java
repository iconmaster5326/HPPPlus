
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
import java.util.ArrayList;

/**
 *
 * @author iconmaster
 */
public class ElementRefCall implements Element {
    public class RefCallData {
        public Element member;
        public String func;
        public ArrayList<Element> args;
        private RefCallData(Element member,String func,ArrayList<Element> args) {
            this.member = member;
            this.func = func;
            this.args = args;
        }
    }
    
    private String func = null;
    private ArrayList<Element> args = null;
    private Element member = null;
    private SourceRange range;
    
    public ElementRefCall() {
        
    }

    public ElementRefCall(Element member,String func,ArrayList<Element> args,SourceRange range) {
        this.func = func;
        this.args = args;
        this.member = member;
        this.range = range;
    }
    
    @Override
    public Object getParsedContent() {
        return new RefCallData(member,func,args);
    }

    @Override
    public SourceRange getRange() {
        return range;
    }

    @Override
    public boolean isMatch(ArrayList<Parseable> parseList, int at) {
        if (at+2>=parseList.size()) {return false;}
        return parseList.get(at+1) instanceof TokenReference && parseList.get(at+2) instanceof TokenChunk;
    }

    @Override
    public Element match(ArrayList<Parseable> parseList, int at) throws HPPPlusException {
       Parseable member = (Parseable) parseList.get(at);
       Parseable func = (Parseable) parseList.get(at+1);
       Parseable args = (Parseable) parseList.get(at+2);
       
       ArrayList<Element> parsed;
       if (member instanceof TokenChunk) {
            parsed = (new Parser((ArrayList<Token>) member.getParsedContent())).parse();
       } else if (member instanceof Token) {
           ArrayList<Token> a = new ArrayList<>();
           a.add((Token) member);
           parsed = (new Parser(a)).parse();
       } else {
           parsed = new ArrayList<>();
           parsed.add((Element) member);
       }
       if (parsed.size()==1) {
           member = parsed.get(0);
       } else {
           throw new SyntaxException(member.getRange(), (Token) member);
       }
       
       return new ElementRefCall((Element) member, (String) func.getParsedContent(),(new Parser((ArrayList<Token>) args.getParsedContent())).parse(),SourceRange.between(member.getRange(), args.getRange()));
    }

    @Override
    public int getDeletionLength() {
        return 3;
    }
    
    @Override
    public String toString() {
        StringBuilder cont = new StringBuilder("{REFCALL OF "+member+" TO "+func+" at "+range+":");
        
        cont.append("\n\t");
        cont.append(args.toString().replace("\n", "\n\t"));

        cont.append("\n}");
        return cont.toString();
    }
}
