package uk.ac.ebi.uniprot.domain.uniprot.feature.impl;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.PositionModifier;
import uk.ac.ebi.uniprot.domain.Range;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.factory.FeatureFactory;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureId;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType;

import static org.junit.Assert.*;
import static uk.ac.ebi.uniprot.domain.uniprot.EvidenceHelper.createEvidences;


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
        FeatureId featureId = FeatureFactory.INSTANCE.createFeatureId("PRO_324");
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
