package org.uniprot.cv.go.impl;

import org.uniprot.core.cv.go.GeneOntologyEntry;
import org.uniprot.cv.common.AbstractFileReader;
import org.uniprot.cv.common.BaseCache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created 25/11/2020
 *
 * @author Edd
 */
enum GOTermCache implements BaseCache<GeneOntologyEntry> {
    INSTANCE;
    private final Map<String, List<GeneOntologyEntry>> goMap = new HashMap<>();
    private AbstractFileReader<GeneOntologyEntry> reader;

    @Override
    public Map<String, List<GeneOntologyEntry>> getCacheMap() {
        return this.goMap;
    }

    @Override
    public AbstractFileReader<GeneOntologyEntry> getReader() {
        if (this.reader == null) {
            this.reader = new GOTermFileReader();
        }
        return this.reader;
    }
}
