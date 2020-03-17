package org.uniprot.core.uniprotkb.impl;

import org.uniprot.core.impl.ValueImpl;
import org.uniprot.core.uniprotkb.UniProtKBId;

public class UniProtKBIdImpl extends ValueImpl implements UniProtKBId {
    private static final long serialVersionUID = 5515211143656089672L;

    // no arg constructor for JSON deserialization
    UniProtKBIdImpl() {
        super(null);
    }

    UniProtKBIdImpl(String value) {
        super(value);
    }
}
