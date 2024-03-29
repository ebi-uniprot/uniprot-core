package org.uniprot.core.flatfile.transformer;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.uniprot.core.PositionModifier;
import org.uniprot.core.flatfile.parser.impl.ft.FeatureLineBuilderFactory;
import org.uniprot.core.flatfile.writer.FFLineBuilder;
import org.uniprot.core.uniprotkb.feature.Ligand;
import org.uniprot.core.uniprotkb.feature.LigandPart;
import org.uniprot.core.uniprotkb.feature.UniProtKBFeature;
import org.uniprot.core.uniprotkb.feature.UniprotKBFeatureType;

class FeatureTranslatorTest {
    private final FeatureTransformer transformer = new FeatureTransformer();

    @Test
    void testNonTer() {
        // "SIGNAL <1 33 Potential.",
        // "CHAIN 34 121 Potential.\n/FTId=PRO_5001267722.",
        //

        String testString = "NON_TER 1\n";

        UniProtKBFeature feature = transformer.transform(testString);
        assertNotNull(feature);
        assertEquals(UniprotKBFeatureType.NON_TER, feature.getType());
        assertEquals(1, feature.getLocation().getStart().getValue().intValue());
        assertEquals(1, feature.getLocation().getEnd().getValue().intValue());
        assertEquals(UniprotKBFeatureType.NON_TER, feature.getType());
    }

    @Test
    void testChain() {
        String testString = "CHAIN ?..121\n/note=\"Potential\"\n" + "/id=\"PRO_5001267722\"\n";

        UniProtKBFeature feature = transformer.transform(testString);
        assertNotNull(feature);
        assertEquals(UniprotKBFeatureType.CHAIN, feature.getType());
        assertEquals(PositionModifier.UNKNOWN, feature.getLocation().getStart().getModifier());
        assertEquals(121, feature.getLocation().getEnd().getValue().intValue());
        assertEquals(UniprotKBFeatureType.CHAIN, feature.getType());
        //      assertEquals(FeatureStatus.POTENTIAL, feature.getFeatureStatus());
        assertEquals("PRO_5001267722", feature.getFeatureId().getValue());
    }

    @Test
    void testSignal() {
        String testString = "SIGNAL <1..33\n/note=\"Potential\"\n";

        UniProtKBFeature feature = transformer.transform(testString);
        assertNotNull(feature);
        assertEquals(UniprotKBFeatureType.SIGNAL, feature.getType());
        assertEquals(1, feature.getLocation().getStart().getValue().intValue());
        assertEquals(PositionModifier.OUTSIDE, feature.getLocation().getStart().getModifier());
        assertEquals(33, feature.getLocation().getEnd().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature.getLocation().getEnd().getModifier());
        assertEquals(UniprotKBFeatureType.SIGNAL, feature.getType());
        //   assertEquals(FeatureStatus.POTENTIAL, feature.getFeatureStatus());
    }

    @Test
    void testBindingFull() {
        String testString =
                "BINDING 138\n"
                        + "/ligand=\"heme c\"\n"
                        + "/ligand_id=\"ChEBI:CHEBI:61717\"\n"
                        + "/ligand_label=\"1\"\n"
                        + "/ligand_note=\"note 1\"\n"
                        + "/ligand_part=\"Fe\"\n"
                        + "/ligand_part_id=\"ChEBI:CHEBI:18248\"\n"
                        + "/note=\"NAD(P)HX; via amide nitrogen\"";

        UniProtKBFeature feature = transformer.transform(testString);
        assertNotNull(feature);
        assertEquals(UniprotKBFeatureType.BINDING, feature.getType());
        assertEquals(138, feature.getLocation().getStart().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature.getLocation().getStart().getModifier());
        assertEquals(138, feature.getLocation().getEnd().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature.getLocation().getEnd().getModifier());

        Ligand ligand = feature.getLigand();
        assertNotNull(ligand);
        assertEquals("heme c", ligand.getName());
        assertEquals("ChEBI:CHEBI:61717", ligand.getId());
        assertEquals("1", ligand.getLabel());
        assertEquals("note 1", ligand.getNote());

        LigandPart ligandPart = feature.getLigandPart();
        assertNotNull(ligandPart);
        assertEquals("Fe", ligandPart.getName());
        assertEquals("ChEBI:CHEBI:18248", ligandPart.getId());
        assertNull(ligandPart.getLabel());
        assertNull(ligandPart.getNote());

        assertEquals("NAD(P)HX; via amide nitrogen", feature.getDescription().getValue());
        FFLineBuilder<UniProtKBFeature> builder = FeatureLineBuilderFactory.create(feature);

        String converted = builder.buildString(feature);
        assertEquals(testString, converted);
    }

