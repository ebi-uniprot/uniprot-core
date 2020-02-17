package org.uniprot.cv.disease;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uniprot.core.cv.disease.DiseaseEntry;
import org.uniprot.cv.common.AbstractFileReader;
import org.uniprot.cv.common.BaseCache;

public enum DiseaseCache implements BaseCache<DiseaseEntry> {
    INSTANCE;
    public static final String FTP_LOCATION =
            "ftp://ftp.uniprot.org/pub/databases/uniprot/knowledgebase/docs/humdisease.txt";
    private Map<String, List<DiseaseEntry>> locationDiseaseMap = new HashMap<>();
    private AbstractFileReader<DiseaseEntry> reader;

    private String defaultDataLocation = FTP_LOCATION;

    @Override
    public String getDefaultDataFile() {
        return this.defaultDataLocation;
    }

    @Override
    public Map<String, List<DiseaseEntry>> getCacheMap() {
        return this.locationDiseaseMap;
    }

    @Override
    public AbstractFileReader<DiseaseEntry> getReader() {
        return this.reader != null ? this.reader : (this.reader = new DiseaseFileReader());
    }
}
