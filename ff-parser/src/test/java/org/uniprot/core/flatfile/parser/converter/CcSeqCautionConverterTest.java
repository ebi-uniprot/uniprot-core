package org.uniprot.core.flatfile.parser.converter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineConverter;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineObject;
import org.uniprot.core.uniprot.comment.Comment;
import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.SequenceCautionComment;
import org.uniprot.core.uniprot.comment.SequenceCautionType;

class CcSeqCautionConverterTest {
    private final CcLineConverter converter = new CcLineConverter(null, null);

    @Test
    void testSequenceCaution() {
        // CC   -!- SEQUENCE CAUTION:
        // CC       Sequence=CAI12537.1; Type=Erroneous gene model prediction;
        // CC       Sequence=CAI39742.1; Type=Erroneous gene model prediction; Positions=388, 399;

        CcLineObject ccLineO = new CcLineObject();
        CcLineObject.CC cc1 = new CcLineObject.CC();
        cc1.topic = CcLineObject.CCTopicEnum.SEQUENCE_CAUTION;
        CcLineObject.SequenceCaution sc = new CcLineObject.SequenceCaution();
        CcLineObject.SequenceCautionObject sco1 = new CcLineObject.SequenceCautionObject();
        sco1.sequence = "CAI12537.1";
        sco1.type = CcLineObject.SequenceCautionType.ERRONEOUS_GENE_MODEL_PREDICTION;
        sc.sequenceCautionObjects.add(sco1);

        CcLineObject.SequenceCautionObject sco2 = new CcLineObject.SequenceCautionObject();
        sco2.sequence = "CAI39742.1";
        sco2.type = CcLineObject.SequenceCautionType.ERRONEOUS_GENE_MODEL_PREDICTION;
      
        sc.sequenceCautionObjects.add(sco2);

        cc1.object = sc;
        ccLineO.ccs.add(cc1);

        List<Comment> comments = converter.convert(ccLineO);
        assertEquals(2, comments.size());

        Comment comment1 = comments.get(0);
        assertEquals(CommentType.SEQUENCE_CAUTION, comment1.getCommentType());
        assertTrue(comment1 instanceof SequenceCautionComment);

        SequenceCautionComment wcomment = (SequenceCautionComment) comment1;

        Comment comment2 = comments.get(1);
        assertEquals(CommentType.SEQUENCE_CAUTION, comment2.getCommentType());
        assertTrue(comment2 instanceof SequenceCautionComment);

        SequenceCautionComment wcomment2 = (SequenceCautionComment) comment2;

        assertEquals(SequenceCautionType.ERRONEOUS_PREDICTION, wcomment.getSequenceCautionType());

        assertEquals("CAI12537.1", wcomment.getSequence());

        assertEquals(SequenceCautionType.ERRONEOUS_PREDICTION, wcomment2.getSequenceCautionType());

        assertEquals("CAI39742.1", wcomment2.getSequence());

    }
}
