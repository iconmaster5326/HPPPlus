
package com.iconmaster.hppplus.link;

import com.iconmaster.hppplus.exception.HPPPlusException;
import com.iconmaster.hppplus.link.handler.FunctionLinker;
import com.iconmaster.hppplus.parse.Element;
import com.iconmaster.hppplus.parse.element.block.ElementFunction;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author iconmaster
 */
public class Linker {
    private static final HashMap<Class,LinkHandler> handlers = new HashMap<>();
    public static void registerHandler(Class eclass,LinkHandler handler) {
        handlers.put(eclass, handler);
    }
    
    public static void registerDefaultHandlers() {
        registerHandler(ElementFunction.class,new FunctionLinker());
    }
    
    private final ArrayList<Element> elements;
    
    public Linker(ArrayList<Element> elements) {
        this.elements = elements;
    }
    
    public LinkSpaceGlobal link() throws HPPPlusException {
        LinkSpaceGlobal varspace = new LinkSpaceGlobal();
        for (Element element : elements) {
            LinkHandler handler = handlers.get(element.getClass());
            if (handler!=null) {
                handler.handleGlobal(this, varspace, element);
            }
        }
        return varspace;
    }
    
    public LinkSpace linkBlock() throws HPPPlusException {
        return null;
    }
}
