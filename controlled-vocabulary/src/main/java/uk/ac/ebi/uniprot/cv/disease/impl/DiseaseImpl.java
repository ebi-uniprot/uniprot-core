package uk.ac.ebi.uniprot.cv.disease.impl;

import java.util.List;

import uk.ac.ebi.uniprot.cv.disease.CrossReference;
import uk.ac.ebi.uniprot.cv.disease.Disease;
import uk.ac.ebi.uniprot.cv.keyword.Keyword;



public class DiseaseImpl  implements Disease{

    private  String id;

    private final String accession;

    private  String acronym;
    private  String definition;
    private  List<String> alternativeNames;
    private List<CrossReference> crossReferences;
    private List<Keyword> keywords;
    private Long proteinCount;

    private DiseaseImpl(){
    	// do nothing.. just to satisfy the objectmapper
		this.accession = null;
	}

	public DiseaseImpl(String id, String accession, String acronym, String definition, List<String> alternativeNames,
			List<CrossReference> crossReferences, List<Keyword> keywords) {
		super();
		this.id = id;
		this.accession = accession;
		this.acronym = acronym;
		this.definition = definition;
		this.alternativeNames = alternativeNames;
		this.crossReferences = crossReferences;
		this.keywords = keywords;
	}

	public DiseaseImpl(String id, String accession, String acronym, String definition, List<String> alternativeNames,
					   List<CrossReference> crossReferences, List<Keyword> keywords, Long proteinCount) {

		this(id, accession, acronym, definition, alternativeNames, crossReferences, keywords);
		this.proteinCount = proteinCount;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAcronym() {
		return acronym;
	}
	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}
	public String getDefinition() {
		return definition;
	}
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	public List<String> getAlternativeNames() {
		return alternativeNames;
	}
	public void setAlternativeNames(List<String> alternativeNames) {
		this.alternativeNames = alternativeNames;
	}
	public List<CrossReference> getCrossReferences() {
		return crossReferences;
	}
	public void setCrossReferences(List<CrossReference> crossReferences) {
		this.crossReferences = crossReferences;
	}
	public List<Keyword> getKeywords() {
		return keywords;
	}
	public void setKeywords(List<Keyword> keywords) {
		this.keywords = keywords;
	}
	public String getAccession() {
		return accession;
	}

	@Override
	public Long getProteinCount() {
		return this.proteinCount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accession == null) ? 0 : accession.hashCode());
		result = prime * result + ((acronym == null) ? 0 : acronym.hashCode());
		result = prime * result + ((alternativeNames == null) ? 0 : alternativeNames.hashCode());
		result = prime * result + ((crossReferences == null) ? 0 : crossReferences.hashCode());
		result = prime * result + ((definition == null) ? 0 : definition.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((keywords == null) ? 0 : keywords.hashCode());
		result = prime * result + ((this.proteinCount == null) ? 0 : this.proteinCount.hashCode());
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
		if (accession == null) {
			if (other.accession != null)
				return false;
		} else if (!accession.equals(other.accession))
			return false;
		if (acronym == null) {
			if (other.acronym != null)
				return false;
		} else if (!acronym.equals(other.acronym))
			return false;
		if (alternativeNames == null) {
			if (other.alternativeNames != null)
				return false;
		} else if (!alternativeNames.equals(other.alternativeNames))
			return false;
		if (crossReferences == null) {
			if (other.crossReferences != null)
				return false;
		} else if (!crossReferences.equals(other.crossReferences))
			return false;
		if (definition == null) {
			if (other.definition != null)
				return false;
		} else if (!definition.equals(other.definition))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (keywords == null) {
			if (other.keywords != null)
				return false;
		} else if (!keywords.equals(other.keywords))
			return false;

		if (this.proteinCount == null) {
			if (other.proteinCount != null)
				return false;
		} else if (!this.proteinCount.equals(other.proteinCount))
			return false;

		return true;
	}

}