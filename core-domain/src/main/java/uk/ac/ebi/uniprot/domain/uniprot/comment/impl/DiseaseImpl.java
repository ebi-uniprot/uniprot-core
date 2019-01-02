package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Disease;
import uk.ac.ebi.uniprot.domain.uniprot.comment.DiseaseReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.util.Utils;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DiseaseImpl implements Disease {


	private final String diseaseId;
	private final String diseaseAccession;
	private final String acronym;
	private final String description;
	private final DBCrossReference<DiseaseReferenceType> reference;
	private final List<Evidence> evidences;
	
	public static final String DEFAULT_ACCESSION ="DI-00000";
	@JsonCreator
	public DiseaseImpl(@JsonProperty("diseaseId") String diseaseId, 
			@JsonProperty("diseaseAccession") String diseaseAccession, 
			@JsonProperty("acronym")String acronym,
			@JsonProperty("description")String description,
			@JsonProperty("reference") DBCrossReference<DiseaseReferenceType> reference,
			@JsonProperty("evidences") List<Evidence> evidences
			) {
		this.diseaseId = diseaseId;
		if(Strings.isNullOrEmpty(diseaseAccession)) {
			this.diseaseAccession =DEFAULT_ACCESSION;
		}else
			this.diseaseAccession = diseaseAccession;
		this.acronym = acronym;
		this.description = description;
		this.reference = reference;
		this.evidences = Utils.unmodifierList(evidences);		
	}


	@Override
	public String getDiseaseId() {
		return diseaseId;
	}
	@Override
	public String getDiseaseAccession() {
		return diseaseAccession;
	}


	@Override
	public String getAcronym() {
		return acronym;
	}

	@Override
	public String getDescription() {
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
		return !Strings.isNullOrEmpty(description);
	}

	private boolean isValidReference() {
		return (getReference() != null && !Strings.isNullOrEmpty(getReference().getId())
				&& getReference().getDatabaseType() != DiseaseReferenceType.NONE);
	}


	@Override
	public List<Evidence> getEvidences() {
		return evidences;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acronym == null) ? 0 : acronym.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((diseaseAccession == null) ? 0 : diseaseAccession.hashCode());
		result = prime * result + ((diseaseId == null) ? 0 : diseaseId.hashCode());
		result = prime * result + ((evidences == null) ? 0 : evidences.hashCode());
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
		if (diseaseAccession == null) {
			if (other.diseaseAccession != null)
				return false;
		} else if (!diseaseAccession.equals(other.diseaseAccession))
			return false;
		if (diseaseId == null) {
			if (other.diseaseId != null)
				return false;
		} else if (!diseaseId.equals(other.diseaseId))
			return false;
		if (evidences == null) {
			if (other.evidences != null)
				return false;
		} else if (!evidences.equals(other.evidences))
			return false;
		if (reference == null) {
			if (other.reference != null)
				return false;
		} else if (!reference.equals(other.reference))
			return false;
		return true;
	}




}
