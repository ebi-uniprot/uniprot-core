package org.uniprot.core.parser.tsv.proteome;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.taxonomy.builder.TaxonomyLineageBuilder;

/**
 * @author jluo
 * @date: 24 Jun 2019
 */
class ProteomeTaxonomyLineageMapTest {

    @Test
    void testAttributeValues() {
        TaxonomyLineage taxon1 =
                new TaxonomyLineageBuilder().taxonId(9605).scientificName("Homo").build();
        TaxonomyLineage taxon2 =
                new TaxonomyLineageBuilder().taxonId(9604).scientificName("Hominidae").build();
        List<TaxonomyLineage> lineage = Arrays.asList(taxon1, taxon2);
        ProteomeTaxonomyLineageMap lineageMap = new ProteomeTaxonomyLineageMap(lineage);
        Map<String, String> result = lineageMap.attributeValues();
        assertEquals(1, result.size());
        assertEquals("Homo, Hominidae", result.get("lineage"));
    }

    @Test
    void testContains() {
        List<String> fields = Arrays.asList("upid", "organism");
        assertFalse(ProteomeTaxonomyLineageMap.contains(fields));
        fields = Arrays.asList("upid", "lineage");
        assertTrue(ProteomeTaxonomyLineageMap.contains(fields));
    }

    @Test
    void testFields() {
        List<String> fields = Arrays.asList("lineage");
        assertEquals(ProteomeTaxonomyLineageMap.FIELDS, fields);
    }
}
