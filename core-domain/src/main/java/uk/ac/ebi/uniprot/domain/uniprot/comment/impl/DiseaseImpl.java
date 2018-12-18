package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Disease;
import uk.ac.ebi.uniprot.domain.uniprot.comment.DiseaseDescription;
import uk.ac.ebi.uniprot.domain.uniprot.comment.DiseaseReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidencedValueImpl;

import java.util.Collections;
import java.util.List;
public class DiseaseImpl implements Disease {

	public static DiseaseDescription createDiseaseDescription(String val, List<Evidence> evidences) {
		return new DiseaseDescriptionImpl(val, evidences);
	}

	private String diseaseId;
    private String diseaseAccession;
	private String acronym;
	private DiseaseDescription description;
	private DBCrossReference<DiseaseReferenceType> reference;
    public static final String DEFAULT_ACCESSION ="DI-00000";

	private DiseaseImpl(){

	}

	public DiseaseImpl(String diseaseId, String diseaseAccession, String acronym, DiseaseDescription description,
			DBCrossReference<DiseaseReferenceType> reference) {
        this.diseaseId = diseaseId;
        if (diseaseAccession == null || diseaseAccession.isEmpty()) {
            this.diseaseAccession = DEFAULT_ACCESSION;
        } else {
            this.diseaseAccession = diseaseAccession;
        }
		this.acronym = acronym;
		this.description = description;
		this.reference = reference;
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
	public DiseaseDescription getDescription() {
		return description;
	}

	@Override
	public DBCrossReference<DiseaseReferenceType> getReference() {
		return reference;
	}

	@Override
	public boolean hasDefinedDisease() {
		return (diseaseId != null && !diseaseId.isEmpty() && (getAcronym() != null && !getAcronym().isEmpty())
				&& isValidDescription() && isValidReference());
	}

	private boolean isValidDescription() {
		return getDescription() != null && getDescription().getValue() != null
				&& !getDescription().getValue().isEmpty();
	}

	private boolean isValidReference() {
		return (getReference() != null && getReference().getId() != null && !getReference().getId().isEmpty()
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


	public static class DiseaseDescriptionImpl extends EvidencedValueImpl implements DiseaseDescription {

		private DiseaseDescriptionImpl(){
			super(null, Collections.emptyList());
		}

		public DiseaseDescriptionImpl(String value,List<Evidence> evidences) {
			super(value, evidences);

		}

	}

}
