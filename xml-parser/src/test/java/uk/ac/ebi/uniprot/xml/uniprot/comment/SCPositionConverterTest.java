package uk.ac.ebi.uniprot.xml.uniprot.comment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.xml.jaxb.uniprot.LocationType;
import uk.ac.ebi.uniprot.xml.uniprot.UniProtXmlTestHelper;
import uk.ac.ebi.uniprot.xml.uniprot.comment.SCPositionConverter;

class SCPositionConverterTest {

	@Test
	void testSingle() {
		String position ="243";
		SCPositionConverter converter =new SCPositionConverter();
		LocationType locationType =converter.toXml(position);
		 System.out.println(UniProtXmlTestHelper.toXmlString(locationType, LocationType.class, "location"));
		String converted = converter.fromXml(locationType);
		assertEquals(position, converted);
	}
	
	@Test
	void testRange() {
		String position ="243-432";
		SCPositionConverter converter =new SCPositionConverter();
		LocationType locationType =converter.toXml(position);
		 System.out.println(UniProtXmlTestHelper.toXmlString(locationType, LocationType.class, "location"));
		String converted = converter.fromXml(locationType);
		assertEquals(position, converted);
	}

}
