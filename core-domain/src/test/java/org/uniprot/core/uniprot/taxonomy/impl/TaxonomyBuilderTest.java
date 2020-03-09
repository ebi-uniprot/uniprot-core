package org.uniprot.core.uniprot.taxonomy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.taxonomy.Taxonomy;

class TaxonomyBuilderTest {

    @Test
    void testTaxonId() {
        Taxonomy taxonomy = new TaxonomyBuilder().taxonId(9606).build();
        assertEquals(9606, taxonomy.getTaxonId());
    }

    @Test
    void testMnemonic() {
        Taxonomy taxonomy = new TaxonomyBuilder().mnemonic("HUMAN").build();
        assertEquals("HUMAN", taxonomy.getMnemonic());
    }

    @Test
    void testFromTaxonomy() {
        Taxonomy taxonomy = getCompleteTaxonomy();
        Taxonomy fromTest = TaxonomyBuilder.from(taxonomy).build();
        assertEquals(taxonomy, fromTest);
    }

    @Test
    void testScientificName() {
        Taxonomy taxonomy =
                new TaxonomyBuilder().taxonId(9606).scientificName("Homo sapiens").build();
        assertEquals("Homo sapiens", taxonomy.getScientificName());
    }

    @Test
    void testCommonName() {
        Taxonomy taxonomy = new TaxonomyBuilder().taxonId(9606).commonName("Human").build();
        assertEquals("Human", taxonomy.getCommonName());
    }

    @Test
    void testSynonyms() {
        Taxonomy taxonomy = getCompleteTaxonomy();
        assertEquals(Collections.singletonList("Some name"), taxonomy.getSynonyms());
    }

    @Test
    void defaultBuilder_differentObject_shouldEqual() {
        Taxonomy taxonomy = new TaxonomyBuilder().build();
        Taxonomy taxonomy2 = new TaxonomyBuilder().build();
        assertTrue(taxonomy.equals(taxonomy2) && taxonomy2.equals(taxonomy));
        assertEquals(taxonomy.hashCode(), taxonomy2.hashCode());
    }

    private Taxonomy getCompleteTaxonomy() {
        return new TaxonomyBuilder()
                .taxonId(9606)
                .scientificName("Homo sapiens")
                .commonName("Human")
                .synonymsSet(Collections.singletonList("Some name"))
                .mnemonic("HUMAN")
                .build();
    }
}
