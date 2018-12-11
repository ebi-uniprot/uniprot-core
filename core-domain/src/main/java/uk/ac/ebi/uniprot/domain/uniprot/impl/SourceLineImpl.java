package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.uniprot.SourceLine;

public class SourceLineImpl  extends ValueImpl implements SourceLine{

    private SourceLineImpl(){
        super("");
    }

    public SourceLineImpl(String value) {
        super(value);
    }
}
