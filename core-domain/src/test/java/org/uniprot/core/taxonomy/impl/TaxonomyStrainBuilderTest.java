package org.uniprot.core.taxonomy.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.getCompleteTaxonomyStrain;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.uniprot.core.taxonomy.TaxonomyStrain;

class TaxonomyStrainBuilderTest {

    @Test
    void testSimpleTaxonomyStrain() {
        TaxonomyStrain taxonomyStrain = new TaxonomyStrainBuilder().name("name").build();

        assertTrue(taxonomyStrain.hasName());
        assertEquals(taxonomyStrain.getName(), "name");

        assertFalse(taxonomyStrain.hasSynonyms());
    }

    @Test
    void testCompleteTaxonomyStrain() {
        TaxonomyStrain taxonomyStrain = getCompleteTaxonomyStrain();
        validateTaxonomyStrain(taxonomyStrain);
    }

    @Test
    void testAddMethodsTaxonomyStrain() {
        TaxonomyStrain taxonomyStrain =
                new TaxonomyStrainBuilder().synonymsAdd("synonym").name("name").build();
        validateTaxonomyStrain(taxonomyStrain);
    }

    @Test
    void testFromTaxonomyStrain() {
        TaxonomyStrain taxonomyStrain = getCompleteTaxonomyStrain();

        TaxonomyStrainBuilder builder = TaxonomyStrainBuilder.from(taxonomyStrain);

        TaxonomyStrain other = builder.build();
        assertEquals(taxonomyStrain.toString(), other.toString());

        boolean equals = taxonomyStrain.equals(other);
        assertTrue(equals);
    }

    private void validateTaxonomyStrain(TaxonomyStrain taxonomyStrain) {
        assertTrue(taxonomyStrain.hasName());
        assertEquals(taxonomyStrain.getName(), "name");

        assertTrue(taxonomyStrain.hasSynonyms());
        MatcherAssert.assertThat(taxonomyStrain.getSynonyms(), Matchers.contains("synonym"));
    }
}
