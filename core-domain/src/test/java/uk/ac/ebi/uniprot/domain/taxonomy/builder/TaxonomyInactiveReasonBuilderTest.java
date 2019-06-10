package uk.ac.ebi.uniprot.domain.taxonomy.builder;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonomyInactiveReason;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonomyInactiveReasonType;

import static org.junit.jupiter.api.Assertions.*;

class TaxonomyInactiveReasonBuilderTest {

    @Test
    void testSimpleTaxonomyInactiveReason(){
        TaxonomyInactiveReason inactiveReason = new TaxonomyInactiveReasonBuilder().build();

        assertFalse(inactiveReason.hasInactiveReasonType());
        assertFalse(inactiveReason.hasMergedTo());

    }

    @Test
    void testCompleteTaxonomyInactiveReason(){
        TaxonomyInactiveReason inactiveReason = getCompleteTaxonomyInactiveReason();

        assertTrue(inactiveReason.hasInactiveReasonType());
        assertEquals(inactiveReason.getInactiveReasonType(),TaxonomyInactiveReasonType.MERGED);

        assertTrue(inactiveReason.hasMergedTo());
        assertEquals(inactiveReason.getMergedTo(),9604L);

    }

    @Test
    void testFromTaxonomyInactiveReason() {
        TaxonomyInactiveReason inactiveReason = getCompleteTaxonomyInactiveReason();

        TaxonomyInactiveReason otherInactiveReason = new TaxonomyInactiveReasonBuilder().from(inactiveReason).build();
        assertEquals(inactiveReason.toString(),otherInactiveReason.toString());
        boolean equals = inactiveReason.equals(otherInactiveReason);
        assertTrue(equals);
    }

    static TaxonomyInactiveReason getCompleteTaxonomyInactiveReason(){
        return new TaxonomyInactiveReasonBuilder()
                .inactiveReasonType(TaxonomyInactiveReasonType.MERGED)
                .mergedTo(9604L)
                .build();
    }
}