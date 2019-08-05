package uk.ac.ebi.uniprot.parser.tsv.disease;

import uk.ac.ebi.uniprot.common.Utils;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.NamedValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.uniprot.core.cv.disease.CrossReference;
import org.uniprot.core.cv.disease.Disease;
import org.uniprot.core.cv.keyword.Keyword;

public class DiseaseEntryMap implements NamedValueMap {
    public static final String EMPTY_STRING = "";

    private final Disease diseaseEntry;

    public DiseaseEntryMap(Disease diseaseEntry) {
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
        map.put("reviewed_protein_count", String.valueOf(this.diseaseEntry.getReviewedProteinCount()));
        map.put("unreviewed_protein_count", String.valueOf(this.diseaseEntry.getUnreviewedProteinCount()));
        return map;
    }

    private String getKewords(List<Keyword> keywords) {
        if (Utils.notEmpty(keywords)) {
            return keywords.stream().map(Keyword::getId).collect(Collectors.joining(","));
        } else {
            return EMPTY_STRING;
        }
    }

    private String getCrossReferences(List<CrossReference> crossReferences) {
        if (Utils.notEmpty(crossReferences)) {
            return crossReferences.stream().map(CrossReference::getId).collect(Collectors.joining(","));
        } else {
            return EMPTY_STRING;
        }
    }


    private String getAlternativeNames(List<String> alternativeNames) {
        if (Utils.notEmpty(alternativeNames)) {
            return String.join(",", alternativeNames);
        } else {
            return EMPTY_STRING;
        }
    }
}