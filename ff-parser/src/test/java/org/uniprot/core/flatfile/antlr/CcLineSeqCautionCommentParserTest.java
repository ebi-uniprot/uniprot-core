package org.uniprot.core.flatfile.antlr;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineConverter;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineFormater;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineObject;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineObject.SequenceCautionObject;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineObject.SequenceCautionType;
import org.uniprot.core.uniprot.comment.Comment;
import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.SequenceCautionComment;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CcLineSeqCautionCommentParserTest {
	@Test
	public void test1() {
		String lines = "CC   -!- SEQUENCE CAUTION:\n"
				+"CC       Sequence=CAI24940.1; Type=Erroneous gene model prediction;\n"
				;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.SequenceCaution);
		CcLineObject.SequenceCaution sc = (CcLineObject.SequenceCaution) cc.object;
		assertEquals(1, sc.sequenceCautionObjects.size());
		verify(sc.sequenceCautionObjects.get(0), "CAI24940.1", 0,0, SequenceCautionType.ERRONEOUS_GENE_MODEL_PREDICTION, null);


	}
	private void verify(SequenceCautionObject obj, String seq, int np, int position, SequenceCautionType type, String note) {
		assertEquals(seq, obj.sequence);
	
		assertEquals(type, obj.type);
		assertEquals(np, obj.positions.size());
		if(np>0) {
			assertEquals(position, obj.positions.get(0).intValue());
		}
		assertEquals(note, obj.note);
	}
	
	private void verify(SequenceCautionObject obj, String seq, String positionValue, SequenceCautionType type, String note) {
		assertEquals(seq, obj.sequence);
	
		assertEquals(type, obj.type);
		assertEquals(positionValue, obj.positionValue);
	
		assertEquals(note, obj.note);
	}
	
	@Test
	public void testAllFields() {
		String lines = "CC   -!- SEQUENCE CAUTION:\n"
				+"CC       Sequence=AAG34697.1; Type=Erroneous termination; Positions=388; Note=Translated as Ser;\n"
				;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.SequenceCaution);
		CcLineObject.SequenceCaution sc = (CcLineObject.SequenceCaution) cc.object;
		assertEquals(1, sc.sequenceCautionObjects.size());
		verify(sc.sequenceCautionObjects.get(0), "AAG34697.1", 1, 388, SequenceCautionType.ERRONEOUS_TERMINATION ,"Translated as Ser" );
		
	}
	@Test
	public void test2() {
		String lines = "CC   -!- SEQUENCE CAUTION:\n"
				+"CC       Sequence=CAI12537.1; Type=Erroneous gene model prediction;\n"
				+"CC       Sequence=CAI39742.1; Type=Erroneous gene model prediction; Positions=388, 399;\n"
				;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.SequenceCaution);
		CcLineObject.SequenceCaution sc = (CcLineObject.SequenceCaution) cc.object;
		assertEquals(2, sc.sequenceCautionObjects.size());
		verify(sc.sequenceCautionObjects.get(0), "CAI12537.1", 0, 0, SequenceCautionType.ERRONEOUS_GENE_MODEL_PREDICTION , null );
		verify(sc.sequenceCautionObjects.get(1), "CAI39742.1", 2, 388, SequenceCautionType.ERRONEOUS_GENE_MODEL_PREDICTION , null );
		
	}
	@Test
	public void test3() {
		String lines = "CC   -!- SEQUENCE CAUTION:\n"
				+"CC       Sequence=AAA25676.1; Type=Frameshift; Positions=Several;\n"
				+"CC       Sequence=CAD59919.1; Type=Frameshift; Positions=519;\n"
				;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.SequenceCaution);
		CcLineObject.SequenceCaution sc = (CcLineObject.SequenceCaution) cc.object;
		assertEquals(2, sc.sequenceCautionObjects.size());
		verify(sc.sequenceCautionObjects.get(0), "AAA25676.1", 0, 0, SequenceCautionType.FRAMESHIFT , null );
		assertEquals("Several",sc.sequenceCautionObjects.get(0).positionValue );
		verify(sc.sequenceCautionObjects.get(1), "CAD59919.1", 1, 519, SequenceCautionType.FRAMESHIFT , null );
		
	}
	
	@Test
	public void test4() {
		String lines = "CC   -!- SEQUENCE CAUTION:\n"
				+"CC       Sequence=AAA85813.1; Type=Frameshift; Positions=134; Note=Frameshift correction allows the C-terminal sequence to be compatible with the results of mass spectrometry and X-ray crystallography;\n"
				;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.SequenceCaution);
		CcLineObject.SequenceCaution sc = (CcLineObject.SequenceCaution) cc.object;
		assertEquals(1, sc.sequenceCautionObjects.size());
		verify(sc.sequenceCautionObjects.get(0), "AAA85813.1", 1, 134, SequenceCautionType.FRAMESHIFT , "Frameshift correction allows the C-terminal sequence to be compatible with the results of mass spectrometry and X-ray crystallography" );

	}
	@Test
	public void test5() {
		String lines = "CC   -!- SEQUENCE CAUTION:\n"
				+"CC       Sequence=CAA57511.1; Type=Frameshift; Positions=421, 589, 591; Note=The predicted gene.; Evidence={ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|Ensembl:ENSP00000409133};\n"
				;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.SequenceCaution);
		CcLineObject.SequenceCaution sc = (CcLineObject.SequenceCaution) cc.object;
		assertEquals(1, sc.sequenceCautionObjects.size());
		verify(sc.sequenceCautionObjects.get(0), "CAA57511.1", 3, 421, SequenceCautionType.FRAMESHIFT , "The predicted gene." );
		assertEquals("ECO:0000256|HAMAP-Rule:MF_00205", obj.evidenceInfo.evidences.get(sc.sequenceCautionObjects.get(0)).get(0));
		assertEquals("ECO:0000313|Ensembl:ENSP00000409133", obj.evidenceInfo.evidences.get(sc.sequenceCautionObjects.get(0)).get(1));
		
	}
	
	@Test
	public void testNoHeader() {
		String ccLineString = "SEQUENCE CAUTION:\n"
				+"Sequence=CAA57511.1; Type=Frameshift; Positions=421, 589, 591; Note=The predicted gene.; Evidence={ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|Ensembl:ENSP00000409133};\n"
				;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineFormater formater  =new CcLineFormater();
		String lines = formater.format(ccLineString);
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.SequenceCaution);
		CcLineObject.SequenceCaution sc = (CcLineObject.SequenceCaution) cc.object;
		assertEquals(1, sc.sequenceCautionObjects.size());
		verify(sc.sequenceCautionObjects.get(0), "CAA57511.1", 3, 421, SequenceCautionType.FRAMESHIFT , "The predicted gene." );
		assertEquals("ECO:0000256|HAMAP-Rule:MF_00205", obj.evidenceInfo.evidences.get(sc.sequenceCautionObjects.get(0)).get(0));
		assertEquals("ECO:0000313|Ensembl:ENSP00000409133", obj.evidenceInfo.evidences.get(sc.sequenceCautionObjects.get(0)).get(1));
		
	}
	
	@Test
	public void testPositionSeveral() {
		String lines = "CC   -!- SEQUENCE CAUTION:\n" + 
				"CC       Sequence=CAA39814.1; Type=Frameshift; Positions=Several; Evidence={ECO:0000305};\n"
				;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		assertTrue(cc.object instanceof CcLineObject.SequenceCaution);
		CcLineObject.SequenceCaution sc = (CcLineObject.SequenceCaution) cc.object;
		assertEquals(1, sc.sequenceCautionObjects.size());
		verify(sc.sequenceCautionObjects.get(0), "CAA39814.1", "Several", SequenceCautionType.FRAMESHIFT,  null);
		 CcLineConverter converter = new CcLineConverter(new HashMap<>(), new HashMap<>(),true);
		 List<Comment> comments =converter.convert(obj);
		 assertEquals(1, comments.size());
		 assertEquals(CommentType.SEQUENCE_CAUTION, comments.get(0).getCommentType());
		 SequenceCautionComment comment = (SequenceCautionComment) comments.get(0);
		 assertEquals("Several", comment.getPositions().get(0));
		 assertEquals("CAA39814.1", comment.getSequence());
		 assertEquals(org.uniprot.core.uniprot.comment.SequenceCautionType.FRAMESHIFT, comment.getSequenceCautionType());
	}
	
	
}
