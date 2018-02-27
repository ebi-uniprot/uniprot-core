package uk.ac.ebi.uniprot.parser.ffwriter.line.ft;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.feature.CarbohydFeature;
import uk.ac.ebi.uniprot.domain.feature.CarbohydLinkType;
import uk.ac.ebi.uniprot.domain.feature.Feature;
import uk.ac.ebi.uniprot.domain.feature.FeatureLocation;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtFeature;



public class FTCarbohydFeatureBuildTest extends FTBuildTestAbstr {
	@Test
	public void testCARBOHYD() {
		String ftLine = 
				"FT   CARBOHYD     61     61       N-linked (GlcNAc...); by host\n" +
				"FT                                (Potential).";
		String ftLineString = 
				"CARBOHYD 61 61 N-linked (GlcNAc...); by host " +
				"(Potential).";
	
		FeatureLocation location =factory.createFeatureLocation(61, 61 );
		String description = "by host (Potential)";
		String featureId ="";
		CarbohydLinkType carbohydLinkType = CarbohydLinkType.NITROGEN;
		String linkedSugar = "(GlcNAc...)";
		CarbohydFeature feature =
		factory.buildCarbohydFeature(location, description, featureId, carbohydLinkType, linkedSugar);
		List<String> evs = new ArrayList<>();
		UniProtFeature<? extends Feature> uniFeature =  factory.createUniProtFeature(feature, createEvidence(evs));
		doTest(ftLine, uniFeature);	
		doTestString(ftLineString,uniFeature);
	}
	
	@Test
	public void testCARBOHYDEvidence() {
		String ftLine = 
				"FT   CARBOHYD     61     61       N-linked (GlcNAc...); by host\n" +
				"FT                                (Potential). {ECO:0000269|PubMed:\n" +
				"FT                                10433554, ECO:0000303|Ref.6}.";
		String ftLineString = 
				"CARBOHYD 61 61 N-linked (GlcNAc...); by host " +
				"(Potential).";
		String ftLineStringEv = 
				"CARBOHYD 61 61 N-linked (GlcNAc...); by host " +
				"(Potential). {ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6}.";
	//	String ev1 ="ECO:0000313|EMBL:BAG16761.1";
		String ev2= "ECO:0000269|PubMed:10433554";
		String ev3 ="ECO:0000303|Ref.6";
		
		FeatureLocation location =factory.createFeatureLocation(61, 61 );
		String description = "by host (Potential)";
		String featureId ="";
		CarbohydLinkType carbohydLinkType = CarbohydLinkType.NITROGEN;
		String linkedSugar = "(GlcNAc...)";
		CarbohydFeature feature =
		factory.buildCarbohydFeature(location, description, featureId, carbohydLinkType, linkedSugar);
		List<String> evs = new ArrayList<>();
		evs.add(ev3);
		evs.add(ev2);
		UniProtFeature<? extends Feature> uniFeature =  factory.createUniProtFeature(feature, createEvidence(evs));
	
		doTest(ftLine, uniFeature);
		doTestString(ftLineString,uniFeature);
		doTestStringEv(ftLineStringEv,uniFeature);
	}
	
}
