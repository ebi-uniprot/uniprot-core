package org.uniprot.core.flatfile.transformer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.PositionModifier;
import org.uniprot.core.flatfile.parser.impl.ft.FtLineTransformer;
import org.uniprot.core.uniprotkb.feature.UniProtKBFeature;
import org.uniprot.core.uniprotkb.feature.UniprotKBFeatureType;

class FtLineTransformerTest {
    private FtLineTransformer transformer = new FtLineTransformer();

    @Test
    void testChain() {
        String lines = "CHAIN ?..121\n/note=\"Potential\"\n" + "/id=\"PRO_5001267722\"\n";

        List<UniProtKBFeature> features = transformer.transformNoHeader(lines);
        assertEquals(1, features.size());
        UniProtKBFeature feature = features.get(0);

        assertEquals(PositionModifier.UNKNOWN, feature.getLocation().getStart().getModifier());
        assertEquals(121, feature.getLocation().getEnd().getValue().intValue());
        assertEquals(UniprotKBFeatureType.CHAIN, feature.getType());
        assertEquals("Potential", feature.getDescription().getValue());
        assertEquals("PRO_5001267722", feature.getFeatureId().getValue());
    }

    @Test
    void testSignal() {
        String lines = "SIGNAL <1..33\n/note=\"some description\"\n";

        List<UniProtKBFeature> features = transformer.transformNoHeader(lines);
        assertEquals(1, features.size());
        UniProtKBFeature feature = features.get(0);

        assertEquals(1, feature.getLocation().getStart().getValue().intValue());
        assertEquals(PositionModifier.OUTSIDE, feature.getLocation().getStart().getModifier());
        assertEquals(33, feature.getLocation().getEnd().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature.getLocation().getEnd().getModifier());
        assertEquals(UniprotKBFeatureType.SIGNAL, feature.getType());
        assertEquals("some description", feature.getDescription().getValue());
    }

    @Test
    void testConflict() {
        String lines = "CONFLICT 124..127\n/note=\"GLTA -> ESHP (in Ref. 1; AAA98633)\"\n";

        List<UniProtKBFeature> features = transformer.transformNoHeader(lines);
        assertEquals(1, features.size());
        UniProtKBFeature feature = features.get(0);

        assertEquals(124, feature.getLocation().getStart().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature.getLocation().getStart().getModifier());
        assertEquals(127, feature.getLocation().getEnd().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature.getLocation().getEnd().getModifier());
        assertEquals(UniprotKBFeatureType.CONFLICT, feature.getType());

        assertEquals("GLTA", feature.getAlternativeSequence().getOriginalSequence());
        assertEquals(1, feature.getAlternativeSequence().getAlternativeSequences().size());
        assertEquals("ESHP", feature.getAlternativeSequence().getAlternativeSequences().get(0));
        //	assertEquals(1,feature.getAlternativeSequence().getReport().getValue().size());
        //	assertEquals("1; AAA98633",
        // feature.getAlternativeSequence().getReport().getValue().get(0));
        assertEquals("in Ref. 1; AAA98633", feature.getDescription().getValue());
    }

    @Test
    void testMutagen() {
        String lines =
                "MUTAGEN 9\n"
                        + "/note=\"K->R: Does not affect E-cadherin/CDH1 repression; when associated"
                        + " with R-16\"\n";

        List<UniProtKBFeature> features = transformer.transformNoHeader(lines);
        assertEquals(1, features.size());
        UniProtKBFeature feature = features.get(0);
        assertEquals(UniprotKBFeatureType.MUTAGEN, feature.getType());

        assertEquals(9, feature.getLocation().getStart().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature.getLocation().getStart().getModifier());
        assertEquals(9, feature.getLocation().getEnd().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature.getLocation().getEnd().getModifier());
        assertEquals(UniprotKBFeatureType.MUTAGEN, feature.getType());

        assertEquals("K", feature.getAlternativeSequence().getOriginalSequence());
        assertEquals(1, feature.getAlternativeSequence().getAlternativeSequences().size());
        assertEquals("R", feature.getAlternativeSequence().getAlternativeSequences().get(0));
        //		assertEquals(
        //				"Does not affect E-cadherin/CDH1 repression; when associated with R-16",
        //				feature.getAlternativeSequence().getReport().getValue().get(0));
        assertEquals(
                "Does not affect E-cadherin/CDH1 repression; when associated with R-16",
                feature.getDescription().getValue());
    }

