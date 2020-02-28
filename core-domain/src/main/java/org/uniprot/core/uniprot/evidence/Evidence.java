package org.uniprot.core.uniprot.evidence;

import java.io.Serializable;

import org.uniprot.core.DBCrossReference;

public interface Evidence extends Comparable<Evidence>, Serializable {
    EvidenceCode getEvidenceCode();

    DBCrossReference<EvidenceDatabase> getSource();

    String getValue();
}