    @Test
    void testBindingNoLigandPart() {
        String testString =
                "BINDING 138\n"
                        + "/ligand=\"heme c\"\n"
                        + "/ligand_id=\"ChEBI:CHEBI:61717\"\n"
                        + "/ligand_label=\"1\"\n"
                        + "/ligand_note=\"note 1\"\n"
                        + "/note=\"NAD(P)HX; via amide nitrogen\"";

        UniProtKBFeature feature = transformer.transform(testString);
        assertNotNull(feature);
        assertEquals(UniprotKBFeatureType.BINDING, feature.getType());
        assertEquals(138, feature.getLocation().getStart().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature.getLocation().getStart().getModifier());
        assertEquals(138, feature.getLocation().getEnd().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature.getLocation().getEnd().getModifier());

        Ligand ligand = feature.getLigand();
        assertNotNull(ligand);
        assertEquals("heme c", ligand.getName());
        assertEquals("ChEBI:CHEBI:61717", ligand.getId());
        assertEquals("1", ligand.getLabel());
        assertEquals("note 1", ligand.getNote());

        LigandPart ligandPart = feature.getLigandPart();
        assertNull(ligandPart);
        assertEquals("NAD(P)HX; via amide nitrogen", feature.getDescription().getValue());
        FFLineBuilder<UniProtKBFeature> builder = FeatureLineBuilderFactory.create(feature);

        String converted = builder.buildString(feature);
        assertEquals(testString, converted);
    }

    @Test
    void testConflict() {
        String testString = "CONFLICT 124..127\n/note=\"GLTA -> ESHP (in Ref. 1; AAA98633)\"";
        UniProtKBFeature feature = transformer.transform(testString);
        assertNotNull(feature);
        assertEquals(UniprotKBFeatureType.CONFLICT, feature.getType());

        assertEquals(124, feature.getLocation().getStart().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature.getLocation().getStart().getModifier());
        assertEquals(127, feature.getLocation().getEnd().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature.getLocation().getEnd().getModifier());
        assertEquals(UniprotKBFeatureType.CONFLICT, feature.getType());
        assertEquals("GLTA", feature.getAlternativeSequence().getOriginalSequence());
        assertEquals(1, feature.getAlternativeSequence().getAlternativeSequences().size());
        assertEquals("ESHP", feature.getAlternativeSequence().getAlternativeSequences().get(0));
        //   assertEquals(1, feature.getAlternativeSequence().getReport().getValue().size());
        //    assertEquals("1; AAA98633",
        // feature.getAlternativeSequence().getReport().getValue().get(0));
        assertEquals("in Ref. 1; AAA98633", feature.getDescription().getValue());

        FFLineBuilder<UniProtKBFeature> builder = FeatureLineBuilderFactory.create(feature);

        String converted = builder.buildString(feature);
        assertEquals(testString, converted);
    }

    @Test
    void testConflict2() {
        String testString =
                "CONFLICT 124..127\n/note=\"GLTA -> ESHP (in Ref. 1; AAA98633 and 3; AA432)\"";
        UniProtKBFeature feature = transformer.transform(testString);
        assertNotNull(feature);
        assertEquals(UniprotKBFeatureType.CONFLICT, feature.getType());

        assertEquals(124, feature.getLocation().getStart().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature.getLocation().getStart().getModifier());
        assertEquals(127, feature.getLocation().getEnd().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature.getLocation().getEnd().getModifier());
        assertEquals(UniprotKBFeatureType.CONFLICT, feature.getType());
        assertEquals("GLTA", feature.getAlternativeSequence().getOriginalSequence());
        assertEquals(1, feature.getAlternativeSequence().getAlternativeSequences().size());
        assertEquals("ESHP", feature.getAlternativeSequence().getAlternativeSequences().get(0));
        // assertEquals(2, feature.getAlternativeSequence().getReport().getValue().size());
        //  assertEquals("1; AAA98633",
        // feature.getAlternativeSequence().getReport().getValue().get(0));
        //   assertEquals("3; AA432",
        // feature.getAlternativeSequence().getReport().getValue().get(1));
        assertEquals("in Ref. 1; AAA98633 and 3; AA432", feature.getDescription().getValue());
        FFLineBuilder<UniProtKBFeature> builder = FeatureLineBuilderFactory.create(feature);

        String converted = builder.buildString(feature);
        assertEquals(testString, converted);
    }

