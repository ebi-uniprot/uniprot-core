package uk.ac.ebi.uniprot.parser.tsv.uniprot;

import uk.ac.ebi.uniprot.domain.taxonomy.OrganismHost;


import java.util.*;
import java.util.stream.Collectors;

public class EntryOrganismHostMap implements NamedValueMap {
    public static final List<String> FIELDS = Arrays.asList("organism_host");
    private final List<OrganismHost> organismHost;

    public EntryOrganismHostMap(List<OrganismHost> organismHost) {
        if (organismHost == null) {
            this.organismHost = Collections.emptyList();
        } else {
            this.organismHost = Collections.unmodifiableList(organismHost);
        }
    }

    public static boolean contains(List<String> fields) {
        return fields.stream().anyMatch(FIELDS::contains);
    }

    @Override
    public Map<String, String> attributeValues() {
        if (organismHost.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, String> map = new HashMap<>();
        map.put(FIELDS.get(0), organismHost.stream().map(this::getOrganismName).collect(Collectors.joining("; ")));
        return map;
    }

    private String getOrganismName(OrganismHost organism) {
        StringBuilder sb = new StringBuilder();
        sb.append(EntryMapUtil.convertOrganism(organism));
        sb.append(" [TaxID: ").append("" + organism.getTaxonId()).append("]");
        return sb.toString();
    }
}
