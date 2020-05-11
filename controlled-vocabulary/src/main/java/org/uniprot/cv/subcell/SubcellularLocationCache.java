package org.uniprot.cv.subcell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uniprot.core.cv.subcell.SubcellularLocationEntry;
import org.uniprot.cv.common.AbstractFileReader;
import org.uniprot.cv.common.BaseCache;

public enum SubcellularLocationCache implements BaseCache<SubcellularLocationEntry> {
    INSTANCE;

    private Map<String, List<SubcellularLocationEntry>> locationSubcellularLocationMap =
            new HashMap<>();
    private AbstractFileReader<SubcellularLocationEntry> reader;

    @Override
    public Map<String, List<SubcellularLocationEntry>> getCacheMap() {
        return this.locationSubcellularLocationMap;
    }

    @Override
    public AbstractFileReader<SubcellularLocationEntry> getReader() {
        return this.reader == null
                ? (this.reader = new SubcellularLocationFileReader())
                : this.reader;
    }
}
