package uk.ac.ebi.uniprot.domain.uniprot.description;

import uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidencedValue;

public interface Name extends EvidencedValue {
    boolean isValid();
}
