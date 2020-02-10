package org.uniprot.cv.keyword;

import java.util.Collection;
import java.util.List;

import org.uniprot.core.cv.keyword.KeywordEntry;

public interface KeywordService {
    KeywordEntry getByAccession(String id);

    Collection<KeywordEntry> getAll();

    List<KeywordEntry> getAllCategories();
}
