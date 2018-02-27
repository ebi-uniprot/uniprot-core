package uk.ac.ebi.uniprot.parser.ffwriter.line.ft;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.feature.Feature;
import uk.ac.ebi.uniprot.domain.feature.FeatureType;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtFeature;


public class FTSimpleFeatureBuildTest extends FTBuildTestAbstr {
	@Test
	public void test1(){
		String ftLine ="FT   ACT_SITE   1691   1871       VWFA 3; main binding site for collagens\n"+
					   "FT                                type I and III.";
		String ftLineString = "ACT_SITE 1691 1871 VWFA 3; main binding site for collagens "+
					   "type I and III.";
		String description = "VWFA 3; main binding site for collagens type I and III";
		List<String> evs = new ArrayList<>();
		UniProtFeature<? extends Feature> feature = createFeature(FeatureType.ACT_SITE, 1691, 1871, 
				description,
				 null, evs);
		
		doTest(ftLine, feature);
	
		doTestString(ftLineString,feature);
	}
	@Test
	public void test2(){
		String ftLine ="FT   CHAIN        61    386       Serine/threonine-protein phosphatase 2A\n" +
				"FT                                56 kDa regulatory subunit gamma isoform.\n" +
				"FT                                /FTId=PRO_0000071458." ;
		String ftLineString ="CHAIN 61 386 Serine/threonine-protein phosphatase 2A " +
				"56 kDa regulatory subunit gamma isoform.\n" +
				"/FTId=PRO_0000071458." ;
		String description4 ="Serine/threonine-protein phosphatase 2A 56 kDa regulatory subunit gamma isoform";
		List<String> evs4 = new ArrayList<>();
		UniProtFeature<? extends Feature> feature = createFeature(FeatureType.CHAIN, 61, 386, 
				description4,
				 "PRO_0000071458", evs4);
		doTest(ftLine, feature);
		doTestString(ftLineString,feature);	
	}
	@Test 
	public void test3(){
		String ftLine =	"FT   BINDING      79    197       Response regulatory (By similarity).";
		String ftLineString =	"BINDING 79 197 Response regulatory (By similarity).";
		String description2 ="Response regulatory (By similarity)";
		List<String> evs2 = new ArrayList<>();
		UniProtFeature<? extends Feature> feature =createFeature(FeatureType.BINDING, 79, 197, 
				description2,
				 null, evs2);
		doTest(ftLine, feature);
		doTestString(ftLineString,feature);	
	}
	
	@Test
	public void test1Ev(){
		String ftLine =	"FT   ACT_SITE   1691   1871       VWFA 3; main binding site for collagens\n" +
				"FT                                type I and III. {ECO:0000303|Ref.6,\n" +
				"FT                                ECO:0000313|EMBL:BAG16761.1}." ;
		String ftLineString = "ACT_SITE 1691 1871 VWFA 3; main binding site for collagens "+
					   "type I and III.";
		String ftLineStringEv = "ACT_SITE 1691 1871 VWFA 3; main binding site for collagens "+
				   "type I and III. "+
				   "{ECO:0000303|Ref.6, ECO:0000313|EMBL:BAG16761.1}.";
		String ev1 ="ECO:0000313|EMBL:BAG16761.1";
		String ev3 ="ECO:0000303|Ref.6";
		
		String description ="VWFA 3; main binding site for collagens type I and III";
		List<String> evs = new ArrayList<>();
		evs.add(ev3);
		evs.add(ev1);	
		UniProtFeature<? extends Feature> feature =createFeature(FeatureType.ACT_SITE, 1691, 1871, 
				description,
				 null, evs);
		
		doTest(ftLine, feature);
	
		doTestString(ftLineString,feature);
		doTestStringEv(ftLineStringEv,feature);
	}
	@Test
	public void test2Ev(){
		String ftLine ="FT   CHAIN        61    386       Serine/threonine-protein phosphatase 2A\n" +
				"FT                                56 kDa regulatory subunit gamma isoform.\n" +
				"FT                                {ECO:0000256|HAMAP-Rule:MF_00205}.\n" +
				"FT                                /FTId=PRO_0000071458." ;
		String ftLineString ="CHAIN 61 386 Serine/threonine-protein phosphatase 2A " +
				"56 kDa regulatory subunit gamma isoform.\n" +
				"/FTId=PRO_0000071458." ;
		String ftLineStringEv ="CHAIN 61 386 Serine/threonine-protein phosphatase 2A " +
				"56 kDa regulatory subunit gamma isoform. {ECO:0000256|HAMAP-Rule:MF_00205}.\n" +
				"/FTId=PRO_0000071458." ;
		String description4 ="Serine/threonine-protein phosphatase 2A 56 kDa regulatory subunit gamma isoform";
		List<String> evs4 = new ArrayList<>();
		evs4.add("ECO:0000256|HAMAP-Rule:MF_00205");
		UniProtFeature<? extends Feature> feature =createFeature(FeatureType.CHAIN, 61, 386, 
				description4,
				 "PRO_0000071458", evs4);
		doTest(ftLine, feature);
		doTestString(ftLineString,feature);	
		doTestStringEv(ftLineStringEv,feature);
	}
	@Test 
	public void test3Ev(){
		String ftLine =	"FT   BINDING      79    197       Response regulatory (By similarity).\n"+
				"FT                                {ECO:0000269|PubMed:10433554,\n" +
				"FT                                ECO:0000313|PDB:3OW2}." ;
		String ftLineString =	"BINDING 79 197 Response regulatory (By similarity).";
		String ftLineStringEv =	"BINDING 79 197 Response regulatory (By similarity). {ECO:0000269|PubMed:10433554, ECO:0000313|PDB:3OW2}.";
		String description2 ="Response regulatory (By similarity)";
		List<String> evs2 = new ArrayList<>();
		evs2.add("ECO:0000269|PubMed:10433554");
		evs2.add("ECO:0000313|PDB:3OW2");
		UniProtFeature<? extends Feature> feature =createFeature(FeatureType.BINDING, 79, 197, 
				description2,
				 null, evs2);
		doTest(ftLine, feature);
		doTestString(ftLineString,feature);	
		doTestStringEv(ftLineStringEv,feature);
	}
	
}
