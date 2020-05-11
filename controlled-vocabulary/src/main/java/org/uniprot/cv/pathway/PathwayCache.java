package org.uniprot.cv.pathway;

import org.uniprot.core.cv.pathway.PathwayEntry;
import org.uniprot.cv.common.AbstractFileReader;
import org.uniprot.cv.common.BaseCache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum PathwayCache implements BaseCache<PathwayEntry> {
    INSTANCE;

    Map<String, List<PathwayEntry>> locationPathwayMap = new HashMap<>();
    private AbstractFileReader<PathwayEntry> reader;

    @Override
    public Map<String, List<PathwayEntry>> getCacheMap() {
        return locationPathwayMap;
    }

    @Override
    public AbstractFileReader<PathwayEntry> getReader() {
        return this.reader != null ? this.reader : (this.reader = new PathwayFileReader());
    }
}
