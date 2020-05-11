package org.uniprot.cv.keyword;

import org.uniprot.core.cv.keyword.KeywordEntry;

import java.util.Collection;
import java.util.List;

public interface KeywordRepo {
    KeywordEntry getByAccession(String id);

    Collection<KeywordEntry> getAll();

    List<KeywordEntry> getAllCategories();
}
