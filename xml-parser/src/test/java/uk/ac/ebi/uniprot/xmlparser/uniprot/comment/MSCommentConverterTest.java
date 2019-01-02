package uk.ac.ebi.uniprot.xmlparser.uniprot.comment;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.Range;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MassSpectrometryComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MassSpectrometryMethod;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MassSpectrometryRange;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.MassSpectrometryCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.LocationType;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xmlparser.uniprot.UniProtXmlTestHelper;

class MSCommentConverterTest {

	@Test
	void testRangeConverter() {
		 MSRangeConverter converter = new MSRangeConverter();
		 Range range =new Range(123, 234);
		 String isoformId ="Some id";
		 MassSpectrometryRange msRange = MassSpectrometryCommentBuilder.createMassSpectrometryRange(range, isoformId);
		 LocationType type = converter.toXml(msRange);
		 System.out.println(UniProtXmlTestHelper.toXmlString(type, LocationType.class, "location"));
		 MassSpectrometryRange converted = converter.fromXml(type);
		 assertEquals(msRange, converted);

	}
	
	@Test
	void test() {
		List<MassSpectrometryRange> ranges = new ArrayList<>();
		Range range = new Range(26, 54);
		Evidence evidence = UniProtFactory.INSTANCE.createEvidence("ECO:0000269|PubMed:22887697");
		String isoformId = "";
		MassSpectrometryRange msRange = MassSpectrometryCommentBuilder.createMassSpectrometryRange(range, isoformId);
		ranges.add(msRange);
		MassSpectrometryCommentBuilder builder = MassSpectrometryCommentBuilder.newInstance();
		MassSpectrometryComment comment = builder.molWeight(3042.79f)
				.massSpectrometryMethod(MassSpectrometryMethod.ELECTROSPRAY).massSpectrometryRanges(ranges)
				.note("Monoisotopic mass.").evidences(Arrays.asList(evidence)).build();

		MSCommentConverter converter = new MSCommentConverter(new EvidenceIndexMapper());
		CommentType xmlComment = converter.toXml(comment);
		System.out.println(UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
		MassSpectrometryComment converted = converter.fromXml(xmlComment);
		assertEquals(comment, converted);
	}

	@Test
	void testWithError() {
		List<MassSpectrometryRange> ranges = new ArrayList<>();
		Range range = new Range(26, 54);
		Evidence evidence = UniProtFactory.INSTANCE.createEvidence("ECO:0000269|PubMed:22887697");
		String isoformId = "";
		MassSpectrometryRange msRange = MassSpectrometryCommentBuilder.createMassSpectrometryRange(range, isoformId);
		ranges.add(msRange);
		MassSpectrometryCommentBuilder builder = MassSpectrometryCommentBuilder.newInstance();
		MassSpectrometryComment comment = builder.molWeight(3042.79f)
				.molWeightError(0.023f)
				.massSpectrometryMethod(MassSpectrometryMethod.ELECTROSPRAY).massSpectrometryRanges(ranges)
				.note("Monoisotopic mass.").evidences(Arrays.asList(evidence)).build();

		MSCommentConverter converter = new MSCommentConverter(new EvidenceIndexMapper());
		CommentType xmlComment = converter.toXml(comment);
		System.out.println(UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
		MassSpectrometryComment converted = converter.fromXml(xmlComment);
		assertEquals(comment, converted);
	}

}
