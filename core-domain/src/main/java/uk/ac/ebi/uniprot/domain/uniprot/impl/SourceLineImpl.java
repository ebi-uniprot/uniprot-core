package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.impl.ValueImpl;
import uk.ac.ebi.uniprot.domain.uniprot.SourceLine;

public class SourceLineImpl extends ValueImpl implements SourceLine {
    private static final long serialVersionUID = 7474937343774049515L;

    private SourceLineImpl() {
        super("");
    }

    public SourceLineImpl(String value) {
        super(value);
    }
}
