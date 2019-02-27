package uk.ac.ebi.uniprot.flatfile.antlr;

import org.junit.Test;
import uk.ac.ebi.uniprot.flatfile.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.flatfile.parser.impl.DefaultUniprotLineParserFactory;
import uk.ac.ebi.uniprot.flatfile.parser.impl.gn.GnLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.gn.GnLineObject.GnNameType;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GnLineParserTest {
	@Test
	public void test() {
		String gnLines = "GN   Name=Jon99Cii; Synonyms=SER1, SER5, Ser99Da; ORFNames=CG7877;\n";
		UniprotLineParser<GnLineObject> parser = new DefaultUniprotLineParserFactory().createGnLineParser();
		GnLineObject obj = parser.parse(gnLines);
		assertEquals(1, obj.gnObjects.size());
		assertEquals(3, obj.gnObjects.get(0).names.size());
		verify(obj.gnObjects.get(0).names.get(0), GnNameType.GENAME, Arrays.asList(new String[] { "Jon99Cii" }), null);
		verify(obj.gnObjects.get(0).names.get(1), GnNameType.SYNNAME,
				Arrays.asList(new String[] { "SER1", "SER5", "Ser99Da" }), null);
		verify(obj.gnObjects.get(0).names.get(2), GnNameType.ORFNAME, Arrays.asList(new String[] { "CG7877" }), null);

	}

	private void verify(GnLineObject.GnName name, GnNameType type, List<String> values, List<String> firstEvidences) {
		assertEquals(type, name.type);
		assertEquals(values, name.names);
		assertEquals(firstEvidences, name.getEvidenceInfo().evidences.get(values.get(0)));
	}

	@Test
	public void testTwoLines() {
		String gnLines = "GN   Name=Jon99Cii; Synonyms=SER1, SER5, Ser99Da;\n" + "GN   ORFNames=CG7877;\n";
		UniprotLineParser<GnLineObject> parser = new DefaultUniprotLineParserFactory().createGnLineParser();
		GnLineObject obj = parser.parse(gnLines);
		assertEquals(1, obj.gnObjects.size());
		assertEquals(3, obj.gnObjects.get(0).names.size());
		verify(obj.gnObjects.get(0).names.get(0), GnNameType.GENAME, Arrays.asList(new String[] { "Jon99Cii" }), null);
		verify(obj.gnObjects.get(0).names.get(1), GnNameType.SYNNAME,
				Arrays.asList(new String[] { "SER1", "SER5", "Ser99Da" }), null);
		verify(obj.gnObjects.get(0).names.get(2), GnNameType.ORFNAME, Arrays.asList(new String[] { "CG7877" }), null);

	}

	@Test
	public void testTwoLines2() {
		String gnLines = "GN   Name=Jon99Cii; Synonyms=SER1, SER5,\n" + "GN   Ser99Da; ORFNames=CG7877;\n";
		UniprotLineParser<GnLineObject> parser = new DefaultUniprotLineParserFactory().createGnLineParser();
		GnLineObject obj = parser.parse(gnLines);
		assertEquals(1, obj.gnObjects.size());
		assertEquals(3, obj.gnObjects.get(0).names.size());
		verify(obj.gnObjects.get(0).names.get(0), GnNameType.GENAME, Arrays.asList(new String[] { "Jon99Cii" }), null);
		verify(obj.gnObjects.get(0).names.get(1), GnNameType.SYNNAME,
				Arrays.asList(new String[] { "SER1", "SER5", "Ser99Da" }), null);
		verify(obj.gnObjects.get(0).names.get(2), GnNameType.ORFNAME, Arrays.asList(new String[] { "CG7877" }), null);
	}

	@Test
	public void testTwoGenes() {
		String gnLines = "GN   Name=Jon99Cii; Synonyms=SER1, SER5, Ser99Da; ORFNames=CG7877;\n" + "GN   and\n"
				+ "GN   Name=Jon99Cii2;\n";
		UniprotLineParser<GnLineObject> parser = new DefaultUniprotLineParserFactory().createGnLineParser();
		GnLineObject obj = parser.parse(gnLines);
		assertEquals(2, obj.gnObjects.size());
		assertEquals(3, obj.gnObjects.get(0).names.size());
		verify(obj.gnObjects.get(0).names.get(0), GnNameType.GENAME, Arrays.asList(new String[] { "Jon99Cii" }), null);
		verify(obj.gnObjects.get(0).names.get(1), GnNameType.SYNNAME,
				Arrays.asList(new String[] { "SER1", "SER5", "Ser99Da" }), null);
		verify(obj.gnObjects.get(0).names.get(2), GnNameType.ORFNAME, Arrays.asList(new String[] { "CG7877" }), null);
		assertEquals(1, obj.gnObjects.get(1).names.size());
		verify(obj.gnObjects.get(1).names.get(0), GnNameType.GENAME, Arrays.asList(new String[] { "Jon99Cii2" }), null);
	}

	@Test
	public void testWithEvidence() {
		String gnLines = "GN   Name=blaCTX-M-14 {ECO:0000006|PubMed:20858735, ECO:0000006};\n"

		;
		UniprotLineParser<GnLineObject> parser = new DefaultUniprotLineParserFactory().createGnLineParser();
		GnLineObject obj = parser.parse(gnLines);
		assertEquals(1, obj.gnObjects.size());
		assertEquals(1, obj.gnObjects.get(0).names.size());
		verify(obj.gnObjects.get(0).names.get(0), GnNameType.GENAME, Arrays.asList(new String[] { "blaCTX-M-14" }),
				Arrays.asList(new String[] { "ECO:0000006|PubMed:20858735", "ECO:0000006" }));
	}

	@Test
	public void testWithEvidenceMore() {
		String gnLines = "GN   Name=GeneA {ECO:0000006|PubMed:20858735}; Synonyms=Syn1 {ECO:0000006|PubMed:20858735,\n"
				+ "GN   ECO:0000005|PubMed:208587235}, Syn2 {ECO:0000005|PubMed:208587235};\n";
		UniprotLineParser<GnLineObject> parser = new DefaultUniprotLineParserFactory().createGnLineParser();
		GnLineObject obj = parser.parse(gnLines);
		assertEquals(1, obj.gnObjects.size());
		assertEquals(2, obj.gnObjects.get(0).names.size());
		verify(obj.gnObjects.get(0).names.get(0), GnNameType.GENAME, Arrays.asList(new String[] { "GeneA" }),
				Arrays.asList(new String[] { "ECO:0000006|PubMed:20858735" }));
		verify(obj.gnObjects.get(0).names.get(1), GnNameType.SYNNAME, Arrays.asList(new String[] { "Syn1", "Syn2" }),
				Arrays.asList(new String[] { "ECO:0000006|PubMed:20858735", "ECO:0000005|PubMed:208587235" }));
	}

	@Test
	public void testWithEvidenceMultiLine() {
		String gnLines = "GN   Name=blaCTX-M-14 {ECO:0000006|PubMed:20858735,\n" + "GN   ECO:0000006};\n";
		UniprotLineParser<GnLineObject> parser = new DefaultUniprotLineParserFactory().createGnLineParser();
		GnLineObject obj = parser.parse(gnLines);
		assertEquals(1, obj.gnObjects.size());
		assertEquals(1, obj.gnObjects.get(0).names.size());
		verify(obj.gnObjects.get(0).names.get(0), GnNameType.GENAME, Arrays.asList(new String[] { "blaCTX-M-14" }),
				Arrays.asList(new String[] { "ECO:0000006|PubMed:20858735", "ECO:0000006" }));
	}

	@Test
	public void testWithEvidenceMultiLine2() {
		String gnLines = "GN   Name=blaCTX-M-14\n" + "GN   {ECO:0000006|PubMed:20858735, ECO:0000006};\n";
		UniprotLineParser<GnLineObject> parser = new DefaultUniprotLineParserFactory().createGnLineParser();
		GnLineObject obj = parser.parse(gnLines);
		assertEquals(1, obj.gnObjects.size());
		assertEquals(1, obj.gnObjects.get(0).names.size());
		verify(obj.gnObjects.get(0).names.get(0), GnNameType.GENAME, Arrays.asList(new String[] { "blaCTX-M-14" }),
				Arrays.asList(new String[] { "ECO:0000006|PubMed:20858735", "ECO:0000006" }));
	}

	@Test
	public void testAll() {
		String gnLines = "GN   Name=ACO2; Synonyms=EI305; OrderedLocusNames=At1g62380;\n" + "GN   ORFNames=F24O1.10;\n";
		UniprotLineParser<GnLineObject> parser = new DefaultUniprotLineParserFactory().createGnLineParser();
		GnLineObject obj = parser.parse(gnLines);
		assertEquals(1, obj.gnObjects.size());
		assertEquals(4, obj.gnObjects.get(0).names.size());
		verify(obj.gnObjects.get(0).names.get(0), GnNameType.GENAME, Arrays.asList(new String[] { "ACO2" }), null);
		verify(obj.gnObjects.get(0).names.get(1), GnNameType.SYNNAME, Arrays.asList(new String[] { "EI305" }), null);
		verify(obj.gnObjects.get(0).names.get(2), GnNameType.OLNAME, Arrays.asList(new String[] { "At1g62380" }), null);
		verify(obj.gnObjects.get(0).names.get(3), GnNameType.ORFNAME, Arrays.asList(new String[] { "F24O1.10" }), null);
	}

	@Test
	public void testGeneWithComma() {
		String gnLines = "GN   Name=ARG5,6;\n";
		UniprotLineParser<GnLineObject> parser = new DefaultUniprotLineParserFactory().createGnLineParser();
		GnLineObject obj = parser.parse(gnLines);
		assertEquals(1, obj.gnObjects.size());
		assertEquals(1, obj.gnObjects.get(0).names.size());
		verify(obj.gnObjects.get(0).names.get(0), GnNameType.GENAME, Arrays.asList(new String[] { "ARG5,6" }), null);
	}

	@Test
	public void testGeneWithSemicolon() {
		String gnLines = "GN   Name=PHT4;1; Synonyms=ANTR1; OrderedLocusNames=At2g29650;\n"
				+ "GN   ORFNames=T27A16.25;\n";
		UniprotLineParser<GnLineObject> parser = new DefaultUniprotLineParserFactory().createGnLineParser();
		GnLineObject obj = parser.parse(gnLines);
		assertEquals(1, obj.gnObjects.size());
		assertEquals(4, obj.gnObjects.get(0).names.size());
		verify(obj.gnObjects.get(0).names.get(0), GnNameType.GENAME, Arrays.asList(new String[] { "PHT4;1" }), null);
		verify(obj.gnObjects.get(0).names.get(1), GnNameType.SYNNAME, Arrays.asList(new String[] { "ANTR1" }), null);
		verify(obj.gnObjects.get(0).names.get(2), GnNameType.OLNAME, Arrays.asList(new String[] { "At2g29650" }), null);
		verify(obj.gnObjects.get(0).names.get(3), GnNameType.ORFNAME, Arrays.asList(new String[] { "T27A16.25" }),
				null);
	}

	@Test
	public void testGeneWithDash() {
		String gnLines = "GN   Name=CXP;2-1;\n";
		UniprotLineParser<GnLineObject> parser = new DefaultUniprotLineParserFactory().createGnLineParser();
		GnLineObject obj = parser.parse(gnLines);
		assertEquals(1, obj.gnObjects.size());
		assertEquals(1, obj.gnObjects.get(0).names.size());
		verify(obj.gnObjects.get(0).names.get(0), GnNameType.GENAME, Arrays.asList(new String[] { "CXP;2-1" }), null);
	}

	@Test
	public void testMultiGene1() {
		String gnLines = "GN   Name=Jon99Cii {ECO:0000313|EMBL:BAG16761.1};\n"
				+ "GN   Synonyms=SER1 {ECO:0000313|EMBL:BAG16761.1}, SER5 {ECO:0000303|Ref.6},\n"
				+ "GN   Ser99Da {ECO:0000269|PubMed:10433554};\n"
				+ "GN   ORFNames=At1g22300 {ECO:0000313|EMBL:BAG16761.1}, CG7877\n"
				+ "GN   {ECO:0000313|EMBL:BAG16761.1}, M117.2 {ECO:0000313|PDB:3OW2};\n" + "GN   and\n"
				+ "GN   Name=Jon99Ciii;\n" + "GN   Synonyms=SER2, SER5 {ECO:0000256|HAMAP-Rule:MF_00205}, Ser99Db;\n"
				+ "GN   ORFNames=CG15519 {ECO:0000303|Ref.6};\n";
		UniprotLineParser<GnLineObject> parser = new DefaultUniprotLineParserFactory().createGnLineParser();
		GnLineObject obj = parser.parse(gnLines);
		assertEquals(2, obj.gnObjects.size());
		assertEquals(3, obj.gnObjects.get(0).names.size());
		verify(obj.gnObjects.get(0).names.get(0), GnNameType.GENAME, Arrays.asList(new String[] { "Jon99Cii" }),
				Arrays.asList(new String[] { "ECO:0000313|EMBL:BAG16761.1" }));
		verify(obj.gnObjects.get(0).names.get(1), GnNameType.SYNNAME,
				Arrays.asList(new String[] { "SER1", "SER5", "Ser99Da" }),
				Arrays.asList(new String[] { "ECO:0000313|EMBL:BAG16761.1" }));
		verify(obj.gnObjects.get(0).names.get(2), GnNameType.ORFNAME,
				Arrays.asList(new String[] { "At1g22300", "CG7877", "M117.2" }),
				Arrays.asList(new String[] { "ECO:0000313|EMBL:BAG16761.1" }));

		verify(obj.gnObjects.get(1).names.get(0), GnNameType.GENAME, Arrays.asList(new String[] { "Jon99Ciii" }), null);
		verify(obj.gnObjects.get(1).names.get(1), GnNameType.SYNNAME,
				Arrays.asList(new String[] { "SER2", "SER5", "Ser99Db" }), null);
		verify(obj.gnObjects.get(1).names.get(2), GnNameType.ORFNAME, Arrays.asList(new String[] { "CG15519" }),
				Arrays.asList(new String[] { "ECO:0000303|Ref.6" }));
	}

	@Test
	public void testMultiGene2() {
		String gnLines = "GN   Name=GF14A {ECO:0000313|EMBL:BAG16761.1, ECO:0000269|PubMed:10433554,\n"
				+ "GN   ECO:0000313|PDB:3OW2};\n" + "GN   OrderedLocusNames=Os08g0480800 {ECO:0000303|Ref.6,\n"
				+ "GN   ECO:0000269|PubMed:10433554, ECO:0000313|PDB:3OW2}, LOC_Os08g37490\n"
				+ "GN   {ECO:0000313|EMBL:BAG16761.1};\n" + "GN   ORFNames=OJ1113_A10.40 {ECO:0000313|PDB:3OW2,\n"
				+ "GN   ECO:0000256|HAMAP-Rule:MF_00205}, OSJNBb0092C08.10;\n";
		UniprotLineParser<GnLineObject> parser = new DefaultUniprotLineParserFactory().createGnLineParser();
		GnLineObject obj = parser.parse(gnLines);
		assertEquals(1, obj.gnObjects.size());
		assertEquals(3, obj.gnObjects.get(0).names.size());
		verify(obj.gnObjects.get(0).names.get(0), GnNameType.GENAME, Arrays.asList(new String[] { "GF14A" }),
				Arrays.asList(new String[] { "ECO:0000313|EMBL:BAG16761.1", "ECO:0000269|PubMed:10433554",
						"ECO:0000313|PDB:3OW2" }));
		verify(obj.gnObjects.get(0).names.get(1), GnNameType.OLNAME,
				Arrays.asList(new String[] { "Os08g0480800", "LOC_Os08g37490" }), Arrays.asList(
						new String[] { "ECO:0000303|Ref.6", "ECO:0000269|PubMed:10433554", "ECO:0000313|PDB:3OW2" }));
		verify(obj.gnObjects.get(0).names.get(2), GnNameType.ORFNAME,
				Arrays.asList(new String[] { "OJ1113_A10.40", "OSJNBb0092C08.10" }),
				Arrays.asList(new String[] { "ECO:0000313|PDB:3OW2", "ECO:0000256|HAMAP-Rule:MF_00205" }));

	}

	@Test
	public void testMultiEvidences() {
		String gnLines = "GN   Name=par-5 {ECO:0000269|PubMed:10433554, ECO:0000269|Ref.6,\n"
				+ "GN   ECO:0000303|PubMed:10433554, ECO:0000305, ECO:0000250|UniProtKB:Q8WUF5, ECO:0000250,\n"
				+ "GN   ECO:0000312|EMBL:BAG16761.1, ECO:0000255|HAMAP-Rule:MF_00205, ECO:0000255,\n"
				+ "GN   ECO:0000244|PubMed:10433554, ECO:0000244|Ref.6, ECO:0000257,\n"
				+ "GN   ECO:0000313|EMBL:BAG16761.1, ECO:0000256|HAMAP-Rule:MF_00205};\n"

		;
		UniprotLineParser<GnLineObject> parser = new DefaultUniprotLineParserFactory().createGnLineParser();
		GnLineObject obj = parser.parse(gnLines);
		assertEquals(1, obj.gnObjects.size());
		assertEquals(1, obj.gnObjects.get(0).names.size());
		verify(obj.gnObjects.get(0).names.get(0), GnNameType.GENAME, Arrays.asList(new String[] { "par-5" }),
				Arrays.asList(new String[] { "ECO:0000269|PubMed:10433554", "ECO:0000269|Ref.6",
						"ECO:0000303|PubMed:10433554", "ECO:0000305", "ECO:0000250|UniProtKB:Q8WUF5", "ECO:0000250",
						"ECO:0000312|EMBL:BAG16761.1", "ECO:0000255|HAMAP-Rule:MF_00205", "ECO:0000255",
						"ECO:0000244|PubMed:10433554", "ECO:0000244|Ref.6", "ECO:0000257",
						"ECO:0000313|EMBL:BAG16761.1", "ECO:0000256|HAMAP-Rule:MF_00205" }));

	}
}
