package uk.ac.ebi.uniprot.domain.uniprot.description;

import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidencedValue;

public interface Name extends EvidencedValue {
    boolean isValid();
}
