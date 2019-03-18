package uk.ac.ebi.uniprot.cv.keyword;

import java.util.List;

public interface KeywordDetail {
	Keyword getKeyword();
	String getDefinition();
	List<String> getSynonyms();
	List<GeneOntology> getGeneOntologies();
	List<KeywordDetail> getParents();
	List<String> getSites();
	KeywordDetail getCategory();
	List<KeywordDetail> getChildren();
	String getAccession();
	
}
