package org.uniprot.core.flatfile.transformer;

import org.junit.jupiter.api.Test;
import org.uniprot.core.PositionModifier;
import org.uniprot.core.flatfile.parser.impl.ft.FtLineTransformer;
import org.uniprot.core.uniprot.feature.Feature;
import org.uniprot.core.uniprot.feature.FeatureType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FtLineTransformerTest {
	private FtLineTransformer transformer =new FtLineTransformer();
	@Test
	public void testChain() {

		String lines = "CHAIN ? 121 Potential.\n/FTId=PRO_5001267722.";
		List<Feature> features = transformer.transformNoHeader( lines) ;
		assertEquals(1, features.size());
		Feature feature = features.get(0);

		
		assertEquals(PositionModifier.UNKNOWN, feature
				.getLocation().getStart().getModifier());
		assertEquals(121, feature.getLocation().getEnd().getValue().intValue());
		assertEquals(FeatureType.CHAIN, feature.getType());
		assertEquals("Potential", feature.getDescription().getValue());
		assertEquals("PRO_5001267722", feature.getFeatureId()
				.getValue());
	}

	@Test
	public void testSignal() {

		String lines = "SIGNAL <1 33 some description.";
		List<Feature> features = transformer.transformNoHeader( lines) ;
		assertEquals(1, features.size());
		Feature feature = features.get(0);

		assertEquals(1, feature.getLocation().getStart().getValue().intValue());
		assertEquals(PositionModifier.OUTSIDE, feature
				.getLocation().getStart().getModifier());
		assertEquals(33, feature.getLocation().getEnd().getValue().intValue());
		assertEquals(PositionModifier.EXACT, feature
				.getLocation().getEnd().getModifier());
		assertEquals(FeatureType.SIGNAL, feature.getType());
		assertEquals("some description", feature.getDescription().getValue());

	}
	@Test
	public void testConflict() {

		String lines = "CONFLICT 124 127 GLTA -> ESHP (in Ref. 1; AAA98633).";
		List<Feature> features = transformer.transformNoHeader( lines) ;
		assertEquals(1, features.size());
		Feature feature = features.get(0);

		assertEquals(124, feature.getLocation().getStart().getValue().intValue());
		assertEquals(PositionModifier.EXACT, feature
				.getLocation().getStart().getModifier());
		assertEquals(127, feature.getLocation().getEnd().getValue().intValue());
		assertEquals(PositionModifier.EXACT, feature
				.getLocation().getEnd().getModifier());
		assertEquals(FeatureType.CONFLICT, feature.getType());

		assertEquals("GLTA", feature.getAlternativeSequence().getOriginalSequence());
		assertEquals(1, feature.getAlternativeSequence().getAlternativeSequences().size());
		assertEquals("ESHP", feature.getAlternativeSequence().getAlternativeSequences().get(0)
				);
	//	assertEquals(1,feature.getAlternativeSequence().getReport().getValue().size());
	//	assertEquals("1; AAA98633", feature.getAlternativeSequence().getReport().getValue().get(0));
		assertEquals("in Ref. 1; AAA98633", feature.getDescription().getValue());
	}

	@Test
	public void testMutagen() {

		String lines = "MUTAGEN 9 9 K->R: Does not affect E-cadherin/CDH1 repression; when associated with R-16.";
		List<Feature> features = transformer.transformNoHeader( lines) ;
		assertEquals(1, features.size());
		 Feature feature = features.get(0);
		 assertEquals(FeatureType.MUTAGEN, feature.getType());

		
		assertEquals(9, feature.getLocation().getStart().getValue().intValue());
		assertEquals(PositionModifier.EXACT, feature.getLocation().getStart().getModifier());
		assertEquals(9, feature.getLocation().getEnd().getValue().intValue());
		assertEquals(PositionModifier.EXACT, feature.getLocation().getEnd().getModifier());
		assertEquals(FeatureType.MUTAGEN, feature.getType());

		assertEquals("K", feature.getAlternativeSequence().getOriginalSequence());
		assertEquals(1, feature.getAlternativeSequence().getAlternativeSequences().size());
		assertEquals("R", feature.getAlternativeSequence().getAlternativeSequences().get(0));
//		assertEquals(
//				"Does not affect E-cadherin/CDH1 repression; when associated with R-16",
//				feature.getAlternativeSequence().getReport().getValue().get(0));
		assertEquals("Does not affect E-cadherin/CDH1 repression; when associated with R-16", feature.getDescription().getValue());
	}
	
	
	
	@Test
	public void testVariant() {

		String lines = "VARIANT 421 421 C -> R (in GS; dbSNP:rs28936387).\n/FTId=VAR_007115.";
		List<Feature> features = transformer.transformNoHeader( lines) ;
		assertEquals(1, features.size());
		Feature feature = features.get(0);


		assertEquals(FeatureType.VARIANT, feature.getType());
		assertEquals(421, feature.getLocation().getStart().getValue().intValue());
		assertEquals(PositionModifier.EXACT, feature.getLocation().getStart().getModifier());
		assertEquals(421, feature.getLocation().getEnd().getValue().intValue());
		assertEquals(PositionModifier.EXACT, feature
				.getLocation().getEnd().getModifier());
		assertEquals(FeatureType.VARIANT, feature.getType());

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
	public void testVariant2() {

		String lines = "VARIANT 561 561 Missing (in GS).\n/FTId=VAR_007118.";
		List<Feature> features = transformer.transformNoHeader( lines) ;
		assertEquals(1, features.size());
		Feature feature = features.get(0);
		assertEquals(FeatureType.VARIANT, feature.getType());
		assertEquals(561, feature.getLocation().getStart().getValue().intValue());
		assertEquals(PositionModifier.EXACT, feature
				.getLocation().getStart().getModifier());
		assertEquals(561, feature.getLocation().getEnd().getValue().intValue());
		assertEquals(PositionModifier.EXACT, feature
				.getLocation().getEnd().getModifier());
		assertEquals(FeatureType.VARIANT, feature.getType());
		//assertEquals("C", cFeature.getOriginalSequence().getValue());
		assertEquals(0, feature.getAlternativeSequence().getAlternativeSequences().size());
	//	assertEquals("R", cFeature.getAlternativeSequences().get(0).getValue());
	
//		assertEquals(
//				"in GS",
//				feature.getAlternativeSequence().getReport().getValue().get(0));
		assertEquals("in GS", feature.getDescription().getValue());
		 assertEquals("VAR_007118", feature.getFeatureId().getValue());
	}
	
	@Test
	public void testVarSeq() {

		String lines = "VAR_SEQ 239 239 E -> ERDVIRSVRLPRE (in isoform PLEC-0, isoform 1C,"
				+ " isoform 2A and isoform 3A).\n/FTId=VSP_005049.";
		
		
		List<Feature> features = transformer.transformNoHeader( lines) ;
		assertEquals(1, features.size());
		Feature feature = features.get(0);		
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
//		assertEquals(4, feature.getAlternativeSequence().getReport().getValue().size());
//		assertEquals(
//				"PLEC-0",
//				feature.getAlternativeSequence().getReport().getValue().get(0));
//		
//		assertEquals(
//				"2A",
//				feature.getAlternativeSequence().getReport().getValue().get(2));
		assertEquals("in isoform PLEC-0, isoform 1C, isoform 2A and isoform 3A", feature.getDescription().getValue());
		 assertEquals("VSP_005049", feature.getFeatureId().getValue());
	}
	
	@Test
	public void testVarSeq2() {

		String lines = "VAR_SEQ 1 242 Missing (in isoform PLEC-1H).\n/FTId=VSP_005040.";
		List<Feature> features = transformer.transformNoHeader( lines) ;
		assertEquals(1, features.size());
		Feature feature = features.get(0);
		assertEquals(FeatureType.VAR_SEQ, feature.getType());

		assertEquals(1, feature.getLocation().getStart().getValue().intValue());
		assertEquals(PositionModifier.EXACT, feature
				.getLocation().getStart().getModifier());
		assertEquals(242, feature.getLocation().getEnd().getValue().intValue());
		assertEquals(PositionModifier.EXACT, feature
				.getLocation().getEnd().getModifier());
		assertEquals(FeatureType.VAR_SEQ, feature.getType());

		//assertEquals("E", cFeature.getOriginalSequence().getValue());
		assertEquals(0, feature.getAlternativeSequence().getAlternativeSequences().size());
		//assertEquals("ERDVIRSVRLPRE", cFeature.getAlternativeSequences().get(0).getValue());
//		assertEquals(1, feature.getAlternativeSequence().getReport().getValue().size());
//		assertEquals(
//				"PLEC-1H",
//				feature.getAlternativeSequence().getReport().getValue().get(0));
		assertEquals("in isoform PLEC-1H", feature.getDescription().getValue());
		 assertEquals("VSP_005040", feature.getFeatureId().getValue());
	}
	//
	
	@Test
	public void testCarbohyd() {

		String lines = "CARBOHYD 196 196 N-linked (GlcNAc...); by host.";	
		List<Feature> features = transformer.transformNoHeader( lines) ;
		assertEquals(1, features.size());
		Feature feature = features.get(0);
		assertEquals(FeatureType.CARBOHYD, feature.getType());

		assertEquals(196, feature.getLocation().getStart().getValue().intValue());
		assertEquals(PositionModifier.EXACT, feature
				.getLocation().getStart().getModifier());
		assertEquals(196, feature.getLocation().getEnd().getValue().intValue());
		assertEquals(PositionModifier.EXACT, feature
				.getLocation().getEnd().getModifier());
		assertEquals(FeatureType.CARBOHYD, feature.getType());
	//	assertEquals(CarbohydLinkType.NITROGEN, feature.getCarbohydLinkType());
	//	assertEquals("(GlcNAc...)", feature.getLinkedSugar().getValue());
		assertEquals("N-linked (GlcNAc...); by host", feature.getDescription().getValue());
		
	}
	@Test
	public void testCarbohyd2() {

		String lines =  "CARBOHYD 7 7 O-linked (GalNAc...).";
		List<Feature> features = transformer.transformNoHeader( lines) ;
		assertEquals(1, features.size());
		Feature feature = features.get(0);
		assertEquals(FeatureType.CARBOHYD, feature.getType());

		assertEquals(7, feature.getLocation().getStart().getValue().intValue());
		assertEquals(PositionModifier.EXACT, feature
				.getLocation().getStart().getModifier());
		assertEquals(7, feature.getLocation().getEnd().getValue().intValue());
		assertEquals(PositionModifier.EXACT, feature
				.getLocation().getEnd().getModifier());
		assertEquals(FeatureType.CARBOHYD, feature.getType());

//		assertEquals(CarbohydLinkType.OXYGEN, feature.getCarbohydLinkType());
//		assertEquals("(GalNAc...)", feature.getLinkedSugar().getValue());
		assertEquals("O-linked (GalNAc...)", feature.getDescription().getValue());

	
	}
}
