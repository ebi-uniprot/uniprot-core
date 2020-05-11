package org.uniprot.core.parser.tsv.proteome;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.taxonomy.Taxonomy;
import org.uniprot.core.uniprotkb.taxonomy.impl.TaxonomyBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author jluo
 * @date: 24 Jun 2019
 */
class ProteomeTaxonomyMapTest {

    @Test
    void testAttributeValues() {
        Taxonomy taxonomy =
                new TaxonomyBuilder().taxonId(9606).scientificName("Homo sapiens").build();
        ProteomeTaxonomyMap taxMap = new ProteomeTaxonomyMap(taxonomy);
        Map<String, String> result = taxMap.attributeValues();
        assertEquals(3, result.size());
        assertEquals("Homo sapiens", result.get("organism"));
        assertEquals("9606", result.get("organism_id"));
        assertNull(result.get("mnemonic"));
    }

    @Test
    void testContains() {
        List<String> fields = Arrays.asList("upid", "organism");
        assertTrue(ProteomeTaxonomyMap.contains(fields));
    }

    @Test
    void testFields() {
        List<String> fields = Arrays.asList("organism", "organism_id", "mnemonic");
        assertEquals(ProteomeTaxonomyMap.FIELDS, fields);
    }
}
