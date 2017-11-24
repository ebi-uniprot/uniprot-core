package uk.ac.ebi.uniprot.domain.uniprot.comments.impl;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comments.PHDependence;

import java.util.List;

public class PhDependenceImpl extends FreeTextImpl implements PHDependence {

    public PhDependenceImpl(List<EvidencedValue> texts) {
        super(texts);
    }

}
