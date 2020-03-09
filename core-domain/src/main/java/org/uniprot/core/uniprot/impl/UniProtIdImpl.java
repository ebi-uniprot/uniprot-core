package org.uniprot.core.uniprot.impl;

import org.uniprot.core.impl.ValueImpl;
import org.uniprot.core.uniprot.UniProtId;

public class UniProtIdImpl extends ValueImpl implements UniProtId {
    private static final long serialVersionUID = 5515211143656089672L;

    // no arg constructor for JSON deserialization
    UniProtIdImpl() {
        super(null);
    }

    UniProtIdImpl(String value) {
        super(value);
    }
}
