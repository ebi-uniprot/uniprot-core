package uk.ac.ebi.uniprot.xmlparser.uniprot.comment;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SequenceCautionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SequenceCautionType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.SequenceCautionCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xmlparser.uniprot.UniProtXmlTestHelper;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SCCommentConverterTest {

	@Test
	void testSeveral() {
		SequenceCautionCommentBuilder builder = SequenceCautionCommentBuilder.newInstance();
		Evidence evidence = UniProtFactory.INSTANCE.createEvidence("ECO:0000305");
		builder.sequence("AAA40939.1")
		.sequenceCautionType(SequenceCautionType.FRAMESHIFT)
		.positions(Arrays.asList("Several"))
		.evidences(Arrays.asList(evidence));
		SequenceCautionComment comment = builder.build();
		SCCommentConverter converter = new SCCommentConverter(new EvidenceIndexMapper());
		CommentType xmlComment = converter.toXml(comment);
		System.out.println(UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
		SequenceCautionComment converted = converter.fromXml(xmlComment);
		assertEquals(comment, converted);
	}

	@Test
	void testPosition() {
		SequenceCautionCommentBuilder builder = SequenceCautionCommentBuilder.newInstance();
		Evidence evidence = UniProtFactory.INSTANCE.createEvidence("ECO:0000305");
		builder.sequence("AAA40939.1")
		.sequenceCautionType(SequenceCautionType.FRAMESHIFT)
		.positions(Arrays.asList("535"))
		.evidences(Arrays.asList(evidence));
		SequenceCautionComment comment = builder.build();
		SCCommentConverter converter = new SCCommentConverter(new EvidenceIndexMapper());
		CommentType xmlComment = converter.toXml(comment);
		System.out.println(UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
		SequenceCautionComment converted = converter.fromXml(xmlComment);
		assertEquals(comment, converted);
	}

	
	@Test
	void testNote() {
	//	 Sequence=AAF87955.1; Type=Erroneous initiation; Note=Translation N-terminally extended.; Evidence={ECO:0000305};
		SequenceCautionCommentBuilder builder = SequenceCautionCommentBuilder.newInstance();
		Evidence evidence = UniProtFactory.INSTANCE.createEvidence("ECO:0000305");
		builder.sequence("AAF87955.1")
		.sequenceCautionType(SequenceCautionType.ERRONEOUS_INITIATION)
		.note("Translation N-terminally extended.")
		.evidences(Arrays.asList(evidence));
		SequenceCautionComment comment = builder.build();
		SCCommentConverter converter = new SCCommentConverter(new EvidenceIndexMapper());
		CommentType xmlComment = converter.toXml(comment);
		System.out.println(UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
		SequenceCautionComment converted = converter.fromXml(xmlComment);
		assertEquals(comment, converted);
	}
}
