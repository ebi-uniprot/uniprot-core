package org.uniprot.cv.chebi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uniprot.core.cv.chebi.Chebi;
import org.uniprot.cv.common.AbstractFileReader;
import org.uniprot.cv.common.BaseCache;

/**
 * Created 05/06/19
 *
 * @author Edd
 */
public enum ChebiCache implements BaseCache<Chebi> {
    INSTANCE;

    private static final String FTP_LOCATION =
            "ftp://ftp.ebi.ac.uk/pub/databases/chebi/ontology/chebi.obo";
    private Map<String, List<Chebi>> chebiMap = new HashMap<>();
    private AbstractFileReader<Chebi> reader;
    private String defaultDataLocation = FTP_LOCATION;

    @Override
    public String getDefaultDataFile() {
        return this.defaultDataLocation;
    }

    @Override
    public Map<String, List<Chebi>> getCacheMap() {
        return this.chebiMap;
    }

    @Override
    public AbstractFileReader<Chebi> getReader() {
        return this.reader != null ? this.reader : (this.reader = new ChebiFileReader());
    }
}
