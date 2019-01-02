package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtId;

public class UniProtIdImpl extends ValueImpl implements UniProtId {

    private UniProtIdImpl() {
        super(null);
    }

    public UniProtIdImpl(String value) {
        super(value);
    }

}
