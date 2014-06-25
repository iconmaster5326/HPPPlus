
package com.iconmaster.hppplus.parse.element.block;

import com.iconmaster.hppplus.HPPPlus;
import com.iconmaster.hppplus.SourceRange;
import com.iconmaster.hppplus.exception.HPPPlusException;
import com.iconmaster.hppplus.exception.parse.DoExpectedException;
import com.iconmaster.hppplus.exception.parse.EndExpectedException;
import com.iconmaster.hppplus.exception.parse.IllegalArgumentCountException;
import com.iconmaster.hppplus.parse.Element;
import com.iconmaster.hppplus.parse.Parser;
import com.iconmaster.hppplus.tokenize.Token;
import com.iconmaster.hppplus.tokenize.element.TokenWord;
import java.util.ArrayList;

/**
 *
 * @author iconmaster
 */
public class ElementFor implements Element {
    public class ForData {
        public Element var;
        public ArrayList<Element> block;
        public Element goal;
        public Element step;
        public ForData(Element var,Element goal,Element step,ArrayList<Element> block) {
            this.var = var;
            this.goal = goal;
            this.step = step;
            this.block = block;
        }
    }
        
    private SourceRange range;
    private Element var;
    private ArrayList<Element> block;
    private int blockSize;
    private Element goal;
    private Element step;
    
    public ElementFor() {
        
    }
    
    public ElementFor(Element var,Element goal,Element step,ArrayList<Element> block,int blockSize,SourceRange range) {
        this.range = range;
        this.var = var;
        this.goal = goal;
        this.step = step;
        this.block = block;
        this.blockSize = blockSize;
    }

    @Override
    public boolean isMatch(ArrayList parseList, int at) {
        if (at+3>=parseList.size()) {return false;}
        if (parseList.get(at) instanceof TokenWord) {
            Token e = (Token) parseList.get(at);
            return "FOR".equalsIgnoreCase((String) e.getParsedContent());
        }
        return false;
    }

    @Override
    public Element match(ArrayList parseList, int at) throws HPPPlusException {
        Element var = null,goal = null,step = null,var1 = null,var2 = null,list = null;
        int doend = -2;
        int nargs = 0;
        boolean foreach = false;
        for (int i=at;i<parseList.size();i++) {
            if (parseList.get(i) instanceof TokenWord) {
                Token e = (Token) parseList.get(i);
                if ("DO".equalsIgnoreCase((String) e.getParsedContent())) {
                    doend = i-1;
                }
            }
        }
        if (doend==-2) {
            throw new DoExpectedException(((Element)parseList.get(at)).getRange(),"FOR");
        } else {
            nargs = doend-at;
            System.out.println(nargs);
            if (nargs < 3 || nargs > 5) {
                throw new IllegalArgumentCountException(((Token)parseList.get(at)).getRange(),parseList.get(at),-1,nargs);
            }
            
            if (nargs==3) {
                if (parseList.get(at+2) instanceof TokenWord && "IN".equalsIgnoreCase((String)((TokenWord)parseList.get(at+2)).getParsedContent())) {
                    foreach = true;
                    var1 = (Element) parseList.get(at+1);
                    list = (Element) parseList.get(at+3);
                } else if (parseList.get(at+2) instanceof TokenWord && "TO".equalsIgnoreCase((String)((TokenWord)parseList.get(at+2)).getParsedContent())) {
                    var = (Element) parseList.get(at+1);
                    goal = (Element) parseList.get(at+3);
                }
            }
            
            if (nargs==4) {
                if (parseList.get(at+3) instanceof TokenWord && "IN".equalsIgnoreCase((String)((TokenWord)parseList.get(at+3)).getParsedContent())) {
                    foreach = true;
                    var1 = (Element) parseList.get(at+1);
                    var2 = (Element) parseList.get(at+2);
                    list = (Element) parseList.get(at+4);
                }
            }
            
            if (nargs==5) {
                var = (Element) parseList.get(at+1);
                goal = (Element) parseList.get(at+3);
                step = (Element) parseList.get(at+5);
            }
        }
        
        int layer = 1;
        int end = -2;
        for (int i=doend+2;i<parseList.size();i++) {
            if (parseList.get(i) instanceof TokenWord && HPPPlus.isBlockBeginner((String)((TokenWord)parseList.get(i)).getParsedContent())) {
                layer++;
            }
            
            if (parseList.get(i) instanceof TokenWord && "END".equalsIgnoreCase((String)((TokenWord)parseList.get(i)).getParsedContent())) {
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
        for (int i=doend+2;i<=end;i++) {
            toParse.add(parseList.get(i));
        }
        Parser parser = new Parser(toParse);
        ArrayList<Element> output = parser.parse();
        if (foreach) {
            return new ElementForEach(var1,var2,list,output,2+end-at+nargs,SourceRange.between(((Token)parseList.get(at)).getRange(), ((Token)parseList.get(end+1)).getRange()));
        } else {
            return new ElementFor(var,goal,step,output,2+end-at+nargs,SourceRange.between(((Token)parseList.get(at)).getRange(), ((Token)parseList.get(end+1)).getRange()));
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
        StringBuilder cont = new StringBuilder("{FOR ("+var+") to ("+goal+")");
        if (step != null) {
            cont.append(" (step ").append(step).append(")");
        }
        cont.append(" at ").append(range).append(":");
        for (Object o : block) {
            cont.append("\n\t");
            cont.append(o.toString().replace("\n", "\n\t"));
        }
        cont.append("\n}");
        return cont.toString();
    }

    @Override
    public Object getParsedContent() {
        return new ForData(var,goal,step,block);
    }
}
