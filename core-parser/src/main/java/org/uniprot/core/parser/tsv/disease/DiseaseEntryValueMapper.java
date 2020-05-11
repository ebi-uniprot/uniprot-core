package org.uniprot.core.parser.tsv.disease;

import org.uniprot.core.cv.disease.DiseaseCrossReference;
import org.uniprot.core.cv.disease.DiseaseEntry;
import org.uniprot.core.cv.keyword.KeywordId;
import org.uniprot.core.parser.tsv.EntityValueMapper;
import org.uniprot.core.util.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DiseaseEntryValueMapper implements EntityValueMapper<DiseaseEntry> {
    static final String EMPTY_STRING = "";

    @Override
    public Map<String, String> mapEntity(DiseaseEntry diseaseEntry, List<String> fields) {
        Map<String, String> map = new HashMap<>();
        map.put("name", diseaseEntry.getName());
        map.put("acronym", diseaseEntry.getAcronym());
        map.put("id", diseaseEntry.getId());
        map.put("definition", diseaseEntry.getDefinition());
        map.put("alternative_names", getAlternativeNames(diseaseEntry.getAlternativeNames()));
        map.put("cross_references", getCrossReferences(diseaseEntry.getCrossReferences()));
        map.put("keywords", getKewords(diseaseEntry.getKeywords()));
        map.put("reviewed_protein_count", String.valueOf(diseaseEntry.getReviewedProteinCount()));
        map.put(
                "unreviewed_protein_count",
                String.valueOf(diseaseEntry.getUnreviewedProteinCount()));
        return map;
    }

    private String getKewords(List<KeywordId> keywords) {
        if (Utils.notNullNotEmpty(keywords)) {
            return keywords.stream().map(KeywordId::getName).collect(Collectors.joining(","));
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
