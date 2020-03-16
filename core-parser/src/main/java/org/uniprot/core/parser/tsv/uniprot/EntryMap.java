package org.uniprot.core.parser.tsv.uniprot;

import java.util.*;

import org.uniprot.core.parser.tsv.uniprot.comment.EntryCommentsMap;
import org.uniprot.core.uniprotkb.UniProtkbEntry;

public class EntryMap implements NamedValueMap {
    private final UniProtkbEntry entry;
    private final List<String> fields;

    public static final List<String> DEFAULT_FIELDS =
            Arrays.asList("accession", "id", "score", "protein_existence");

    public static final String FIELD_FEATURE = "feature";

    public EntryMap(UniProtkbEntry entry, List<String> fields) {
        this.entry = entry;
        this.fields = Collections.unmodifiableList(fields);
    }

    public static boolean contains(List<String> fields) {
        return fields.stream().anyMatch(DEFAULT_FIELDS::contains);
    }

    public List<String> getData() {
        List<String> result = new ArrayList<>();
        Map<String, String> mapped = attributeValues();
        for (String field : fields) {
            result.add(mapped.getOrDefault(field, ""));
        }
        return result;
    }

    @Override
    public Map<String, String> attributeValues() {
        Map<String, String> map = new HashMap<>();
        if (EntryAuditMap.contains(fields)) {
            addData(map, new EntryAuditMap(entry.getEntryAudit()));
        }
        if (EntryCommentsMap.contains(fields)) {
            addData(map, new EntryCommentsMap(entry.getComments()));
        }
        if (EntryEncodedMap.contains(fields)) {
            addData(map, new EntryEncodedMap(entry.getGeneLocations()));
        }
        if (EntryCrossReferenceMap.contains(fields)) {
            addData(map, new EntryCrossReferenceMap(entry.getUniProtkbCrossReferences()));
        }

        if (EntryTypeMap.contains(fields)) {
            addData(map, new EntryTypeMap(entry.getEntryType()));
        }
        if (EntryFeaturesMap.contains(fields)) {
            addData(map, new EntryFeaturesMap(entry.getFeatures()));
        }
        if (EntryFeaturesMap.contains(fields)) {
            addData(map, new EntryFeaturesMap(entry.getFeatures()));
        }
        if (EntryGeneMap.contains(fields)) {
            addData(map, new EntryGeneMap(entry.getGenes()));
        }
        if (EntryKeywordMap.contains(fields)) {
            addData(map, new EntryKeywordMap(entry.getKeywords()));
        }
        /* TODO: We need to evaluate the reason for these lineage fields with the team
        if (EntryLineageMap.contains(fields)) {
            addData(map, new EntryLineageMap(entry.getLineage()));
        }*/

        if (EntryOrganismMap.contains(fields)) {
            addData(map, new EntryOrganismMap(entry.getOrganism()));
        }
        if (EntryOrganismMap.contains(fields)) {
            addData(map, new EntryOrganismMap(entry.getOrganism()));
        }

        if (EntryOrganismHostMap.contains(fields)) {
            addData(map, new EntryOrganismHostMap(entry.getOrganismHosts()));
        }
        if (EntryProteinMap.contains(fields)) {
            addData(map, new EntryProteinMap(entry.getProteinDescription()));
        }
        if (EntryReferenceMap.contains(fields)) {
            addData(map, new EntryReferenceMap(entry.getReferences()));
        }
        if (EntrySequenceMap.contains(fields)) {
            addData(map, new EntrySequenceMap(entry.getSequence()));
        }
        if (contains(fields)) {
            map.putAll(getSimpleFields());
        }
        if (fields.contains(FIELD_FEATURE)) {
            map.put(FIELD_FEATURE, getFeatures());
        }
        return map;
    }

    private void addData(Map<String, String> map, NamedValueMap dl) {
        map.putAll(dl.attributeValues());
    }

    private Map<String, String> getSimpleFields() {
        Map<String, String> map = new HashMap<>();
        map.put("accession", entry.getPrimaryAccession().getValue());
        map.put("id", entry.getUniProtkbId().getValue());
        // map.put("score", entry.getAnnotationScore() + ""); TODO: Check with Jie about the
        // annotation score field
        if (entry.getProteinExistence() != null) {
            map.put("protein_existence", entry.getProteinExistence().getValue());
        }
        return map;
    }

    private String getFeatures() {
        Set<String> listFeatures = new TreeSet<>();
        listFeatures.addAll(EntryFeaturesMap.getFeatures(entry.getFeatures()));
        listFeatures.addAll(EntryCommentsMap.getSequenceCautionTypes(entry.getComments()));

        if (!listFeatures.isEmpty()) {
            return String.join("; ", listFeatures);
        } else return "";
    }
}
