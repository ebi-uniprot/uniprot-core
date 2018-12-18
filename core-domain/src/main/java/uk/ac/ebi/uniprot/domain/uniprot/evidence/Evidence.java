package uk.ac.ebi.uniprot.domain.uniprot.evidence;

import uk.ac.ebi.uniprot.domain.DBCrossReference;

public interface Evidence extends Comparable<Evidence> {
    EvidenceCode getEvidenceCode();

    DBCrossReference<EvidenceType> getSource();

    String getValue();
}
