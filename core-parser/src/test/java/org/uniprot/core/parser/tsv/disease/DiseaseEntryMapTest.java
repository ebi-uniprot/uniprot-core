package org.uniprot.core.parser.tsv.disease;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.disease.DiseaseCrossReference;
import org.uniprot.core.cv.disease.DiseaseEntry;
import org.uniprot.core.cv.disease.impl.DiseaseCrossReferenceBuilder;
import org.uniprot.core.cv.disease.impl.DiseaseEntryBuilder;
import org.uniprot.core.cv.keyword.KeywordId;
import org.uniprot.core.cv.keyword.impl.KeywordIdBuilder;

class DiseaseEntryMapTest {

    @Test
    void checkSimpleEntryAttributeValues() {
        String accession = "DI-1234";
        String acronym = "ACRONYM";
        String id = "DiseaseEntry Id";
        String def = "DiseaseEntry definition. sample";
        long revCount = 10L;
        long unrevCount = 20L;
        DiseaseEntryBuilder builder =
                getSimpleDiseaseEntry(accession, acronym, id, def, revCount, unrevCount);
        DiseaseEntry diseaseEntry = builder.build();

        Map<String, String> mappedEntries = new DiseaseEntryMap(diseaseEntry).attributeValues();
        assertThat(mappedEntries, notNullValue());
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
        String id = "DiseaseEntry Id";
        String def = "DiseaseEntry definition. sample";
        long revCount = 10L;
        long unrevCount = 20L;

        DiseaseEntryBuilder builder =
                getSimpleDiseaseEntry(accession, acronym, id, def, revCount, unrevCount);
        List<String> alternativeNames = Arrays.asList("ALT1", "ALT2", "ALT3");
        List<DiseaseCrossReference> xrefs =
                Arrays.asList(
                        new DiseaseCrossReferenceBuilder().databaseType("DB1").id("ID1").build(),
                        new DiseaseCrossReferenceBuilder().databaseType("DB1").id("ID2").build());
        List<KeywordId> keywords =
                Arrays.asList(
                        kw("KW1", "VAL1"), kw("KW2", "VAL2"), kw("KW3", "VAL3"), kw("KW4", "VAL4"));
        builder.alternativeNamesSet(alternativeNames).crossReferencesSet(xrefs);
        builder.keywordsSet(keywords);

        DiseaseEntry entry = builder.build();

        Map<String, String> mappedEntries = new DiseaseEntryMap(entry).attributeValues();

        assertThat(mappedEntries, notNullValue());
        assertEquals(9, mappedEntries.size());
        assertEquals(accession, mappedEntries.get("accession"));
        assertEquals(acronym, mappedEntries.get("acronym"));
        assertEquals(id, mappedEntries.get("id"));
        assertEquals(def, mappedEntries.get("definition"));
        assertEquals(String.valueOf(revCount), mappedEntries.get("reviewed_protein_count"));
        assertEquals(String.valueOf(unrevCount), mappedEntries.get("unreviewed_protein_count"));
        assertEquals(String.join(",", alternativeNames), mappedEntries.get("alternative_names"));
        assertEquals(
                String.join(",", Arrays.asList("ID1", "ID2")),
                mappedEntries.get("cross_references"));
        assertEquals(String.join(",", "KW1", "KW2", "KW3", "KW4"), mappedEntries.get("keywords"));
    }

    private DiseaseEntryBuilder getSimpleDiseaseEntry(
            String accession,
            String acronym,
            String id,
            String def,
            long revCount,
            long unrevCount) {
        DiseaseEntryBuilder builder = new DiseaseEntryBuilder();
        builder.accession(accession).acronym(acronym).id(id);
        builder.definition(def);
        builder.reviewedProteinCount(revCount).unreviewedProteinCount(unrevCount);
        return builder;
    }

    private KeywordId kw(String id, String accession) {
        return new KeywordIdBuilder().id(id).accession(accession).build();
    }
}