    @Test
    void testConflict3() {
        String testString =
                "CONFLICT 124..127\n"
                        + "/note=\"GLTA -> ESHP (in Ref. 1; AAA98633, 3; AA432 and 4; AB321)\"";

        UniProtKBFeature feature = transformer.transform(testString);
        assertNotNull(feature);
        assertEquals(UniprotKBFeatureType.CONFLICT, feature.getType());

        assertEquals(124, feature.getLocation().getStart().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature.getLocation().getStart().getModifier());
        assertEquals(127, feature.getLocation().getEnd().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature.getLocation().getEnd().getModifier());
        assertEquals(UniprotKBFeatureType.CONFLICT, feature.getType());
        assertEquals("GLTA", feature.getAlternativeSequence().getOriginalSequence());
        assertEquals(1, feature.getAlternativeSequence().getAlternativeSequences().size());
        assertEquals("ESHP", feature.getAlternativeSequence().getAlternativeSequences().get(0));
        //        assertEquals(3, feature.getAlternativeSequence().getReport().getValue().size());
        //        assertEquals("1; AAA98633",
        // feature.getAlternativeSequence().getReport().getValue().get(0));
        //        assertEquals("3; AA432",
        // feature.getAlternativeSequence().getReport().getValue().get(1));
        //        assertEquals("4; AB321",
        // feature.getAlternativeSequence().getReport().getValue().get(2));
        assertEquals(
                "in Ref. 1; AAA98633, 3; AA432 and 4; AB321", feature.getDescription().getValue());
        FFLineBuilder<UniProtKBFeature> builder = FeatureLineBuilderFactory.create(feature);

        String converted = builder.buildString(feature);
        assertEquals(testString, converted);
    }

    @Test
    void testMutagen() {
        String testString =
                "MUTAGEN 9\n"
                        + "/note=\"K->R: Does not affect E-cadherin/CDH1 repression; when associated"
                        + " with R-16\"";

        UniProtKBFeature feature = transformer.transform(testString);
        assertNotNull(feature);
        assertEquals(UniprotKBFeatureType.MUTAGEN, feature.getType());

        assertEquals(9, feature.getLocation().getStart().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature.getLocation().getStart().getModifier());
        assertEquals(9, feature.getLocation().getEnd().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature.getLocation().getEnd().getModifier());
        assertEquals(UniprotKBFeatureType.MUTAGEN, feature.getType());

        assertEquals("K", feature.getAlternativeSequence().getOriginalSequence());
        assertEquals(1, feature.getAlternativeSequence().getAlternativeSequences().size());
        assertEquals("R", feature.getAlternativeSequence().getAlternativeSequences().get(0));
        //   assertEquals(
        //            "Does not affect E-cadherin/CDH1 repression; when associated with R-16",
        //            feature.getAlternativeSequence().getReport().getValue().get(0));
        assertEquals(
                "Does not affect E-cadherin/CDH1 repression; when associated with R-16",
                feature.getDescription().getValue());
        FFLineBuilder<UniProtKBFeature> builder = FeatureLineBuilderFactory.create(feature);

        String converted = builder.buildString(feature);
        assertEquals(testString, converted);
    }

    @Test
    void testVariant() {
        String testString =
                "VARIANT 421\n/note=\"C -> R (in GS; dbSNP:rs28936387)\"\n" + "/id=\"VAR_007115\"";

        UniProtKBFeature feature = transformer.transform(testString);
        assertNotNull(feature);
        assertEquals(UniprotKBFeatureType.VARIANT, feature.getType());

        assertEquals(421, feature.getLocation().getStart().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature.getLocation().getStart().getModifier());
        assertEquals(421, feature.getLocation().getEnd().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature.getLocation().getEnd().getModifier());
        assertEquals(UniprotKBFeatureType.VARIANT, feature.getType());

        assertEquals("C", feature.getAlternativeSequence().getOriginalSequence());
        assertEquals(1, feature.getAlternativeSequence().getAlternativeSequences().size());
        assertEquals("R", feature.getAlternativeSequence().getAlternativeSequences().get(0));

        //   assertEquals(
        //           "in GS; dbSNP:rs28936387",
        //            feature.getAlternativeSequence().getReport().getValue().get(0));
        assertEquals("in GS; dbSNP:rs28936387", feature.getDescription().getValue());
        FFLineBuilder<UniProtKBFeature> builder = FeatureLineBuilderFactory.create(feature);

        String converted = builder.buildString(feature);
        assertEquals(testString, converted);
        assertEquals("VAR_007115", feature.getFeatureId().getValue());
    }

    @Test
    void testVariant2() {

        String testString = "VARIANT 561\n/note=\"Missing (in GS)\"\n" + "/id=\"VAR_007118\"";
        UniProtKBFeature feature = transformer.transform(testString);
        assertNotNull(feature);

        assertEquals(UniprotKBFeatureType.VARIANT, feature.getType());
        assertEquals(561, feature.getLocation().getStart().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature.getLocation().getStart().getModifier());
        assertEquals(561, feature.getLocation().getEnd().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature.getLocation().getEnd().getModifier());
        assertEquals(UniprotKBFeatureType.VARIANT, feature.getType());
        assertEquals(0, feature.getAlternativeSequence().getAlternativeSequences().size());

        //        assertEquals(
        //                "in GS",
        //                feature.getAlternativeSequence().getReport().getValue().get(0));
        assertEquals("in GS", feature.getDescription().getValue());
        FFLineBuilder<UniProtKBFeature> builder = FeatureLineBuilderFactory.create(feature);

        String converted = builder.buildString(feature);

        assertEquals(testString, converted);
        assertEquals("VAR_007118", feature.getFeatureId().getValue());
    }

