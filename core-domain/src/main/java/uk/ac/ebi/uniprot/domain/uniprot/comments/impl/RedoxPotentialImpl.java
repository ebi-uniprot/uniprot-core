package uk.ac.ebi.uniprot.domain.uniprot.comments.impl;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comments.RedoxPotential;

import java.util.List;

public class RedoxPotentialImpl extends FreeTextImpl implements RedoxPotential {
    public RedoxPotentialImpl(List<EvidencedValue> texts) {
        super(texts);
    }

}