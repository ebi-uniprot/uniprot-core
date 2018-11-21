package uk.ac.ebi.uniprot.domain.uniprot.impl;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import uk.ac.ebi.uniprot.domain.uniprot.EvidenceLine;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.util.LocalDateDeserializer;
import uk.ac.ebi.uniprot.domain.util.LocalDateSerializer;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EvidenceLineImpl implements EvidenceLine {
	private final String evidence;
	@JsonDeserialize(using = LocalDateDeserializer.class)  
	@JsonSerialize(using = LocalDateSerializer.class)  
	private final LocalDate createDate;
	private final String curator;
	
	@JsonCreator
	public EvidenceLineImpl(@JsonProperty("evidence") String evidence, 
			@JsonProperty("createDate") LocalDate createDate, 
			@JsonProperty("curator") String curator ) {
		this.evidence = evidence;
		this.createDate = createDate;
		if(curator ==null) {
			curator ="";
		}
		this.curator = curator;
	}
	@Override
	public String getEvidence() {
		return evidence;
	}

	@Override
	public LocalDate getCreateDate() {
		return createDate;
	}

	@Override
	public String getCurator() {
		return curator;
	}
	@Override
	public Evidence toEvidence() {
		return EvidenceImpl.parseEvidenceLine(evidence);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((curator == null) ? 0 : curator.hashCode());
		result = prime * result + ((createDate == null) ? 0 : createDate.hashCode());
		result = prime * result + ((evidence == null) ? 0 : evidence.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EvidenceLineImpl other = (EvidenceLineImpl) obj;
		if (curator == null) {
			if (other.curator != null)
				return false;
		} else if (!curator.equals(other.curator))
			return false;
		if (createDate == null) {
			if (other.createDate != null)
				return false;
		} else if (!createDate.equals(other.createDate))
			return false;
		if (evidence == null) {
			if (other.evidence != null)
				return false;
		} else if (!evidence.equals(other.evidence))
			return false;
		return true;
	}
	

}
