
package com.iconmaster.hppplus.link.handler;

import com.iconmaster.hppplus.exception.HPPPlusException;
import com.iconmaster.hppplus.exception.link.InvalidLocallyException;
import com.iconmaster.hppplus.exception.link.VariableExpectedException;
import com.iconmaster.hppplus.link.FunctionPrototype;
import com.iconmaster.hppplus.link.LinkHandler;
import com.iconmaster.hppplus.link.LinkSpace;
import com.iconmaster.hppplus.link.LinkSpaceGlobal;
import com.iconmaster.hppplus.link.Linker;
import com.iconmaster.hppplus.link.VariablePrototype;
import com.iconmaster.hppplus.parse.Element;
import com.iconmaster.hppplus.parse.element.block.ElementFunction;
import com.iconmaster.hppplus.parse.element.block.ElementFunction.FunctionData;
import java.util.ArrayList;

/**
 *
 * @author iconmaster
 */
public class FunctionLinker implements LinkHandler<ElementFunction> {

    @Override
    public void handleLocal(Linker linker, LinkSpace linkspace, ElementFunction element) throws HPPPlusException {
        throw new InvalidLocallyException(element.getRange(),"Function creation");
    }

    @Override
    public void handleGlobal(Linker linker, LinkSpaceGlobal linkspace, ElementFunction element) throws HPPPlusException {
        FunctionData data = (FunctionData)element.getParsedContent();
        ArrayList<VariablePrototype> vars = new ArrayList<>();
        for (Element e : data.args) {
            String vname = Linker.extractVarName(e);
            if (vname!=null) {
                vars.add(new VariablePrototype(vname));
            } else {
                throw new VariableExpectedException(e.getRange(),e);
            }
        }
        linkspace.addFunction(new FunctionPrototype(data.name,vars,data.block));
    }
    
}
