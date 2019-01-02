package uk.ac.ebi.uniprot.xmlparser.uniprot.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.RnaEdPosition;
import uk.ac.ebi.uniprot.domain.uniprot.comment.RnaEditingComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.RnaEditingCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.CommentFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.LocationType;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xmlparser.uniprot.UniProtXmlTestHelper;

class RnaEditingCommentConverterTest {

	@Test
	void testPositionConverter() {
		//Modified_positions=320 {ECO:0000269|PubMed:10574461, ECO:0000269|PubMed:11230166
		String pos = "320";
		RnaEdPosition position =
		RnaEditingCommentBuilder.createPosition(pos,
				Arrays.asList(UniProtFactory.INSTANCE.createEvidence("ECO:0000269|PubMed:10574461"),
						UniProtFactory.INSTANCE.createEvidence("ECO:0000269|PubMed:11230166")));
		RnaEdPositionConverter converter = new RnaEdPositionConverter(new EvidenceIndexMapper());
		
		LocationType xmlLocation = converter.toXml(position);
		
		 System.out.println(UniProtXmlTestHelper.toXmlString(xmlLocation, LocationType.class, "location"));
		 RnaEdPosition converted = converter.fromXml(xmlLocation);
		 assertEquals(position, converted);
	}
	
	@Test
	void test() {
		String pos = "320";
		RnaEdPosition position =
				RnaEditingCommentBuilder.createPosition(pos,
						Arrays.asList(UniProtFactory.INSTANCE.createEvidence("ECO:0000269|PubMed:10574461"),
								UniProtFactory.INSTANCE.createEvidence("ECO:0000269|PubMed:11230166"),
								UniProtFactory.INSTANCE.createEvidence("ECO:0000269|PubMed:15731336")));
		RnaEditingCommentBuilder builder = RnaEditingCommentBuilder.newInstance();
		builder.locations(Arrays.asList(position));
		builder.note(createNote("Partially edited. Editing appears to be brain-specific",
				Arrays.asList("ECO:0000269|PubMed:15731336")
				));
		RnaEditingComment comment = builder.build();
		RnaEditingCommentConverter converter = new RnaEditingCommentConverter(new EvidenceIndexMapper());
		
		CommentType xmlComment =converter.toXml(comment);
		System.out.println(UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
		RnaEditingComment converted =converter.fromXml(xmlComment);
		assertEquals(comment, converted);
	}
	private Note createNote(String val, List<String> evids) {
        List<EvidencedValue> texts = new ArrayList<>();
        texts.add(
        UniProtFactory.INSTANCE.createEvidencedValue(val, createEvidence(evids)));
        return CommentFactory.INSTANCE.createNote(texts);
    }
	 private List<Evidence> createEvidence(List<String> evids) {
	        List<Evidence> evidences = new ArrayList<>();
	        for (String ev : evids) {
	            evidences.add( UniProtFactory.INSTANCE.createEvidence(ev));
	        }
	        return evidences;
	    }
}
