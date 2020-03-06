package org.uniprot.core.uniprot.impl;

import org.uniprot.core.impl.ValueImpl;
import org.uniprot.core.uniprot.SourceLine;

public class SourceLineImpl extends ValueImpl implements SourceLine {
    private static final long serialVersionUID = 7474937343774049515L;

    // no arg constructor for JSON deserialization
    SourceLineImpl() {
        super("");
    }

    SourceLineImpl(String value) {
        super(value);
    }
}
