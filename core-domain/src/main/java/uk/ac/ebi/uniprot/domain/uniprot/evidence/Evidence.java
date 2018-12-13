package uk.ac.ebi.uniprot.domain.uniprot.evidence;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.util.json.EvidenceDeserializer;
import uk.ac.ebi.uniprot.domain.util.json.EvidenceSerializer;

public interface Evidence extends Comparable<Evidence> {
	EvidenceCode getEvidenceCode();
	DBCrossReference<EvidenceType> getSource();
	String getValue();
}