    @Test
    void testVariant() {
        String lines =
                "VARIANT 421\n/note=\"C -> R (in GS; dbSNP:rs28936387)\"\n"
                        + "/id=\"VAR_007115\"\n";

        List<UniProtKBFeature> features = transformer.transformNoHeader(lines);
        assertEquals(1, features.size());
        UniProtKBFeature feature = features.get(0);

        assertEquals(UniprotKBFeatureType.VARIANT, feature.getType());
        assertEquals(421, feature.getLocation().getStart().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature.getLocation().getStart().getModifier());
        assertEquals(421, feature.getLocation().getEnd().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature.getLocation().getEnd().getModifier());
        assertEquals(UniprotKBFeatureType.VARIANT, feature.getType());

        assertEquals("C", feature.getAlternativeSequence().getOriginalSequence());
        assertEquals(1, feature.getAlternativeSequence().getAlternativeSequences().size());
        assertEquals("R", feature.getAlternativeSequence().getAlternativeSequences().get(0));

        //		assertEquals(
        //				"in GS; dbSNP:rs28936387",
        //				feature.getAlternativeSequence().getReport().getValue().get(0));
        assertEquals("VAR_007115", feature.getFeatureId().getValue());
        assertEquals("in GS; dbSNP:rs28936387", feature.getDescription().getValue());
    }

    @Test
    void testVariant2() {
        String lines = "VARIANT 561\n/note=\"Missing (in GS)\"\n" + "/id=\"VAR_007118\"\n";

        List<UniProtKBFeature> features = transformer.transformNoHeader(lines);
        assertEquals(1, features.size());
        UniProtKBFeature feature = features.get(0);
        assertEquals(UniprotKBFeatureType.VARIANT, feature.getType());
        assertEquals(561, feature.getLocation().getStart().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature.getLocation().getStart().getModifier());
        assertEquals(561, feature.getLocation().getEnd().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature.getLocation().getEnd().getModifier());
        assertEquals(UniprotKBFeatureType.VARIANT, feature.getType());
        // assertEquals("C", cFeature.getOriginalSequence().getValue());
        assertEquals(0, feature.getAlternativeSequence().getAlternativeSequences().size());
        //	assertEquals("R", cFeature.getAlternativeSequences().get(0).getValue());

        //		assertEquals(
        //				"in GS",
        //				feature.getAlternativeSequence().getReport().getValue().get(0));
        assertEquals("in GS", feature.getDescription().getValue());
        assertEquals("VAR_007118", feature.getFeatureId().getValue());
    }

    @Test
    void testVarSeq() {
        String lines =
                "VAR_SEQ 239\n/note=\"E -> ERDVIRSVRLPRE (in isoform PLEC-0, isoform 1C,"
                        + " isoform 2A and isoform 3A)\"\n"
                        + "/id=\"VSP_005049\"\n";

        List<UniProtKBFeature> features = transformer.transformNoHeader(lines);
        assertEquals(1, features.size());
        UniProtKBFeature feature = features.get(0);
        assertEquals(UniprotKBFeatureType.VAR_SEQ, feature.getType());

        assertEquals(239, feature.getLocation().getStart().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature.getLocation().getStart().getModifier());
        assertEquals(239, feature.getLocation().getEnd().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature.getLocation().getEnd().getModifier());
        assertEquals(UniprotKBFeatureType.VAR_SEQ, feature.getType());
        assertEquals("E", feature.getAlternativeSequence().getOriginalSequence());
        assertEquals(1, feature.getAlternativeSequence().getAlternativeSequences().size());
        assertEquals(
                "ERDVIRSVRLPRE", feature.getAlternativeSequence().getAlternativeSequences().get(0));
        //		assertEquals(4, feature.getAlternativeSequence().getReport().getValue().size());
        //		assertEquals(
        //				"PLEC-0",
        //				feature.getAlternativeSequence().getReport().getValue().get(0));
        //
        //		assertEquals(
        //				"2A",
        //				feature.getAlternativeSequence().getReport().getValue().get(2));
        assertEquals(
                "in isoform PLEC-0, isoform 1C, isoform 2A and isoform 3A",
                feature.getDescription().getValue());
        assertEquals("VSP_005049", feature.getFeatureId().getValue());
    }

