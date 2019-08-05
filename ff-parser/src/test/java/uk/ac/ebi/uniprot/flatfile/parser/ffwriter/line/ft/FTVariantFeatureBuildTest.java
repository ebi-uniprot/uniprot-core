package uk.ac.ebi.uniprot.flatfile.parser.ffwriter.line.ft;

import org.junit.Test;
import org.uniprot.core.Range;
import org.uniprot.core.uniprot.feature.AlternativeSequence;
import org.uniprot.core.uniprot.feature.Feature;
import org.uniprot.core.uniprot.feature.FeatureType;

import java.util.ArrayList;
import java.util.List;

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
		String description ="in a breast cancer sample; somatic mutation; dbSNP:rs35514614";
		AlternativeSequence altSeq = createAlternativeSequence(originalSequence, alternativeSequences);
		Feature feature = createFeature(FeatureType.VARIANT, location, description, featureId, altSeq, evs);

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
		String description ="in a breast cancer sample; somatic mutation; dbSNP:rs35514614";
		AlternativeSequence altSeq = createAlternativeSequence(originalSequence, alternativeSequences);
		Feature feature = createFeature(FeatureType.VARIANT, location, description, featureId, altSeq, evs);

		doTest(ftLine, feature);
		doTestString(ftLineString, feature);
		doTestStringEv(ftLineStringEv, feature);
	}

}
