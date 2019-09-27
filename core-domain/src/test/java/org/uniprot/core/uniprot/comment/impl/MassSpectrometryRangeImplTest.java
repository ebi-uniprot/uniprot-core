package org.uniprot.core.uniprot.comment.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.PositionModifier;
import org.uniprot.core.Range;
import org.uniprot.core.uniprot.comment.MassSpectrometryRange;

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
    }
}
