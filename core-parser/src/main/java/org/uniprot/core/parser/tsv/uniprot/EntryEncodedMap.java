package org.uniprot.core.parser.tsv.uniprot;

import org.uniprot.core.parser.tsv.NamedValueMap;
import org.uniprot.core.uniprotkb.GeneLocation;

import java.util.*;
import java.util.stream.Collectors;

public class EntryEncodedMap implements NamedValueMap {
    static final List<String> FIELDS = Arrays.asList(new String[] {"organelle"});
    private final List<GeneLocation> geneLocations;

    EntryEncodedMap(List<GeneLocation> geneLocations) {
        if (geneLocations == null) {
            this.geneLocations = Collections.emptyList();
        } else {
            this.geneLocations = Collections.unmodifiableList(geneLocations);
        }
    }

    @Override
    public Map<String, String> attributeValues() {
        if (geneLocations.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<String, String> map = new HashMap<>();
        map.put(
                FIELDS.get(0),
                geneLocations.stream()
                        .map(this::getGeneLocation)
                        .collect(Collectors.joining("; ")));

        return map;
    }

    private String getGeneLocation(GeneLocation g) {
        StringBuilder sb = new StringBuilder();
        sb.append(g.getGeneEncodingType().getName());
        if (g.getValue() != null && !g.getValue().isEmpty()) {
            sb.append(" ").append(g.getValue());
        }
        return sb.toString();
    }

    public static boolean contains(List<String> fields) {
        return fields.stream().anyMatch(FIELDS::contains);
    }
}
