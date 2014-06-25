
package com.iconmaster.hppplus.tokenize;

import com.iconmaster.hppplus.Parseable;
import com.iconmaster.hppplus.exception.HPPPlusException;

/**
 *
 * @author iconmaster
 */
public interface Token extends Parseable {
    public boolean isMatch(Tokenizer l1p,char first);
    public Token match(Tokenizer l1p) throws HPPPlusException;
}
