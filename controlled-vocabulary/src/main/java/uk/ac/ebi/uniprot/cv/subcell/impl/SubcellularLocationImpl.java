package uk.ac.ebi.uniprot.cv.subcell.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import uk.ac.ebi.uniprot.cv.keyword.GeneOntology;
import uk.ac.ebi.uniprot.cv.keyword.Keyword;
import uk.ac.ebi.uniprot.cv.subcell.SubcellLocationType;
import uk.ac.ebi.uniprot.cv.subcell.SubcellularLocation;

public class SubcellularLocationImpl implements SubcellularLocation {
	private SubcellLocationType type;
	private String id;
	private String accession;
	private String definition;
	private String content;
	private List<String> synonyms =  Collections.emptyList();;
	private Keyword keyword;
	private List<GeneOntology> geneOntologies =  Collections.emptyList();;
	private String note;
	private List<String> references  = Collections.emptyList();
	private List<String> links  =  Collections.emptyList();
	private List<SubcellularLocation> isA = Collections.emptyList();
	private List<SubcellularLocation> partOf = Collections.emptyList();
	@Override
	public SubcellLocationType getType() {
		return type;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getAccession() {
		return accession;
	}

	@Override
	public String getDefinition() {
		return definition;
	}
	
	@Override
	public String getContent() {
		return content;
	}


	@Override
	public List<String> getSynonyms() {
		return synonyms;
	}

	@Override
	public Optional<Keyword> getKeyword() {
		return Optional.ofNullable(keyword);
	}

	@Override
	public List<GeneOntology> getGeneOntologies() {
		return geneOntologies;
	}

	@Override
	public String getNote() {
		return note;
	}

	@Override
	public List<String> getReferences() {
		return references;
	}

	@Override
	public List<String> getLinks() {
		return links;
	}

	@Override
	public List<SubcellularLocation> getIsA() {
		return isA;
	}

	@Override
	public List<SubcellularLocation> getPartOf() {
		return partOf;
	}

	public void setType(SubcellLocationType type) {
		this.type = type;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setAccession(String accession) {
		this.accession = accession;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setSynonyms(List<String> synonyms) {
		this.synonyms = synonyms;
	}

	public void setKeyword(Keyword keyword) {
		this.keyword = keyword;
	}

	public void setGeneOntologies(List<GeneOntology> geneOntologies) {
		this.geneOntologies = geneOntologies;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setReferences(List<String> references) {
		this.references = references;
	}

	public void setLinks(List<String> links) {
		this.links = links;
	}

	public void setIsA(List<SubcellularLocation> isA) {
		this.isA = isA;
	}

	public void setPartOf(List<SubcellularLocation> partOf) {
		this.partOf = partOf;
	}

	
}
