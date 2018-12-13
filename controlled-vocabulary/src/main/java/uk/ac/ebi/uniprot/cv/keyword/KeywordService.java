package uk.ac.ebi.uniprot.cv.keyword;

import java.util.List;

public interface KeywordService {
	Keyword getById(String id);
	List<KeywordDetail> getAll();
}
