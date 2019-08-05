package org.uniprot.core.flatfile.writer.line.cc;

import org.junit.Test;
import org.uniprot.core.flatfile.parser.impl.cc.CCSequenceCautionCommentLineBuilder;
import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.uniprot.comment.SequenceCautionComment;
import org.uniprot.core.uniprot.comment.SequenceCautionType;
import org.uniprot.core.uniprot.comment.builder.SequenceCautionCommentBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class CCSequenceCautionBuildTest extends CCBuildTestAbstr {
	CCSequenceCautionCommentLineBuilder builder = new CCSequenceCautionCommentLineBuilder();
	@Test
	public void testSequenceCauction() {
		String ccLine =
				("CC   -!- SEQUENCE CAUTION:\n" +
						"CC       Sequence=CAA57511.1; Type=Frameshift; Positions=421, 589, 591; Note=The predicted gene.;");
		String ccLineString =
				(	"SEQUENCE CAUTION:\n" +
						"Sequence=CAA57511.1; Type=Frameshift; Positions=421, 589, 591; Note=The predicted gene.;");
		String sequence ="CAA57511.1";
		String note = "The predicted gene.";
		List<String> evs = new ArrayList<>();
		List<String> positions = new ArrayList<>();
		positions.add("421");
		positions.add("589");
		positions.add("591");
		
		SequenceCautionComment comment = buildComment(
				sequence,
				SequenceCautionType.FRAMESHIFT,
				positions,
				note,
				evs
				);
		doTest(ccLine, comment);	
		doTestString(ccLineString, comment);	
	}
	
	@Test
	public void testSequenceCauctionWithEvidence() {
		String ccLine =
				(
						"CC   -!- SEQUENCE CAUTION:\n" +
						"CC       Sequence=CAA57511.1; Type=Frameshift; Positions=421, 589, 591; Note=The predicted gene.; Evidence={ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|Ensembl:ENSP00000409133};");
		String ccLineString =
				(
						"SEQUENCE CAUTION:\n" +
						"Sequence=CAA57511.1; Type=Frameshift; Positions=421, 589, 591; Note=The predicted gene.;");
		String ccLineStringEvidence =
				(
						"SEQUENCE CAUTION:\n" +
						"Sequence=CAA57511.1; Type=Frameshift; Positions=421, 589, 591; Note=The predicted gene.; Evidence={ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|Ensembl:ENSP00000409133};");
		
		String sequence ="CAA57511.1";
		String note = "The predicted gene.";
		List<String> evs = new ArrayList<>();
		evs.add("ECO:0000256|HAMAP-Rule:MF_00205");
		evs.add("ECO:0000313|Ensembl:ENSP00000409133");
		List<String> positions = new ArrayList<>();
		positions.add("421");
		positions.add("589");
		positions.add("591");
		
		SequenceCautionComment comment = buildComment(
				sequence,
				SequenceCautionType.FRAMESHIFT,
				positions,
				note,
				evs
				);
		doTest(ccLine, comment);
		doTestString(ccLineString, comment);	
		doTestStringEv(ccLineStringEvidence, comment);
	}
	
	
	@Test
	public void testSequenceCauction2() {
		String ccLine =
				("CC   -!- SEQUENCE CAUTION:\n" +
						"CC       Sequence=CAA57511.1; Type=Erroneous gene model prediction; Note=The predicted gene.;");
		String sequence ="CAA57511.1";
		String note = "The predicted gene.";
		List<String> evs = new ArrayList<>();
		List<String> positions = new ArrayList<>();
	//	positions.add("421");
	//	positions.add("589");
	//	positions.add("591");
		
		SequenceCautionComment comment = buildComment(
				sequence,
				SequenceCautionType.ERRONEOUS_PREDICTION,
				positions,
				note,
				evs
				);
		doTest(ccLine, comment);	
	}
	
	@Test
	public void testSequenceCauction2WithEvidence() {
		String ccLine =
				("CC   -!- SEQUENCE CAUTION:\n" +
						"CC       Sequence=CAA57511.1; Type=Erroneous gene model prediction; Note=The predicted gene.; Evidence={ECO:0000256|HAMAP-Rule:MF_00205};");
		String ccLineStringEvidence =
				(	"SEQUENCE CAUTION:\n" +
						"Sequence=CAA57511.1; Type=Erroneous gene model prediction; Note=The predicted gene.; Evidence={ECO:0000256|HAMAP-Rule:MF_00205};");
		String ccLineString =
				(	"SEQUENCE CAUTION:\n" +
						"Sequence=CAA57511.1; Type=Erroneous gene model prediction; Note=The predicted gene.;");
	
		String sequence ="CAA57511.1";
		String note = "The predicted gene.";
		List<String> evs = new ArrayList<>();
		evs.add("ECO:0000256|HAMAP-Rule:MF_00205");
		List<String> positions = new ArrayList<>();
	//	positions.add("421");
	//	positions.add("589");
	//	positions.add("591");
		
		SequenceCautionComment comment = buildComment(
				sequence,
				SequenceCautionType.ERRONEOUS_PREDICTION,
				positions,
				note,
				evs
				);
		doTest(ccLine, comment);
		doTestString(ccLineString, comment);	
		//doTestStringEv(ccLineStringEvidence, comment);
	}
	
	

	SequenceCautionComment buildComment(String sequence,
			SequenceCautionType type, List<String> positions, String note,
			List<String> evs){
		
		SequenceCautionCommentBuilder builder = new SequenceCautionCommentBuilder();
		
		builder.sequenceCautionType(type)
		.sequence(sequence)
		.note(note)
		.positions(positions)
		.evidences(createEvidence(evs));
		
		return builder.build();

	}
	private void doTest(String ccLine, SequenceCautionComment comment){
		FFLine ffLine = builder.buildWithEvidence(comment);
		String resultString = ffLine.toString();
		System.out.println(resultString);
		System.out.println(ccLine);
		assertEquals(ccLine, resultString);
	}
	private void doTestString(String ccLine, SequenceCautionComment comment){
		String value = builder.buildString(comment);
		
		System.out.println(value);
		assertEquals(ccLine, value);
	}
	
	private void doTestStringEv(String ccLine, SequenceCautionComment comment){
		String value = builder.buildStringWithEvidence(comment);
		
		System.out.println(value);
		assertEquals(ccLine, value);
	}
	
}
