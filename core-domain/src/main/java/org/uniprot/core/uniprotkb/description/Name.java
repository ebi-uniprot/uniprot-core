package org.uniprot.core.uniprotkb.description;

import org.uniprot.core.uniprotkb.evidence.EvidencedValue;

public interface Name extends EvidencedValue {
    boolean isValid();
}
