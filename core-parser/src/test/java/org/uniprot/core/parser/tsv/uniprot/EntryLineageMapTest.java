package org.uniprot.core.parser.tsv.uniprot;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.taxonomy.TaxonomyRank;
import org.uniprot.core.taxonomy.impl.TaxonomyLineageBuilder;

class EntryLineageMapTest {

    @Test
    void testFields() {
        List<String> fields = EntryLineageMap.FIELDS;
        List<String> expected = List.of("lineage", "lineage_ids");
        assertEquals(expected, fields);
    }

    @Test
    void testGetDataEmpty() {
        EntryLineageMap dl = new EntryLineageMap(null);
        Map<String, String> result = dl.attributeValues();
        assertEquals(2, result.size());
        assertTrue(dl.attributeValues().containsKey("lineage"));
        assertEquals("", dl.attributeValues().get("lineage"));
        assertTrue(dl.attributeValues().containsKey("lineage_ids"));
        assertEquals("", dl.attributeValues().get("lineage_ids"));
    }

    @Test
    void testGetData() {
        List<TaxonomyLineage> lineage = new ArrayList<>();
        lineage.add(
                new TaxonomyLineageBuilder()
                        .taxonId(10)
                        .scientificName("Eukaryota")
                        .rank(TaxonomyRank.SUPERKINGDOM)
                        .build());
        lineage.add(
                new TaxonomyLineageBuilder()
                        .taxonId(20)
                        .scientificName("Metazoa")
                        .rank(TaxonomyRank.KINGDOM)
                        .build());
        lineage.add(
                new TaxonomyLineageBuilder()
                        .taxonId(30)
                        .scientificName("Primates")
                        .rank(TaxonomyRank.ORDER)
                        .build());
        lineage.add(
                new TaxonomyLineageBuilder()
                        .taxonId(40)
                        .scientificName("Homo sapiens")
                        .rank(TaxonomyRank.SPECIES)
                        .build());
        EntryLineageMap dl = new EntryLineageMap(lineage);
        Map<String, String> result = dl.attributeValues();
        assertEquals(2, result.size());
        assertTrue(dl.attributeValues().containsKey("lineage"));
        String expectedLineage =
                "Eukaryota (superkingdom), Metazoa (kingdom), Primates (order), Homo sapiens (species)";
        assertEquals(expectedLineage, dl.attributeValues().get("lineage"));

        assertTrue(dl.attributeValues().containsKey("lineage_ids"));
        String expectedLineageIds = "10 (superkingdom), 20 (kingdom), 30 (order), 40 (species)";
        assertEquals(expectedLineageIds, dl.attributeValues().get("lineage_ids"));
    }
}
