package uk.ac.ebi.uniprot.antlr;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import uk.ac.ebi.uniprot.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineFormater;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject.AlternativeNameSequenceEnum;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineObject.AlternativeProducts;

public class CcLineAPCommentParserTest {
	@Test
	public void testParser() {
		String lines = "CC   -!- ALTERNATIVE PRODUCTS:\n"
				+"CC       Event=Alternative splicing; Named isoforms=3;\n"
				+"CC         Comment=Additional isoforms seem to exist. Experimental\n"
				+"CC         confirmation may be lacking for some isoforms.;\n"
				+"CC       Name=1; Synonyms=AIRE-1;\n"
				+"CC         IsoId=O43918-1; Sequence=Displayed;\n"
				+"CC       Name=2; Synonyms=AIRE-2;\n"
				+"CC         IsoId=O43918-2; Sequence=VSP_004089;\n"
				+"CC       Name=3; Synonyms=AIRE-3;\n"
				+"CC         IsoId=O43918-3; Sequence=VSP_004089, VSP_004090;\n";
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj =parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof AlternativeProducts);
		AlternativeProducts ap = (AlternativeProducts)cc.object;
		assertEquals(1, ap.events.size());
		assertEquals("Alternative splicing", ap.events.get(0));
		assertEquals("3", ap.namedIsoforms);
		assertEquals("Additional isoforms seem to exist. Experimental confirmation may be lacking for some isoforms.", ap.comment.get(0).value);
		assertEquals(3, ap.names.size());
		assertEquals("1",ap.names.get(0).name.value);
		assertEquals(1,ap.names.get(0).isoId.size());
		assertEquals("O43918-1",ap.names.get(0).isoId.get(0));
		assertEquals(AlternativeNameSequenceEnum.Displayed, ap.names.get(0).sequence_enum);
		assertTrue(ap.names.get(0).sequence_FTId.isEmpty());
		assertEquals(1, ap.names.get(0).synNames.size());
		assertEquals("AIRE-1", ap.names.get(0).synNames.get(0).value);

		assertEquals("2",ap.names.get(1).name.value);
		assertEquals(1,ap.names.get(1).isoId.size());
		assertEquals("O43918-2",ap.names.get(1).isoId.get(0));

		assertEquals(1, ap.names.get(1).sequence_FTId.size());
		assertEquals("VSP_004089", ap.names.get(1).sequence_FTId.get(0));
		assertEquals(1, ap.names.get(1).synNames.size());
		assertEquals("AIRE-2", ap.names.get(1).synNames.get(0).value);
		
