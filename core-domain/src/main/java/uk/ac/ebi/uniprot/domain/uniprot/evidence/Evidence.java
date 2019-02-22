package uk.ac.ebi.uniprot.domain.uniprot.evidence;

import uk.ac.ebi.uniprot.domain.DBCrossReference;

import java.io.Serializable;

public interface Evidence extends Comparable<Evidence>, Serializable {
    EvidenceCode getEvidenceCode();

    DBCrossReference<EvidenceType> getSource();

    String getValue();
}
