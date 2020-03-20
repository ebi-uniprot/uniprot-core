package org.uniprot.cv.pathway;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uniprot.core.cv.pathway.UniPathway;
import org.uniprot.cv.common.AbstractFileReader;
import org.uniprot.cv.common.BaseCache;

public enum UniPathwayCache implements BaseCache<UniPathway> {
    INSTANCE;

    private Map<String, List<UniPathway>> locationPathwayMap = new HashMap<>();
    private AbstractFileReader<UniPathway> reader;

    @Override
    public Map<String, List<UniPathway>> getCacheMap() {
        return this.locationPathwayMap;
    }

    @Override
    public AbstractFileReader<UniPathway> getReader() {
        return this.reader != null ? this.reader : (this.reader = new UniPathwayFileReader());
    }
}