		assertEquals("3",ap.names.get(2).name.value);
		assertEquals(1,ap.names.get(2).isoId.size());
		assertEquals("O43918-3",ap.names.get(2).isoId.get(0));
		assertEquals(null, ap.names.get(2).sequence_enum);
		assertEquals(2, ap.names.get(2).sequence_FTId.size());
		assertEquals("VSP_004089", ap.names.get(2).sequence_FTId.get(0));
		assertEquals("VSP_004090", ap.names.get(2).sequence_FTId.get(1));
		assertEquals(1, ap.names.get(2).synNames.size());
		assertEquals("AIRE-3", ap.names.get(2).synNames.get(0).value);
		
	}

	@Test
	public void testParser2() {
		String lines = "CC   -!- ALTERNATIVE PRODUCTS:\n"
				+"CC       Event=Alternative promoter usage; Named isoforms=2;\n"
				+"CC       Name=alpha;\n"
				+"CC         IsoId=P12544-1; Sequence=Displayed;\n"
				+"CC       Name=beta;\n"
				+"CC         IsoId=P12544-2; Sequence=VSP_038571, VSP_038572;\n"
				;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj =parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof AlternativeProducts);
		AlternativeProducts ap = (AlternativeProducts)cc.object;
		assertEquals(1, ap.events.size());
		assertEquals("Alternative promoter usage", ap.events.get(0));
		assertEquals("2", ap.namedIsoforms);
		assertEquals(2, ap.names.size());
		assertEquals("alpha",ap.names.get(0).name.value);
		assertEquals(1,ap.names.get(0).isoId.size());
		assertEquals("P12544-1",ap.names.get(0).isoId.get(0));
		assertEquals(AlternativeNameSequenceEnum.Displayed, ap.names.get(0).sequence_enum);
		assertTrue(ap.names.get(0).sequence_FTId.isEmpty());
		assertEquals(0, ap.names.get(0).synNames.size());

		assertEquals("beta",ap.names.get(1).name.value);
		assertEquals(1,ap.names.get(1).isoId.size());
		assertEquals("P12544-2",ap.names.get(1).isoId.get(0));

		assertEquals(2, ap.names.get(1).sequence_FTId.size());
		assertEquals("VSP_038571", ap.names.get(1).sequence_FTId.get(0));
		assertEquals("VSP_038572", ap.names.get(1).sequence_FTId.get(1));
		assertEquals(0, ap.names.get(1).synNames.size());

	
	}

	
	@Test
	public void testParserSequenceValue() {
		String lines =  "CC   -!- ALTERNATIVE PRODUCTS:\n"+
		        "CC       Event=Alternative splicing; Named isoforms=6;\n"+
		        "CC       Name=1; Synonyms=A;\n"+
		        "CC         IsoId=Q9V8R9-1; Sequence=Displayed;\n"+
		        "CC       Name=2;\n"+
		        "CC         IsoId=Q9V8R9-2; Sequence=VSP_000476, VSP_000477, VSP_000479,\n"+
		        "CC                                  VSP_000480, VSP_000481;\n"+
		        "CC       Name=3; Synonyms=C;\n"+
		        "CC         IsoId=Q9V8R9-3; Sequence=VSP_000475, VSP_000478, VSP_000479;\n"+
		        "CC       Name=4; Synonyms=B;\n"+
		        "CC         IsoId=Q9V8R9-4; Sequence=VSP_000476, VSP_000477, VSP_000479;\n"+
		        "CC       Name=5;\n"+
		        "CC         IsoId=Q9V8R9-5; Sequence=VSP_000474, VSP_000478;\n"+
		        "CC         Note=No experimental confirmation available.;\n"+
		        "CC       Name=6; Synonyms=D;\n"+
		        "CC         IsoId=Q9V8R9-6; Sequence=VSP_000478;\n"+
		        "CC         Note=No experimental confirmation available.;\n"
				;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj =parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof AlternativeProducts);
		AlternativeProducts ap = (AlternativeProducts)cc.object;
		assertEquals(6, ap.names.size());
		assertEquals(5, ap.names.get(1).sequence_FTId.size());
		
	}
	
	@Test
	public void testParserSynonyms() {
		String lines =  "CC   -!- ALTERNATIVE PRODUCTS:\n"
		        +"CC       Event=Alternative splicing; Named isoforms=20;\n"
		        +"CC       Name=Bim-alpha3; Synonyms=BCL2-like 11 transcript variant 10,\n"
		        +"CC       BimAD, Bim-AD;\n"
		        +"CC         IsoId=O43521-6; Sequence=VSP_035608, VSP_035620;\n"
				;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj =parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof AlternativeProducts);
		AlternativeProducts ap = (AlternativeProducts)cc.object;
		assertEquals(1, ap.names.size());
		assertEquals(3, ap.names.get(0).synNames.size());
		assertEquals("BCL2-like 11 transcript variant 10", ap.names.get(0).synNames.get(0).value);
		assertEquals("BimAD", ap.names.get(0).synNames.get(1).value);
		assertEquals("Bim-AD", ap.names.get(0).synNames.get(2).value);
	}
	
	@Test
	public void testParserSynonyms2() {
		String lines =  "CC   -!- ALTERNATIVE PRODUCTS:\n"
		        +"CC       Event=Alternative splicing; Named isoforms=15;\n"
		        +"CC       Name=1; Synonyms=FLIP-L, CLARP1, MRIT alpha-1, CASH alpha, I-FLICE\n"
		        +"CC       1, FLAME-1 gamma, Usurpin alpha;\n"
		        +"CC         IsoId=O15519-1; Sequence=Displayed;\n"
				;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj =parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof AlternativeProducts);
		AlternativeProducts ap = (AlternativeProducts)cc.object;
		assertEquals(1, ap.names.size());
		assertEquals(7, ap.names.get(0).synNames.size());
		assertEquals("I-FLICE 1", ap.names.get(0).synNames.get(4).value);
	}
	
	@Test
	public void testParserWithEvidences() {
		String lines =  "CC   -!- ALTERNATIVE PRODUCTS:\n"
				+"CC       Event=Alternative splicing; Named isoforms=6;\n"
				+"CC       Name=1 {ECO:0000313|EMBL:BAG16761.1};\n"
		        +"CC         IsoId=Q9NQ94-1; Sequence=Displayed;\n"
		        +"CC       Name=2 {ECO:0000313|EMBL:BAG16761.1};\n"
		        +"CC         IsoId=Q9NQ94-2; Sequence=VSP_051929;\n"
		        +"CC         Note=Major isoform found in 66-78% of cDNA clones.;\n"
		        +"CC       Name=3;\n"
		        +"CC         IsoId=Q9NQ94-3; Sequence=VSP_051926, VSP_051929;\n"
		        +"CC       Name=4;\n"
		        +"CC         IsoId=Q9NQ94-4; Sequence=VSP_051927, VSP_051929;\n"
		        +"CC         Note=Does not exhibit APOBEC1 complementation activity. Ref.4\n"
		        +"CC         sequence is in conflict in positions: 33:I->T. No experimental\n"
		        +"CC         confirmation available.;\n"
		        +"CC       Name=5 {ECO:0000313|EMBL:BAG16761.1};\n"
		        +"CC         IsoId=Q9NQ94-5; Sequence=VSP_051925;\n"
		        +"CC         Note=Does not exhibit APOBEC1 complementation activity.;\n"
		        +"CC       Name=6 {ECO:0000313|EMBL:BAG16761.1};\n"
		        +"CC         IsoId=Q9NQ94-6; Sequence=VSP_051928;\n"
		        +"CC         Note=Minor isoform found in 2-3% of cDNA clones. {ECO:0000313|EMBL:BAG16761.1};\n"
				;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj =parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof AlternativeProducts);
		AlternativeProducts ap = (AlternativeProducts)cc.object;
		assertEquals(6, ap.names.size());
		assertEquals("2", ap.names.get(1).name.value);
		assertNotNull(ap.names.get(1).name.evidences);
		assertEquals(1, ap.names.get(1).name.evidences.size());
		assertEquals("ECO:0000313|EMBL:BAG16761.1", ap.names.get(1).name.evidences.get(0));
	}
	

	@Test
	public void testParserWithEvidences2() {
		String lines =  "CC   -!- ALTERNATIVE PRODUCTS:\n"
				    +"CC       Event=Alternative splicing; Named isoforms=6;\n"
				    +"CC         Comment=Additional isoforms seem to exist.\n"
                    +"CC         {ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6};\n"
                    +"CC       Name=1 {ECO:0000313|EMBL:BAG16761.1}; Synonyms=A\n"
                    +"CC       {ECO:0000256|HAMAP-Rule:MF_002051, ECO:0000313|PDB:3OW2};\n"
                    +"CC         IsoId=Q9V8R9-1; Sequence=Displayed;\n"
                    +"CC         Note=Does not exhibit APOBEC1 complementation activity. Ref.4\n"
                    +"CC         sequence is in conflict in positions: 33:I->T. No experimental\n"
                    +"CC         confirmation available. {ECO:0000313|PDB:3OW2};\n"
                    +"CC       Name=2;\n"
                    +"CC         IsoId=Q9V8R9-2; Sequence=VSP_000476, VSP_000477, VSP_000479,\n"
                    +"CC                                  VSP_000480, VSP_000481;\n"
                    +"CC       Name=Bim-alpha3 {ECO:0000256|HAMAP-Rule:MF_00205,\n"
                    +"CC       ECO:0000313|PDB:3OW2}; Synonyms=BCL2-like 11 transcript variant 10\n"
                    +"CC       {ECO:0000313|EMBL:BAG16761.1}, Bim-AD\n"
                    +"CC       {ECO:0000256|HAMAP-Rule:MF_00205}, BimAD {ECO:0000313|PDB:3OW2};\n"
                    +"CC         IsoId=Q9V8R9-3; Sequence=VSP_000475, VSP_000478, VSP_000479;\n"
                    +"CC       Name=4; Synonyms=B;\n"
                    +"CC         IsoId=Q9V8R9-4; Sequence=VSP_000476, VSP_000477, VSP_000479;\n"
                    +"CC       Name=5;\n"
                    +"CC         IsoId=Q9V8R9-5; Sequence=VSP_000474, VSP_000478;\n"
                    +"CC         Note=No experimental confirmation available.\n"
                    +"CC         {ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1};\n"
                    +"CC       Name=6; Synonyms=D;\n"
                    +"CC         IsoId=Q9V8R9-6; Sequence=Described;\n"
                    +"CC         Note=No experimental confirmation.;\n"
				;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj =parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof AlternativeProducts);
		AlternativeProducts ap = (AlternativeProducts)cc.object;
		assertEquals(1, ap.events.size());
		assertEquals("Alternative splicing", ap.events.get(0));
		assertEquals(1, ap.comment.size());
		assertEquals("Additional isoforms seem to exist.", ap.comment.get(0).value);
		assertEquals("ECO:0000269|PubMed:10433554", ap.comment.get(0).evidences.get(0));
		assertEquals("ECO:0000303|Ref.6", ap.comment.get(0).evidences.get(1));
		assertEquals(6, ap.names.size());
		assertEquals("1", ap.names.get(0).name.value);
		assertNotNull(ap.names.get(0).name.evidences);
		assertEquals(1, ap.names.get(0).name.evidences.size());
		assertEquals("ECO:0000313|EMBL:BAG16761.1", ap.names.get(0).name.evidences.get(0));
		assertEquals(1, ap.names.get(0).synNames.size());
		assertEquals("A",  ap.names.get(0).synNames.get(0).value);
		assertEquals("ECO:0000256|HAMAP-Rule:MF_002051", ap.names.get(0).synNames.get(0).evidences.get(0));
		assertEquals("ECO:0000313|PDB:3OW2", ap.names.get(0).synNames.get(0).evidences.get(1));
		assertEquals("Does not exhibit APOBEC1 complementation activity. Ref.4 sequence is in conflict in positions: 33:I->T. No experimental confirmation available.", ap.names.get(0).note.get(0).value);
		assertEquals("ECO:0000313|PDB:3OW2", ap.names.get(0).note.get(0).evidences.get(0));
		
		
		assertEquals("Bim-alpha3", ap.names.get(2).name.value);
		assertNotNull(ap.names.get(2).name.evidences);
		assertEquals(2, ap.names.get(2).name.evidences.size());
		assertEquals("ECO:0000256|HAMAP-Rule:MF_00205", ap.names.get(2).name.evidences.get(0));
		assertEquals("ECO:0000313|PDB:3OW2", ap.names.get(2).name.evidences.get(1));
		assertEquals(3, ap.names.get(2).synNames.size());
		assertEquals("BCL2-like 11 transcript variant 10",  ap.names.get(2).synNames.get(0).value);
		assertEquals("ECO:0000313|EMBL:BAG16761.1", ap.names.get(2).synNames.get(0).evidences.get(0));
		
		assertEquals("Bim-AD",  ap.names.get(2).synNames.get(1).value);
		assertEquals("ECO:0000256|HAMAP-Rule:MF_00205", ap.names.get(2).synNames.get(1).evidences.get(0));
		
		assertEquals("BimAD",  ap.names.get(2).synNames.get(2).value);
		assertEquals("ECO:0000313|PDB:3OW2", ap.names.get(2).synNames.get(2).evidences.get(0));
		

		assertEquals("No experimental confirmation available.", ap.names.get(4).note.get(0).value);
		assertEquals("ECO:0000269|PubMed:10433554", ap.names.get(4).note.get(0).evidences.get(0));
		
	}
	@Test
	public void testNoHeaderWithEvidence() {
		String ccLineStringEvidence ="ALTERNATIVE PRODUCTS:\n" +
		        "Event=Alternative splicing; Named isoforms=6;\n" +
		        "Comment=Additional isoforms seem to exist. {ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6};"
		        + " Another additional isoforms also seem to exist. {ECO:0000269|PubMed:10433554};\n" +
		        "Name=1 {ECO:0000313|EMBL:BAG16761.1, ECO:0000313|PDB:3OW2}; Synonyms=A {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|PDB:3OW2};\n" +
		        "  IsoId=Q9V8R9-1; Sequence=Displayed;\n" +
		        "  Note=Does not exhibit APOBEC1 complementation activity. Ref.4 sequence is in conflict in positions: 33:I->T."
		        + " No experimental confirmation available. {ECO:0000313|PDB:3OW2};\n" +
		        "Name=2;\n" +
		        "  IsoId=Q9V8R9-2; Sequence=VSP_000476, VSP_000477, VSP_000479, VSP_000480, VSP_000481;\n" +
		        "Name=Bim-alpha3 {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|PDB:3OW2}; Synonyms=BCL2-like 11 transcript"
		        + " variant 10 {ECO:0000313|EMBL:BAG16761.1}, Bim-AD, BimAD {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|PDB:3OW2};\n" +
		        "  IsoId=Q9V8R9-3; Sequence=VSP_000475, VSP_000478, VSP_000479;\n" +
		        "Name=4; Synonyms=B;\n" +
		        "  IsoId=Q9V8R9-4; Sequence=VSP_000476, VSP_000477, VSP_000479;\n" +
		        "Name=5;\n" +
		        "  IsoId=Q9V8R9-5; Sequence=VSP_000474, VSP_000478;\n" +
		        "  Note=No experimental confirmation available. {ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1};"
		        + " Another no experimental confirmation also available. {ECO:0000269|PubMed:1043355,"
		        + " ECO:0000313|EMBL:BAG16761.1};\n" +
		        "Name=6; Synonyms=D;\n" +
		        "  IsoId=Q9V8R9-6; Sequence=Described;\n" +
		        "  Note=No experimental confirmation.;";
		CcLineFormater formater  =new CcLineFormater();
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		String lines = formater.format(ccLineStringEvidence);
		System.out.println(lines);
		CcLineObject obj = parser.parse(lines);
		assertNotNull(obj);
		
		
	}
	
	@Test
	public void testNoHeader() {
		String ccLineString ="ALTERNATIVE PRODUCTS:\n" +
		        "Event=Alternative splicing; Named isoforms=6;\n" +
				"Comment=Additional isoforms seem to exist.;\n" +
		        "Name=1; Synonyms=A;\n" +
		        "IsoId=Q9V8R9-1; Sequence=Displayed;\n" +
		        "Note=Does not exhibit APOBEC1 complementation activity. Ref.4 sequence is"
		        + " in conflict in positions: 33:I->T. No experimental confirmation available.;\n" +
		        "Name=2;\n" +
		        "IsoId=Q9V8R9-2; Sequence=VSP_000476, VSP_000477, VSP_000479, VSP_000480, VSP_000481;\n" +
		        "Name=Bim-alpha3; Synonyms=BCL2-like 11 transcript variant 10, Bim-AD, BimAD;\n" +
		        "IsoId=Q9V8R9-3; Sequence=VSP_000475, VSP_000478, VSP_000479;\n" +
		        "Name=4; Synonyms=B;\n" +
		        "IsoId=Q9V8R9-4; Sequence=VSP_000476, VSP_000477, VSP_000479;\n" +
		        "Name=5;\n" +
		        "IsoId=Q9V8R9-5; Sequence=VSP_000474, VSP_000478;\n" +
		        "Note=No experimental confirmation available.;\n" +
		        "Name=6; Synonyms=D;\n" +
		        "IsoId=Q9V8R9-6; Sequence=Described;\n" +
		        "Note=No experimental confirmation available.;";
		CcLineFormater formater  =new CcLineFormater();
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		String lines = formater.format(ccLineString);
		CcLineObject obj = parser.parse(lines);
		assertNotNull(obj);
		
		
	}
	
	@Test
	public void testNoHeaderWithEvidence2() {
		String ccLineStringEvidence ="ALTERNATIVE PRODUCTS:\n" +
		        "Event=Alternative splicing; Named isoforms=6;\n" +
		        "Comment=Additional isoforms seem to exist. {ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6};"
		        + " Another additional isoforms also seem to exist. {ECO:0000269|PubMed:10433554};\n" +
		        "Name=1 {ECO:0000313|EMBL:BAG16761.1, ECO:0000313|PDB:3OW2}; Synonyms=A {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|PDB:3OW2};\n" +
		        "IsoId=Q9V8R9-1; Sequence=Displayed;\n" +
		        "Note=Does not exhibit APOBEC1 complementation activity. Ref.4 sequence is in conflict in positions: 33:I->T."
		        + " No experimental confirmation available. {ECO:0000313|PDB:3OW2};\n" +
		        "Name=2;\n" +
		        "IsoId=Q9V8R9-2; Sequence=VSP_000476, VSP_000477, VSP_000479, VSP_000480, VSP_000481;\n" +
		        "Name=Bim-alpha3 {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|PDB:3OW2}; Synonyms=BCL2-like 11 transcript"
		        + " variant 10 {ECO:0000313|EMBL:BAG16761.1}, Bim-AD, BimAD {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|PDB:3OW2};\n" +
		        "IsoId=Q9V8R9-3; Sequence=VSP_000475, VSP_000478, VSP_000479;\n" +
		        "Name=4; Synonyms=B;\n" +
		        "IsoId=Q9V8R9-4; Sequence=VSP_000476, VSP_000477, VSP_000479;\n" +
		        "Name=5;\n" +
		        "IsoId=Q9V8R9-5; Sequence=VSP_000474, VSP_000478;\n" +
		        "Note=No experimental confirmation available. {ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1};"
		        + " Another no experimental confirmation also available. {ECO:0000269|PubMed:1043355, ECO:0000313|EMBL:BAG16761.1};\n" +
		        "Name=6; Synonyms=D;\n" +
		        "IsoId=Q9V8R9-6; Sequence=Described;\n" +
		        "Note=No experimental confirmation.;";
		CcLineFormater formater  =new CcLineFormater();
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		String lines = formater.format(ccLineStringEvidence);
		CcLineObject obj = parser.parse(lines);
		assertNotNull(obj);
		
		
	}
	@Test
	public void testNoHeaderWithEvidence4() {
		String ccLineStringEvidence ="ALTERNATIVE PRODUCTS:\n" +
		        "Event=Alternative splicing; Named isoforms=6;\n" +
		        "Comment=Additional isoforms seem to exist. {ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6};"
		        + " Another additional isoforms also seem to exist. {ECO:0000269|PubMed:10433554};\n" +
		        "Name=1 {ECO:0000313|EMBL:BAG16761.1, ECO:0000313|PDB:3OW2}; Synonyms=A {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|PDB:3OW2};\n" +
		        "IsoId=Q9V8R9-1; Sequence=Displayed;\n" +
		        "Note=Does not exhibit APOBEC1 complementation activity. Ref.4 sequence is in conflict in positions: 33:I->T."
		        + " No experimental confirmation available. {ECO:0000313|PDB:3OW2};\n" +
		        "Name=2;\n" +
		        "IsoId=Q9V8R9-2; Sequence=VSP_000476, VSP_000477, VSP_000479, VSP_000480, VSP_000481;\n" +
		        "Name=Bim-alpha3 {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|PDB:3OW2}; Synonyms=BCL2-like 11 transcript"
		        + " variant 10 {ECO:0000313|EMBL:BAG16761.1}, Bim-AD, BimAD {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|PDB:3OW2};\n" +
		        "IsoId=Q9V8R9-3; Sequence=VSP_000475, VSP_000478, VSP_000479;\n" +
		        "Name=4; Synonyms=B;\n" +
		        "IsoId=Q9V8R9-4; Sequence=VSP_000476, VSP_000477,\n"
		        + "VSP_000479;\n" +
		        "Name=5;\n" +
		        "IsoId=Q9V8R9-5; Sequence=VSP_000474, VSP_000478;\n" +
		        "Note=No experimental confirmation available. {ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1};"
		        + " Another no experimental confirmation also available. {ECO:0000269|PubMed:1043355, ECO:0000313|EMBL:BAG16761.1};\n" +
		        "Name=6; Synonyms=D;\n" +
		        "IsoId=Q9V8R9-6; Sequence=Described;\n" +
		        "Note=No experimental confirmation.;";
		CcLineFormater formater  =new CcLineFormater();
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		String lines = formater.format(ccLineStringEvidence);
		CcLineObject obj = parser.parse(lines);
		assertNotNull(obj);
		
		
	}
	
	@Test
	public void testNoHeaderWithEvidence5() {
	String ccLineStringEvidence =   "ALTERNATIVE PRODUCTS:\n"
			  +"Event=Alternative splicing; Named isoforms=6;\n"
			  +" Comment=Additional isoforms seem to exist.\n"
          +"{ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6};\n"
          +"Name=1 {ECO:0000313|EMBL:BAG16761.1}; Synonyms=A\n"
          +"{ECO:0000256|HAMAP-Rule:MF_002051, ECO:0000313|PDB:3OW2};\n"
          +"IsoId=Q9V8R9-1; Sequence=Displayed;\n"
          +"Note=Does not exhibit APOBEC1 complementation activity. Ref.4\n"
          +"sequence is in conflict in positions: 33:I->T. No experimental\n"
          +"confirmation available. {ECO:0000313|PDB:3OW2};\n"
          +"Name=2;\n"
          +" IsoId=Q9V8R9-2; Sequence=VSP_000476, VSP_000477, VSP_000479,\n"
          +"VSP_000480, VSP_000481;\n"
          +"Name=Bim-alpha3 {ECO:0000256|HAMAP-Rule:MF_00205,\n"
          +"ECO:0000313|PDB:3OW2}; Synonyms=BCL2-like 11 transcript variant 10\n"
          +"{ECO:0000313|EMBL:BAG16761.1}, Bim-AD\n"
          +"{ECO:0000256|HAMAP-Rule:MF_00205}, BimAD {ECO:0000313|PDB:3OW2};\n"
          +"IsoId=Q9V8R9-3; Sequence=VSP_000475, VSP_000478, VSP_000479;\n"
          +"Name=4; Synonyms=B;\n"
          +"IsoId=Q9V8R9-4; Sequence=VSP_000476, VSP_000477, VSP_000479;\n"
          +"Name=5;\n"
          +"IsoId=Q9V8R9-5; Sequence=VSP_000474, VSP_000478;\n"
          +"Note=No experimental confirmation available.\n"
          +"{ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1};\n"
          +"Name=6; Synonyms=D;\n"
          +"IsoId=Q9V8R9-6; Sequence=Described;\n"
          +"Note=No experimental confirmation.;\n";
		CcLineFormater formater  =new CcLineFormater();
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		String lines = formater.format(ccLineStringEvidence);
		CcLineObject obj = parser.parse(lines);
		assertNotNull(obj);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof AlternativeProducts);
		AlternativeProducts ap = (AlternativeProducts)cc.object;
		assertEquals(1, ap.events.size());
		assertEquals("Alternative splicing", ap.events.get(0));
		assertEquals(1, ap.comment.size());
		assertEquals("Additional isoforms seem to exist.", ap.comment.get(0).value);
		assertEquals("ECO:0000269|PubMed:10433554", ap.comment.get(0).evidences.get(0));
		assertEquals("ECO:0000303|Ref.6", ap.comment.get(0).evidences.get(1));
		assertEquals(6, ap.names.size());
		assertEquals("1", ap.names.get(0).name.value);
		assertNotNull(ap.names.get(0).name.evidences);
		assertEquals(1, ap.names.get(0).name.evidences.size());
		assertEquals("ECO:0000313|EMBL:BAG16761.1", ap.names.get(0).name.evidences.get(0));
		assertEquals(1, ap.names.get(0).synNames.size());
		assertEquals("A",  ap.names.get(0).synNames.get(0).value);
		assertEquals("ECO:0000256|HAMAP-Rule:MF_002051", ap.names.get(0).synNames.get(0).evidences.get(0));
		assertEquals("ECO:0000313|PDB:3OW2", ap.names.get(0).synNames.get(0).evidences.get(1));
		assertEquals("Does not exhibit APOBEC1 complementation activity. Ref.4 sequence is in conflict in positions: 33:I->T. No experimental confirmation available.", ap.names.get(0).note.get(0).value);
		assertEquals("ECO:0000313|PDB:3OW2", ap.names.get(0).note.get(0).evidences.get(0));
		
		
		assertEquals("Bim-alpha3", ap.names.get(2).name.value);
		assertNotNull(ap.names.get(2).name.evidences);
		assertEquals(2, ap.names.get(2).name.evidences.size());
		assertEquals("ECO:0000256|HAMAP-Rule:MF_00205", ap.names.get(2).name.evidences.get(0));
		assertEquals("ECO:0000313|PDB:3OW2", ap.names.get(2).name.evidences.get(1));
		assertEquals(3, ap.names.get(2).synNames.size());
		assertEquals("BCL2-like 11 transcript variant 10",  ap.names.get(2).synNames.get(0).value);
		assertEquals("ECO:0000313|EMBL:BAG16761.1", ap.names.get(2).synNames.get(0).evidences.get(0));
		
		assertEquals("Bim-AD",  ap.names.get(2).synNames.get(1).value);
		assertEquals("ECO:0000256|HAMAP-Rule:MF_00205", ap.names.get(2).synNames.get(1).evidences.get(0));
		
		assertEquals("BimAD",  ap.names.get(2).synNames.get(2).value);
		assertEquals("ECO:0000313|PDB:3OW2", ap.names.get(2).synNames.get(2).evidences.get(0));
		

		assertEquals("No experimental confirmation available.", ap.names.get(4).note.get(0).value);
		assertEquals("ECO:0000269|PubMed:10433554", ap.names.get(4).note.get(0).evidences.get(0));
		
		
	}
	
	
}
