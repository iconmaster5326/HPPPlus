
package com.iconmaster.hppplus.link;

import com.iconmaster.hppplus.exception.HPPPlusException;
import com.iconmaster.hppplus.parse.Element;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author iconmaster
 */
public class Linker {
    private static final HashMap<Class,LinkHandler> localHandlers = new HashMap<>();
    public static void registerLocalHandler(Class eclass,LinkHandler handler) {
        localHandlers.put(eclass, handler);
    }
    
    private static final HashMap<Class,LinkHandler> globalHandlers = new HashMap<>();
    public static void registerGlobalHandler(Class eclass,LinkHandler handler) {
        globalHandlers.put(eclass, handler);
    }
    
    public static void registerDefaultHandlers() {
        
    }
    
    private final ArrayList<Element> elements;
    
    public Linker(ArrayList<Element> elements) {
        this.elements = elements;
    }
    
    public LinkSpaceGlobal link() throws HPPPlusException {
        LinkSpaceGlobal varspace = new LinkSpaceGlobal();
        for (Element element : elements) {

        }
        return varspace;
    }
    
    public LinkSpace linkBlock() throws HPPPlusException {
        return null;
    }
}
