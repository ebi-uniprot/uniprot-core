package uk.ac.ebi.uniprot.parser.ffwriter.line.ft;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.Range;

import uk.ac.ebi.uniprot.domain.uniprot.feature.AlternativeSequence;
import uk.ac.ebi.uniprot.domain.uniprot.feature.Feature;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType;

public class FTVariantFeatureBuildTest extends FTBuildTestAbstr {
	@Test
	public void testVariant() throws Exception {
		String ftLine = "FT   VARIANT     221    221       G -> E (in a breast cancer sample;\n"
				+ "FT                                somatic mutation; dbSNP:rs35514614).\n"
				+ "FT                                /FTId=VAR_038685.";
		String ftLineString = "VARIANT 221 221 G -> E (in a breast cancer sample; "
				+ "somatic mutation; dbSNP:rs35514614).\n" + "/FTId=VAR_038685.";

		String originalSequence = "G";
		List<String> alternativeSequences = new ArrayList<>();
		alternativeSequences.add("E");
		// alternativeSequences.add("N");
		List<String> report = new ArrayList<>();

		report.add("in a breast cancer sample; somatic mutation; dbSNP:rs35514614");
		Range location = Range.create(221, 221);
		String featureId = "VAR_038685";

		List<String> evs = new ArrayList<>();

		AlternativeSequence altSeq = createAlternativeSequence(originalSequence, alternativeSequences, report);
		Feature feature = createFeature(FeatureType.VARIANT, location, "", featureId, altSeq, evs);

		doTest(ftLine, feature);
		doTestString(ftLineString, feature);
	}

	@Test
	public void testVariantEvidence() throws Exception {
		String ftLine = "FT   VARIANT     221    221       G -> E,D (in a breast cancer sample;\n"
				+ "FT                                somatic mutation; dbSNP:rs35514614).\n"
				+ "FT                                {ECO:0000269|PubMed:10433554,\n"
				+ "FT                                ECO:0000313|EMBL:BAG16761.1}.\n"
				+ "FT                                /FTId=VAR_038685.";
		String ftLineString = "VARIANT 221 221 G -> E,D (in a breast cancer sample; "
				+ "somatic mutation; dbSNP:rs35514614).\n" + "/FTId=VAR_038685.";
		String ftLineStringEv = "VARIANT 221 221 G -> E,D (in a breast cancer sample; "
				+ "somatic mutation; dbSNP:rs35514614). {ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1}.\n"
				+ "/FTId=VAR_038685.";
		String ev1 = "ECO:0000313|EMBL:BAG16761.1";
		String ev2 = "ECO:0000269|PubMed:10433554";

		String originalSequence = "G";
		List<String> alternativeSequences = new ArrayList<>();
		alternativeSequences.add("E");
		alternativeSequences.add("D");
		List<String> report = new ArrayList<>();

		report.add("in a breast cancer sample; somatic mutation; dbSNP:rs35514614");
		Range location = Range.create(221, 221);
		String featureId = "VAR_038685";

		List<String> evs = new ArrayList<>();
		evs.add(ev1);
		evs.add(ev2);
		AlternativeSequence altSeq = createAlternativeSequence(originalSequence, alternativeSequences, report);
		Feature feature = createFeature(FeatureType.VARIANT, location, "", featureId, altSeq, evs);

		doTest(ftLine, feature);
		doTestString(ftLineString, feature);
		doTestStringEv(ftLineStringEv, feature);
	}

}
