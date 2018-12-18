package uk.ac.ebi.uniprot.cv.keyword;

import java.util.List;

public interface KeywordDetail {
	Keyword getKeyword();
	String getDefinition();
	List<String> getSynonyms();
	List<GeneOntology> getGeneOntologies();
	List<Keyword> getHierarchy();
	List<String> getSites();
	Keyword getCategory();
	
}
