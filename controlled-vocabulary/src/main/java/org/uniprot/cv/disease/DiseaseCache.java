package org.uniprot.cv.disease;

import org.uniprot.core.cv.disease.DiseaseEntry;
import org.uniprot.cv.common.AbstractFileReader;
import org.uniprot.cv.common.BaseCache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum DiseaseCache implements BaseCache<DiseaseEntry> {
    INSTANCE;

    private Map<String, List<DiseaseEntry>> locationDiseaseMap = new HashMap<>();
    private AbstractFileReader<DiseaseEntry> reader;

    @Override
    public Map<String, List<DiseaseEntry>> getCacheMap() {
        return this.locationDiseaseMap;
    }

    @Override
    public AbstractFileReader<DiseaseEntry> getReader() {
        return this.reader != null ? this.reader : (this.reader = new DiseaseFileReader());
    }
}
