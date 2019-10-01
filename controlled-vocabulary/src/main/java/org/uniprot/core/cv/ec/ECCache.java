package org.uniprot.core.cv.ec;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uniprot.core.cv.common.AbstractFileReader;
import org.uniprot.core.cv.common.BaseCache;

/**
 * Created 15/03/19
 *
 * @author Edd
 */
public enum ECCache implements BaseCache<EC> {
    INSTANCE;
    public static final String ENZYME_DAT = "enzyme.dat";
    public static final String ENZCLASS_TXT = "enzclass.txt";
    private static final String FTP_LOCATION = "ftp://ftp.expasy.org/databases/enzyme/";
    private Map<String, List<EC>> locationECMap = new HashMap<>();
    private AbstractFileReader<EC> reader;

    private String defaultDataLocation = FTP_LOCATION;

    @Override
    public String getDefaultDataFile() {
        return this.defaultDataLocation;
    }

    @Override
    public Map<String, List<EC>> getCacheMap() {
        return this.locationECMap;
    }

    @Override
    public AbstractFileReader<EC> getReader() {
        return this.reader != null ? this.reader : (this.reader = new ECFileReader());
    }
}
