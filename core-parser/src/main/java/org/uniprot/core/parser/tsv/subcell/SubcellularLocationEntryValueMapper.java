package org.uniprot.core.parser.tsv.subcell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.uniprot.core.cv.keyword.KeywordId;
import org.uniprot.core.cv.subcell.SubcellularLocationEntry;
import org.uniprot.core.parser.tsv.EntityValueMapper;
import org.uniprot.core.util.Utils;

/**
 * @author lgonzales
 * @since 2019-07-22
 */
public class SubcellularLocationEntryValueMapper
        implements EntityValueMapper<SubcellularLocationEntry> {

    @Override
    public Map<String, String> mapEntity(SubcellularLocationEntry entry, List<String> fieldNames) {
        Map<String, String> map = new HashMap<>();
        map.put("id", Utils.emptyOrString(entry.getId()));
        map.put("accession", Utils.emptyOrString(entry.getAccession()));
        map.put("definition", Utils.emptyOrString(entry.getDefinition()));
        map.put("category", getCategory(entry));
        map.put("keyword", getKeyword(entry));
        map.put("synonyms", String.join(", ", entry.getSynonyms()));
        map.put("content", Utils.emptyOrString(entry.getContent()));
        map.put("gene_ontologies", getGeneOntologies(entry));
        map.put("note", Utils.emptyOrString(entry.getNote()));
        map.put("references", String.join(", ", entry.getReferences()));
        map.put("links", String.join(", ", entry.getLinks()));
        map.put("is_a", getIsA(entry));
        map.put("part_of", getPartOf(entry));
        map.put("statistics", getStatistics(entry));

        return map;
    }

    private String getStatistics(SubcellularLocationEntry entry) {
        StringBuilder result = new StringBuilder();
        if (Utils.notNull(entry.getStatistics())) {
            result.append("reviewed:")
                    .append(entry.getStatistics().getReviewedProteinCount())
                    .append("; ")
                    .append("annotated:")
                    .append(entry.getStatistics().getUnreviewedProteinCount());
        }
        return result.toString();
    }

    private String getPartOf(SubcellularLocationEntry entry) {
        if (Utils.notNullNotEmpty(entry.getPartOf())) {
            return entry.getPartOf().stream()
                    .map(
                            mapped ->
                                    mapped.getAccession()
                                            + "; "
                                            + mapped.getId()
                                            + "; "
                                            + mapped.getCategory())
                    .collect(Collectors.joining(", "));
        } else {
            return "";
        }
    }

    private String getIsA(SubcellularLocationEntry entry) {
        if (Utils.notNullNotEmpty(entry.getIsA())) {
            return entry.getIsA().stream()
                    .map(
                            mapped ->
                                    mapped.getAccession()
                                            + "; "
                                            + mapped.getId()
                                            + "; "
                                            + mapped.getCategory())
                    .collect(Collectors.joining(", "));
        } else {
            return "";
        }
    }

    private String getGeneOntologies(SubcellularLocationEntry entry) {
        if (Utils.notNullNotEmpty(entry.getGeneOntologies())) {
            return entry.getGeneOntologies().stream()
                    .map(mapped -> mapped.getId() + ":" + mapped.getName())
                    .collect(Collectors.joining(", "));
        } else {
            return "";
        }
    }

    private String getKeyword(SubcellularLocationEntry entry) {
        return entry.getKeyword().map(KeywordId::getId).orElse("");
    }

    private String getCategory(SubcellularLocationEntry entry) {
        if (entry.getCategory() != null) {
            return Utils.emptyOrString(entry.getCategory().toDisplayName());
        } else {
            return "";
        }
    }
}
