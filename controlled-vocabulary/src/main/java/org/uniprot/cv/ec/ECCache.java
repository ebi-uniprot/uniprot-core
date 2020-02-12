package org.uniprot.cv.ec;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uniprot.core.cv.ec.ECEntry;
import org.uniprot.cv.common.AbstractFileReader;
import org.uniprot.cv.common.BaseCache;

/**
 * Created 15/03/19
 *
 * @author Edd
 */
public enum ECCache implements BaseCache<ECEntry> {
    INSTANCE;
    public static final String ENZYME_DAT = "enzyme.dat";
    public static final String ENZCLASS_TXT = "enzclass.txt";
    private static final String FTP_LOCATION = "ftp://ftp.expasy.org/databases/enzyme/";
    private Map<String, List<ECEntry>> locationECMap = new HashMap<>();
    private AbstractFileReader<ECEntry> reader;

    private String defaultDataLocation = FTP_LOCATION;

    @Override
    public String getDefaultDataFile() {
        return this.defaultDataLocation;
    }

    @Override
    public Map<String, List<ECEntry>> getCacheMap() {
        return this.locationECMap;
    }

    @Override
    public AbstractFileReader<ECEntry> getReader() {
        return this.reader != null ? this.reader : (this.reader = new ECFileReader());
    }
}
