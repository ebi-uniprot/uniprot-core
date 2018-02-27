package uk.ac.ebi.uniprot.parser.ffwriter.line.ft;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.feature.Feature;
import uk.ac.ebi.uniprot.domain.feature.FeatureLocation;
import uk.ac.ebi.uniprot.domain.feature.MutagenFeature;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtFeature;


public class FTMutagenFeatureBuildTest extends FTBuildTestAbstr {
	@Test
	public void testMutagen() throws Exception {
		String ftLine ="FT   MUTAGEN       2      2       B->A,N: Less than 1% residual activity.";
		String ftLineString = "MUTAGEN 2 2 B->A,N: Less than 1% residual activity.";
		String originalSequence = "B";
		List<String> alternativeSequences = new ArrayList<>();
		alternativeSequences.add("A");
		alternativeSequences.add("N");
		List<String> report =new ArrayList<>();

		report.add("Less than 1% residual activity");
		FeatureLocation location =factory.createFeatureLocation(2, 2);
		MutagenFeature feature = 
		factory.buildMutagenFeature(location, originalSequence, alternativeSequences, report);
		List<String> evs = new ArrayList<>();
		UniProtFeature<? extends Feature> uniFeature =  factory.createUniProtFeature(feature, createEvidence(evs));
	
		doTest(ftLine, uniFeature);	
		doTestString(ftLineString,uniFeature);
	}
	@Test
	public void testMutagenEvidence() throws Exception {
		String ftLine =
				"FT   MUTAGEN       2      2       B->A,N: Less than 1% residual activity.\n" +
				"FT                                {ECO:0000269|PubMed:10433554,\n" +
				"FT                                ECO:0000313|EMBL:BAG16761.1}.";
		String ftLineString = "MUTAGEN 2 2 B->A,N: Less than 1% residual activity.";
		String ftLineStringEv = "MUTAGEN 2 2 B->A,N: Less than 1% residual activity. "+
		"{ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1}.";
		String ev1 ="ECO:0000313|EMBL:BAG16761.1";
		String ev2= "ECO:0000269|PubMed:10433554";
		String originalSequence = "B";
		List<String> alternativeSequences = new ArrayList<>();
		alternativeSequences.add("A");
		alternativeSequences.add("N");
		List<String> report =new ArrayList<>();

		report.add("Less than 1% residual activity");
		FeatureLocation location =factory.createFeatureLocation(2, 2);
		MutagenFeature feature = 
		factory.buildMutagenFeature(location, originalSequence, alternativeSequences, report);
		List<String> evs = new ArrayList<>();
		evs.add(ev1);
		evs.add(ev2);
		UniProtFeature<? extends Feature> uniFeature =  factory.createUniProtFeature(feature, createEvidence(evs));
		
	
		doTest(ftLine, uniFeature);	
		doTestString(ftLineString,uniFeature);
		doTestStringEv(ftLineStringEv,uniFeature);
	}
}
