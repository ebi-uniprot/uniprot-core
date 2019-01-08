package uk.ac.ebi.uniprot.xmlparser.uniprot.comment;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocation;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocationComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocationValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.SubcellularLocationCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.CommentFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xmlparser.uniprot.UniProtXmlTestHelper;

class SCLCommentConverterTest {

	@Test
	void test() {
		SubcellularLocationValue location = create("Membrane, caveola",
				Arrays.asList("ECO:0000256|PIRNR:PIRNR037393", "ECO:0000256|RuleBase:RU361271"));
		SubcellularLocationValue topology = create("Lipid-anchor, GPI-like-anchor",
				Arrays.asList("ECO:0000256|PIRNR:PIRNR0373943", "ECO:0000256|RuleBase:RU361271"));

		SubcellularLocationValue orientation = create("Peripheral membrane protein",
				Arrays.asList("ECO:0000256|RuleBase:RU000680", "ECO:0000256|SAAS:SAAS00583323"));
		SubcellularLocation subcelLocation = SubcellularLocationCommentBuilder.createSubcellularLocation(location,
				topology, orientation);

		List<SubcellularLocation> subcelLocations =new ArrayList<>();
		subcelLocations.add(subcelLocation);
		SubcellularLocationValue location2 = create("Cell membrane",
				Arrays.asList("ECO:0000250"));
		SubcellularLocation subcelLocation2 = SubcellularLocationCommentBuilder.createSubcellularLocation(location2,
				null, null);
		subcelLocations.add(subcelLocation2);
		SubcellularLocationCommentBuilder  builder = SubcellularLocationCommentBuilder.newInstance();
		builder.molecule("Some mol")
		.subcellularLocations(subcelLocations)
		.note(createNote("Some note", Arrays.asList("ECO:0000256|RuleBase:RU361271", "ECO:0000256|SAAS:SAAS00583323")));
		SubcellularLocationComment comment = builder.build();
		SCLCommentConverter converter = new SCLCommentConverter(new EvidenceIndexMapper());
		CommentType xmlComment = converter.toXml(comment);
		System.out.println(UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
		
		SubcellularLocationComment converted = converter.fromXml(xmlComment);
		assertEquals(comment, converted);
		
	}
	private Note createNote(String val, List<String> evids) {
		List<EvidencedValue> texts = new ArrayList<>();
		texts.add(UniProtFactory.INSTANCE.createEvidencedValue(val, createEvidence(evids)));
		return CommentFactory.INSTANCE.createNote(texts);
	}
	private SubcellularLocationValue create(String val, List<String> evidences) {
		return SubcellularLocationCommentBuilder.createSubcellularLocationValue(val, createEvidence(evidences));
	}

	private List<Evidence> createEvidence(List<String> evids) {
		List<Evidence> evidences = new ArrayList<>();
		for (String ev : evids) {
			evidences.add(UniProtFactory.INSTANCE.createEvidence(ev));
		}
		return evidences;
	}
}
