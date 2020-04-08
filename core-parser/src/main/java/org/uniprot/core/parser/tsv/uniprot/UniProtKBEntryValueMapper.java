package org.uniprot.core.parser.tsv.uniprot;

import static java.util.stream.Collectors.toMap;

import java.util.*;
import java.util.function.Function;

import org.uniprot.core.parser.tsv.EntityValueMapper;
import org.uniprot.core.parser.tsv.NamedValueMap;
import org.uniprot.core.parser.tsv.uniprot.comment.EntryCommentsMap;
import org.uniprot.core.uniprotkb.UniProtKBEntry;
import org.uniprot.core.uniprotkb.impl.UniProtKBEntryBuilder;

public class UniProtKBEntryValueMapper implements EntityValueMapper<UniProtKBEntry> {

    private static final List<String> DEFAULT_FIELDS =
            Arrays.asList("accession", "id", "score", "protein_existence");

    // TODO: FIX IT!!!
    private static final List<String> UNSUPORTED_FIELDS =
            Arrays.asList("matched_text", "tools", "uniparc_id", "mapped_pm_id");

    private static final String FIELD_FEATURE = "feature";

    public static boolean contains(List<String> fields) {
        return fields.stream().anyMatch(DEFAULT_FIELDS::contains);
    }

    private static boolean containsUnsupported(List<String> fields) {
        return fields.stream().anyMatch(UNSUPPORTED_FIELDS::contains);
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
        if (containsUnsupported(fields)) {
            map.putAll(getUnsupportedFields());
        }
        if (fields.contains(FIELD_FEATURE)) {
            map.put(FIELD_FEATURE, getFeatures(entry));
        }

        if (UniProtKBEntryBuilder.ExtraAttributeName.contains(fields)) {
            Map<String, String> extraAttribsMap = getExtraAttributeMap(entry, fields);
            map.putAll(extraAttribsMap);
        }
        return map;
    }

    private Map<String, String> getUnsupportedFields() {
        return UNSUPPORTED_FIELDS.stream().collect(toMap(Function.identity(), Function.identity()));
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
            map.put("protein_existence", entry.getProteinExistence().getName());
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

    private Map<String, String> getExtraAttributeMap(UniProtKBEntry entry, List<String> fields) {
        Map<String, String> extraAttributeMap =
                Arrays.stream(UniProtKBEntryBuilder.ExtraAttributeName.values())
                        .filter(ean -> fields.contains(ean.getFieldName()))
                        .collect(
                                toMap(
                                        UniProtKBEntryBuilder.ExtraAttributeName::getFieldName,
                                        ean ->
                                                EntryMapUtil.convertToTSVString(
                                                        entry.getExtraAttributeValue(
                                                                ean.getDisplayName()))));
        return extraAttributeMap;
    }
}