    @Test
    void testVarSeq() {
        String testString =
                "VAR_SEQ 239\n/note=\"E -> ERDVIRSVRLPRE (in isoform PLEC-0, isoform 1C,"
                        + " isoform 2A and isoform 3A)\"\n"
                        + "/id=\"VSP_005049\"";
        UniProtKBFeature feature = transformer.transform(testString);
        assertNotNull(feature);
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
        //  assertEquals(4, feature.getAlternativeSequence().getReport().getValue().size());
        //   assertEquals(
        //           "PLEC- 0",
        //           feature.getAlternativeSequence().getReport().getValue().get(0));
        //        assertEquals(
        //                "2A",
        //                feature.getAlternativeSequence().getReport().getValue().get(2));
        assertEquals(
                "in isoform PLEC-0, isoform 1C, isoform 2A and isoform 3A",
                feature.getDescription().getValue());
        FFLineBuilder<UniProtKBFeature> builder = FeatureLineBuilderFactory.create(feature);

        String converted = builder.buildString(feature);
        assertEquals(testString, converted);
        assertEquals("VSP_005049", feature.getFeatureId().getValue());
    }

    @Test
    void testVarSeq2() {
        String testString =
                "VAR_SEQ 1..242\n/note=\"Missing (in isoform PLEC-1H)\"\n" + "/id=\"VSP_005040\"";

        UniProtKBFeature feature = transformer.transform(testString);
        assertNotNull(feature);
        assertEquals(UniprotKBFeatureType.VAR_SEQ, feature.getType());

        assertEquals(1, feature.getLocation().getStart().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature.getLocation().getStart().getModifier());
        assertEquals(242, feature.getLocation().getEnd().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature.getLocation().getEnd().getModifier());
        assertEquals(UniprotKBFeatureType.VAR_SEQ, feature.getType());

        assertEquals(0, feature.getAlternativeSequence().getAlternativeSequences().size());
        //        assertEquals(1, feature.getAlternativeSequence().getReport().getValue().size());
        //        assertEquals(
        //                "PLEC-1H",
        //                feature.getAlternativeSequence().getReport().getValue().get(0));
        assertEquals("in isoform PLEC-1H", feature.getDescription().getValue());
        FFLineBuilder<UniProtKBFeature> builder = FeatureLineBuilderFactory.create(feature);

        String converted = builder.buildString(feature);
        assertEquals(testString, converted);

        assertEquals("VSP_005040", feature.getFeatureId().getValue());
    }
    //

    @Test
    void testCarbohyd() {
        String testString = "CARBOHYD 196\n/note=\"N-linked (GlcNAc...); by host\"\n";

        UniProtKBFeature feature = transformer.transform(testString);
        assertNotNull(feature);
        assertEquals(UniprotKBFeatureType.CARBOHYD, feature.getType());

        assertEquals(196, feature.getLocation().getStart().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature.getLocation().getStart().getModifier());
        assertEquals(196, feature.getLocation().getEnd().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature.getLocation().getEnd().getModifier());
        assertEquals(UniprotKBFeatureType.CARBOHYD, feature.getType());
        assertEquals("N-linked (GlcNAc...); by host", feature.getDescription().getValue());
        FFLineBuilder<UniProtKBFeature> builder = FeatureLineBuilderFactory.create(feature);

        String converted = builder.buildString(feature);
        assertEquals(testString, converted + "\n");
    }

    @Test
    void testCarbohyd2() {
        String testString = "CARBOHYD 7\n/note=\"O-linked (GalNAc...)\"";

        UniProtKBFeature feature = transformer.transform(testString);
        assertNotNull(feature);
        assertEquals(UniprotKBFeatureType.CARBOHYD, feature.getType());
        ;

        assertEquals(7, feature.getLocation().getStart().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature.getLocation().getStart().getModifier());
        assertEquals(7, feature.getLocation().getEnd().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature.getLocation().getEnd().getModifier());
        assertEquals(UniprotKBFeatureType.CARBOHYD, feature.getType());

        assertEquals("O-linked (GalNAc...)", feature.getDescription().getValue());
        FFLineBuilder<UniProtKBFeature> builder = FeatureLineBuilderFactory.create(feature);

        String converted = builder.buildString(feature);
        assertEquals(testString, converted);
    }
}
