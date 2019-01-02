package uk.ac.ebi.uniprot.parser.ffwriter.line.ft;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.Range;
import uk.ac.ebi.uniprot.domain.uniprot.feature.AlternativeSequence;
import uk.ac.ebi.uniprot.domain.uniprot.feature.Feature;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType;

public class FTMutagenFeatureBuildTest extends FTBuildTestAbstr {
	@Test
	public void testMutagen() throws Exception {
		String ftLine = "FT   MUTAGEN       2      2       B->A,N: Less than 1% residual activity.";
		String ftLineString = "MUTAGEN 2 2 B->A,N: Less than 1% residual activity.";
		String originalSequence = "B";
		List<String> alternativeSequences = new ArrayList<>();
		alternativeSequences.add("A");
		alternativeSequences.add("N");
		List<String> report = new ArrayList<>();

		report.add("Less than 1% residual activity");
		Range location = Range.create(2, 2);
		List<String> evs = new ArrayList<>();
		String description = "Less than 1% residual activity";
		AlternativeSequence altSeq = createAlternativeSequence(originalSequence, alternativeSequences);
		Feature feature = createFeature(FeatureType.MUTAGEN, location, description, "", altSeq, evs);

		doTest(ftLine, feature);
		doTestString(ftLineString, feature);
	}

	@Test
	public void testMutagenEvidence() throws Exception {
		String ftLine = "FT   MUTAGEN       2      2       B->A,N: Less than 1% residual activity.\n"
				+ "FT                                {ECO:0000269|PubMed:10433554,\n"
				+ "FT                                ECO:0000313|EMBL:BAG16761.1}.";
		String ftLineString = "MUTAGEN 2 2 B->A,N: Less than 1% residual activity.";
		String ftLineStringEv = "MUTAGEN 2 2 B->A,N: Less than 1% residual activity. "
				+ "{ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1}.";
		String ev1 = "ECO:0000313|EMBL:BAG16761.1";
		String ev2 = "ECO:0000269|PubMed:10433554";
		String originalSequence = "B";
		List<String> alternativeSequences = new ArrayList<>();
		alternativeSequences.add("A");
		alternativeSequences.add("N");
		List<String> report = new ArrayList<>();

		report.add("Less than 1% residual activity");
		Range location = Range.create(2, 2);

		List<String> evs = new ArrayList<>();
		evs.add(ev1);
		evs.add(ev2);
		String description = "Less than 1% residual activity";
		AlternativeSequence altSeq = createAlternativeSequence(originalSequence, alternativeSequences);
		Feature feature = createFeature(FeatureType.MUTAGEN, location, description, "", altSeq, evs);

		doTest(ftLine, feature);
		doTestString(ftLineString, feature);
		doTestStringEv(ftLineStringEv, feature);
	}
}
