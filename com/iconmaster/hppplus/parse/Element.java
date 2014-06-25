
package com.iconmaster.hppplus.parse;

import com.iconmaster.hppplus.Parseable;
import com.iconmaster.hppplus.exception.HPPPlusException;
import java.util.ArrayList;

/**
 *
 * @author iconmaster
 */
public interface Element extends Parseable {
    public boolean isMatch(ArrayList<Parseable> parseList,int at);
    public Element match(ArrayList<Parseable> parseList,int at) throws HPPPlusException;

    public int getDeletionLength();
}
