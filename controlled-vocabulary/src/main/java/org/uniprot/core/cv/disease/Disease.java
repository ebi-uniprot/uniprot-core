package org.uniprot.core.cv.disease;

import org.uniprot.core.cv.keyword.Keyword;

import java.util.List;


public interface Disease {
	String getId();
	String getAccession();
	String getAcronym();
	String getDefinition();
	List<String> getAlternativeNames();
	List<CrossReference> getCrossReferences();
	List<Keyword> getKeywords();
	Long getReviewedProteinCount();
	Long getUnreviewedProteinCount();
}
