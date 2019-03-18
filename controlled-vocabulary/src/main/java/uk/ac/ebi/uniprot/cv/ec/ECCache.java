package uk.ac.ebi.uniprot.cv.ec;

import uk.ac.ebi.uniprot.cv.impl.ECFileReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created 15/03/19
 *
 * @author Edd
 */
public enum ECCache {
    INSTANCE;
    public static final String ENZYME_DAT = "enzyme.dat";
    public static final String ENZCLASS_TXT = "enzclass.txt";
    private static final String FTP_LOCATION = "ftp://ftp.expasy.org/databases/enzyme/";
    Map<String, List<EC>> locationECMap = new HashMap<>();

    public List<EC> get(String dir) {
        String filename = dir;
        if ((filename == null) || filename.isEmpty()) {
            filename = FTP_LOCATION;
        }

        List<EC> result = locationECMap.get(filename);
        if (result != null)
            return result;

        result = buildCache(filename);
        if (result.isEmpty() && !FTP_LOCATION.equals(filename)) {
            result = locationECMap.get(FTP_LOCATION);
            if (result == null) {
                result = buildCache(FTP_LOCATION);
                locationECMap.put(FTP_LOCATION, result);
                return result;
            } else {
                return result;
            }
        } else {
            locationECMap.put(filename, result);
            return result;
        }

    }

    private List<EC> buildCache(String dirLocation) {
        return new ECFileReader().parse(dirLocation);
    }
}
