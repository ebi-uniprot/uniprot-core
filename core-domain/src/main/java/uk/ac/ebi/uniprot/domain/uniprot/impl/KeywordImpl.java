package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.uniprot.Keyword;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class KeywordImpl extends EvidencedValueImpl implements Keyword {

    public KeywordImpl(String value, List<Evidence> evidences) {
        super(value, evidences);
    }

}
