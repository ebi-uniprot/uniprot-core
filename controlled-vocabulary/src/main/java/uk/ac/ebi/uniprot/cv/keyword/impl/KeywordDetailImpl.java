package uk.ac.ebi.uniprot.cv.keyword.impl;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.cv.keyword.GeneOntology;
import uk.ac.ebi.uniprot.cv.keyword.Keyword;
import uk.ac.ebi.uniprot.cv.keyword.KeywordDetail;

public class KeywordDetailImpl implements KeywordDetail {
	
	private Keyword keyword;
	private  String definition;
	private  List<String> synonyms;
	private  List<GeneOntology> geneOntologies;
	private  List<KeywordDetail> parents;
	private  List<String> sites;
	private KeywordDetail category;
	private  List<KeywordDetail> children =new ArrayList<>();
	
	
	public KeywordDetailImpl() {
		
	}
	
	public KeywordDetailImpl(Keyword keyword, String definition, List<String> synonyms,
			List<GeneOntology> geneOntologies, List<KeywordDetail> hierarchy, List<String> sites, KeywordDetail category) {
		this.keyword = keyword;
		this.definition = definition;
		this.synonyms = synonyms;
		this.geneOntologies = geneOntologies;
		this.parents = hierarchy;
		this.sites = sites;
		this.category = category;
	}




	

	public void setDefinition(String definition) {
		this.definition = definition;
	}




	public void setSynonyms(List<String> synonyms) {
		this.synonyms = synonyms;
	}




	public void setGeneOntologies(List<GeneOntology> geneOntologies) {
		this.geneOntologies = geneOntologies;
	}




	public void setSites(List<String> sites) {
		this.sites = sites;
	}




	public List<KeywordDetail> getParents() {
		return parents;
	}




	public void setParents(List<KeywordDetail> hierarchy) {
		this.parents = hierarchy;
		for(KeywordDetail keyword : hierarchy) {
			if (!keyword.getChildren().contains(this)) {
				keyword.getChildren().add(this);
			}
		}
		
	}




	public KeywordDetail getCategory() {
		return category;
	}




	public void setCategory(KeywordDetail category) {
		this.category = category;
	}



	public Keyword getKeyword() {
		return keyword;
	}


	public void setKeyword(Keyword keyword) {
		this.keyword = keyword;
	}

	public String getDefinition() {
		return definition;
	}




	public List<String> getSynonyms() {
		return synonyms;
	}




	public List<GeneOntology> getGeneOntologies() {
		return geneOntologies;
	}




	public List<String> getSites() {
		return sites;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((definition == null) ? 0 : definition.hashCode());
		result = prime * result + ((geneOntologies == null) ? 0 : geneOntologies.hashCode());
		result = prime * result + ((parents == null) ? 0 : parents.hashCode());
		result = prime * result + ((keyword == null) ? 0 : keyword.hashCode());
		result = prime * result + ((sites == null) ? 0 : sites.hashCode());
		result = prime * result + ((synonyms == null) ? 0 : synonyms.hashCode());
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
		KeywordDetailImpl other = (KeywordDetailImpl) obj;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (definition == null) {
			if (other.definition != null)
				return false;
		} else if (!definition.equals(other.definition))
			return false;
		if (geneOntologies == null) {
			if (other.geneOntologies != null)
				return false;
		} else if (!geneOntologies.equals(other.geneOntologies))
			return false;
		if (parents == null) {
			if (other.parents != null)
				return false;
		} else if (!parents.equals(other.parents))
			return false;
		if (keyword == null) {
			if (other.keyword != null)
				return false;
		} else if (!keyword.equals(other.keyword))
			return false;
		if (sites == null) {
			if (other.sites != null)
				return false;
		} else if (!sites.equals(other.sites))
			return false;
		if (synonyms == null) {
			if (other.synonyms != null)
				return false;
		} else if (!synonyms.equals(other.synonyms))
			return false;
		return true;
	}

	@Override
	public List<KeywordDetail> getChildren() {
		return children;
	}

	@Override
	public String getAccession() {
		return getKeyword().getAccession();
	}



	
}
