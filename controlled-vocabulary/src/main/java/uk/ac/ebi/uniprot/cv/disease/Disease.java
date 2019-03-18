package uk.ac.ebi.uniprot.cv.disease;

import java.util.List;

import uk.ac.ebi.uniprot.cv.keyword.Keyword;


public interface Disease {
	String getId();
	String getAccession();
	String getAcronym();
	String getDefinition();
	List<String> getAlternativeNames();
	List<CrossReference> getCrossReferences();
	List<Keyword> getKeywords();
	
}
