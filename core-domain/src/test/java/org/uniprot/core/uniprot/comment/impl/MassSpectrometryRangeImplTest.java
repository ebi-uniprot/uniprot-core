package org.uniprot.core.uniprot.comment.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.PositionModifier;
import org.uniprot.core.Range;
import org.uniprot.core.TestHelper;
import org.uniprot.core.uniprot.comment.MassSpectrometryRange;
import org.uniprot.core.uniprot.comment.impl.MassSpectrometryRangeImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MassSpectrometryRangeImplTest {

    @Test
    void testMassSpectrometryRangeImplIntIntString() {
        MassSpectrometryRange range = new MassSpectrometryRangeImpl(23, 25, "P1232");
        verify(range, new Range(23, 25), "P1232");
    }

    @Test
    void testMassSpectrometryRangeImplRangeString() {
        Range range = new Range(null, 25, PositionModifier.UNKNOWN, PositionModifier.EXACT);

        MassSpectrometryRange msRange = new MassSpectrometryRangeImpl(range, "");
        verify(msRange, range, "");
    }

    private void verify(MassSpectrometryRange msRange, Range range, String isoformId) {
        assertEquals(range, msRange.getRange());
        assertEquals(isoformId, msRange.getIsoformId());
        TestHelper.verifyJson(msRange);
    }
}
