package uk.ac.ebi.uniprot.cv.keyword;

import java.util.Collection;

public interface KeywordService {
	KeywordDetail getByAccession(String id);
	Collection<KeywordDetail> getAll();
}
