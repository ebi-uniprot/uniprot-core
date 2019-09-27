package org.uniprot.core.cv.disease;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uniprot.core.cv.common.AbstractFileReader;
import org.uniprot.core.cv.common.BaseCache;

public enum DiseaseCache implements BaseCache<Disease> {
    INSTANCE;
    public static final String FTP_LOCATION =
            "ftp://ftp.uniprot.org/pub/databases/uniprot/knowledgebase/docs/humdisease.txt";
    private Map<String, List<Disease>> locationDiseaseMap = new HashMap<>();
    private AbstractFileReader<Disease> reader;

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
    public Map<String, List<Disease>> getCacheMap() {
        return this.locationDiseaseMap;
    }

    @Override
    public AbstractFileReader<Disease> getReader() {
        return this.reader != null ? this.reader : (this.reader = new DiseaseFileReader());
    }
}
