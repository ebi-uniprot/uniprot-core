package org.uniprot.core.uniprot.description;

import org.uniprot.core.uniprot.evidence.EvidencedValue;

public interface Name extends EvidencedValue {
    boolean isValid();
}
