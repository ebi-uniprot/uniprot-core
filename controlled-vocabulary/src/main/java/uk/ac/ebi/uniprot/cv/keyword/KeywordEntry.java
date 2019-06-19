package uk.ac.ebi.uniprot.cv.keyword;

import java.util.List;
import java.util.Set;

public interface KeywordEntry {
	Keyword getKeyword();
	String getDefinition();
	List<String> getSynonyms();
	List<GeneOntology> getGeneOntologies();

    Set<KeywordEntry> getParents();
	List<String> getSites();

    Keyword getCategory();

    List<KeywordEntry> getChildren();
	String getAccession();

    KeywordStatistics getStatistics();
}
