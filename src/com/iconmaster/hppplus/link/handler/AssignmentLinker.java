
package com.iconmaster.hppplus.link.handler;

import com.iconmaster.hppplus.exception.HPPPlusException;
import com.iconmaster.hppplus.link.LinkHandler;
import com.iconmaster.hppplus.link.LinkSpace;
import com.iconmaster.hppplus.link.LinkSpaceGlobal;
import com.iconmaster.hppplus.link.Linker;
import com.iconmaster.hppplus.link.VariablePrototype;
import com.iconmaster.hppplus.parse.element.operator.ElementAssignment;

/**
 *
 * @author iconmaster
 */
public class AssignmentLinker implements LinkHandler<ElementAssignment> {

    @Override
    public void handleLocal(Linker linker, LinkSpace linkspace, ElementAssignment element) throws HPPPlusException {
        String vname = Linker.extractVarName(element);
        if (vname!=null) {
            linkspace.addVarEnsureDefined(new VariablePrototype(vname),element.getRange());
        } else {
            
        }
    }

    @Override
    public void handleGlobal(Linker linker, LinkSpaceGlobal linkspace, ElementAssignment element) throws HPPPlusException {
        String vname = Linker.extractVarName(element);
        if (vname!=null) {
            linkspace.addVar(new VariablePrototype(vname));
        } else {
            
        }
    }
    
}
