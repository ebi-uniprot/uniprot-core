package uk.ac.ebi.uniprot.flatfile.parser.ffwriter.line.ft;

import org.junit.Test;
import org.uniprot.core.Range;
import org.uniprot.core.uniprot.feature.AlternativeSequence;
import org.uniprot.core.uniprot.feature.Feature;
import org.uniprot.core.uniprot.feature.FeatureType;

import java.util.ArrayList;
import java.util.List;

public class FTConflictFeatureBuildTest extends FTBuildTestAbstr {
	@Test
	public void testTwoReports() {
		String ftLine = "FT   CONFLICT      1      1       A -> Q (in Ref. 1; BAA37160/BAA37165 and\n"
				+ "FT                                2).";
		String ftLineString = "CONFLICT 1 1 A -> Q (in Ref. 1; BAA37160/BAA37165 and 2).";

		Range location = new Range(1, 1);
		String originalSequence = "A";
		List<String> alternativeSequences = new ArrayList<>();
		alternativeSequences.add("Q");
		List<String> report = new ArrayList<>();
		report.add("1; BAA37160/BAA37165");
		report.add("2");
		String description = "in Ref. 1; BAA37160/BAA37165 and 2";
		List<String> evs = new ArrayList<>();
		AlternativeSequence altSeq = createAlternativeSequence(originalSequence, alternativeSequences);
		Feature feature = createFeature(FeatureType.CONFLICT, location, description, "", altSeq, evs);

		doTest(ftLine, feature);
		doTestString(ftLineString, feature);
	}

	String val = "Q -> K (in Ref. 1; AAO59377, 2; ABO40479 and 6; AAH63566)";

	@Test
	public void testConflictEvidence() {
		String ftLine = "FT   CONFLICT      1      1       A -> Q (in Ref. 1; BAA37160/BAA37165 and\n"
				+ "FT                                2). {ECO:0000269|PubMed:10433554,\n"
				+ "FT                                ECO:0000313|EMBL:BAG16761.1}.";
		String ftLineString = "CONFLICT 1 1 A -> Q (in Ref. 1; BAA37160/BAA37165 and 2).";
		String ftLineStringEv = "CONFLICT 1 1 A -> Q (in Ref. 1; BAA37160/BAA37165 and 2). "
				+ "{ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1}.";
		String ev1 = "ECO:0000313|EMBL:BAG16761.1";
		String ev2 = "ECO:0000269|PubMed:10433554";
		// String ev3 ="ECO:0000303|Ref.6";
		Range location = new Range(1, 1);

		String originalSequence = "A";
		List<String> alternativeSequences = new ArrayList<>();
		alternativeSequences.add("Q");
		List<String> report = new ArrayList<>();
		report.add("1; BAA37160/BAA37165");
		report.add("2");

		List<String> evs = new ArrayList<>();
		evs.add(ev1);
		evs.add(ev2);
		
		String description = "in Ref. 1; BAA37160/BAA37165 and 2";

		AlternativeSequence altSeq = createAlternativeSequence(originalSequence, alternativeSequences);
		Feature feature = createFeature(FeatureType.CONFLICT, location, description, "", altSeq, evs);

		doTest(ftLine, feature);
		doTestString(ftLineString, feature);
		doTestStringEv(ftLineStringEv, feature);

	}

	@Test
	public void testThreeReprotsAndTwoAltSeq() {
		String ftLine = "FT   CONFLICT      1      1       A -> QK (in Ref. 1; BAA37160/BAA37165, 2;\n"
				+ "FT                                ABO40479 and 6; AAH63566).";
		String ftLineString = "CONFLICT 1 1 A -> QK (in Ref. 1; BAA37160/BAA37165, 2; ABO40479 and 6; AAH63566).";
		Range location = new Range(1, 1);

		String originalSequence = "A";
		List<String> alternativeSequences = new ArrayList<>();
		alternativeSequences.add("QK");
		List<String> report = new ArrayList<>();
		report.add("1; BAA37160/BAA37165");
		report.add("2; ABO40479");
		report.add("6; AAH63566");
		List<String> evs = new ArrayList<>();
		String description ="in Ref. 1; BAA37160/BAA37165, 2; ABO40479 and 6; AAH63566";
		AlternativeSequence altSeq = createAlternativeSequence(originalSequence, alternativeSequences);
		Feature feature = createFeature(FeatureType.CONFLICT, location, description, "", altSeq, evs);


		doTest(ftLine, feature);
		doTestString(ftLineString, feature);
	}

}
