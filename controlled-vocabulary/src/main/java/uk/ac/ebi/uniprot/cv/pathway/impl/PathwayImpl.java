package uk.ac.ebi.uniprot.cv.pathway.impl;

import java.util.List;

import uk.ac.ebi.uniprot.cv.disease.CrossReference;
import uk.ac.ebi.uniprot.cv.pathway.Pathway;

public class PathwayImpl implements Pathway {
	private String accession;
	private String id;
	private String pathwayClass;
	private String definition;
	private  List<String> synonyms;
	private List<Pathway> isAParents;
	private List<Pathway> partOfParents;
	private List<CrossReference> crossReferences;
	@Override
	public String getAccession() {
		return accession;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getPathwayClass() {
		return pathwayClass;
	}

	@Override
	public String getDefinition() {
		return definition;
	}
	@Override
	public List<String> getSynonyms() {
		return synonyms;
	}

	@Override
	public List<Pathway> getIsAParents() {
		return isAParents;
	}

	@Override
	public List<Pathway> getPartOfParents() {
		return partOfParents;
	}

	@Override
	public List<CrossReference> getCrossReferences() {
		return crossReferences;
	}

	public void setAccession(String accession) {
		this.accession = accession;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPathwayClass(String pathwayClass) {
		this.pathwayClass = pathwayClass;
	}
	
	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public void setSynonyms(List<String> synonyms) {
		this.synonyms = synonyms;
	}

	public void setIsAParents(List<Pathway> isAParents) {
		this.isAParents = isAParents;
	}

	public void setPartOfParents(List<Pathway> partOfParents) {
		this.partOfParents = partOfParents;
	}

	public void setCrossReferences(List<CrossReference> crossReferences) {
		this.crossReferences = crossReferences;
	}


}
