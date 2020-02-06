package org.uniprot.core.parser.tsv.proteome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.uniprot.core.parser.tsv.uniprot.NamedValueMap;
import org.uniprot.core.proteome.ProteomeEntry;
import org.uniprot.core.proteome.ProteomeXReferenceType;

/**
 * @author jluo
 * @date: 1 May 2019
 */
public class ProteomeEntryMap implements NamedValueMap {
    private final ProteomeEntry entry;
    private final List<String> fields;
    public static final List<String> PROTEOME_FIELDS =
            Collections.unmodifiableList(
                    Arrays.asList("upid", "genome_assembly_id", "protein_count"));

    public ProteomeEntryMap(ProteomeEntry entry, List<String> fields) {
        this.entry = entry;
        this.fields = Collections.unmodifiableList(fields);
    }

    @Override
    public Map<String, String> attributeValues() {
        Map<String, String> map = new HashMap<>();
        if (contains(fields)) {
            map.putAll(getSimpleAttributeValues());
        }
        if (ProteomeTaxonomyMap.contains(fields)) {
            addData(map, new ProteomeTaxonomyMap(entry.getTaxonomy()));
        }
        if (ProteomeComponentMap.contains(fields)) {
            addData(map, new ProteomeComponentMap(entry.getComponents()));
        }
        if (ProteomeTaxonomyLineageMap.contains(fields)) {
            addData(map, new ProteomeTaxonomyLineageMap(entry.getTaxonLineages()));
        }
        return map;
    }

    public List<String> getData() {
        List<String> result = new ArrayList<>();
        Map<String, String> mapped = attributeValues();
        for (String field : fields) {
            result.add(mapped.getOrDefault(field, ""));
        }
        return result;
    }

    private void addData(Map<String, String> map, NamedValueMap dl) {
        map.putAll(dl.attributeValues());
    }

    private Map<String, String> getSimpleAttributeValues() {
        Map<String, String> map = new HashMap<>();
        map.put(PROTEOME_FIELDS.get(0), entry.getId().getValue());
        map.put(PROTEOME_FIELDS.get(1), getGenomeAssemblyId());
        map.put(PROTEOME_FIELDS.get(2), "" + entry.getProteinCount());

        return map;
    }

    private static boolean contains(List<String> fields) {
        return fields.stream().anyMatch(PROTEOME_FIELDS::contains);
    }

    private String getGenomeAssemblyId() {

        return entry.getDbXReferences().stream()
                .filter(val -> val.getDatabaseType() == ProteomeXReferenceType.GENOME_ASSEMBLY)
                .map(val -> val.getId())
                .collect(Collectors.joining(", "));
    }
}
