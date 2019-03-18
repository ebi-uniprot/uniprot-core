package uk.ac.ebi.uniprot.cv.keyword;

import java.util.Collection;
import java.util.List;

public interface KeywordService {
	KeywordDetail getByAccession(String id);
	Collection<KeywordDetail> getAll();
	List<KeywordDetail> getAllCategories();
}
