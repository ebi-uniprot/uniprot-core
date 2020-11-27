package org.uniprot.cv.go.impl;

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
enum GORelationsCache implements BaseCache<GORelationFileReader.GORelationshipsEntry> {
    INSTANCE;
    private final Map<String, List<GORelationFileReader.GORelationshipsEntry>> goMap =
            new HashMap<>();
    private AbstractFileReader<GORelationFileReader.GORelationshipsEntry> reader;

    @Override
    public Map<String, List<GORelationFileReader.GORelationshipsEntry>> getCacheMap() {
        return this.goMap;
    }

    @Override
    public AbstractFileReader<GORelationFileReader.GORelationshipsEntry> getReader() {
        if (this.reader == null) {
            this.reader = new GORelationFileReader();
        }
        return this.reader;
    }
}
