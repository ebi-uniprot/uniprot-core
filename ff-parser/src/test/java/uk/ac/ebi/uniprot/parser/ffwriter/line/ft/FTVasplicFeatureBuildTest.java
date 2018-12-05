package uk.ac.ebi.uniprot.parser.ffwriter.line.ft;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.Range;
import uk.ac.ebi.uniprot.domain.uniprot.feature.AlternativeSequence;
import uk.ac.ebi.uniprot.domain.uniprot.feature.Feature;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType;

public class FTVasplicFeatureBuildTest extends FTBuildTestAbstr {
	@Test
	public void testVarsplic() throws Exception {

		String ftLine = "FT   VAR_SEQ     167    229       Missing (in isoform Alpha and isoform\n"
				+ "FT                                Beta).\n" + "FT                                /FTId=VSP_005610.";
		String ftLineString = "VAR_SEQ 167 229 Missing (in isoform Alpha and isoform " + "Beta).\n"
				+ "/FTId=VSP_005610.";

		String originalSequence = null;
		List<String> alternativeSequences = new ArrayList<>();
		// alternativeSequences.add("E");
		// alternativeSequences.add("N");
		List<String> report = new ArrayList<>();

		report.add("Alpha");
		report.add("Beta");
		Range location = Range.create(167, 229);
		String featureId = "VSP_005610";

		List<String> evs = new ArrayList<>();
		AlternativeSequence altSeq = createAlternativeSequence(originalSequence, alternativeSequences, report);
		Feature feature = createFeature(FeatureType.VAR_SEQ, location, "", featureId, altSeq, evs);

		doTest(ftLine, feature);
		doTestString(ftLineString, feature);

	}

	@Test
	public void testVarsplicEvidence() throws Exception {

		String ftLine = "FT   VAR_SEQ     167    229       Missing (in isoform Alpha and isoform\n"
				+ "FT                                Beta). {ECO:0000269|PubMed:10433554,\n"
				+ "FT                                ECO:0000313|EMBL:BAG16761.1}.\n"
				+ "FT                                /FTId=VSP_005610.";
		String ftLineStringEv = "VAR_SEQ 167 229 Missing (in isoform Alpha and isoform "
				+ "Beta). {ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1}.\n" + "/FTId=VSP_005610.";
		String ftLineString = "VAR_SEQ 167 229 Missing (in isoform Alpha and isoform " + "Beta).\n"
				+ "/FTId=VSP_005610.";
		String ev1 = "ECO:0000313|EMBL:BAG16761.1";
		String ev2 = "ECO:0000269|PubMed:10433554";

		String originalSequence = null;
		List<String> alternativeSequences = new ArrayList<>();
		// alternativeSequences.add("E");
		// alternativeSequences.add("N");
		List<String> report = new ArrayList<>();

		report.add("Alpha");
		report.add("Beta");
		Range location = Range.create(167, 229);
		String featureId = "VSP_005610";

		List<String> evs = new ArrayList<>();
		evs.add(ev1);
		evs.add(ev2);
		AlternativeSequence altSeq = createAlternativeSequence(originalSequence, alternativeSequences, report);
		Feature feature = createFeature(FeatureType.VAR_SEQ, location, "", featureId, altSeq, evs);

		doTest(ftLine, feature);
		doTestString(ftLineString, feature);
		doTestStringEv(ftLineStringEv, feature);

	}

	@Test
	public void testVarsplicMulti() throws Exception {
		String ftLine = "FT   VAR_SEQ      46     46       R -> MLW,RRKI,GPQMTLSHA (in isoform\n"
				+ "FT                                Long).\n" + "FT                                /FTId=VSP_005610.";
		String ftLineString = "VAR_SEQ 46 46 R -> MLW,RRKI,GPQMTLSHA (in isoform " + "Long).\n" + "/FTId=VSP_005610.";

		String originalSequence = "R";
		List<String> alternativeSequences = new ArrayList<>();
		alternativeSequences.add("MLW");
		alternativeSequences.add("RRKI");
		alternativeSequences.add("GPQMTLSHA");
		List<String> report = new ArrayList<>();

		report.add("Long");
		// report.add("Beta");
		Range location = Range.create(46, 46);
		String featureId = "VSP_005610";
		

		List<String> evs = new ArrayList<>();
		// evs.add(ev1);
		// evs.add(ev2);
		AlternativeSequence altSeq = createAlternativeSequence(originalSequence, alternativeSequences, report);
		Feature feature = createFeature(FeatureType.VAR_SEQ, location, "", featureId, altSeq, evs);

		doTest(ftLine, feature);
		doTestString(ftLineString, feature);
	}

