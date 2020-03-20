package org.uniprot.core.parser.tsv.taxonomy;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.uniprot.core.parser.tsv.uniprot.NamedValueMap;
import org.uniprot.core.taxonomy.TaxonomyEntry;
import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.taxonomy.TaxonomyStrain;
import org.uniprot.core.uniprotkb.taxonomy.Taxonomy;
import org.uniprot.core.util.Utils;

public class TaxonomyEntryMap implements NamedValueMap {

    private final TaxonomyEntry taxonomyEntry;

    public TaxonomyEntryMap(TaxonomyEntry taxonomyEntry) {
        this.taxonomyEntry = taxonomyEntry;
    }

    @Override
    public Map<String, String> attributeValues() {
        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(taxonomyEntry.getTaxonId()));
        map.put("parent", String.valueOf(taxonomyEntry.getParentId()));
        map.put("mnemonic", getOrDefaultEmpty(taxonomyEntry.getMnemonic()));
        map.put("scientific_name", getOrDefaultEmpty(taxonomyEntry.getScientificName()));
        map.put("common_name", getOrDefaultEmpty(taxonomyEntry.getCommonName()));
        map.put("synonym", getOrDefaultEmpty(taxonomyEntry.getSynonyms()));
        map.put("other_names", getOrDefaultEmpty(taxonomyEntry.getOtherNames()));
        map.put("link", getOrDefaultEmpty(taxonomyEntry.getLinks()));
        map.put("rank", getRank());
        map.put("reviewed", getReviewed());
        map.put("lineage", getLineage());
        map.put("strain", getStrains());
        map.put("host", getHosts());
        map.put("statistics", getStatistics());
        return map;
    }

    private String getReviewed() {
        if (taxonomyEntry.hasStatistics()
                && taxonomyEntry.getStatistics().hasReviewedProteinCount()) {
            return "reviewed";
        } else if (taxonomyEntry.hasStatistics()
                && taxonomyEntry.getStatistics().hasUnreviewedProteinCount()) {
            return "annotated";
        } else {
            return "";
        }
    }

    private String getHosts() {
        if (Utils.notNullNotEmpty(taxonomyEntry.getHosts())) {
            return taxonomyEntry.getHosts().stream()
                    .map(Taxonomy::getScientificName)
                    .collect(Collectors.joining(", "));
        } else {
            return "";
        }
    }

    private String getStrains() {
        if (Utils.notNullNotEmpty(taxonomyEntry.getStrains())) {
            return taxonomyEntry.getStrains().stream()
                    .map(TaxonomyStrain::getName)
                    .collect(Collectors.joining(", "));
        } else {
            return "";
        }
    }

    private String getLineage() {
        if (Utils.notNullNotEmpty(taxonomyEntry.getLineages())) {
            return taxonomyEntry.getLineages().stream()
                    .map(TaxonomyLineage::getScientificName)
                    .collect(Collectors.joining(", "));
        } else {
            return "";
        }
    }

    private String getRank() {
        if (taxonomyEntry.getRank() != null) {
            return taxonomyEntry.getRank().toDisplayName();
        } else {
            return "";
        }
    }

    private String getStatistics() {
        if (taxonomyEntry.getStatistics() != null) {
            return "reviewed:"
                    + taxonomyEntry.getStatistics().getReviewedProteinCount()
                    + "; "
                    + "annotated:"
                    + taxonomyEntry.getStatistics().getUnreviewedProteinCount()
                    + "; "
                    + "reference:"
                    + taxonomyEntry.getStatistics().getReferenceProteomeCount()
                    + "; "
                    + "complete:"
                    + taxonomyEntry.getStatistics().getProteomeCount();
        } else {
            return "";
        }
    }
}
