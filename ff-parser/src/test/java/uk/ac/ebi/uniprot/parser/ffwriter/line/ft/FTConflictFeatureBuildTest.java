package uk.ac.ebi.uniprot.parser.ffwriter.line.ft;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.feature.ConflictFeature;
import uk.ac.ebi.uniprot.domain.feature.Feature;
import uk.ac.ebi.uniprot.domain.feature.FeatureLocation;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtFeature;



public class FTConflictFeatureBuildTest extends FTBuildTestAbstr {
	@Test 
	public void testTwoReports(){
		String ftLine =
				"FT   CONFLICT      1      1       A -> Q (in Ref. 1; BAA37160/BAA37165 and\n" +
				"FT                                2).";
		String ftLineString = "CONFLICT 1 1 A -> Q (in Ref. 1; BAA37160/BAA37165 and 2).";

		FeatureLocation location =factory.createFeatureLocation(1, 1 );
		String originalSequence = "A";
		List<String> alternativeSequences = new ArrayList<>();
		alternativeSequences.add("Q");
		List<String> report =new ArrayList<>();
		report.add("1; BAA37160/BAA37165");
		report.add("2");
		ConflictFeature feature =
		factory.buildConflictFeature(location, originalSequence, alternativeSequences, report);
		List<String> evs = new ArrayList<>();
	
		UniProtFeature<? extends Feature> uniFeature =  factory.createUniProtFeature(feature, createEvidence(evs));
		
		

	//	ConflictReport report1 = factory.buildConflictReport("in Ref. 1");
	//	ConflictReport report2 = factory.buildConflictReport("BAA37160/BAA37165 and 2");

		doTest(ftLine, uniFeature);	
		doTestString(ftLineString,uniFeature);
	}
	
	
	String val="Q -> K (in Ref. 1; AAO59377, 2; ABO40479 and 6; AAH63566)";
	@Test 
	public void testConflictEvidence(){
		String ftLine =
				"FT   CONFLICT      1      1       A -> Q (in Ref. 1; BAA37160/BAA37165 and\n" +
				"FT                                2). {ECO:0000269|PubMed:10433554,\n" +
				"FT                                ECO:0000313|EMBL:BAG16761.1}.";
		String ftLineString = "CONFLICT 1 1 A -> Q (in Ref. 1; BAA37160/BAA37165 and 2).";
		String ftLineStringEv = "CONFLICT 1 1 A -> Q (in Ref. 1; BAA37160/BAA37165 and 2). "+
		"{ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1}.";
		String ev1 ="ECO:0000313|EMBL:BAG16761.1";
		String ev2= "ECO:0000269|PubMed:10433554";
	//	String ev3 ="ECO:0000303|Ref.6";
		
		FeatureLocation location =factory.createFeatureLocation(1, 1 );
		String originalSequence = "A";
		List<String> alternativeSequences = new ArrayList<>();
		alternativeSequences.add("Q");
		List<String> report =new ArrayList<>();
		report.add("1; BAA37160/BAA37165");
		report.add("2");
		ConflictFeature feature =
		factory.buildConflictFeature(location, originalSequence, alternativeSequences, report);
		List<String> evs = new ArrayList<>();
		evs.add(ev1);
		evs.add(ev2);
		UniProtFeature<? extends Feature> uniFeature =  factory.createUniProtFeature(feature, createEvidence(evs));
		
		
		
		doTest(ftLine, uniFeature);	
		doTestString(ftLineString,uniFeature);
		doTestStringEv(ftLineStringEv,uniFeature);
	
	}
	
	@Test 
	public void testThreeReprotsAndTwoAltSeq(){
		String ftLine =
				"FT   CONFLICT      1      1       A -> QK (in Ref. 1; BAA37160/BAA37165, 2;\n" +
				"FT                                ABO40479 and 6; AAH63566).";
		String ftLineString = "CONFLICT 1 1 A -> QK (in Ref. 1; BAA37160/BAA37165, 2; ABO40479 and 6; AAH63566).";
		FeatureLocation location =factory.createFeatureLocation(1, 1 );
		String originalSequence = "A";
		List<String> alternativeSequences = new ArrayList<>();
		alternativeSequences.add("QK");
		List<String> report =new ArrayList<>();
		report.add("1; BAA37160/BAA37165");
		report.add("2; ABO40479");
		report.add("6; AAH63566");
		ConflictFeature feature =
		factory.buildConflictFeature(location, originalSequence, alternativeSequences, report);
		List<String> evs = new ArrayList<>();
	
		UniProtFeature<? extends Feature> uniFeature =  factory.createUniProtFeature(feature, createEvidence(evs));
		
		

	//	ConflictReport report1 = factory.buildConflictReport("in Ref. 1");
	//	ConflictReport report2 = factory.buildConflictReport("BAA37160/BAA37165 and 2");

		doTest(ftLine, uniFeature);	
		doTestString(ftLineString,uniFeature);
	}
	
}
