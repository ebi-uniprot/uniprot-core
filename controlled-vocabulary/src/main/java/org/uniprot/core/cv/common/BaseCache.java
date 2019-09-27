package org.uniprot.core.cv.common;

import java.util.List;
import java.util.Map;

public interface BaseCache<T> {

    String getDefaultDataFile();

    void setDefaultDataFile(String dataFile);

    Map<String, List<T>> getCacheMap();

    AbstractFileReader<T> getReader();

    default List<T> get(String dir) {
        String filename = dir;
        if ((filename == null) || filename.isEmpty()) {
            filename = getDefaultDataFile();
        }

        List<T> result = getCacheMap().get(filename);
        if (result != null) {
            return result;
        }

        result = buildCache(filename);
        if (result.isEmpty() && !getDefaultDataFile().equals(filename)) {
            result = getCacheMap().get(getDefaultDataFile());
            if (result == null) {
                result = buildCache(getDefaultDataFile());
                getCacheMap().put(getDefaultDataFile(), result);
                return result;
            } else {
                return result;
            }
        } else {
            getCacheMap().put(filename, result);
            return result;
        }
    }

    default List<T> buildCache(String filename) {
        return getReader().parse(filename);
    }
}
