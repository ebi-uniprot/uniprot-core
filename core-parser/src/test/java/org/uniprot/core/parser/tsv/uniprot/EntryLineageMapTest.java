package org.uniprot.core.parser.tsv.uniprot;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.taxonomy.TaxonomyRank;
import org.uniprot.core.taxonomy.impl.TaxonomyLineageBuilder;

class EntryLineageMapTest {

    @Test
    void testFields() {
        List<String> fields = EntryLineageMap.DEFAULT_FIELDS;
        List<String> expected = Collections.singletonList("lineage");
        assertEquals(expected, fields);
    }

    @Test
    void testGetDataEmpty() {
        EntryLineageMap dl = new EntryLineageMap(null);
        Map<String, String> result = dl.attributeValues();
        assertEquals(1, result.size());
        assertTrue(dl.attributeValues().containsKey("lineage"));
        assertEquals("", dl.attributeValues().get("lineage"));
    }

    @Test
    void testGetData() {
        List<TaxonomyLineage> lineage = new ArrayList<>();
        lineage.add(
                new TaxonomyLineageBuilder()
                        .scientificName("Eukaryota")
                        .rank(TaxonomyRank.SUPERKINGDOM)
                        .build());
        lineage.add(
                new TaxonomyLineageBuilder()
                        .scientificName("Metazoa")
                        .rank(TaxonomyRank.KINGDOM)
                        .build());
        lineage.add(
                new TaxonomyLineageBuilder()
                        .scientificName("Primates")
                        .rank(TaxonomyRank.ORDER)
                        .build());
        lineage.add(
                new TaxonomyLineageBuilder()
                        .scientificName("Homo sapiens")
                        .rank(TaxonomyRank.SPECIES)
                        .build());
        EntryLineageMap dl = new EntryLineageMap(lineage);
        Map<String, String> result = dl.attributeValues();
        assertEquals(1, result.size());
        assertTrue(dl.attributeValues().containsKey("lineage"));
        String expectedLineage =
                "Eukaryota (superkingdom), Metazoa (kingdom), Primates (order), Homo sapiens (species)";
        assertEquals(expectedLineage, dl.attributeValues().get("lineage"));
    }
}