    @Test
    void testVarSeq2() {
        String lines =
                "VAR_SEQ 1..242\n/note=\"Missing (in isoform PLEC-1H)\"\n" + "/id=\"VSP_005040\"\n";

        List<UniProtKBFeature> features = transformer.transformNoHeader(lines);
        assertEquals(1, features.size());
        UniProtKBFeature feature = features.get(0);
        assertEquals(UniprotKBFeatureType.VAR_SEQ, feature.getType());

        assertEquals(1, feature.getLocation().getStart().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature.getLocation().getStart().getModifier());
        assertEquals(242, feature.getLocation().getEnd().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature.getLocation().getEnd().getModifier());
        assertEquals(UniprotKBFeatureType.VAR_SEQ, feature.getType());

        // assertEquals("E", cFeature.getOriginalSequence().getValue());
        assertEquals(0, feature.getAlternativeSequence().getAlternativeSequences().size());
        // assertEquals("ERDVIRSVRLPRE", cFeature.getAlternativeSequences().get(0).getValue());
        //		assertEquals(1, feature.getAlternativeSequence().getReport().getValue().size());
        //		assertEquals(
        //				"PLEC-1H",
        //				feature.getAlternativeSequence().getReport().getValue().get(0));
        assertEquals("in isoform PLEC-1H", feature.getDescription().getValue());
        assertEquals("VSP_005040", feature.getFeatureId().getValue());
    }
    //

    @Test
    void testCarbohyd() {
        String lines = "CARBOHYD 196\n/note=\"N-linked (GlcNAc...); by host\"\n";

        List<UniProtKBFeature> features = transformer.transformNoHeader(lines);
        assertEquals(1, features.size());
        UniProtKBFeature feature = features.get(0);
        assertEquals(UniprotKBFeatureType.CARBOHYD, feature.getType());

        assertEquals(196, feature.getLocation().getStart().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature.getLocation().getStart().getModifier());
        assertEquals(196, feature.getLocation().getEnd().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature.getLocation().getEnd().getModifier());
        assertEquals(UniprotKBFeatureType.CARBOHYD, feature.getType());
        //	assertEquals(CarbohydLinkType.NITROGEN, feature.getCarbohydLinkType());
        //	assertEquals("(GlcNAc...)", feature.getLinkedSugar().getValue());
        assertEquals("N-linked (GlcNAc...); by host", feature.getDescription().getValue());
    }

    @Test
    void testCarbohyd2() {
        String lines = "CARBOHYD 7\n/note=\"O-linked (GalNAc...)\"\n";
        List<UniProtKBFeature> features = transformer.transformNoHeader(lines);
        assertEquals(1, features.size());
        UniProtKBFeature feature = features.get(0);
        assertEquals(UniprotKBFeatureType.CARBOHYD, feature.getType());

        assertEquals(7, feature.getLocation().getStart().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature.getLocation().getStart().getModifier());
        assertEquals(7, feature.getLocation().getEnd().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature.getLocation().getEnd().getModifier());
        assertEquals(UniprotKBFeatureType.CARBOHYD, feature.getType());

        //		assertEquals(CarbohydLinkType.OXYGEN, feature.getCarbohydLinkType());
        //		assertEquals("(GalNAc...)", feature.getLinkedSugar().getValue());
        assertEquals("O-linked (GalNAc...)", feature.getDescription().getValue());
    }
}
