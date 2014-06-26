
package com.iconmaster.hppplus;

import com.iconmaster.hppplus.exception.HPPPlusException;
import com.iconmaster.hppplus.link.LinkSpaceGlobal;
import com.iconmaster.hppplus.link.Linker;
import com.iconmaster.hppplus.parse.Parser;
import com.iconmaster.hppplus.tokenize.Tokenizer;
import java.util.ArrayList;

/**
 *
 * @author iconmaster
 */
public class HPPPlus {
    public static String[] reservedWords = new String[]{"IF","THEN","ELSE","END","LOCAL","RETURN","CLASS","FOR","TO","BY","DO","AS","IS","IN","WHILE","REPEAT","UNTIL","FUNCTION","BREAK","CONTINUE","CASE","DEFAULT","TRY","CATCH","SWITCH","NEW"};
    public static String[] blockBeginners = new String[] {"DO","THEN","FUNCTION","TRY","SWITCH"};
    /**
     * @param args the command line arguments
     * @throws com.iconmaster.hppplus.exception.HPPPlusException
     */
    public static void main(String[] args) throws HPPPlusException {
        Tokenizer.registerDefaultHandlers();
        Parser.registerDefaultHandlers();
        Linker.registerDefaultHandlers();
        
        System.out.println("--TOKENIZE--");
        ArrayList a = (new Tokenizer("i=1 function test(v1,v2) v1=v2 end")).tokenize();
        System.out.println(a);
        System.out.println("--PARSE--");
        ArrayList a2 = (new Parser(a)).parse();
        System.out.println(a2);
        System.out.println("--LINK--");
        LinkSpaceGlobal a3 = (new Linker(a2)).link();
        System.out.println(a3);
    }
    
    public static boolean isReservedWord(String string) {
        for (String word : reservedWords) {
            if (string.equalsIgnoreCase(word)) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isBlockBeginner(String string) {
        for (String word : blockBeginners) {
            if (string.equalsIgnoreCase(word)) {
                return true;
            }
        }
        return false;
    }
}
