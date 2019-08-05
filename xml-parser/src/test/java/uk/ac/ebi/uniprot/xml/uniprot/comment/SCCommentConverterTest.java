package uk.ac.ebi.uniprot.xml.uniprot.comment;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.SequenceCautionComment;
import org.uniprot.core.uniprot.comment.SequenceCautionType;
import org.uniprot.core.uniprot.comment.builder.SequenceCautionCommentBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;

import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;
import uk.ac.ebi.uniprot.xml.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xml.uniprot.UniProtXmlTestHelper;
import uk.ac.ebi.uniprot.xml.uniprot.comment.SCCommentConverter;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.core.uniprot.evidence.impl.EvidenceHelper.parseEvidenceLine;

class SCCommentConverterTest {

	@Test
	void testSeveral() {
		SequenceCautionCommentBuilder builder = new SequenceCautionCommentBuilder();
		Evidence evidence = parseEvidenceLine("ECO:0000305");
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
		SequenceCautionCommentBuilder builder = new SequenceCautionCommentBuilder();
		Evidence evidence = parseEvidenceLine("ECO:0000305");
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
		SequenceCautionCommentBuilder builder = new SequenceCautionCommentBuilder();
		Evidence evidence = parseEvidenceLine("ECO:0000305");
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
