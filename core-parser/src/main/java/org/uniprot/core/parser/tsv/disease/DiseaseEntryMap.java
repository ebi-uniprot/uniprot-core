package org.uniprot.core.parser.tsv.disease;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.uniprot.core.cv.disease.DiseaseCrossReference;
import org.uniprot.core.cv.disease.DiseaseEntry;
import org.uniprot.core.cv.keyword.KeywordEntryKeyword;
import org.uniprot.core.parser.tsv.uniprot.NamedValueMap;
import org.uniprot.core.util.Utils;

public class DiseaseEntryMap implements NamedValueMap {
    public static final String EMPTY_STRING = "";

    private final DiseaseEntry diseaseEntry;

    public DiseaseEntryMap(DiseaseEntry diseaseEntry) {
        this.diseaseEntry = diseaseEntry;
    }

    @Override
    public Map<String, String> attributeValues() {
        Map<String, String> map = new HashMap<>();
        map.put("id", this.diseaseEntry.getId());
        map.put("acronym", this.diseaseEntry.getAcronym());
        map.put("accession", this.diseaseEntry.getAccession());
        map.put("definition", this.diseaseEntry.getDefinition());
        map.put("alternative_names", getAlternativeNames(this.diseaseEntry.getAlternativeNames()));
        map.put("cross_references", getCrossReferences(this.diseaseEntry.getCrossReferences()));
        map.put("keywords", getKewords(this.diseaseEntry.getKeywords()));
        map.put(
                "reviewed_protein_count",
                String.valueOf(this.diseaseEntry.getReviewedProteinCount()));
        map.put(
                "unreviewed_protein_count",
                String.valueOf(this.diseaseEntry.getUnreviewedProteinCount()));
        return map;
    }

    private String getKewords(List<KeywordEntryKeyword> keywords) {
        if (Utils.notNullNotEmpty(keywords)) {
            return keywords.stream()
                    .map(KeywordEntryKeyword::getId)
                    .collect(Collectors.joining(","));
        } else {
            return EMPTY_STRING;
        }
    }

    private String getCrossReferences(List<DiseaseCrossReference> crossReferences) {
        if (Utils.notNullNotEmpty(crossReferences)) {
            return crossReferences.stream()
                    .map(DiseaseCrossReference::getId)
                    .collect(Collectors.joining(","));
        } else {
            return EMPTY_STRING;
        }
    }

    private String getAlternativeNames(List<String> alternativeNames) {
        if (Utils.notNullNotEmpty(alternativeNames)) {
            return String.join(",", alternativeNames);
        } else {
            return EMPTY_STRING;
        }
    }
}
