package org.uniprot.core.parser.tsv.uniprot;

import java.util.*;
import java.util.stream.Collectors;

import org.uniprot.core.parser.tsv.NamedValueMap;
import org.uniprot.core.uniprotkb.taxonomy.OrganismHost;

public class EntryVirusHostMap implements NamedValueMap {
    protected static final List<String> FIELDS = Collections.singletonList("virus_hosts");
    private final List<OrganismHost> organismHost;

    public EntryVirusHostMap(List<OrganismHost> organismHost) {
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
        map.put(
                FIELDS.get(0),
                organismHost.stream().map(this::getOrganismName).collect(Collectors.joining("; ")));
        return map;
    }

    private String getOrganismName(OrganismHost organism) {
        StringBuilder sb = new StringBuilder();
        sb.append(EntryMapUtil.convertOrganism(organism));
        sb.append(" [TaxID: ").append("" + organism.getTaxonId()).append("]");
        return sb.toString();
    }
}
