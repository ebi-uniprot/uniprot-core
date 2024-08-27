package org.uniprot.core.parser.tsv.uniparc;

import org.uniprot.core.parser.tsv.EntityValueMapper;
import org.uniprot.core.uniparc.UniParcEntryLight;
import org.uniprot.core.uniprotkb.taxonomy.Organism;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class UniParcEntryLightValueMapper implements EntityValueMapper<UniParcEntryLight> {

    private static final List<String> UNIPARC_FIELDS =
            List.of("upi",
                    "oldestCrossRefCreated",
                    "mostRecentCrossRefUpdated",
                    "organism",
                    "organism_id",
                    "gene",
                    "protein",
                    "proteome",
                    "accession",
                    "first_seen",
                    "last_seen");
    private static final String DELIMITER2 = "; ";

    @Override
    public Map<String, String> mapEntity(UniParcEntryLight entry, List<String> fields) {
        Map<String, String> map = new HashMap<>();
        if(contains(fields)){
            map.putAll(getSimpleAttributeValues(entry, fields));
        }
        if (UniParcSequenceFeatureMap.contains(fields)) {
            UniParcSequenceFeatureMap featureMapper = new UniParcSequenceFeatureMap(entry.getSequenceFeatures());
            map.putAll(featureMapper.attributeValues());
        }
        if (UniParcSequenceMap.contains(fields)) {
            UniParcSequenceMap sequenceMapper = new UniParcSequenceMap(entry.getSequence());
            map.putAll(sequenceMapper.attributeValues());
        }
        return map;
    }



    private static boolean contains(List<String> fields) {
        return fields.stream().anyMatch(UNIPARC_FIELDS::contains);
    }

    private Map<String, String> getSimpleAttributeValues(UniParcEntryLight entry, List<String> fields) {
        Map<String, String> map = new HashMap<>();
        for (String field : fields) {
            switch (field) {
                case "upi":
                    map.put(UNIPARC_FIELDS.get(0), entry.getUniParcId());
                    break;
                case "oldestCrossRefCreated":
                    map.put(
                            UNIPARC_FIELDS.get(1),
                            Optional.of(entry.getOldestCrossRefCreated())
                                    .map(LocalDate::toString)
                                    .orElse(""));
                    break;
                case "mostRecentCrossRefUpdated":
                    map.put(
                            UNIPARC_FIELDS.get(2),
                            Optional.of(entry.getMostRecentCrossRefUpdated())
                                    .map(LocalDate::toString)
                                    .orElse(""));
                    break;
                case "organism":
                    map.put(
                            UNIPARC_FIELDS.get(3),
                            entry.getOrganisms().stream()
                                    .map(Organism::getScientificName)
                                    .collect(Collectors.joining(DELIMITER2)));
                    break;
                case "organism_id":
                    map.put(
                            UNIPARC_FIELDS.get(4),
                            entry.getOrganisms().stream()
                                    .map(Organism::getTaxonId)
                                    .map(String::valueOf)
                                    .collect(Collectors.joining(DELIMITER2)));
                    break;
                case "gene":
                    map.put(UNIPARC_FIELDS.get(5),String.join(DELIMITER2, entry.getGeneNames()));
                    break;
                case "protein":
                    map.put(UNIPARC_FIELDS.get(6),String.join(DELIMITER2, entry.getProteinNames()));
                    break;
                case "proteome":
                    map.put(UNIPARC_FIELDS.get(7),entry.getProteomes().stream().map(e -> e.getId()+":"+e.getComponent()).collect(Collectors.joining(DELIMITER2)));
                    break;
                case "accession":
                    map.put(UNIPARC_FIELDS.get(8),String.join(DELIMITER2, entry.getUniProtKBAccessions()));
                    break;
                case "first_seen":
                    map.put(
                            UNIPARC_FIELDS.get(9),
                            Optional.of(entry.getOldestCrossRefCreated())
                                    .map(LocalDate::toString)
                                    .orElse(""));
                    break;
                case "last_seen":
                    map.put(
                            UNIPARC_FIELDS.get(10),
                            Optional.of(entry.getMostRecentCrossRefUpdated())
                                    .map(LocalDate::toString)
                                    .orElse(""));
                    break;
                default:
                    // do nothing
            }
        }
        return map;
    }
}
