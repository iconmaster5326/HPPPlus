
package com.iconmaster.hppplus.link;

import com.iconmaster.hppplus.exception.HPPPlusException;
import com.iconmaster.hppplus.parse.Element;

/**
 *
 * @author iconmaster
 */
public interface LinkHandler<T extends Element> {
    public void handleLocal(Linker linker,LinkSpace linkspace,T element) throws HPPPlusException;
    public void handleGlobal(Linker linker,LinkSpaceGlobal linkspace,T element) throws HPPPlusException;
}
