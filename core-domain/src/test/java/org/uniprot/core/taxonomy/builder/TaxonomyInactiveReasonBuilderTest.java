package org.uniprot.core.taxonomy.builder;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.getCompleteTaxonomyInactiveReason;

import org.junit.jupiter.api.Test;
import org.uniprot.core.taxonomy.TaxonomyInactiveReason;
import org.uniprot.core.taxonomy.TaxonomyInactiveReasonType;

class TaxonomyInactiveReasonBuilderTest {

    @Test
    void testSimpleTaxonomyInactiveReason() {
        TaxonomyInactiveReason inactiveReason = new TaxonomyInactiveReasonBuilder().build();

        assertFalse(inactiveReason.hasInactiveReasonType());
        assertFalse(inactiveReason.hasMergedTo());
    }

    @Test
    void testCompleteTaxonomyInactiveReason() {
        TaxonomyInactiveReason inactiveReason = getCompleteTaxonomyInactiveReason();

        assertTrue(inactiveReason.hasInactiveReasonType());
        assertEquals(inactiveReason.getInactiveReasonType(), TaxonomyInactiveReasonType.MERGED);

        assertTrue(inactiveReason.hasMergedTo());
        assertEquals(inactiveReason.getMergedTo(), 9604L);
    }

    @Test
    void testFromTaxonomyInactiveReason() {
        TaxonomyInactiveReason inactiveReason = getCompleteTaxonomyInactiveReason();

        TaxonomyInactiveReason otherInactiveReason =
                new TaxonomyInactiveReasonBuilder().from(inactiveReason).build();
        assertEquals(inactiveReason.toString(), otherInactiveReason.toString());
        boolean equals = inactiveReason.equals(otherInactiveReason);
        assertTrue(equals);
    }
}
