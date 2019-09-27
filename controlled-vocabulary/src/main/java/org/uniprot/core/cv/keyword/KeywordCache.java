package org.uniprot.core.cv.keyword;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uniprot.core.cv.common.AbstractFileReader;
import org.uniprot.core.cv.common.BaseCache;

public enum KeywordCache implements BaseCache<KeywordEntry> {
    INSTANCE;
    public static final String FTP_LOCATION =
            "ftp://ftp.uniprot.org/pub/databases/uniprot/knowledgebase/docs/keywlist.txt";
    private Map<String, List<KeywordEntry>> locationKeywordMap = new HashMap<>();
    private AbstractFileReader<KeywordEntry> reader;

    private String defaultDataLocation = FTP_LOCATION;

    @Override
    public String getDefaultDataFile() {
        return this.defaultDataLocation;
    }

    @Override
    public void setDefaultDataFile(String dataFile) {
        this.defaultDataLocation = dataFile;
    }

    @Override
    public Map<String, List<KeywordEntry>> getCacheMap() {
        return this.locationKeywordMap;
    }

    @Override
    public AbstractFileReader<KeywordEntry> getReader() {
        return this.reader != null ? this.reader : (this.reader = new KeywordFileReader());
    }
}
