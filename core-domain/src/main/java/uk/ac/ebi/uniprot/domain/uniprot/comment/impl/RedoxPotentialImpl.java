package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.RedoxPotential;

import java.util.List;

public class RedoxPotentialImpl extends FreeTextImpl implements RedoxPotential {
    public RedoxPotentialImpl(List<EvidencedValue> texts) {
        super(texts);
    }

}