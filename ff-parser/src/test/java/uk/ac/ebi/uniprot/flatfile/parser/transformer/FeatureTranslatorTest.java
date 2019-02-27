package uk.ac.ebi.uniprot.flatfile.parser.transformer;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.PositionModifier;
import uk.ac.ebi.uniprot.domain.uniprot.feature.Feature;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType;
import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.FFLineBuilder;
import uk.ac.ebi.uniprot.flatfile.parser.impl.ft.FeatureLineBuilderFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class FeatureTranslatorTest {
    private final FeatureTransformer transformer = new FeatureTransformer();

    @Test
    public void testNonTer() {
        // "SIGNAL <1 33 Potential.",
        // "CHAIN 34 121 Potential.\n/FTId=PRO_5001267722.",
        //
        String testString = "NON_TER 1 1";
        Feature feature = transformer.transform(testString);
        assertNotNull(feature);
        assertEquals(FeatureType.NON_TER, feature.getType());
        assertEquals(1, feature.getLocation().getStart().getValue().intValue());
        assertEquals(1, feature.getLocation().getEnd().getValue().intValue());
        assertEquals(FeatureType.NON_TER, feature.getType());
    }

    @Test
    public void testChain() {

        String testString = "CHAIN ? 121 Potential.\n/FTId=PRO_5001267722.";
        Feature feature = transformer.transform(testString);
        assertNotNull(feature);
        assertEquals(FeatureType.CHAIN, feature.getType());
        assertEquals(PositionModifier.UNKOWN, feature
                .getLocation().getStart().getModifier());
        assertEquals(121, feature.getLocation().getEnd().getValue().intValue());
        assertEquals(FeatureType.CHAIN, feature.getType());
  //      assertEquals(FeatureStatus.POTENTIAL, feature.getFeatureStatus());
        assertEquals("PRO_5001267722", feature.getFeatureId()
                .getValue());
    }

    @Test
    public void testSignal() {

        String testString = "SIGNAL <1 33 Potential.";
        Feature feature = transformer.transform(testString);
        assertNotNull(feature);
        assertEquals(FeatureType.SIGNAL, feature.getType());
        assertEquals(1, feature.getLocation().getStart().getValue().intValue());
        assertEquals(PositionModifier.OUTSIDE, feature
                .getLocation().getStart().getModifier());
        assertEquals(33, feature.getLocation().getEnd().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature
                .getLocation().getEnd().getModifier());
        assertEquals(FeatureType.SIGNAL, feature.getType());
     //   assertEquals(FeatureStatus.POTENTIAL, feature.getFeatureStatus());
    }

    @Test
    public void testBinding() {

        String testString = "BINDING 138 138 NAD(P)HX; via amide nitrogen.";
        Feature feature = transformer.transform(testString);
        assertNotNull(feature);
        assertEquals(FeatureType.BINDING, feature.getType());
        assertEquals(138, feature.getLocation().getStart().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature
                .getLocation().getStart().getModifier());
        assertEquals(138, feature.getLocation().getEnd().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature
                .getLocation().getEnd().getModifier());
        assertEquals(FeatureType.BINDING, feature.getType());
        assertEquals("NAD(P)HX; via amide nitrogen", feature
                .getDescription().getValue());
        FFLineBuilder<Feature> builder = FeatureLineBuilderFactory
                .create(feature);

        String converted = builder.buildString(feature);
        assertEquals(testString, converted);
    }

    @Test
    public void testConflict() {

        String testString = "CONFLICT 124 127 GLTA -> ESHP (in Ref. 1; AAA98633).";
        Feature feature = transformer.transform(testString);
        assertNotNull(feature);
        assertEquals(FeatureType.CONFLICT, feature.getType());

        assertEquals(124, feature.getLocation().getStart().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature
                .getLocation().getStart().getModifier());
        assertEquals(127, feature.getLocation().getEnd().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature
                .getLocation().getEnd().getModifier());
        assertEquals(FeatureType.CONFLICT, feature.getType());
        assertEquals("GLTA", feature.getAlternativeSequence().getOriginalSequence());
        assertEquals(1, feature.getAlternativeSequence().getAlternativeSequences().size());
        assertEquals("ESHP", feature.getAlternativeSequence().getAlternativeSequences().get(0));
     //   assertEquals(1, feature.getAlternativeSequence().getReport().getValue().size());
    //    assertEquals("1; AAA98633", feature.getAlternativeSequence().getReport().getValue().get(0));
        assertEquals("in Ref. 1; AAA98633", feature.getDescription().getValue());

        FFLineBuilder<Feature> builder = FeatureLineBuilderFactory
                .create(feature);

        String converted = builder.buildString(feature);
        
        System.out.println(converted);
        assertEquals(testString, converted);
    }

    @Test
    public void testConflict2() {

        String testString = "CONFLICT 124 127 GLTA -> ESHP (in Ref. 1; AAA98633 and 3; AA432).";
        Feature feature = transformer.transform(testString);
        assertNotNull(feature);
        assertEquals(FeatureType.CONFLICT, feature.getType());

        assertEquals(124, feature.getLocation().getStart().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature
                .getLocation().getStart().getModifier());
        assertEquals(127, feature.getLocation().getEnd().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature
                .getLocation().getEnd().getModifier());
        assertEquals(FeatureType.CONFLICT, feature.getType());
        assertEquals("GLTA", feature.getAlternativeSequence().getOriginalSequence());
        assertEquals(1, feature.getAlternativeSequence().getAlternativeSequences().size());
        assertEquals("ESHP", feature.getAlternativeSequence().getAlternativeSequences().get(0));
       // assertEquals(2, feature.getAlternativeSequence().getReport().getValue().size());
      //  assertEquals("1; AAA98633", feature.getAlternativeSequence().getReport().getValue().get(0));
     //   assertEquals("3; AA432", feature.getAlternativeSequence().getReport().getValue().get(1));
        assertEquals("in Ref. 1; AAA98633 and 3; AA432", feature.getDescription().getValue());
        FFLineBuilder<Feature> builder = FeatureLineBuilderFactory
                .create(feature);

        String converted = builder.buildString(feature);
        
        System.out.println(converted);
        assertEquals(testString, converted);
    }

    
    @Test
    public void testConflict3() {

        String testString = "CONFLICT 124 127 GLTA -> ESHP (in Ref. 1; AAA98633, 3; AA432 and 4; AB321).";
        Feature feature = transformer.transform(testString);
        assertNotNull(feature);
        assertEquals(FeatureType.CONFLICT, feature.getType());

        assertEquals(124, feature.getLocation().getStart().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature
                .getLocation().getStart().getModifier());
        assertEquals(127, feature.getLocation().getEnd().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature
                .getLocation().getEnd().getModifier());
        assertEquals(FeatureType.CONFLICT, feature.getType());
        assertEquals("GLTA", feature.getAlternativeSequence().getOriginalSequence());
        assertEquals(1, feature.getAlternativeSequence().getAlternativeSequences().size());
        assertEquals("ESHP", feature.getAlternativeSequence().getAlternativeSequences().get(0));
//        assertEquals(3, feature.getAlternativeSequence().getReport().getValue().size());
//        assertEquals("1; AAA98633", feature.getAlternativeSequence().getReport().getValue().get(0));
//        assertEquals("3; AA432", feature.getAlternativeSequence().getReport().getValue().get(1));
//        assertEquals("4; AB321", feature.getAlternativeSequence().getReport().getValue().get(2));
        assertEquals("in Ref. 1; AAA98633, 3; AA432 and 4; AB321", feature.getDescription().getValue());
        FFLineBuilder<Feature> builder = FeatureLineBuilderFactory
                .create(feature);

        String converted = builder.buildString(feature);
        
        System.out.println(converted);
        assertEquals(testString, converted);
    }
    
    @Test
    public void testMutagen() {

        String testString = "MUTAGEN 9 9 K->R: Does not affect E-cadherin/CDH1 repression; when associated with R-16.";
        Feature feature = transformer.transform(testString);
        assertNotNull(feature);
        assertEquals(FeatureType.MUTAGEN, feature.getType());

        assertEquals(9, feature.getLocation().getStart().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature
                .getLocation().getStart().getModifier());
        assertEquals(9, feature.getLocation().getEnd().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature
                .getLocation().getEnd().getModifier());
        assertEquals(FeatureType.MUTAGEN, feature.getType());
      
        assertEquals("K", feature.getAlternativeSequence().getOriginalSequence());
        assertEquals(1, feature.getAlternativeSequence().getAlternativeSequences().size());
        assertEquals("R", feature.getAlternativeSequence().getAlternativeSequences().get(0));
     //   assertEquals(
    //            "Does not affect E-cadherin/CDH1 repression; when associated with R-16",
    //            feature.getAlternativeSequence().getReport().getValue().get(0));
        assertEquals("Does not affect E-cadherin/CDH1 repression; when associated with R-16", feature.getDescription().getValue());
        FFLineBuilder<Feature> builder = FeatureLineBuilderFactory
                .create(feature);

        String converted = builder.buildString(feature);
        assertEquals(testString, converted);
    }

    @Test
    public void testVariant() {

        String testString = "VARIANT 421 421 C -> R (in GS; dbSNP:rs28936387).\n/FTId=VAR_007115.";
        Feature feature = transformer.transform(testString);
        assertNotNull(feature);
        assertEquals(FeatureType.VARIANT, feature.getType());

        assertEquals(421, feature.getLocation().getStart().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature
                .getLocation().getStart().getModifier());
        assertEquals(421, feature.getLocation().getEnd().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature
                .getLocation().getEnd().getModifier());
        assertEquals(FeatureType.VARIANT, feature.getType());
   
        assertEquals("C", feature.getAlternativeSequence().getOriginalSequence());
        assertEquals(1, feature.getAlternativeSequence().getAlternativeSequences().size());
        assertEquals("R", feature.getAlternativeSequence().getAlternativeSequences().get(0));

     //   assertEquals(
     //           "in GS; dbSNP:rs28936387",
    //            feature.getAlternativeSequence().getReport().getValue().get(0));
        assertEquals("in GS; dbSNP:rs28936387", feature.getDescription().getValue());
        FFLineBuilder<Feature> builder = FeatureLineBuilderFactory
                .create(feature);

        String converted = builder.buildString(feature);
        assertEquals(testString, converted);
        assertEquals("VAR_007115", feature.getFeatureId().getValue());
    }

    @Test
    public void testVariant2() {

        String testString = "VARIANT 561 561 Missing (in GS).\n/FTId=VAR_007118.";
        Feature feature = transformer.transform(testString);
        assertNotNull(feature);
       
        assertEquals(FeatureType.VARIANT, feature.getType());
        assertEquals(561, feature.getLocation().getStart().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature
                .getLocation().getStart().getModifier());
        assertEquals(561, feature.getLocation().getEnd().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature
                .getLocation().getEnd().getModifier());
        assertEquals(FeatureType.VARIANT, feature.getType());
        assertEquals(0, feature.getAlternativeSequence().getAlternativeSequences().size());

//        assertEquals(
//                "in GS",
//                feature.getAlternativeSequence().getReport().getValue().get(0));
        assertEquals("in GS", feature.getDescription().getValue());
        FFLineBuilder<Feature> builder = FeatureLineBuilderFactory
                .create(feature);

        String converted = builder.buildString(feature);
        assertEquals(testString, converted);
        assertEquals("VAR_007118", feature.getFeatureId().getValue());
    }

    @Test
    public void testVarSeq() {

        String testString = "VAR_SEQ 239 239 E -> ERDVIRSVRLPRE (in isoform PLEC- 0, isoform 1C,"
                + " isoform 2A and isoform 3A).\n/FTId=VSP_005049.";
        Feature feature = transformer.transform(testString);
        assertNotNull(feature);
        assertEquals(FeatureType.VAR_SEQ, feature.getType());

        assertEquals(239, feature.getLocation().getStart().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature
                .getLocation().getStart().getModifier());
        assertEquals(239, feature.getLocation().getEnd().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature
                .getLocation().getEnd().getModifier());
        assertEquals(FeatureType.VAR_SEQ, feature.getType());
        assertEquals("E", feature.getAlternativeSequence().getOriginalSequence());
        assertEquals(1, feature.getAlternativeSequence().getAlternativeSequences().size());
        assertEquals("ERDVIRSVRLPRE", feature.getAlternativeSequence().getAlternativeSequences().get(0));
      //  assertEquals(4, feature.getAlternativeSequence().getReport().getValue().size());
     //   assertEquals(
     //           "PLEC- 0",
     //           feature.getAlternativeSequence().getReport().getValue().get(0));
//        assertEquals(
//                "2A",
//                feature.getAlternativeSequence().getReport().getValue().get(2));
        assertEquals("in isoform PLEC- 0, isoform 1C, isoform 2A and isoform 3A", feature.getDescription().getValue());
        FFLineBuilder<Feature> builder = FeatureLineBuilderFactory
                .create(feature);

        String converted = builder.buildString(feature);
        assertEquals(testString, converted);
        assertEquals("VSP_005049", feature.getFeatureId().getValue());
    }

    @Test
    public void testVarSeq2() {

        String testString = "VAR_SEQ 1 242 Missing (in isoform PLEC-1H).\n/FTId=VSP_005040.";
        Feature feature = transformer.transform(testString);
        assertNotNull(feature);
        assertEquals(FeatureType.VAR_SEQ, feature.getType());

        assertEquals(1, feature.getLocation().getStart().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature
                .getLocation().getStart().getModifier());
        assertEquals(242, feature.getLocation().getEnd().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature
                .getLocation().getEnd().getModifier());
        assertEquals(FeatureType.VAR_SEQ, feature.getType());
     
        assertEquals(0, feature.getAlternativeSequence().getAlternativeSequences().size());
//        assertEquals(1, feature.getAlternativeSequence().getReport().getValue().size());
//        assertEquals(
//                "PLEC-1H",
//                feature.getAlternativeSequence().getReport().getValue().get(0));
        assertEquals("in isoform PLEC-1H", feature.getDescription().getValue());
        FFLineBuilder<Feature> builder = FeatureLineBuilderFactory
                .create(feature);

        String converted = builder.buildString(feature);
        assertEquals(testString, converted);
        assertEquals("VSP_005040", feature.getFeatureId().getValue());
    }
    //

    @Test
    public void testCarbohyd() {

        String testString = "CARBOHYD 196 196 N-linked (GlcNAc...); by host.";
        Feature feature = transformer.transform(testString);
        assertNotNull(feature);
        assertEquals(FeatureType.CARBOHYD, feature.getType());


        assertEquals(196, feature.getLocation().getStart().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature
                .getLocation().getStart().getModifier());
        assertEquals(196, feature.getLocation().getEnd().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature
                .getLocation().getEnd().getModifier());
        assertEquals(FeatureType.CARBOHYD, feature.getType());
        assertEquals("N-linked (GlcNAc...); by host", feature.getDescription().getValue());
        FFLineBuilder<Feature> builder = FeatureLineBuilderFactory
                .create(feature);

        String converted = builder.buildString(feature);
        assertEquals(testString, converted);

    }

    @Test
    public void testCarbohyd2() {

        String testString = "CARBOHYD 7 7 O-linked (GalNAc...).";
        Feature feature = transformer.transform(testString);
        assertNotNull(feature);
        assertEquals(FeatureType.CARBOHYD, feature.getType());;

        assertEquals(7, feature.getLocation().getStart().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature
                .getLocation().getStart().getModifier());
        assertEquals(7, feature.getLocation().getEnd().getValue().intValue());
        assertEquals(PositionModifier.EXACT, feature
                .getLocation().getEnd().getModifier());
        assertEquals(FeatureType.CARBOHYD, feature.getType());
       
        assertEquals("O-linked (GalNAc...)", feature.getDescription().getValue());
        FFLineBuilder<Feature> builder = FeatureLineBuilderFactory
                .create(feature);

        String converted = builder.buildString(feature);
        assertEquals(testString, converted);

    }
}
