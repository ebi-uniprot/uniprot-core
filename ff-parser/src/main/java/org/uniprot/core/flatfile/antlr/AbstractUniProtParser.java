package org.uniprot.core.flatfile.antlr;

import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.TokenStream;

import java.io.Serializable;

/**
 * @author jluo
 * @date: 2 Jul 2019
 */
public abstract class AbstractUniProtParser extends Parser implements Serializable {
    /** */
    private static final long serialVersionUID = -1468342786931025490L;

    protected AbstractUniProtParser(TokenStream input) {
        super(input);
    }
}
