package org.uniprot.cv.common;

import static org.uniprot.core.util.Utils.notNullNotEmpty;
import static org.uniprot.core.util.Utils.nullOrEmpty;

import java.util.List;
import java.util.Map;

public interface BaseCache<T> {

    Map<String, List<T>> getCacheMap();

    AbstractFileReader<T> getReader();

    default List<T> get(String dir) {
        if (nullOrEmpty(dir)) {
            throw new ControlledVocabularyFileLocationException();
        }

        List<T> result = getCacheMap().get(dir);
        if (notNullNotEmpty(result)) {
            return result;
        } else {
            result = buildCache(dir);
            getCacheMap().put(dir, result);
            return result;
        }
    }

    default List<T> buildCache(String filename) {
        return getReader().parse(filename);
    }
}
