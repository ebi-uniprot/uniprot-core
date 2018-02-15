package uk.ac.ebi.uniprot.parser.transformer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.feature.CarbohydFeature;
import uk.ac.ebi.uniprot.domain.feature.CarbohydLinkType;
import uk.ac.ebi.uniprot.domain.feature.ChainFeature;
import uk.ac.ebi.uniprot.domain.feature.ConflictFeature;
import uk.ac.ebi.uniprot.domain.feature.Feature;
import uk.ac.ebi.uniprot.domain.feature.FeatureLocationModifier;
import uk.ac.ebi.uniprot.domain.feature.FeatureType;
import uk.ac.ebi.uniprot.domain.feature.MutagenFeature;
import uk.ac.ebi.uniprot.domain.feature.SignalFeature;
import uk.ac.ebi.uniprot.domain.feature.VarSeqFeature;
import uk.ac.ebi.uniprot.domain.feature.VariantFeature;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtFeature;
import uk.ac.ebi.uniprot.parser.impl.ft.FtLineTransformer;



public class FtLineTransformerTest {
	private FtLineTransformer transformer =new FtLineTransformer();
	@Test
	public void testChain() {

		String lines = "CHAIN ? 121 Potential.\n/FTId=PRO_5001267722.";
		List<UniProtFeature<? extends Feature>> features = transformer.transformNoHeader( lines) ;
		assertEquals(1, features.size());
		UniProtFeature<? extends Feature> unifeature = features.get(0);
		Feature feature1 = unifeature.getFeature();
		assertTrue(feature1 instanceof ChainFeature);
		ChainFeature feature = (ChainFeature) feature1;
		
		assertEquals(FeatureLocationModifier.UNKOWN, feature
				.getFeatureLocation().getStartModifier());
		assertEquals(121, feature.getFeatureLocation().getEnd().intValue());
		assertEquals(FeatureType.CHAIN, feature.getType());
		assertEquals("Potential", feature.getDescription().getValue());
		assertEquals("PRO_5001267722", ((ChainFeature) feature).getFeatureId()
				.getValue());
	}

	@Test
	public void testSignal() {

		String lines = "SIGNAL <1 33 some description.";
		List<UniProtFeature<? extends Feature>> features = transformer.transformNoHeader( lines) ;
		assertEquals(1, features.size());
		UniProtFeature<? extends Feature> unifeature = features.get(0);
		Feature feature1 = unifeature.getFeature();
		assertTrue(feature1 instanceof SignalFeature);
		SignalFeature feature = (SignalFeature) feature1;
		assertEquals(1, feature.getFeatureLocation().getStart().intValue());
		assertEquals(FeatureLocationModifier.OUTSIDE_KNOWN_SEQUENCE, feature
				.getFeatureLocation().getStartModifier());
		assertEquals(33, feature.getFeatureLocation().getEnd().intValue());
		assertEquals(FeatureLocationModifier.EXACT, feature
				.getFeatureLocation().getEndModifier());
		assertEquals(FeatureType.SIGNAL, feature.getType());
		assertEquals("some description", feature.getDescription().getValue());

	}
	@Test
	public void testConflict() {

		String lines = "CONFLICT 124 127 GLTA -> ESHP (in Ref. 1; AAA98633).";
		List<UniProtFeature<? extends Feature>> features = transformer.transformNoHeader( lines) ;
		assertEquals(1, features.size());
		UniProtFeature<? extends Feature> unifeature = features.get(0);
		Feature feature1 = unifeature.getFeature();
		assertTrue(feature1 instanceof ConflictFeature);
		ConflictFeature feature = (ConflictFeature) feature1;

		assertEquals(124, feature.getFeatureLocation().getStart().intValue());
		assertEquals(FeatureLocationModifier.EXACT, feature
				.getFeatureLocation().getStartModifier());
		assertEquals(127, feature.getFeatureLocation().getEnd().intValue());
		assertEquals(FeatureLocationModifier.EXACT, feature
				.getFeatureLocation().getEndModifier());
		assertEquals(FeatureType.CONFLICT, feature.getType());

		assertEquals("GLTA", feature.getOriginalSequence().getValue());
		assertEquals(1, feature.getAlternativeSequences().size());
		assertEquals("ESHP", feature.getAlternativeSequences().get(0)
				.getValue());
		assertEquals(1,feature.getReport().getValue().size());
		assertEquals("1; AAA98633", feature.getReport().getValue().get(0));

	}

