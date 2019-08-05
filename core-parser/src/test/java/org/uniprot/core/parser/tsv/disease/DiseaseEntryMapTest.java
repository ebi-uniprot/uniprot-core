package org.uniprot.core.parser.tsv.disease;

import org.junit.jupiter.api.Test;
import org.uniprot.core.builder.DiseaseBuilder;
import org.uniprot.core.cv.disease.CrossReference;
import org.uniprot.core.cv.disease.Disease;
import org.uniprot.core.cv.keyword.Keyword;
import org.uniprot.core.cv.keyword.impl.KeywordImpl;
import org.uniprot.core.parser.tsv.disease.DiseaseEntryMap;
import org.uniprot.core.parser.tsv.taxonomy.TaxonomyEntryMap;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DiseaseEntryMapTest {



    @Test
    void checkSimpleEntryAttributeValues() {
        String accession = "DI-1234";
        String acronym = "ACRONYM";
        String id = "Disease Id";
        String def = "Disease definition. sample";
        long revCount = 10L;
        long unrevCount = 20L;
        DiseaseBuilder builder = getSimpleDiseaseEntry(accession, acronym, id, def, revCount, unrevCount);
        Disease diseaseEntry = builder.build();

        Map<String,String> mappedEntries = new DiseaseEntryMap(diseaseEntry).attributeValues();
        assertThat(mappedEntries,notNullValue());
        assertEquals(9, mappedEntries.size());
        assertEquals(accession, mappedEntries.get("accession"));
        assertEquals(acronym, mappedEntries.get("acronym"));
        assertEquals(id, mappedEntries.get("id"));
        assertEquals(def, mappedEntries.get("definition"));
        assertEquals(String.valueOf(revCount), mappedEntries.get("reviewed_protein_count"));
        assertEquals(String.valueOf(unrevCount), mappedEntries.get("unreviewed_protein_count"));
        assertEquals(DiseaseEntryMap.EMPTY_STRING, mappedEntries.get("alternative_names"));
        assertEquals(DiseaseEntryMap.EMPTY_STRING, mappedEntries.get("cross_references"));
        assertEquals(DiseaseEntryMap.EMPTY_STRING, mappedEntries.get("keywords"));
    }


    @Test
    void checkCompleteEntryAttributeValues() {
        String accession = "DI-1234";
        String acronym = "ACRONYM";
        String id = "Disease Id";
        String def = "Disease definition. sample";
        long revCount = 10L;
        long unrevCount = 20L;

        DiseaseBuilder builder = getSimpleDiseaseEntry(accession, acronym, id, def, revCount, unrevCount);
        List<String> alternativeNames = Arrays.asList("ALT1", "ALT2", "ALT3");
        List<CrossReference> xrefs = Arrays.asList(new CrossReference("DB1", "ID1"), new CrossReference("DB1", "ID2"));
        List<Keyword> keywords = Arrays.asList(new KeywordImpl("KW1", "VAL1"),
                new KeywordImpl("KW2", "VAL2"), new KeywordImpl("KW3", "VAL3"),
                new KeywordImpl("KW4", "VAL4"));
        builder.alternativeNames(alternativeNames).crossReferences(xrefs);
        builder.keywords(keywords);

        Disease entry = builder.build();


        Map<String,String> mappedEntries = new DiseaseEntryMap(entry).attributeValues();

        assertThat(mappedEntries,notNullValue());
        assertEquals(9, mappedEntries.size());
        assertEquals(accession, mappedEntries.get("accession"));
        assertEquals(acronym, mappedEntries.get("acronym"));
        assertEquals(id, mappedEntries.get("id"));
        assertEquals(def, mappedEntries.get("definition"));
        assertEquals(String.valueOf(revCount), mappedEntries.get("reviewed_protein_count"));
        assertEquals(String.valueOf(unrevCount), mappedEntries.get("unreviewed_protein_count"));
        assertEquals(String.join(",", alternativeNames), mappedEntries.get("alternative_names"));
        assertEquals(String.join(",", Arrays.asList("ID1", "ID2")), mappedEntries.get("cross_references"));
        assertEquals(String.join(",", "KW1","KW2","KW3","KW4"), mappedEntries.get("keywords"));
    }

    private DiseaseBuilder getSimpleDiseaseEntry(String accession, String acronym, String id, String def, long revCount, long unrevCount) {
        DiseaseBuilder builder = new DiseaseBuilder();
        builder.accession(accession).acronym(acronym).id(id);
        builder.definition(def);
        builder.reviewedProteinCount(revCount).unreviewedProteinCount(unrevCount);
        return builder;
    }
}