	@Test
	public void testVarsplic2() throws Exception {
		String ftLine = "FT   VAR_SEQ      46     46       R -> MLWRRKIGPQMTLSHAAG (in isoform\n"
				+ "FT                                Long).\n" + "FT                                /FTId=VSP_005610.";
		String ftLineString = "VAR_SEQ 46 46 R -> MLWRRKIGPQMTLSHAAG (in isoform " + "Long).\n" + "/FTId=VSP_005610.";

		String originalSequence = "R";
		List<String> alternativeSequences = new ArrayList<>();
		alternativeSequences.add("MLWRRKIGPQMTLSHAAG");
		// alternativeSequences.add("N");
		List<String> report = new ArrayList<>();

		report.add("Long");
		// report.add("Beta");
		Range location = Range.create(46, 46);
		String featureId = "VSP_005610";
	
		List<String> evs = new ArrayList<>();
		// evs.add(ev1);
		// evs.add(ev2);
		AlternativeSequence altSeq = createAlternativeSequence(originalSequence, alternativeSequences, report);
		Feature feature = createFeature(FeatureType.VAR_SEQ, location, "", featureId, altSeq, evs);

		doTest(ftLine, feature);
		doTestString(ftLineString, feature);
	}

	@Test
	public void testVarsplic2Evidence() throws Exception {
		String ftLine = "FT   VAR_SEQ      46     46       R -> MLWRRKIGPQMTLSHAAG (in isoform\n"
				+ "FT                                Long). {ECO:0000269|PubMed:10433554,\n"
				+ "FT                                ECO:0000313|EMBL:BAG16761.1}.\n"
				+ "FT                                /FTId=VSP_005610.";
		String ftLineString = "VAR_SEQ 46 46 R -> MLWRRKIGPQMTLSHAAG (in isoform " + "Long).\n" + "/FTId=VSP_005610.";
		String ftLineStringEv = "VAR_SEQ 46 46 R -> MLWRRKIGPQMTLSHAAG (in isoform "
				+ "Long). {ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1}.\n" + "/FTId=VSP_005610.";
		String ev1 = "ECO:0000313|EMBL:BAG16761.1";
		String ev2 = "ECO:0000269|PubMed:10433554";

		String originalSequence = "R";
		List<String> alternativeSequences = new ArrayList<>();
		alternativeSequences.add("MLWRRKIGPQMTLSHAAG");
		// alternativeSequences.add("N");
		List<String> report = new ArrayList<>();

		report.add("Long");
		// report.add("Beta");
		Range location = Range.create(46, 46);
		String featureId = "VSP_005610";
		
		List<String> evs = new ArrayList<>();
		evs.add(ev1);
		evs.add(ev2);
		AlternativeSequence altSeq = createAlternativeSequence(originalSequence, alternativeSequences, report);
		Feature feature = createFeature(FeatureType.VAR_SEQ, location, "", featureId, altSeq, evs);

		doTest(ftLine, feature);
		doTestString(ftLineString, feature);
		doTestStringEv(ftLineStringEv, feature);
	}

	@Test
	public void testVarsplic3() throws Exception {
		String ftLine = "FT   VAR_SEQ     103    222       GTQLLLEACVQASVPVFIYTSSIEVAGPNSYKEIIQNGHEE\n"
				+ "FT                                EPLENTWPTPYPYSKKLAEKAVLAANGWNLKNGDTLYTCAL\n"
				+ "FT                                RPTYIYGEGGPFLSASINEALNNNGILSSVGK -> FSTVN\n"
				+ "FT                                ELQNKIKLTVLEGDILDEPFLKRACQDVSVVIHTACIIDVF\n"
				+ "FT                                GVTHRQSIMNVNVKGRVAWGGDKARWGNEDQKEGQEGKRSL\n"
				+ "FT                                SIEHLLCSGPSDFADHYQLGELKAAIFSFIDEKTRTEQ\n"
				+ "FT                                (in isoform 2).\n"
				+ "FT                                /FTId=VSP_037399.";
		String ftLineString = "VAR_SEQ 103 222 GTQLLLEACVQASVPVFIYTSSIEVAGPNSYKEIIQNGHEE"
				+ "EPLENTWPTPYPYSKKLAEKAVLAANGWNLKNGDTLYTCAL" + "RPTYIYGEGGPFLSASINEALNNNGILSSVGK -> FSTVN"
				+ "ELQNKIKLTVLEGDILDEPFLKRACQDVSVVIHTACIIDVF" + "GVTHRQSIMNVNVKGRVAWGGDKARWGNEDQKEGQEGKRSL"
				+ "SIEHLLCSGPSDFADHYQLGELKAAIFSFIDEKTRTEQ " + "(in isoform 2).\n" + "/FTId=VSP_037399.";

		String originalSequence = "GTQLLLEACVQASVPVFIYTSSIEVAGPNSYKEIIQNGHEEEPLENTWPTPYPYSKKLAEKAVLAANGWNLKNGDTLYTCAL"
				+ "RPTYIYGEGGPFLSASINEALNNNGILSSVGK";
		String alt = "FSTVNELQNKIKLTVLEGDILDEPFLKRACQDVSVVIHTACIIDVF" + "GVTHRQSIMNVNVKGRVAWGGDKARWGNEDQKEGQEGKRSL"
				+ "SIEHLLCSGPSDFADHYQLGELKAAIFSFIDEKTRTEQ";
		List<String> alternativeSequences = new ArrayList<>();
		alternativeSequences.add(alt);
		// alternativeSequences.add("N");
		List<String> report = new ArrayList<>();

		report.add("2");
		// report.add("Beta");
		Range location = Range.create(103, 222);
		String featureId = "VSP_037399";
		

		List<String> evs = new ArrayList<>();
		// evs.add(ev1);
		// evs.add(ev2);
		AlternativeSequence altSeq = createAlternativeSequence(originalSequence, alternativeSequences, report);
		Feature feature = createFeature(FeatureType.VAR_SEQ, location, "", featureId, altSeq, evs);

		doTest(ftLine, feature);
		doTestString(ftLineString, feature);
	}

