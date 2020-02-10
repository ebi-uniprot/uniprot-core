package org.uniprot.cv.keyword;

import java.util.Collection;
import java.util.List;

public interface KeywordService {
    KeywordEntry getByAccession(String id);

    Collection<KeywordEntry> getAll();

    List<KeywordEntry> getAllCategories();
}
