package uk.ac.ebi.uniprot.domain.uniprot.feature.impl;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.PositionModifier;
import uk.ac.ebi.uniprot.domain.Range;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.FeatureFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.domain.uniprot.feature.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void testWithFeatureIdAndAlternativeSequence() {
        Range location = new Range(32, 96);
        FeatureId featureId = FeatureFactory.INSTANCE.createFeatureId("VSP_324");
        AlternativeSequence alterSeq = createAlternativeSequence();
        FeatureImpl feature = new FeatureImpl(FeatureType.VAR_SEQ, location, "Some description",
                                              featureId, alterSeq, null,
                                              createEvidences());
        assertEquals(location, feature.getLocation());
        assertEquals("Some description", feature.getDescription().getValue());
        assertEquals(2, feature.getEvidences().size());
        assertEquals(FeatureType.VAR_SEQ, feature.getType());
        assertTrue(feature.hasAlternativeSequence());
        assertTrue(feature.hasFeatureId());
        assertEquals(featureId, feature.getFeatureId());
        assertEquals(alterSeq, feature.getAlternativeSequence());
        assertNull(feature.getDbXref());
        TestHelper.verifyJson(feature);
    }

    @Test
    void testWithFeatureIdAndAlternativeSequenceAndXref() {
        Range location = new Range(32, 96);
        FeatureId featureId = FeatureFactory.INSTANCE.createFeatureId("VAR_324");
        AlternativeSequence alterSeq = createAlternativeSequence();
        DBCrossReference<FeatureXDbType> xref = new DBCrossReferenceImpl<>(FeatureXDbType.DBSNP, "rs123414");
        FeatureImpl feature = new FeatureImpl(FeatureType.VARIANT, location, "Some description",
                                              featureId, alterSeq, xref,
                                              createEvidences());
        assertEquals(location, feature.getLocation());
        assertEquals("Some description", feature.getDescription().getValue());
        assertEquals(2, feature.getEvidences().size());
        assertEquals(FeatureType.VARIANT, feature.getType());
        assertTrue(feature.hasAlternativeSequence());
        assertTrue(feature.hasFeatureId());
        assertEquals(featureId, feature.getFeatureId());
        assertEquals(alterSeq, feature.getAlternativeSequence());
        assertEquals(xref, feature.getDbXref());
        TestHelper.verifyJson(feature);
    }

    @Test
    void testWithAlternativeSequence() {
        Range location = new Range(null, 96, PositionModifier.UNKOWN,
                                   PositionModifier.OUTSIDE
        );
        FeatureId featureId = null;
        AlternativeSequence alterSeq = createAlternativeSequence();
        FeatureImpl feature = new FeatureImpl(FeatureType.CONFLICT, location, "Some description",
                                              featureId, alterSeq, null,
                                              createEvidences());
        assertEquals(location, feature.getLocation());
        assertEquals("Some description", feature.getDescription().getValue());
        assertEquals(2, feature.getEvidences().size());
        assertEquals(FeatureType.CONFLICT, feature.getType());
        assertTrue(feature.hasAlternativeSequence());
        assertFalse(feature.hasFeatureId());

        assertEquals(alterSeq, feature.getAlternativeSequence());
        assertNull(feature.getDbXref());
        TestHelper.verifyJson(feature);
    }

    private AlternativeSequence createAlternativeSequence() {
        List<String> value = Arrays.asList("some report", "another report");
        SequenceReport report = FeatureFactory.INSTANCE.createReport(value);
        AlternativeSequence as = new AlternativeSequenceImpl("AB", Arrays.asList("DC", "SDGASS"),
                                                             report
        );
        return as;
    }

    private List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000255|PROSITE-ProRule:PRU10028"));
        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000256|PIRNR:PIRNR001361"));
        return evidences;
    }
}
