package org.uniprot.core.parser.tsv.uniparc;

import java.util.*;
import java.util.stream.Collectors;

import org.uniprot.core.parser.tsv.EntityValueMapper;
import org.uniprot.core.parser.tsv.NamedValueMap;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.UniParcEntry;
import org.uniprot.core.uniprotkb.taxonomy.Organism;

/**
 * @author jluo
 * @date: 21 Jun 2019
 */
public class UniParcEntryValueMapper implements EntityValueMapper<UniParcEntry> {

    private static final List<String> UNIPARC_FIELDS = Collections.singletonList("upi");

    @Override
    public Map<String, String> mapEntity(UniParcEntry entry, List<String> fields) {
        Map<String, String> map = new HashMap<>();
        if (contains(fields)) {
            map.putAll(getSimpleAttributeValues(entry));
        }
        if (UniParcOrganismMap.contains(fields)) {
            List<Organism> organisms = entry.getUniParcCrossReferences()
                    .stream()
                    .map(UniParcCrossReference::getTaxonomy)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            addData(map, new UniParcOrganismMap(organisms));
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
        return map;
    }

    private void addData(Map<String, String> map, NamedValueMap dl) {
        map.putAll(dl.attributeValues());
    }

    private static boolean contains(List<String> fields) {
        return fields.stream().anyMatch(UNIPARC_FIELDS::contains);
    }

    private Map<String, String> getSimpleAttributeValues(UniParcEntry entry) {
        Map<String, String> map = new HashMap<>();
        map.put(UNIPARC_FIELDS.get(0), entry.getUniParcId().getValue());
        return map;
    }
}
