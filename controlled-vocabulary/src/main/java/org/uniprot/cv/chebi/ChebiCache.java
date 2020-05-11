package org.uniprot.cv.chebi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uniprot.core.cv.chebi.ChebiEntry;
import org.uniprot.cv.common.AbstractFileReader;
import org.uniprot.cv.common.BaseCache;

/**
 * Created 05/06/19
 *
 * @author Edd
 */
public enum ChebiCache implements BaseCache<ChebiEntry> {
    INSTANCE;

    private Map<String, List<ChebiEntry>> chebiMap = new HashMap<>();
    private AbstractFileReader<ChebiEntry> reader;

    @Override
    public Map<String, List<ChebiEntry>> getCacheMap() {
        return this.chebiMap;
    }

    @Override
    public AbstractFileReader<ChebiEntry> getReader() {
        return this.reader != null ? this.reader : (this.reader = new ChebiFileReader());
    }
}
