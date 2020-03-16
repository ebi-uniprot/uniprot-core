package org.uniprot.core.uniprotkb.impl;

import org.uniprot.core.impl.ValueImpl;
import org.uniprot.core.uniprotkb.UniProtkbId;

public class UniProtkbIdImpl extends ValueImpl implements UniProtkbId {
    private static final long serialVersionUID = 5515211143656089672L;

    // no arg constructor for JSON deserialization
    UniProtkbIdImpl() {
        super(null);
    }

    UniProtkbIdImpl(String value) {
        super(value);
    }
}
