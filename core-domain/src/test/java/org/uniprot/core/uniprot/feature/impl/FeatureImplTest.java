package org.uniprot.core.uniprot.feature.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.PositionModifier;
import org.uniprot.core.Range;
import org.uniprot.core.TestHelper;
import org.uniprot.core.uniprot.feature.FeatureId;
import org.uniprot.core.uniprot.feature.FeatureType;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.uniprot.EvidenceHelper.createEvidences;


class FeatureImplTest {

    @Test
    void testSimple() {
        Range location = new Range(32, 50, PositionModifier.EXACT,
                                   PositionModifier.UNSURE);
        FeatureImpl feature = new FeatureImpl(FeatureType.ACT_SITE, location, "Some description", createEvidences());
        assertEquals(location, feature.getLocation());
        assertEquals("Some description", feature.getDescription().getValue());
        assertEquals(2, feature.getEvidences().size());
        assertEquals(FeatureType.ACT_SITE, feature.getType());
        assertFalse(feature.hasAlternativeSequence());
        assertFalse(feature.hasFeatureId());
        TestHelper.verifyJson(feature);
    }

    @Test
    void testWithFeatureId() {
        Range location = new Range(32, 96);
        FeatureId featureId = new FeatureIdImpl("PRO_324");
        FeatureImpl feature = new FeatureImpl(FeatureType.CHAIN, location, "Some chain description",
                                              featureId,
                                              createEvidences());
        assertEquals(location, feature.getLocation());
        assertEquals("Some chain description", feature.getDescription().getValue());
        assertEquals(2, feature.getEvidences().size());
        assertEquals(FeatureType.CHAIN, feature.getType());
        assertFalse(feature.hasAlternativeSequence());
        assertTrue(feature.hasFeatureId());
        assertEquals(featureId, feature.getFeatureId());
        assertNull(feature.getAlternativeSequence());
        assertNull(feature.getDbXref());
        TestHelper.verifyJson(feature);
    }
}
