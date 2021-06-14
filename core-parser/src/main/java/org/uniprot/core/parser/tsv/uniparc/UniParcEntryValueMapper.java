package org.uniprot.core.parser.tsv.uniparc;

import java.time.LocalDate;
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

    private static final List<String> UNIPARC_FIELDS = List.of("upi", "oldestCrossRefCreated", "mostRecentCrossRefUpdated");

    @Override
    public Map<String, String> mapEntity(UniParcEntry entry, List<String> fields) {
        Map<String, String> map = new HashMap<>();
        if (contains(fields)) {
            map.putAll(getSimpleAttributeValues(entry, fields));
        }
        if (UniParcOrganismMap.contains(fields)) {
            List<Organism> organisms =
                    entry.getUniParcCrossReferences().stream()
                            .map(UniParcCrossReference::getOrganism)
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

    private Map<String, String> getSimpleAttributeValues(UniParcEntry entry, List<String> fields) {
        Map<String, String> map = new HashMap<>();
        for(String field : fields) {
            switch (field){
                case "upi":
                    map.put(UNIPARC_FIELDS.get(0), entry.getUniParcId().getValue());
                    break;
                case "oldestCrossRefCreated":
                    map.put(UNIPARC_FIELDS.get(1), Optional.of(entry.getOldestCrossRefCreated()).map(LocalDate::toString).orElse(""));
                    break;
                case "mostRecentCrossRefUpdated":
                    map.put(UNIPARC_FIELDS.get(2), Optional.of(entry.getMostRecentCrossRefUpdated()).map(LocalDate::toString).orElse(""));
                    break;
                default:
                    //do nothing
            }

        }
        return map;
    }
}
