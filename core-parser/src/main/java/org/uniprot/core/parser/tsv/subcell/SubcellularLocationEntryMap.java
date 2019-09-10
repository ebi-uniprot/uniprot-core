package org.uniprot.core.parser.tsv.subcell;

import org.uniprot.core.cv.subcell.SubcellularLocationEntry;
import org.uniprot.core.parser.tsv.uniprot.NamedValueMap;
import org.uniprot.core.util.Utils;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lgonzales
 * @since 2019-07-22
 */
public class SubcellularLocationEntryMap implements NamedValueMap {

    private final SubcellularLocationEntry entry;

    public SubcellularLocationEntryMap(SubcellularLocationEntry entry) {
        this.entry = entry;
    }

    @Override
    public Map<String, String> attributeValues() {
        Map<String, String> map = new HashMap<>();
        map.put("id", Utils.nullToEmpty(entry.getId()));
        map.put("accession", Utils.nullToEmpty(entry.getAccession()));
        map.put("definition", Utils.nullToEmpty(entry.getDefinition()));
        map.put("category", getCategory());
        map.put("keyword", getKeyword());
        map.put("synonyms", String.join(", ", entry.getSynonyms()));
        map.put("content", Utils.nullToEmpty(entry.getContent()));
        map.put("gene_ontologies", getGeneOntologies());
        map.put("note", Utils.nullToEmpty(entry.getNote()));
        map.put("references", String.join(", ", entry.getReferences()));
        map.put("links", String.join(", ", entry.getLinks()));
        map.put("is_a", getIsA());
        map.put("part_of", getPartOf());
        map.put("statistics", getStatistics());


        return map;
    }

    private String getStatistics() {
        StringBuilder result = new StringBuilder();
        if (Utils.nonNull(entry.getStatistics())) {
            result.append("reviewed:").append(entry.getStatistics().getReviewedProteinCount()).append("; ")
            .append("annotated:").append(entry.getStatistics().getUnreviewedProteinCount());
        }
        return result.toString();
    }

    private String getPartOf() {
        if(Utils.notEmpty(entry.getPartOf())){
            return entry.getPartOf().stream()
                    .map(mapped -> mapped.getAccession() + "; " +
                            mapped.getId() + "; " +
                            mapped.getCategory())
                    .collect(Collectors.joining(", "));
        }else {
            return "";
        }
    }

    private String getIsA() {
        if(Utils.notEmpty(entry.getIsA())){
            return entry.getIsA().stream()
                    .map(mapped -> mapped.getAccession() + "; " +
                            mapped.getId() + "; " +
                            mapped.getCategory())
                    .collect(Collectors.joining(", "));
        }else {
            return "";
        }
    }

    private String getGeneOntologies() {
        if(Utils.notEmpty(entry.getGeneOntologies())){
            return entry.getGeneOntologies().stream()
                    .map(mapped -> mapped.getGoId() + ":" + mapped.getGoTerm())
                    .collect(Collectors.joining(", "));
        }else {
            return "";
        }
    }

    private String getKeyword() {
    	return entry.getKeyword().map(val ->val.getAccession()).orElse("");
    }

    private String getCategory() {
        if(entry.getCategory() != null) {
            return Utils.nullToEmpty(entry.getCategory().toDisplayName());
        }else {
            return "";
        }
    }

}
