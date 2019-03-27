package uk.ac.ebi.uniprot.xml.uniprot.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType.Conflict;
import uk.ac.ebi.uniprot.xml.uniprot.UniProtXmlTestHelper;
import uk.ac.ebi.uniprot.xml.uniprot.comment.SCConflictConverter;

class SCConflictConverterTest {

	@Test
	void testSequence() {
		String sequence="BAA86482.2";
		SCConflictConverter converter = new SCConflictConverter();
		Conflict conflict =converter.toXml(sequence);
		conflict.setType("erroneous initiation");
		 System.out.println(UniProtXmlTestHelper.toXmlString(conflict, Conflict.class, "confict"));
		String converted = converter.fromXml(conflict);
		assertEquals(sequence, converted);
	}
	@Test
	void testRef() {
		String sequence="Ref.1";
		SCConflictConverter converter = new SCConflictConverter();
		Conflict conflict =converter.toXml(sequence);
		conflict.setType("erroneous initiation");
		 System.out.println(UniProtXmlTestHelper.toXmlString(conflict, Conflict.class, "confict"));
		String converted = converter.fromXml(conflict);
		assertEquals(sequence, converted);
	}

}
