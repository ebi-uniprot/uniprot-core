package org.uniprot.core.uniprotkb.evidence;

import java.io.Serializable;

import org.uniprot.core.CrossReference;

public interface Evidence extends Comparable<Evidence>, Serializable {
    EvidenceCode getEvidenceCode();

    CrossReference<EvidenceDatabase> getEvidenceCrossReference();

    String getValue();
}