	@Test
	public void testVarsplic3Evidence() throws Exception {
		String ftLine = "FT   VAR_SEQ     103    222       GTQLLLEACVQASVPVFIYTSSIEVAGPNSYKEIIQNGHEE\n"
				+ "FT                                EPLENTWPTPYPYSKKLAEKAVLAANGWNLKNGDTLYTCAL\n"
				+ "FT                                RPTYIYGEGGPFLSASINEALNNNGILSSVGK -> FSTVN\n"
				+ "FT                                ELQNKIKLTVLEGDILDEPFLKRACQDVSVVIHTACIIDVF\n"
				+ "FT                                GVTHRQSIMNVNVKGRVAWGGDKARWGNEDQKEGQEGKRSL\n"
				+ "FT                                SIEHLLCSGPSDFADHYQLGELKAAIFSFIDEKTRTEQ\n"
				+ "FT                                (in isoform 2).\n"
				+ "FT                                {ECO:0000269|PubMed:10433554,\n"
				+ "FT                                ECO:0000313|EMBL:BAG16761.1}.\n"
				+ "FT                                /FTId=VSP_037399.";
		String ftLineString = "VAR_SEQ 103 222 GTQLLLEACVQASVPVFIYTSSIEVAGPNSYKEIIQNGHEE"
				+ "EPLENTWPTPYPYSKKLAEKAVLAANGWNLKNGDTLYTCAL" + "RPTYIYGEGGPFLSASINEALNNNGILSSVGK -> FSTVN"
				+ "ELQNKIKLTVLEGDILDEPFLKRACQDVSVVIHTACIIDVF" + "GVTHRQSIMNVNVKGRVAWGGDKARWGNEDQKEGQEGKRSL"
				+ "SIEHLLCSGPSDFADHYQLGELKAAIFSFIDEKTRTEQ " + "(in isoform 2).\n" + "/FTId=VSP_037399.";

		String ftLineStringEv = "VAR_SEQ 103 222 GTQLLLEACVQASVPVFIYTSSIEVAGPNSYKEIIQNGHEE"
				+ "EPLENTWPTPYPYSKKLAEKAVLAANGWNLKNGDTLYTCAL" + "RPTYIYGEGGPFLSASINEALNNNGILSSVGK -> FSTVN"
				+ "ELQNKIKLTVLEGDILDEPFLKRACQDVSVVIHTACIIDVF" + "GVTHRQSIMNVNVKGRVAWGGDKARWGNEDQKEGQEGKRSL"
				+ "SIEHLLCSGPSDFADHYQLGELKAAIFSFIDEKTRTEQ "
				+ "(in isoform 2). {ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1}.\n" + "/FTId=VSP_037399.";
		String ev1 = "ECO:0000313|EMBL:BAG16761.1";
		String ev2 = "ECO:0000269|PubMed:10433554";

		String originalSequence = "GTQLLLEACVQASVPVFIYTSSIEVAGPNSYKEIIQNGHEEEPLENTWPTPYPYSKKLAEKAVLAANGWNLKNGDTLYTCAL"
				+ "RPTYIYGEGGPFLSASINEALNNNGILSSVGK";
		String alt = "FSTVNELQNKIKLTVLEGDILDEPFLKRACQDVSVVIHTACIIDVF" + "GVTHRQSIMNVNVKGRVAWGGDKARWGNEDQKEGQEGKRSL"
				+ "SIEHLLCSGPSDFADHYQLGELKAAIFSFIDEKTRTEQ";
		List<String> alternativeSequences = new ArrayList<>();
		alternativeSequences.add(alt);
		// alternativeSequences.add("N");
		List<String> report = new ArrayList<>();

		report.add("2");
		// report.add("Beta");
		Range location = Range.create(103, 222);
		String featureId = "VSP_037399";
		

		List<String> evs = new ArrayList<>();
		evs.add(ev1);
		evs.add(ev2);
		AlternativeSequence altSeq = createAlternativeSequence(originalSequence, alternativeSequences, report);
		Feature feature = createFeature(FeatureType.VAR_SEQ, location, "", featureId, altSeq, evs);

		doTest(ftLine, feature);
		doTestString(ftLineString, feature);
		doTestStringEv(ftLineStringEv, feature);
	}
}
