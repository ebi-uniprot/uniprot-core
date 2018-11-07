package uk.ac.ebi.uniprot.domain.uniprot.evidence;

import uk.ac.ebi.uniprot.domain.xdb.DBCrossReference;


public interface Evidence extends Comparable<Evidence> {
	EvidenceCode getEvidenceCode();
	DBCrossReference getSource();
	String getValue();
}
