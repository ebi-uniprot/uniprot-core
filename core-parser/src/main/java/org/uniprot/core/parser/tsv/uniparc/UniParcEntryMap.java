package org.uniprot.core.parser.tsv.uniparc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uniprot.core.parser.tsv.uniprot.NamedValueMap;
import org.uniprot.core.uniparc.UniParcEntry;

/**
 * @author jluo
 * @date: 21 Jun 2019
 */
public class UniParcEntryMap implements NamedValueMap {
    private final UniParcEntry entry;
    private final List<String> fields;

    public static final List<String> FIELDS = Arrays.asList("upi");

    public UniParcEntryMap(UniParcEntry entry, List<String> fields) {
        this.entry = entry;
        this.fields = Collections.unmodifiableList(fields);
    }

    @Override
    public Map<String, String> attributeValues() {
        Map<String, String> map = new HashMap<>();
        if (contains(fields)) {
            map.putAll(getSimpleAttributeValues());
        }
        if (UniParcOrganismMap.contains(fields)) {
            addData(map, new UniParcOrganismMap(entry.getTaxonomies()));
        }
        if (UniParcSequenceFeatureMap.contains(fields)) {
            addData(map, new UniParcSequenceFeatureMap(entry.getSequenceFeatures()));
        }
        if (UniParcSequenceMap.contains(fields)) {
            addData(map, new UniParcSequenceMap(entry.getSequence()));
        }
        if (UniParcXrefMap.contains(fields)) {
            addData(map, new UniParcXrefMap(entry.getDbXReferences()));
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

    private static boolean contains(List<String> fields) {
        return fields.stream().anyMatch(FIELDS::contains);
    }

    private Map<String, String> getSimpleAttributeValues() {
        Map<String, String> map = new HashMap<>();
        map.put(FIELDS.get(0), entry.getUniParcId().getValue());
        return map;
    }
}
