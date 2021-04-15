package org.uniprot.core.interpro.impl;

import org.uniprot.core.impl.ValueImpl;
import org.uniprot.core.interpro.Abstract;

/**
 * @author jluo
 * @date: 12 Apr 2021
 */
public class AbstractImpl extends ValueImpl implements Abstract {

    /** */
    private static final long serialVersionUID = -7930583372638791351L;

    AbstractImpl() {
        super(null);
    }

    AbstractImpl(String value) {
        super(value);
    }
}
