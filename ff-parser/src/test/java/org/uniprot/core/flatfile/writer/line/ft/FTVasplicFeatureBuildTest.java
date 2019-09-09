package org.uniprot.core.flatfile.writer.line.ft;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Range;
import org.uniprot.core.uniprot.feature.AlternativeSequence;
import org.uniprot.core.uniprot.feature.Feature;
import org.uniprot.core.uniprot.feature.FeatureType;

import java.util.ArrayList;
import java.util.List;

class FTVasplicFeatureBuildTest extends FTBuildTestAbstr {
	@Test
	void testVarsplic() {

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
		String description ="in isoform Alpha and isoform Beta";
		List<String> evs = new ArrayList<>();
		AlternativeSequence altSeq = createAlternativeSequence(originalSequence, alternativeSequences);
		Feature feature = createFeature(FeatureType.VAR_SEQ, location, description, featureId, altSeq, evs);

		doTest(ftLine, feature);
		doTestString(ftLineString, feature);

	}

	@Test
	void testVarsplicEvidence() {

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
		String description ="in isoform Alpha and isoform Beta";
		AlternativeSequence altSeq = createAlternativeSequence(originalSequence, alternativeSequences);
		Feature feature = createFeature(FeatureType.VAR_SEQ, location, description, featureId, altSeq, evs);

		doTest(ftLine, feature);
		doTestString(ftLineString, feature);
		doTestStringEv(ftLineStringEv, feature);

	}

	@Test
	void testVarsplicMulti() {
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
		String description ="in isoform Long";
		AlternativeSequence altSeq = createAlternativeSequence(originalSequence, alternativeSequences);
		Feature feature = createFeature(FeatureType.VAR_SEQ, location, description, featureId, altSeq, evs);

		doTest(ftLine, feature);
		doTestString(ftLineString, feature);
	}

	@Test
	void testVarsplic2() {
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
		String description ="in isoform Long";
		AlternativeSequence altSeq = createAlternativeSequence(originalSequence, alternativeSequences);
		Feature feature = createFeature(FeatureType.VAR_SEQ, location, description, featureId, altSeq, evs);

		doTest(ftLine, feature);
		doTestString(ftLineString, feature);
	}

	@Test
	void testVarsplic2Evidence() {
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
		String description ="in isoform Long";
		AlternativeSequence altSeq = createAlternativeSequence(originalSequence, alternativeSequences);
		Feature feature = createFeature(FeatureType.VAR_SEQ, location, description, featureId, altSeq, evs);

		doTest(ftLine, feature);
		doTestString(ftLineString, feature);
		doTestStringEv(ftLineStringEv, feature);
	}

	@Test
	void testVarsplic3() {
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
		String description ="in isoform 2";
		AlternativeSequence altSeq = createAlternativeSequence(originalSequence, alternativeSequences);
		Feature feature = createFeature(FeatureType.VAR_SEQ, location, description, featureId, altSeq, evs);

		doTest(ftLine, feature);
		doTestString(ftLineString, feature);
	}

	@Test
	void testVarsplic3Evidence() {
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
		String description ="in isoform 2";
		AlternativeSequence altSeq = createAlternativeSequence(originalSequence, alternativeSequences);
		Feature feature = createFeature(FeatureType.VAR_SEQ, location, description, featureId, altSeq, evs);

		doTest(ftLine, feature);
		doTestString(ftLineString, feature);
		doTestStringEv(ftLineStringEv, feature);
	}
}
