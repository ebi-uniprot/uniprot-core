package org.uniprot.core.parser.tsv.proteome;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.uniprot.core.CrossReference;
import org.uniprot.core.parser.tsv.EntityValueMapper;
import org.uniprot.core.parser.tsv.NamedValueMap;
import org.uniprot.core.proteome.ProteomeDatabase;
import org.uniprot.core.proteome.ProteomeEntry;

/**
 * @author jluo
 * @date: 1 May 2019
 */
public class ProteomeEntryValueMapper implements EntityValueMapper<ProteomeEntry> {

    public static final List<String> PROTEOME_FIELDS =
            Collections.unmodifiableList(Arrays.asList("upid", "genome_assembly", "protein_count"));

    @Override
    public Map<String, String> mapEntity(ProteomeEntry entry, List<String> fields) {
        Map<String, String> map = new HashMap<>();
        if (contains(fields)) {
            map.putAll(getSimpleAttributeValues(entry));
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

    private void addData(Map<String, String> map, NamedValueMap dl) {
        map.putAll(dl.attributeValues());
    }

    private Map<String, String> getSimpleAttributeValues(ProteomeEntry entry) {
        Map<String, String> map = new HashMap<>();
        map.put(PROTEOME_FIELDS.get(0), entry.getId().getValue());
        map.put(PROTEOME_FIELDS.get(1), getGenomeAssemblyId(entry));
        map.put(PROTEOME_FIELDS.get(2), "" + entry.getProteinCount());

        return map;
    }

    private static boolean contains(List<String> fields) {
        return fields.stream().anyMatch(PROTEOME_FIELDS::contains);
    }

    private String getGenomeAssemblyId(ProteomeEntry entry) {
        return entry.getProteomeCrossReferences().stream()
                .filter(val -> val.getDatabase() == ProteomeDatabase.GENOME_ASSEMBLY)
                .map(CrossReference::getId)
                .collect(Collectors.joining(", "));
    }
}