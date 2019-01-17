package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.PositionModifier;
import uk.ac.ebi.uniprot.domain.Range;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MassSpectrometryRange;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MassSpectrometryRangeImplTest {

    @Test
    void testMassSpectrometryRangeImplIntIntString() {
        MassSpectrometryRange range = new MassSpectrometryRangeImpl(23, 25, "P1232");
        verify(range, new Range(23, 25), "P1232");
    }

    @Test
    void testMassSpectrometryRangeImplRangeString() {
        Range range = new Range(null, 25, PositionModifier.UNKOWN, PositionModifier.EXACT);

        MassSpectrometryRange msRange = new MassSpectrometryRangeImpl(range, "");
        verify(msRange, range, "");
    }

    private void verify(MassSpectrometryRange msRange, Range range, String isoformId) {
        assertEquals(range, msRange.getRange());
        assertEquals(isoformId, msRange.getIsoformId());
        TestHelper.verifyJson(msRange);
    }
}
