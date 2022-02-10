package org.uniprot.core.xml.uniprot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.feature.LigandPart;
import org.uniprot.core.uniprotkb.feature.impl.LigandPartBuilder;
import org.uniprot.core.xml.jaxb.uniprot.LigandPartType;

/**
 *
 * @author jluo
 * @date: 9 Feb 2022
 *
*/

class FeatureLigandPartConverterTest {
	private final FeatureLigandPartConverter converter = new FeatureLigandPartConverter();
	@Test
	void testLigandFull() {
		LigandPart ligandPart = createLigand("heme c", "ChEBI:CHEBI:61717", "2", "structural" );
		LigandPartType xmlObj =converter.toXml(ligandPart);
		String expectedXml = "<ligandPart xmlns=\"http://uniprot.org/uniprot\">\n"
				+ "    <name>heme c</name>\n"
				+ "    <dbReference type=\"ChEBI\" id=\"CHEBI:61717\"/>\n"
				+ "    <label>2</label>\n"
				+ "    <note>structural</note>\n"
				+ "</ligandPart>";
		assertEquals(expectedXml, UniProtXmlTestHelper.toXmlString(xmlObj, LigandPartType.class, "ligandPart"));
		LigandPart roundTrip = converter.fromXml(xmlObj);
		assertEquals(ligandPart, roundTrip);		
	}
	
	@Test
	void testLigandNoNote() {
		LigandPart ligandPart = createLigand("heme c", "ChEBI:CHEBI:61717", "2",null );
		LigandPartType xmlObj =converter.toXml(ligandPart);
		String expectedXml = "<ligandPart xmlns=\"http://uniprot.org/uniprot\">\n"
				+ "    <name>heme c</name>\n"
				+ "    <dbReference type=\"ChEBI\" id=\"CHEBI:61717\"/>\n"
				+ "    <label>2</label>\n"
				+ "</ligandPart>";
		assertEquals(expectedXml, UniProtXmlTestHelper.toXmlString(xmlObj, LigandPartType.class, "ligandPart"));
		LigandPart roundTrip = converter.fromXml(xmlObj);
		assertEquals(ligandPart, roundTrip);
	}
	
	@Test
	void testLigandNoNoteLabel() {
		LigandPart ligandPart = createLigand("heme c", "ChEBI:CHEBI:61717", null,null );
		LigandPartType xmlObj =converter.toXml(ligandPart);
		String expectedXml = "<ligandPart xmlns=\"http://uniprot.org/uniprot\">\n"
				+ "    <name>heme c</name>\n"
				+ "    <dbReference type=\"ChEBI\" id=\"CHEBI:61717\"/>\n"
				+ "</ligandPart>";
		assertEquals(expectedXml, UniProtXmlTestHelper.toXmlString(xmlObj, LigandPartType.class, "ligandPart"));
		LigandPart roundTrip = converter.fromXml(xmlObj);
		assertEquals(ligandPart, roundTrip);	
	}
	@Test
	void testLigandNoNoteLabelNoId() {
		LigandPart ligandPart = createLigand("heme c", null, null,null );
		LigandPartType xmlObj =converter.toXml(ligandPart);
		String expectedXml = "<ligandPart xmlns=\"http://uniprot.org/uniprot\">\n"
				+ "    <name>heme c</name>\n"
				+ "</ligandPart>";
		assertEquals(expectedXml, UniProtXmlTestHelper.toXmlString(xmlObj, LigandPartType.class, "ligandPart"));
		LigandPart roundTrip = converter.fromXml(xmlObj);
		assertEquals(ligandPart, roundTrip);				
	}

	private LigandPart createLigand(String name, String id, String label, String note) {
		return new LigandPartBuilder().name(name).id(id).label(label).note(note).build();
	}
}

