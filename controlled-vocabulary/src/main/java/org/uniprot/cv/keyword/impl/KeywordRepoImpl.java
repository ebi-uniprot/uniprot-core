package org.uniprot.cv.keyword.impl;

import org.uniprot.core.cv.keyword.KeywordEntry;
import org.uniprot.cv.keyword.KeywordCache;
import org.uniprot.cv.keyword.KeywordRepo;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class KeywordRepoImpl implements KeywordRepo {
    private Map<String, KeywordEntry> keywordAccessionMap;
    private List<KeywordEntry> categories;

    public KeywordRepoImpl(String filename) {
        List<KeywordEntry> keywords = KeywordCache.INSTANCE.get(filename);
        keywordAccessionMap =
                keywords.stream()
                        .collect(Collectors.toMap(KeywordEntry::getAccession, Function.identity()));
        categories =
                keywords.stream()
                        .filter(val -> (val.getParents() == null) || val.getParents().isEmpty())
                        .collect(Collectors.toList());
    }

    @Override
    public KeywordEntry getByAccession(String id) {
        return keywordAccessionMap.get(id);
    }

    @Override
    public Collection<KeywordEntry> getAll() {
        return keywordAccessionMap.values();
    }

    @Override
    public List<KeywordEntry> getAllCategories() {
        return categories;
    }
}
