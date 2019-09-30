package org.uniprot.core.cv.keyword.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.uniprot.core.cv.keyword.KeywordCache;
import org.uniprot.core.cv.keyword.KeywordEntry;
import org.uniprot.core.cv.keyword.KeywordService;

public class KeywordServiceImpl implements KeywordService {
    private Map<String, KeywordEntry> keywordAccessionMap;
    private List<KeywordEntry> categories;

    public KeywordServiceImpl(String filename) {
        List<KeywordEntry> keywords = KeywordCache.INSTANCE.get(filename);
        keywordAccessionMap =
                keywords.stream()
                        .collect(Collectors.toMap(KeywordEntry::getAccession, Function.identity()));
        categories =
                keywords.stream()
                        .filter(val -> (val.getParents() == null) || val.getParents().isEmpty())
                        .collect(Collectors.toList());
    }

    public KeywordServiceImpl() {
        this(KeywordCache.FTP_LOCATION);
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
