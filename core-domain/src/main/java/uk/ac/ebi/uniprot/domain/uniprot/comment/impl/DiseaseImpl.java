package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Disease;
import uk.ac.ebi.uniprot.domain.uniprot.comment.DiseaseDescription;
import uk.ac.ebi.uniprot.domain.uniprot.comment.DiseaseReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidencedValueImpl;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DiseaseImpl implements Disease {

	public static DiseaseDescription createDiseaseDescription(String val, List<Evidence> evidences) {
		return new DiseaseDescriptionImpl(val, evidences);
	}

	private final String diseaseId;
	private final String acronym;
	private final DiseaseDescription description;
	private final DBCrossReference<DiseaseReferenceType> reference;
	@JsonCreator
	public DiseaseImpl(@JsonProperty("diseaseId") String diseaseId, 
			@JsonProperty("acronym")String acronym,
			@JsonProperty("description")DiseaseDescription description,
			@JsonProperty("reference") DBCrossReference<DiseaseReferenceType> reference) {
		this.diseaseId = diseaseId;
		this.acronym = acronym;
		this.description = description;
		this.reference = reference;
	}

	@Override
	public String getDiseaseId() {
		return diseaseId;
	}

	@Override
	public String getAcronym() {
		return acronym;
	}

	@Override
	public DiseaseDescription getDescription() {
		return description;
	}

	@Override
	public DBCrossReference<DiseaseReferenceType> getReference() {
		return reference;
	}
	@JsonIgnore
	@Override
	public boolean hasDefinedDisease() {
		return (!Strings.isNullOrEmpty(diseaseId) && (getAcronym() != null && !getAcronym().isEmpty())
				&& isValidDescription() && isValidReference());
	}

	private boolean isValidDescription() {
		return getDescription() != null && getDescription().getValue() != null
				&& !getDescription().getValue().isEmpty();
	}

	private boolean isValidReference() {
		return (getReference() != null && !Strings.isNullOrEmpty(getReference().getId())
				&& getReference().getDatabaseType() != DiseaseReferenceType.NONE);
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acronym == null) ? 0 : acronym.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((diseaseId == null) ? 0 : diseaseId.hashCode());
		result = prime * result + ((reference == null) ? 0 : reference.hashCode());
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
		DiseaseImpl other = (DiseaseImpl) obj;
		if (acronym == null) {
			if (other.acronym != null)
				return false;
		} else if (!acronym.equals(other.acronym))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (diseaseId == null) {
			if (other.diseaseId != null)
				return false;
		} else if (!diseaseId.equals(other.diseaseId))
			return false;
		if (reference == null) {
			if (other.reference != null)
				return false;
		} else if (!reference.equals(other.reference))
			return false;
		return true;
	}


	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	public static class DiseaseDescriptionImpl extends EvidencedValueImpl implements DiseaseDescription {
		@JsonCreator
		public DiseaseDescriptionImpl(@JsonProperty("value") String value,
				@JsonProperty("evidences") List<Evidence> evidences) {
			super(value, evidences);

		}

	}

}
