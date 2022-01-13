package org.uniprot.core.parser.tsv.disease;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Statistics;
import org.uniprot.core.cv.disease.DiseaseCrossReference;
import org.uniprot.core.cv.disease.DiseaseEntry;
import org.uniprot.core.cv.disease.impl.DiseaseCrossReferenceBuilder;
import org.uniprot.core.cv.disease.impl.DiseaseEntryBuilder;
import org.uniprot.core.cv.keyword.KeywordId;
import org.uniprot.core.cv.keyword.impl.KeywordIdBuilder;
import org.uniprot.core.impl.StatisticsBuilder;

class DiseaseEntryValueMapperTest {

    @Test
    void checkSimpleMapEntity() {
        String id = "DI-1234";
        String acronym = "ACRONYM";
        String name = "DiseaseEntry Id";
        String def = "DiseaseEntry definition. sample";
        long revCount = 10L;
        long unrevCount = 20L;
        DiseaseEntryBuilder builder =
                getSimpleDiseaseEntry(id, acronym, name, def, revCount, unrevCount);
        DiseaseEntry diseaseEntry = builder.build();

        Map<String, String> mappedEntries =
                new DiseaseEntryValueMapper().mapEntity(diseaseEntry, Collections.emptyList());

        assertThat(mappedEntries, notNullValue());
        assertEquals(8, mappedEntries.size());
        assertEquals(id, mappedEntries.get("id"));
        assertEquals(acronym, mappedEntries.get("acronym"));
        assertEquals(name, mappedEntries.get("name"));
        assertEquals(def, mappedEntries.get("definition"));
        assertEquals("reviewed:10; annotated:20", mappedEntries.get("statistics"));
        assertEquals(DiseaseEntryValueMapper.EMPTY_STRING, mappedEntries.get("alternative_names"));
        assertEquals(DiseaseEntryValueMapper.EMPTY_STRING, mappedEntries.get("cross_references"));
        assertEquals(DiseaseEntryValueMapper.EMPTY_STRING, mappedEntries.get("keywords"));
    }

    @Test
    void checkCompleteEntryAttributeValues() {
        String id = "DI-1234";
        String acronym = "ACRONYM";
        String name = "DiseaseEntry Id";
        String def = "DiseaseEntry definition. sample";
        long revCount = 10L;
        long unrevCount = 20L;

        DiseaseEntryBuilder builder =
                getSimpleDiseaseEntry(id, acronym, name, def, revCount, unrevCount);
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

        Map<String, String> mappedEntries =
                new DiseaseEntryValueMapper().mapEntity(entry, Collections.emptyList());

        assertThat(mappedEntries, notNullValue());
        assertEquals(8, mappedEntries.size());
        assertEquals(id, mappedEntries.get("id"));
        assertEquals(acronym, mappedEntries.get("acronym"));
        assertEquals(name, mappedEntries.get("name"));
        assertEquals(def, mappedEntries.get("definition"));
        assertEquals("reviewed:10; annotated:20", mappedEntries.get("statistics"));
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
        builder.id(accession).acronym(acronym).name(id);
        builder.definition(def);
        Statistics statistics =
                new StatisticsBuilder()
                        .reviewedProteinCount(revCount)
                        .unreviewedProteinCount(unrevCount)
                        .build();
        builder.statistics(statistics);
        return builder;
    }

    private KeywordId kw(String id, String accession) {
        return new KeywordIdBuilder().name(id).id(accession).build();
    }
}
