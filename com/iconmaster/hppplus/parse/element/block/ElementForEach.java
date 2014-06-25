
package com.iconmaster.hppplus.parse.element.block;

import com.iconmaster.hppplus.SourceRange;
import com.iconmaster.hppplus.exception.HPPPlusException;
import com.iconmaster.hppplus.parse.Element;
import java.util.ArrayList;

/**
 *
 * @author iconmaster
 */
public class ElementForEach implements Element {
    public class ForEachData {
        public Element var1;
        public ArrayList<Element> block;
        public Element var2;
        public Element list;
        public ForEachData(Element var1,Element var2,Element list,ArrayList<Element> block) {
            this.var1 = var1;
            this.var2 = var2;
            this.list = list;
            this.block = block;
        }
    }
    
    private SourceRange range;
    private Element var1;
    private ArrayList<Element> block;
    private int blockSize;
    private Element var2;
    private Element list;
    
    public ElementForEach(Element var1,Element var2,Element list,ArrayList<Element> block,int blockSize,SourceRange range) {
        this.range = range;
        this.var1 = var1;
        this.var2 = var2;
        this.list = list;
        this.block = block;
        this.blockSize = blockSize;
    }

    @Override
    public boolean isMatch(ArrayList parseList, int at) {
        return false;
    }

    @Override
    public Element match(ArrayList parseList, int at) throws HPPPlusException {
        return null;
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
        StringBuilder cont = new StringBuilder("{FOREACH ("+var1+") to ("+list+")");
        if (var2 != null) {
            cont.append(" (var2 ").append(var2).append(")");
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
        return new ForEachData(var1,var2,list,block);
    }
}
