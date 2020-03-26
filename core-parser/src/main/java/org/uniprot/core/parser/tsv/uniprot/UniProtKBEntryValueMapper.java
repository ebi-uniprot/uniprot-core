package org.uniprot.core.parser.tsv.uniprot;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.uniprot.core.parser.tsv.EntityValueMapper;
import org.uniprot.core.parser.tsv.NamedValueMap;
import org.uniprot.core.parser.tsv.uniprot.comment.EntryCommentsMap;
import org.uniprot.core.uniprotkb.UniProtKBEntry;

public class UniProtKBEntryValueMapper implements EntityValueMapper<UniProtKBEntry> {

    public static final List<String> DEFAULT_FIELDS =
            Arrays.asList("accession", "id", "score", "protein_existence");

    // TODO: FIX IT!!!
    public static final List<String> UNSUPORTED_FIELDS =
            Arrays.asList("matched_text", "tools", "uniparc_id", "mapped_pm_id");

    public static final String FIELD_FEATURE = "feature";

    public static boolean contains(List<String> fields) {
        return fields.stream().anyMatch(DEFAULT_FIELDS::contains);
    }

    public static boolean containsUnsuported(List<String> fields) {
        return fields.stream().anyMatch(UNSUPORTED_FIELDS::contains);
    }

    public Map<String, String> mapEntity(UniProtKBEntry entry, List<String> fields) {
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
            addData(map, new EntryCrossReferenceMap(entry.getUniProtKBCrossReferences()));
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

        if (EntryLineageMap.contains(fields)) {
            addData(map, new EntryLineageMap(entry.getLineages()));
        }

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
            map.putAll(getSimpleFields(entry));
        }
        if (containsUnsuported(fields)) {
            map.putAll(getUnsuportedFields());
        }
        if (fields.contains(FIELD_FEATURE)) {
            map.put(FIELD_FEATURE, getFeatures(entry));
        }
        return map;
    }

    private Map<String, String> getUnsuportedFields() {
        return UNSUPORTED_FIELDS.stream()
                .collect(Collectors.toMap(Function.identity(), Function.identity()));
    }

    private void addData(Map<String, String> map, NamedValueMap dl) {
        map.putAll(dl.attributeValues());
    }

    private Map<String, String> getSimpleFields(UniProtKBEntry entry) {
        Map<String, String> map = new HashMap<>();
        map.put("accession", entry.getPrimaryAccession().getValue());
        map.put("id", entry.getUniProtkbId().getValue());
        map.put("score", entry.getAnnotationScore() + "");

        if (entry.getProteinExistence() != null) {
            map.put("protein_existence", entry.getProteinExistence().getValue());
        }
        return map;
    }

    private String getFeatures(UniProtKBEntry entry) {
        Set<String> listFeatures = new TreeSet<>();
        listFeatures.addAll(EntryFeaturesMap.getFeatures(entry.getFeatures()));
        listFeatures.addAll(EntryCommentsMap.getSequenceCautionTypes(entry.getComments()));

        if (!listFeatures.isEmpty()) {
            return String.join("; ", listFeatures);
        } else return "";
    }
}
