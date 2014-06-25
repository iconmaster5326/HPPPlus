
package com.iconmaster.hppplus.parse;

import com.iconmaster.hppplus.parse.element.operator.*;
import com.iconmaster.hppplus.Parseable;
import com.iconmaster.hppplus.exception.HPPPlusException;
import com.iconmaster.hppplus.exception.parse.SyntaxException;
import com.iconmaster.hppplus.parse.element.*;
import com.iconmaster.hppplus.parse.element.block.*;
import com.iconmaster.hppplus.tokenize.Token;
import java.util.ArrayList;

/**
 *
 * @author iconmaster
 */
public class Parser {
    public static String[] opl1 = new String[] {"^","*","/","%","+","-","&","|","<<",">>"};
    public static String[] opl2 = new String[] {"-","!"};
    public static String[] opl3 = new String[] {"==","!=","<",">","<=",">=","&&","||"};
    
    private final ArrayList<Parseable> data = new ArrayList<>();
    private static final ArrayList<Element> handlers = new ArrayList<>();
    
    public Parser(ArrayList<Token> tokens) {
        data.addAll(tokens);
    }
    
    public static void registerHandler(Element t) {
        handlers.add(t);
    }
    
    public static void registerDefaultHandlers() {
        registerHandler(new ElementWhitespace());
        
        //calls
        registerHandler(new ElementCall());
        registerHandler(new ElementIndex());
        registerHandler(new ElementRefCall());
        registerHandler(new ElementRef());
        
        //parens
        registerHandler(new ElementParen());
        
        //consts
        registerHandler(new ElementVariable());
        registerHandler(new ElementNumber());
        registerHandler(new ElementString());
        
        //operators
        for (String s : opl1) {
            registerHandler(new ElementOperator(s));
        }
        
        //cast
        registerHandler(new ElementCast());
        
        //class test
        registerHandler(new ElementClassTest());
        
        //unary ops
        for (String s : opl2) {
            registerHandler(new ElementUnaryOperator(s));
        }
        
        //logic ops
        for (String s : opl3) {
            registerHandler(new ElementOperator(s));
        }
        
        //assign
        registerHandler(new ElementAssignment());
        
        //blocks
        registerHandler(new ElementIf());
        registerHandler(new ElementWhile());
        registerHandler(new ElementFor());
        registerHandler(new ElementFunction());
        
        registerHandler(new ElementReturn());
        registerHandler(new ElementBreak());
        registerHandler(new ElementContinue());
        
        registerHandler(new ElementLocal());

        //seps
        registerHandler(new ElementSeperator());
        
        //directives
        registerHandler(new ElementGlobalDirective());
        registerHandler(new ElementDirective());
    }
    
    public ArrayList<Element> parse() throws HPPPlusException {
        ArrayList<Parseable> parseList = (ArrayList<Parseable>) data.clone();

        for (int i=0;i<handlers.size();i++) {
            Element handler = handlers.get(i);
            for (int j=0;j<parseList.size();j++) {
                if (handler.isMatch(parseList, j)) {
                    Element e = handler.match(parseList, j);
                    if (e!=null) {
                        for (int k=0;k<e.getDeletionLength();k++) {
                            if (j<parseList.size())
                                parseList.remove(j);
                        }
                        parseList.add(j, e);
                    } else {
                       for (int k=0;k<handler.getDeletionLength();k++) {
                            parseList.remove(j);
                        }
                    }
                    i--;
                    break;
                }
            }
        }
        
        ArrayList<Element> output = new ArrayList<>();
        for (Parseable o : parseList) {
            if (o instanceof Token) {
                //System.out.println(parseList);
                throw new SyntaxException(o.getRange(), (Token) o);
            } else {
                output.add((Element) o);
            }
        }
        return output;
    }
}
