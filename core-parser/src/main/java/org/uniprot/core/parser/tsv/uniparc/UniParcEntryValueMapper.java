package org.uniprot.core.parser.tsv.uniparc;

import java.util.*;

import org.uniprot.core.parser.tsv.EntityValueMapper;
import org.uniprot.core.parser.tsv.NamedValueMap;
import org.uniprot.core.uniparc.UniParcEntry;

/**
 * @author jluo
 * @date: 21 Jun 2019
 */
public class UniParcEntryValueMapper implements EntityValueMapper<UniParcEntry> {

    private static final List<String> UNIPARC_FIELDS = Collections.singletonList("upi");

    // TODO: FIX IT!!!
    public static final List<String> UNSUPORTED_FIELDS = Collections.singletonList("matched");

    @Override
    public Map<String, String> mapEntity(UniParcEntry entry, List<String> fields) {
        Map<String, String> map = new HashMap<>();
        if (contains(fields)) {
            map.putAll(getSimpleAttributeValues(entry));
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
        if (UniParcCrossReferenceMap.contains(fields)) {
            addData(map, new UniParcCrossReferenceMap(entry.getUniParcCrossReferences()));
        }
        if (containsUnsuported(fields)) {
            map.put(UNSUPORTED_FIELDS.get(0), "TODO: UNSUPORTED FIELD");
        }
        return map;
    }

    private void addData(Map<String, String> map, NamedValueMap dl) {
        map.putAll(dl.attributeValues());
    }

    private static boolean contains(List<String> fields) {
        return fields.stream().anyMatch(UNIPARC_FIELDS::contains);
    }

    private static boolean containsUnsuported(List<String> fields) {
        return fields.stream().anyMatch(UNSUPORTED_FIELDS::contains);
    }

    private Map<String, String> getSimpleAttributeValues(UniParcEntry entry) {
        Map<String, String> map = new HashMap<>();
        map.put(UNIPARC_FIELDS.get(0), entry.getUniParcId().getValue());
        return map;
    }
}
