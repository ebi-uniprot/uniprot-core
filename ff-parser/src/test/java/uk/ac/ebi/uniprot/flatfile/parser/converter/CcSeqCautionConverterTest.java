package uk.ac.ebi.uniprot.flatfile.parser.converter;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Comment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SequenceCautionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SequenceCautionType;
import uk.ac.ebi.uniprot.flatfile.parser.impl.cc.CcLineConverter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.cc.CcLineObject;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CcSeqCautionConverterTest {
	private final CcLineConverter converter = new CcLineConverter(null);
	
	@Test
	public void testSequenceCaution(){
		//CC   -!- SEQUENCE CAUTION:
        //CC       Sequence=CAI12537.1; Type=Erroneous gene model prediction;
       //CC       Sequence=CAI39742.1; Type=Erroneous gene model prediction; Positions=388, 399;

		CcLineObject ccLineO = new CcLineObject();	
		CcLineObject.CC cc1 =new CcLineObject.CC();
		cc1.topic =CcLineObject.CCTopicEnum.SEQUENCE_CAUTION;
		CcLineObject.SequenceCaution sc =new CcLineObject.SequenceCaution();
		CcLineObject.SequenceCautionObject sco1 =new CcLineObject.SequenceCautionObject();
		sco1.sequence ="CAI12537.1";
		sco1.type = CcLineObject.SequenceCautionType.ERRONEOUS_GENE_MODEL_PREDICTION;
		sc.sequenceCautionObjects.add(sco1);
		
		CcLineObject.SequenceCautionObject sco2 =new CcLineObject.SequenceCautionObject();
		sco2.sequence ="CAI39742.1";
		sco2.type = CcLineObject.SequenceCautionType.ERRONEOUS_GENE_MODEL_PREDICTION;
		sco2.positions.add(388);
		sco2.positions.add(399);
		sc.sequenceCautionObjects.add(sco2);
		
		cc1.object =sc;
		ccLineO.ccs.add(cc1);
	
		List<Comment> comments = converter.convert(ccLineO) ;
		assertEquals(2, comments.size());
		
		Comment comment1 =comments.get(0);
		assertEquals(CommentType.SEQUENCE_CAUTION, comment1.getCommentType());
		assertTrue (comment1 instanceof SequenceCautionComment);
		
		SequenceCautionComment wcomment = (SequenceCautionComment) comment1;
		
		Comment comment2 =comments.get(1);
		assertEquals(CommentType.SEQUENCE_CAUTION, comment2.getCommentType());
		assertTrue (comment2 instanceof SequenceCautionComment);
		
		SequenceCautionComment wcomment2 = (SequenceCautionComment) comment2;
		
		assertEquals(SequenceCautionType.ERRONEOUS_PREDICTION, wcomment.getSequenceCautionType());
		assertEquals(0, wcomment.getPositions().size());
		assertEquals("CAI12537.1", wcomment.getSequence());
		
		
		assertEquals(SequenceCautionType.ERRONEOUS_PREDICTION, wcomment2.getSequenceCautionType());
		assertEquals(2, wcomment2.getPositions().size());
		assertEquals("CAI39742.1", wcomment2.getSequence());
		assertEquals("388", wcomment2.getPositions().get(0) );
		assertEquals("399", wcomment2.getPositions().get(1) );
		
		
	}
}
