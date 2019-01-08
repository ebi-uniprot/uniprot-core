package uk.ac.ebi.uniprot.domain.uniprot.feature.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.PositionModifier;
import uk.ac.ebi.uniprot.domain.Range;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.FeatureFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.domain.uniprot.feature.AlternativeSequence;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureId;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType;


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


	private AlternativeSequence createAlternativeSequence() {
		AlternativeSequence as =new AlternativeSequenceImpl("AB", Arrays.asList("DC", "SDGASS"));
		return as;
	}
	private List<Evidence> createEvidences() {
		List<Evidence> evidences = new ArrayList<>();
		evidences.add(  UniProtFactory.INSTANCE.createEvidence("ECO:0000255|PROSITE-ProRule:PRU10028"));
		evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000256|PIRNR:PIRNR001361"));
		return evidences;
	}

}
