package org.uniprot.core.flatfile.writer.line.ft;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.feature.Feature;
import org.uniprot.core.uniprot.feature.FeatureType;

import java.util.ArrayList;
import java.util.List;

public class FTCarbohydFeatureBuildTest extends FTBuildTestAbstr {
	@Test
	public void testCARBOHYD() {
		String ftLine = 
				"FT   CARBOHYD     61     61       N-linked (GlcNAc...); by host\n" +
				"FT                                (Potential).";
		String ftLineString = 
				"CARBOHYD 61 61 N-linked (GlcNAc...); by host " +
				"(Potential).";
	

		String description = "N-linked (GlcNAc...); by host (Potential)";
		String featureId ="";
		List<String> evs = new ArrayList<>();
		Feature feature = createFeature(FeatureType.CARBOHYD,  61, 61,
				 description, featureId, evs);

		doTest(ftLine, feature);	
		doTestString(ftLineString,feature);
	}
	
	@Test
	public void testCARBOHYDEvidence() {
		String ftLine = 
				"FT   CARBOHYD     61     61       N-linked (GlcNAc...); by host\n" +
				"FT                                (Potential).\n" +
				"FT                                {ECO:0000269|PubMed:10433554,\n"+
				"FT                                ECO:0000303|Ref.6}.";
		String ftLineString = 
				"CARBOHYD 61 61 N-linked (GlcNAc...); by host " +
				"(Potential).";
		String ftLineStringEv = 
				"CARBOHYD 61 61 N-linked (GlcNAc...); by host " +
				"(Potential). {ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6}.";
	//	String ev1 ="ECO:0000313|EMBL:BAG16761.1";
		String ev2= "ECO:0000269|PubMed:10433554";
		String ev3 ="ECO:0000303|Ref.6";

		String description = "N-linked (GlcNAc...); by host (Potential)";
		String featureId ="";

		List<String> evs = new ArrayList<>();
		evs.add(ev3);
		evs.add(ev2);
		Feature feature = createFeature(FeatureType.CARBOHYD,  61, 61,
				 description, featureId, evs);
	
		doTest(ftLine, feature);
		doTestString(ftLineString,feature);
		doTestStringEv(ftLineStringEv,feature);
	}
	
}
