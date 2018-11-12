package uk.ac.ebi.uniprot.domain.uniprot.evidence;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import uk.ac.ebi.uniprot.domain.DBCrossReference;

@JsonTypeInfo(use = NAME, include = PROPERTY)
@JsonSubTypes({
  @JsonSubTypes.Type(value=uk.ac.ebi.uniprot.domain.uniprot.impl.EvidenceImpl.class, name = "evidence")
})

public interface Evidence extends Comparable<Evidence> {
	EvidenceCode getEvidenceCode();
	DBCrossReference getSource();
	String getValue();
}
