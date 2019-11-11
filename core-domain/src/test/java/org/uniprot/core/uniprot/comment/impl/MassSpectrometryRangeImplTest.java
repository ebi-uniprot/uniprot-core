package org.uniprot.core.uniprot.comment.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.PositionModifier;
import org.uniprot.core.Range;
import org.uniprot.core.uniprot.comment.MassSpectrometryRange;
import org.uniprot.core.uniprot.comment.builder.MassSpectrometryRangeBuilder;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        MassSpectrometryRange obj = new MassSpectrometryRangeImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        MassSpectrometryRange impl = new MassSpectrometryRangeImpl(1,2,"isofrom");
        MassSpectrometryRange obj = new MassSpectrometryRangeBuilder().from(impl).build();

        assertTrue(impl.hasIsoformId());

        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }

    private void verify(MassSpectrometryRange msRange, Range range, String isoformId) {
        assertEquals(range, msRange.getRange());
        assertEquals(isoformId, msRange.getIsoformId());
    }
}
