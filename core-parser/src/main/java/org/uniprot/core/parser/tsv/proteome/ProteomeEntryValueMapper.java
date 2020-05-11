package org.uniprot.core.parser.tsv.proteome;

import org.uniprot.core.parser.tsv.EntityValueMapper;
import org.uniprot.core.parser.tsv.NamedValueMap;
import org.uniprot.core.proteome.ProteomeCompletenessReport;
import org.uniprot.core.proteome.ProteomeEntry;
import org.uniprot.core.util.Utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jluo
 * @date: 1 May 2019
 */
public class ProteomeEntryValueMapper implements EntityValueMapper<ProteomeEntry> {

    public static final List<String> PROTEOME_FIELDS =
            Collections.unmodifiableList(
                    Arrays.asList("upid", "genome_assembly", "protein_count", "busco", "cpd"));

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
        map.put(PROTEOME_FIELDS.get(3), "" + getBuscoReport(entry));
        map.put(PROTEOME_FIELDS.get(4), "" + getCPDReport(entry));

        return map;
    }

    private String getCPDReport(ProteomeEntry entry) {
        String result = "";
        if (Utils.notNull(entry.getProteomeCompletenessReport())) {
            ProteomeCompletenessReport report = entry.getProteomeCompletenessReport();
            if (Utils.notNull(report.getCPDReport())) {
                result = report.getCPDReport().getStatus().getDisplayName();
            }
        }
        return result;
    }

    private String getBuscoReport(ProteomeEntry entry) {
        String result = "";
        if (Utils.notNull(entry.getProteomeCompletenessReport())) {
            ProteomeCompletenessReport report = entry.getProteomeCompletenessReport();
            if (Utils.notNull(report.getBuscoReport())) {
                result = report.getBuscoReport().calculateSummary();
            }
        }
        return result;
    }

    private static boolean contains(List<String> fields) {
        return fields.stream().anyMatch(PROTEOME_FIELDS::contains);
    }

    private String getGenomeAssemblyId(ProteomeEntry entry) {
        String result = "";
        if (Utils.notNull(entry.getGenomeAssembly())) {
            result = entry.getGenomeAssembly().getAssemblyId();
        }
        return result;
    }
}
