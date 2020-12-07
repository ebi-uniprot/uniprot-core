package org.uniprot.core.parser.tsv.uniprot;

import static org.uniprot.core.util.Utils.*;

import java.util.*;
import java.util.stream.Collectors;

import org.uniprot.core.parser.tsv.NamedValueMap;
import org.uniprot.core.taxonomy.TaxonomyLineage;

public class EntryLineageMap implements NamedValueMap {
    private final List<TaxonomyLineage> lineage;
    public static final List<String> DEFAULT_FIELDS = Collections.singletonList("lineage");

    public EntryLineageMap(List<TaxonomyLineage> lineage) {
        if (notNull(lineage)) {
            this.lineage = Collections.unmodifiableList(lineage);
        } else {
            this.lineage = Collections.emptyList();
        }
    }

    @Override
    public Map<String, String> attributeValues() {
        Map<String, String> map = new HashMap<>();
        map.put("lineage", getAllLineages());
        return map;
    }

    private String getAllLineages() {
        return lineage.stream()
                .map(this::getLineage)
                .collect(Collectors.joining(", "));
    }

    private String getLineage(TaxonomyLineage taxonomyLineage) {
        String result = taxonomyLineage.getScientificName();
        if(taxonomyLineage.hasRank()){
            result += " ("+taxonomyLineage.getRank().getDisplayName()+")";
        }
        return result;
    }

    public static boolean contains(List<String> fields) {
        return fields.stream().anyMatch(DEFAULT_FIELDS::contains);
    }
}
