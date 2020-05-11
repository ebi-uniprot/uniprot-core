package org.uniprot.core.parser.tsv.proteome;

import org.uniprot.core.parser.tsv.NamedValueMap;
import org.uniprot.core.taxonomy.TaxonomyLineage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author jluo
 * @date: 1 May 2019
 */
public class ProteomeTaxonomyLineageMap implements NamedValueMap {
    public static final List<String> FIELDS = Arrays.asList("lineage");

    private final List<TaxonomyLineage> lineage;

    public ProteomeTaxonomyLineageMap(List<TaxonomyLineage> lineage) {
        this.lineage = lineage;
    }

    @Override
    public Map<String, String> attributeValues() {
        String value =
                lineage.stream()
                        .map(val -> val.getScientificName())
                        .collect(Collectors.joining(", "));
        Map<String, String> map = new HashMap<>();
        map.put(FIELDS.get(0), value);
        return map;
    }

    public static boolean contains(List<String> fields) {
        return fields.stream().anyMatch(FIELDS::contains);
    }
}
