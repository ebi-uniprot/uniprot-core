package org.uniprot.core.flatfile.parser.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineConverter;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.CC;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.CcLineObject;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.SequenceCaution;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.SequenceCautionObject;
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
        CC cc1 = new CC();
        cc1.setTopic(CcLineObject.CCTopicEnum.SEQUENCE_CAUTION);
        SequenceCaution sc = new SequenceCaution();
        SequenceCautionObject sco1 = new SequenceCautionObject();
        sco1.setSequence("CAI12537.1");
        sco1.setType(CcLineObject.SequenceCautionType.ERRONEOUS_GENE_MODEL_PREDICTION);
        sc.getSequenceCautionObjects().add(sco1);

        SequenceCautionObject sco2 = new SequenceCautionObject();
        sco2.setSequence("CAI39742.1");
        sco2.setType(CcLineObject.SequenceCautionType.ERRONEOUS_GENE_MODEL_PREDICTION);

        sc.getSequenceCautionObjects().add(sco2);

        cc1.setObject(sc);
        ccLineO.getCcs().add(cc1);

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
