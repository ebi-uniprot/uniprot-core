package org.uniprot.core.cv.subcell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uniprot.core.cv.common.AbstractFileReader;
import org.uniprot.core.cv.common.BaseCache;

public enum SubcellularLocationCache implements BaseCache<SubcellularLocationEntry> {
    INSTANCE;
    public static final String FTP_LOCATION =
            "ftp://ftp.uniprot.org/pub/databases/uniprot/knowledgebase/docs/subcell.txt";
    private Map<String, List<SubcellularLocationEntry>> locationSubcellularLocationMap =
            new HashMap<>();
    private AbstractFileReader<SubcellularLocationEntry> reader;
    private String defaultDataLocation = FTP_LOCATION;

    @Override
    public String getDefaultDataFile() {
        return this.defaultDataLocation;
    }

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
