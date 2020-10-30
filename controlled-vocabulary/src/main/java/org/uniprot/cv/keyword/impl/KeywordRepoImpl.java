package org.uniprot.cv.keyword.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.uniprot.core.cv.keyword.KeywordEntry;
import org.uniprot.cv.keyword.KeywordCache;
import org.uniprot.cv.keyword.KeywordRepo;

public class KeywordRepoImpl implements KeywordRepo {
    private final Map<String, KeywordEntry> keywordAccessionMap;
    private final Map<String, KeywordEntry> idNameMap;
    private final List<KeywordEntry> categories;

    public KeywordRepoImpl(String filename) {
        List<KeywordEntry> keywords = KeywordCache.INSTANCE.get(filename);
        keywordAccessionMap =
                keywords.stream()
                        .collect(Collectors.toMap(KeywordEntry::getAccession, Function.identity()));
        categories =
                keywords.stream()
                        .filter(val -> (val.getParents() == null) || val.getParents().isEmpty())
                        .collect(Collectors.toList());
        idNameMap = keywords.stream()
                .collect(Collectors.toMap(kw -> kw.getKeyword().getName(), Function.identity()));
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

    @Override
    public KeywordEntry getByName(String name) {
        return idNameMap.get(name);
    }
}