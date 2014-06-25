
package com.iconmaster.hppplus.link.handler;

import com.iconmaster.hppplus.exception.HPPPlusException;
import com.iconmaster.hppplus.exception.link.InvalidLocallyException;
import com.iconmaster.hppplus.link.FunctionPrototype;
import com.iconmaster.hppplus.link.LinkHandler;
import com.iconmaster.hppplus.link.LinkSpace;
import com.iconmaster.hppplus.link.LinkSpaceGlobal;
import com.iconmaster.hppplus.link.Linker;
import com.iconmaster.hppplus.parse.element.block.ElementFunction;
import com.iconmaster.hppplus.parse.element.block.ElementFunction.FunctionData;

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
        //add the function here
    }
    
}