	@Test
	public void testMutagen() {

		String lines = "MUTAGEN 9 9 K->R: Does not affect E-cadherin/CDH1 repression; when associated with R-16.";
		List<UniProtFeature<? extends Feature>> features = transformer.transformNoHeader( lines) ;
		assertEquals(1, features.size());
		UniProtFeature<? extends Feature> unifeature = features.get(0);
		Feature feature1 = unifeature.getFeature();
		assertTrue(feature1 instanceof MutagenFeature);
		MutagenFeature feature = (MutagenFeature) feature1;
		
		assertEquals(9, feature.getFeatureLocation().getStart().intValue());
		assertEquals(FeatureLocationModifier.EXACT, feature
				.getFeatureLocation().getStartModifier());
		assertEquals(9, feature.getFeatureLocation().getEnd().intValue());
		assertEquals(FeatureLocationModifier.EXACT, feature
				.getFeatureLocation().getEndModifier());
		assertEquals(FeatureType.MUTAGEN, feature.getType());

		assertEquals("K", feature.getOriginalSequence().getValue());
		assertEquals(1, feature.getAlternativeSequences().size());
		assertEquals("R", feature.getAlternativeSequences().get(0).getValue());
		assertEquals(
				"Does not affect E-cadherin/CDH1 repression; when associated with R-16",
				feature.getReport().getValue().get(0));

	}
	
	
	
	@Test
	public void testVariant() {

		String lines = "VARIANT 421 421 C -> R (in GS; dbSNP:rs28936387).\n/FTId=VAR_007115.";
		List<UniProtFeature<? extends Feature>> features = transformer.transformNoHeader( lines) ;
		assertEquals(1, features.size());
		UniProtFeature<? extends Feature> unifeature = features.get(0);
		Feature feature1 = unifeature.getFeature();
		assertTrue(feature1 instanceof VariantFeature);
		VariantFeature feature = (VariantFeature) feature1;

		assertEquals(421, feature.getFeatureLocation().getStart().intValue());
		assertEquals(FeatureLocationModifier.EXACT, feature
				.getFeatureLocation().getStartModifier());
		assertEquals(421, feature.getFeatureLocation().getEnd().intValue());
		assertEquals(FeatureLocationModifier.EXACT, feature
				.getFeatureLocation().getEndModifier());
		assertEquals(FeatureType.VARIANT, feature.getType());

		assertEquals("C", feature.getOriginalSequence().getValue());
		assertEquals(1, feature.getAlternativeSequences().size());
		assertEquals("R", feature.getAlternativeSequences().get(0).getValue());
	
		assertEquals(
				"in GS; dbSNP:rs28936387",
				feature.getReport().getValue().get(0));
		assertEquals("VAR_007115", feature.getFeatureId().getValue());

	
	}
	
	
	@Test
	public void testVariant2() {

		String lines = "VARIANT 561 561 Missing (in GS).\n/FTId=VAR_007118.";
		List<UniProtFeature<? extends Feature>> features = transformer.transformNoHeader( lines) ;
		assertEquals(1, features.size());
		UniProtFeature<? extends Feature> unifeature = features.get(0);
		Feature feature1 = unifeature.getFeature();
		assertTrue(feature1 instanceof VariantFeature);
		VariantFeature feature = (VariantFeature) feature1;

		assertEquals(561, feature.getFeatureLocation().getStart().intValue());
		assertEquals(FeatureLocationModifier.EXACT, feature
				.getFeatureLocation().getStartModifier());
		assertEquals(561, feature.getFeatureLocation().getEnd().intValue());
		assertEquals(FeatureLocationModifier.EXACT, feature
				.getFeatureLocation().getEndModifier());
		assertEquals(FeatureType.VARIANT, feature.getType());
		//assertEquals("C", cFeature.getOriginalSequence().getValue());
		assertEquals(0, feature.getAlternativeSequences().size());
	//	assertEquals("R", cFeature.getAlternativeSequences().get(0).getValue());
	
		assertEquals(
				"in GS",
				feature.getReport().getValue().get(0));

		 assertEquals("VAR_007118", feature.getFeatureId().getValue());
	}
	
	@Test
	public void testVarSeq() {

		String lines = "VAR_SEQ 239 239 E -> ERDVIRSVRLPRE (in isoform PLEC- 0, isoform 1C,"
				+ " isoform 2A and isoform 3A).\n/FTId=VSP_005049.";
		
		
		List<UniProtFeature<? extends Feature>> features = transformer.transformNoHeader( lines) ;
		assertEquals(1, features.size());
		UniProtFeature<? extends Feature> unifeature = features.get(0);
		Feature feature1 = unifeature.getFeature();
		assertTrue(feature1 instanceof VarSeqFeature);
		VarSeqFeature feature = (VarSeqFeature) feature1;
		


		assertEquals(239, feature.getFeatureLocation().getStart().intValue());
		assertEquals(FeatureLocationModifier.EXACT, feature
				.getFeatureLocation().getStartModifier());
		assertEquals(239, feature.getFeatureLocation().getEnd().intValue());
		assertEquals(FeatureLocationModifier.EXACT, feature
				.getFeatureLocation().getEndModifier());
		assertEquals(FeatureType.VAR_SEQ, feature.getType());
		assertEquals("E", feature.getOriginalSequence().getValue());
		assertEquals(1, feature.getAlternativeSequences().size());
		assertEquals("ERDVIRSVRLPRE", feature.getAlternativeSequences().get(0).getValue());
		assertEquals(4, feature.getReport().getValue().size());
		assertEquals(
				"PLEC- 0",
				feature.getReport().getValue().get(0));
		
		assertEquals(
				"2A",
				feature.getReport().getValue().get(2));

		 assertEquals("VSP_005049", feature.getFeatureId().getValue());
	}
	
