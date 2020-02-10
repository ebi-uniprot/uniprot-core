package org.uniprot.cv.pathway;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uniprot.core.cv.pathway.Pathway;

public enum PathwayCache {
    INSTANCE;
    public static final String FTP_LOCATION =
            "ftp://ftp.uniprot.org/pub/databases/uniprot/current_release/knowledgebase/complete/docs/pathlist.txt";
    Map<String, List<Pathway>> locationPathwayMap = new HashMap<>();

    public List<Pathway> get(String file) {
        String filename = file;
        if ((filename == null) || filename.isEmpty()) {
            filename = FTP_LOCATION;
        }

        List<Pathway> result = locationPathwayMap.get(filename);
        if (result != null) return result;

        result = buildCache(filename);
        if (result.isEmpty() && !FTP_LOCATION.equals(filename)) {
            result = locationPathwayMap.get(FTP_LOCATION);
            if (result == null) {
                result = buildCache(FTP_LOCATION);
                locationPathwayMap.put(FTP_LOCATION, result);
                return result;
            } else {
                return result;
            }
        } else {
            locationPathwayMap.put(filename, result);
            return result;
        }
    }

    private List<Pathway> buildCache(String filename) {
        PathwayFileReader reader = new PathwayFileReader();
        return reader.parse(filename);
    }
}
