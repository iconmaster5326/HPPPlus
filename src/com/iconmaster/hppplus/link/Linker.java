
package com.iconmaster.hppplus.link;

import com.iconmaster.hppplus.exception.HPPPlusException;
import com.iconmaster.hppplus.link.handler.*;
import com.iconmaster.hppplus.parse.Element;
import com.iconmaster.hppplus.parse.element.ElementVariable;
import com.iconmaster.hppplus.parse.element.block.ElementFunction;
import com.iconmaster.hppplus.parse.element.operator.ElementAssignment;
import com.iconmaster.hppplus.parse.element.operator.ElementCast;
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
        registerHandler(ElementAssignment.class,new AssignmentLinker());
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
    
    public LinkSpace linkBlock(ArrayList<VariablePrototype> vars) throws HPPPlusException {
        LinkSpace varspace = new LinkSpace();
        
        for (VariablePrototype var : vars) {
            varspace.addVar(var);
        }
        
        for (Element element : elements) {
            LinkHandler handler = handlers.get(element.getClass());
            if (handler!=null) {
                handler.handleLocal(this, varspace, element);
            }
        }
        return varspace;
    }
    public LinkSpace linkBlock() throws HPPPlusException {
        return linkBlock(new ArrayList<VariablePrototype>());
    }
    
    public static String extractVarName(Element e) {
        if (e instanceof ElementVariable) {
            return (String) e.getParsedContent();
        } else if (e instanceof ElementCast) {
            ElementCast.CastData cdata = (ElementCast.CastData) e.getParsedContent();
            return extractVarName(cdata.lvalue);
        } else if (e instanceof ElementAssignment) {
            ElementAssignment.AssignmentData cdata = (ElementAssignment.AssignmentData) e.getParsedContent();
            return extractVarName(cdata.lvalue);
        }
        return null;
    }
}
