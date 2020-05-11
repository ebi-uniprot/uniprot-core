package org.uniprot.core.xml.uniprot.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.cv.evidence.EvidenceHelper.parseEvidenceLine;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.SequenceCautionComment;
import org.uniprot.core.uniprotkb.comment.SequenceCautionType;
import org.uniprot.core.uniprotkb.comment.impl.SequenceCautionCommentBuilder;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.xml.jaxb.uniprot.CommentType;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;
import org.uniprot.core.xml.uniprot.UniProtXmlTestHelper;

import java.util.Arrays;

class SCCommentConverterTest {

    @Test
    void testSeveral() {
        SequenceCautionCommentBuilder builder = new SequenceCautionCommentBuilder();
        Evidence evidence = parseEvidenceLine("ECO:0000305");
        builder.sequence("AAA40939.1")
                .sequenceCautionType(SequenceCautionType.FRAMESHIFT)
                .evidencesSet(Arrays.asList(evidence));
        SequenceCautionComment comment = builder.build();
        SCCommentConverter converter = new SCCommentConverter(new EvidenceIndexMapper());
        CommentType xmlComment = converter.toXml(comment);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
        SequenceCautionComment converted = converter.fromXml(xmlComment);
        assertEquals(comment, converted);
    }

    @Test
    void testPosition() {
        SequenceCautionCommentBuilder builder = new SequenceCautionCommentBuilder();
        Evidence evidence = parseEvidenceLine("ECO:0000305");
        builder.sequence("AAA40939.1")
                .sequenceCautionType(SequenceCautionType.FRAMESHIFT)
                .evidencesSet(Arrays.asList(evidence));
        SequenceCautionComment comment = builder.build();
        SCCommentConverter converter = new SCCommentConverter(new EvidenceIndexMapper());
        CommentType xmlComment = converter.toXml(comment);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
        SequenceCautionComment converted = converter.fromXml(xmlComment);
        assertEquals(comment, converted);
    }

    @Test
    void testNote() {
        //	 Sequence=AAF87955.1; Type=Erroneous initiation; Note=Translation N-terminally extended.;
        // Evidence={ECO:0000305};
        SequenceCautionCommentBuilder builder = new SequenceCautionCommentBuilder();
        Evidence evidence = parseEvidenceLine("ECO:0000305");
        builder.sequence("AAF87955.1")
                .sequenceCautionType(SequenceCautionType.ERRONEOUS_INITIATION)
                .note("Translation N-terminally extended.")
                .evidencesSet(Arrays.asList(evidence));
        SequenceCautionComment comment = builder.build();
        SCCommentConverter converter = new SCCommentConverter(new EvidenceIndexMapper());
        CommentType xmlComment = converter.toXml(comment);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
        SequenceCautionComment converted = converter.fromXml(xmlComment);
        assertEquals(comment, converted);
    }
}
