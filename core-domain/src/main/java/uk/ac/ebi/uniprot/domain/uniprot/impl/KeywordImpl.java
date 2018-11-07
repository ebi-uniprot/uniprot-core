package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.uniprot.Keyword;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.List;

public class KeywordImpl extends EvidencedValueImpl implements Keyword {

    public KeywordImpl(String value, List<Evidence> evidences) {
        super(value, evidences);
    }

}
