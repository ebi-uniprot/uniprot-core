package org.uniprot.core.parser.tsv;

import java.util.List;
import java.util.Map;

/**
 * @author lgonzales
 * @since 2020-03-22
 */
public interface EntityValueMapper<T> {

    Map<String, String> mapEntity(T entity, List<String> fieldNames);
}
