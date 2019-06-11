package uk.ac.ebi.uniprot.cv.chebi;

import uk.ac.ebi.uniprot.cv.impl.ChebiFileReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created 05/06/19
 *
 * @author Edd
 */
public enum ChebiCache {
    INSTANCE;

    private static final String FTP_LOCATION = "ftp://ftp.ebi.ac.uk/pub/databases/chebi/ontology/chebi.obo";
    private Map<String, List<Chebi>> chebiMap = new HashMap<>();

    public List<Chebi> get(String dir) {
        String filename = dir;
        if ((filename == null) || filename.isEmpty()) {
            filename = FTP_LOCATION;
        }

        List<Chebi> result = chebiMap.get(filename);
        if (result != null) {
            return result;
        }

        result = buildCache(filename);
        if (result.isEmpty() && !FTP_LOCATION.equals(filename)) {
            result = chebiMap.get(FTP_LOCATION);
            if (result == null) {
                result = buildCache(FTP_LOCATION);
                chebiMap.put(FTP_LOCATION, result);
                return result;
            } else {
                return result;
            }
        } else {
            chebiMap.put(filename, result);
            return result;
        }
    }

    private List<Chebi> buildCache(String filename) {
        return new ChebiFileReader().parse(filename);
    }
}
