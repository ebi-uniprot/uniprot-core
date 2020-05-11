package org.uniprot.core.uniprotkb.evidence;

import org.uniprot.core.CrossReference;

import java.io.Serializable;

public interface Evidence extends Comparable<Evidence>, Serializable {
    EvidenceCode getEvidenceCode();

    CrossReference<EvidenceDatabase> getEvidenceCrossReference();

    String getValue();
}
