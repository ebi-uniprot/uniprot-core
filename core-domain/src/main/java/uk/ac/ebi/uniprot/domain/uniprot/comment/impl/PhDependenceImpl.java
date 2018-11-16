package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.PHDependence;

import java.util.List;

public class PhDependenceImpl extends FreeTextImpl implements PHDependence {

    public PhDependenceImpl(List<EvidencedValue> texts) {
        super(texts);
    }

}
