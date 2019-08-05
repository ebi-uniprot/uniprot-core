package org.uniprot.core.taxonomy.builder;

import org.junit.jupiter.api.Test;
import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.taxonomy.TaxonomyRank;
import org.uniprot.core.taxonomy.builder.TaxonomyLineageBuilder;

import static org.junit.jupiter.api.Assertions.*;

class TaxonomyLineageBuilderTest {

    @Test
    void testSimpleTaxonomyLineage(){
        TaxonomyLineageBuilder builder = new TaxonomyLineageBuilder();
        builder.taxonId(9606L);

        TaxonomyLineage taxonomyLineage = builder.build();

        assertTrue(taxonomyLineage.hasTaxonId());
        assertEquals(taxonomyLineage.getTaxonId(),9606L);

        assertFalse(taxonomyLineage.hasScientificName());
        assertFalse(taxonomyLineage.hasRank());
    }

    @Test
    void testCompleteTaxonomyLineage(){
        TaxonomyLineage taxonomyLineage = getCompleteTaxonomyLineage();

        assertTrue(taxonomyLineage.hasTaxonId());
        assertEquals(taxonomyLineage.getTaxonId(),9606L);

        assertTrue(taxonomyLineage.hasRank());
        assertEquals(taxonomyLineage.getRank(),TaxonomyRank.FAMILY);

        assertTrue(taxonomyLineage.hasScientificName());
        assertEquals(taxonomyLineage.getScientificName(),"Scientific Name");
    }

    @Test
    void testFromTaxonomyLineage(){
        TaxonomyLineage taxonomyLineage = getCompleteTaxonomyLineage();

        TaxonomyLineageBuilder builder = new TaxonomyLineageBuilder().from(taxonomyLineage);

        TaxonomyLineage other = builder.build();
        assertEquals(taxonomyLineage.toString(), other.toString());

        boolean equals = taxonomyLineage.equals(other);
        assertTrue(equals);
    }

    static TaxonomyLineage getCompleteTaxonomyLineage(){
        TaxonomyLineageBuilder builder = new TaxonomyLineageBuilder();
        builder.taxonId(9606L).scientificName("Scientific Name").hidden(true).rank(TaxonomyRank.FAMILY);
        return builder.build();
    }

}