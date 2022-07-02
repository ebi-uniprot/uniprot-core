package org.uniprot.core.parser.tsv.uniprot;

import static org.uniprot.core.util.Utils.*;

import java.util.*;
import java.util.stream.Collectors;

import org.uniprot.core.parser.tsv.NamedValueMap;
import org.uniprot.core.taxonomy.TaxonomyLineage;

public class EntryLineageMap implements NamedValueMap {
    private final List<TaxonomyLineage> lineage;
    public static final List<String> FIELDS = List.of("lineage", "lineage_ids");

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
        map.put(FIELDS.get(0), getAllLineages());
        map.put(FIELDS.get(1), getAllLineageIds());
        return map;
    }

    private String getAllLineageIds() {
        return lineage.stream().map(this::getLineageIds).collect(Collectors.joining(", "));
    }

    private String getLineageIds(TaxonomyLineage taxonomyLineage) {
        String result = String.valueOf(taxonomyLineage.getTaxonId());
        if (taxonomyLineage.hasRank()) {
            result += " (" + taxonomyLineage.getRank().getDisplayName() + ")";
        }
        return result;
    }

    private String getAllLineages() {
        return lineage.stream().map(this::getLineage).collect(Collectors.joining(", "));
    }

    private String getLineage(TaxonomyLineage taxonomyLineage) {
        String result = taxonomyLineage.getScientificName();
        if (taxonomyLineage.hasRank()) {
            result += " (" + taxonomyLineage.getRank().getDisplayName() + ")";
        }
        return result;
    }

    public static boolean contains(List<String> fields) {
        return fields.stream().anyMatch(FIELDS::contains);
    }
}
