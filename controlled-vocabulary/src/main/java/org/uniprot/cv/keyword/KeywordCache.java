package org.uniprot.cv.keyword;

import org.uniprot.core.cv.keyword.KeywordEntry;
import org.uniprot.cv.common.AbstractFileReader;
import org.uniprot.cv.common.BaseCache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum KeywordCache implements BaseCache<KeywordEntry> {
    INSTANCE;
    private Map<String, List<KeywordEntry>> locationKeywordMap = new HashMap<>();
    private AbstractFileReader<KeywordEntry> reader;

    @Override
    public Map<String, List<KeywordEntry>> getCacheMap() {
        return this.locationKeywordMap;
    }

    @Override
    public AbstractFileReader<KeywordEntry> getReader() {
        return this.reader != null ? this.reader : (this.reader = new KeywordFileReader());
    }
}
