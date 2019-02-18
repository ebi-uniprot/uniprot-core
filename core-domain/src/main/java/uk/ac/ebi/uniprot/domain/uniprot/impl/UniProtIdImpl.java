package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtId;

public class UniProtIdImpl extends ValueImpl implements UniProtId {
    private static final long serialVersionUID = 5515211143656089672L;

    private UniProtIdImpl() {
        super(null);
    }

    public UniProtIdImpl(String value) {
        super(value);
    }

}
