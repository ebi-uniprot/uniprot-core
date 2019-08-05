package org.uniprot.core.cv.disease.impl;

import java.util.List;
import java.util.Objects;

import org.uniprot.core.cv.disease.CrossReference;
import org.uniprot.core.cv.disease.Disease;
import org.uniprot.core.cv.keyword.Keyword;



public class DiseaseImpl  implements Disease{

    private  String id;

    private final String accession;

    private  String acronym;
    private  String definition;
    private  List<String> alternativeNames;
    private List<CrossReference> crossReferences;
    private List<Keyword> keywords;
    private Long reviewedProteinCount;
    private Long unreviewedProteinCount;

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
					   List<CrossReference> crossReferences, List<Keyword> keywords,
					   Long reviewedProteinCount, Long unreviewedProteinCount) {

		this(id, accession, acronym, definition, alternativeNames, crossReferences, keywords);
		this.reviewedProteinCount = reviewedProteinCount;
		this.unreviewedProteinCount = unreviewedProteinCount;
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
	public Long getReviewedProteinCount() {
		return this.reviewedProteinCount;
	}

	@Override
	public Long getUnreviewedProteinCount() {
		return this.unreviewedProteinCount;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		DiseaseImpl disease = (DiseaseImpl) o;
		return Objects.equals(id, disease.id) &&
				Objects.equals(accession, disease.accession) &&
				Objects.equals(acronym, disease.acronym) &&
				Objects.equals(definition, disease.definition) &&
				Objects.equals(alternativeNames, disease.alternativeNames) &&
				Objects.equals(crossReferences, disease.crossReferences) &&
				Objects.equals(keywords, disease.keywords) &&
				Objects.equals(reviewedProteinCount, disease.reviewedProteinCount) &&
				Objects.equals(unreviewedProteinCount, disease.unreviewedProteinCount);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, accession, acronym, definition, alternativeNames, crossReferences,
				keywords, reviewedProteinCount, unreviewedProteinCount);
	}
}