	@Test
	public void testVarSeq2() {

		String lines = "VAR_SEQ 1 242 Missing (in isoform PLEC-1H).\n/FTId=VSP_005040.";
		List<UniProtFeature<? extends Feature>> features = transformer.transformNoHeader( lines) ;
		assertEquals(1, features.size());
		UniProtFeature<? extends Feature> unifeature = features.get(0);
		Feature feature1 = unifeature.getFeature();
		assertTrue(feature1 instanceof VarSeqFeature);
		VarSeqFeature feature = (VarSeqFeature) feature1;
		

		assertEquals(1, feature.getFeatureLocation().getStart().intValue());
		assertEquals(FeatureLocationModifier.EXACT, feature
				.getFeatureLocation().getStartModifier());
		assertEquals(242, feature.getFeatureLocation().getEnd().intValue());
		assertEquals(FeatureLocationModifier.EXACT, feature
				.getFeatureLocation().getEndModifier());
		assertEquals(FeatureType.VAR_SEQ, feature.getType());

		//assertEquals("E", cFeature.getOriginalSequence().getValue());
		assertEquals(0, feature.getAlternativeSequences().size());
		//assertEquals("ERDVIRSVRLPRE", cFeature.getAlternativeSequences().get(0).getValue());
		assertEquals(1, feature.getReport().getValue().size());
		assertEquals(
				"PLEC-1H",
				feature.getReport().getValue().get(0));
		
		 assertEquals("VSP_005040", feature.getFeatureId().getValue());
	}
	//
	
	@Test
	public void testCarbohyd() {

		String lines = "CARBOHYD 196 196 N-linked (GlcNAc...); by host.";	
		List<UniProtFeature<? extends Feature>> features = transformer.transformNoHeader( lines) ;
		assertEquals(1, features.size());
		UniProtFeature<? extends Feature> unifeature = features.get(0);
		Feature feature1 = unifeature.getFeature();
		assertTrue(feature1 instanceof CarbohydFeature);
		CarbohydFeature feature = (CarbohydFeature) feature1;

		assertEquals(196, feature.getFeatureLocation().getStart().intValue());
		assertEquals(FeatureLocationModifier.EXACT, feature
				.getFeatureLocation().getStartModifier());
		assertEquals(196, feature.getFeatureLocation().getEnd().intValue());
		assertEquals(FeatureLocationModifier.EXACT, feature
				.getFeatureLocation().getEndModifier());
		assertEquals(FeatureType.CARBOHYD, feature.getType());
		assertEquals(CarbohydLinkType.NITROGEN, feature.getCarbohydLinkType());
		assertEquals("(GlcNAc...)", feature.getLinkedSugar().getValue());
		assertEquals("by host", feature.getDescription().getValue());
		
	}
	@Test
	public void testCarbohyd2() {

		String lines =  "CARBOHYD 7 7 O-linked (GalNAc...).";
		List<UniProtFeature<? extends Feature>> features = transformer.transformNoHeader( lines) ;
		assertEquals(1, features.size());
		UniProtFeature<? extends Feature> unifeature = features.get(0);
		Feature feature1 = unifeature.getFeature();
		assertTrue(feature1 instanceof CarbohydFeature);
		CarbohydFeature feature = (CarbohydFeature) feature1;

		assertEquals(7, feature.getFeatureLocation().getStart().intValue());
		assertEquals(FeatureLocationModifier.EXACT, feature
				.getFeatureLocation().getStartModifier());
		assertEquals(7, feature.getFeatureLocation().getEnd().intValue());
		assertEquals(FeatureLocationModifier.EXACT, feature
				.getFeatureLocation().getEndModifier());
		assertEquals(FeatureType.CARBOHYD, feature.getType());

		assertEquals(CarbohydLinkType.OXYGEN, feature.getCarbohydLinkType());
		assertEquals("(GalNAc...)", feature.getLinkedSugar().getValue());
		assertEquals("", feature.getDescription().getValue());

	
	}
}
