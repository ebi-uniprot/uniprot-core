package org.uniprot.core.parser.tsv.taxonomy;

import static org.uniprot.core.parser.tsv.TSVUtil.*;

import org.uniprot.core.parser.tsv.EntityValueMapper;
import org.uniprot.core.taxonomy.TaxonomyEntry;
import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.taxonomy.TaxonomyStrain;
import org.uniprot.core.uniprotkb.taxonomy.Taxonomy;
import org.uniprot.core.util.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TaxonomyEntryValueMapper implements EntityValueMapper<TaxonomyEntry> {

    @Override
    public Map<String, String> mapEntity(TaxonomyEntry taxonomyEntry, List<String> fieldNames) {
        Map<String, String> map = new HashMap<>();
        map.put("id", String.valueOf(taxonomyEntry.getTaxonId()));
        map.put("parent", String.valueOf(taxonomyEntry.getParentId()));
        map.put("mnemonic", getOrDefaultEmpty(taxonomyEntry.getMnemonic()));
        map.put("scientific_name", getOrDefaultEmpty(taxonomyEntry.getScientificName()));
        map.put("common_name", getOrDefaultEmpty(taxonomyEntry.getCommonName()));
        map.put("synonym", getOrDefaultEmpty(taxonomyEntry.getSynonyms()));
        map.put("other_names", getOrDefaultEmpty(taxonomyEntry.getOtherNames()));
        map.put("links", getOrDefaultEmpty(taxonomyEntry.getLinks()));
        map.put("rank", getRank(taxonomyEntry));
        map.put("reviewed", getReviewed(taxonomyEntry));
        map.put("lineage", getLineage(taxonomyEntry));
        map.put("strain", getStrains(taxonomyEntry));
        map.put("host", getHosts(taxonomyEntry));
        map.put("statistics", getStatistics(taxonomyEntry));
        return map;
    }

    private String getReviewed(TaxonomyEntry taxonomyEntry) {
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

    private String getHosts(TaxonomyEntry taxonomyEntry) {
        if (Utils.notNullNotEmpty(taxonomyEntry.getHosts())) {
            return taxonomyEntry.getHosts().stream()
                    .map(Taxonomy::getScientificName)
                    .collect(Collectors.joining(", "));
        } else {
            return "";
        }
    }

    private String getStrains(TaxonomyEntry taxonomyEntry) {
        if (Utils.notNullNotEmpty(taxonomyEntry.getStrains())) {
            return taxonomyEntry.getStrains().stream()
                    .map(TaxonomyStrain::getName)
                    .collect(Collectors.joining(", "));
        } else {
            return "";
        }
    }

    private String getLineage(TaxonomyEntry taxonomyEntry) {
        if (Utils.notNullNotEmpty(taxonomyEntry.getLineages())) {
            return taxonomyEntry.getLineages().stream()
                    .map(TaxonomyLineage::getScientificName)
                    .collect(Collectors.joining(", "));
        } else {
            return "";
        }
    }

    private String getRank(TaxonomyEntry taxonomyEntry) {
        if (taxonomyEntry.getRank() != null) {
            return taxonomyEntry.getRank().getDisplayName();
        } else {
            return "";
        }
    }

    private String getStatistics(TaxonomyEntry taxonomyEntry) {
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
