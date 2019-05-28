package uk.ac.ebi.uniprot.xml.uniparc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.Location;
import uk.ac.ebi.uniprot.domain.uniparc.SequenceFeature;
import uk.ac.ebi.uniprot.domain.uniparc.SignatureDbType;
import uk.ac.ebi.uniprot.domain.uniparc.builder.InterProGroupBuilder;
import uk.ac.ebi.uniprot.domain.uniparc.builder.SequenceFeatureBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniparc.SeqFeatureType;

/**
 *
 * @author jluo
 * @date: 24 May 2019
 *
*/

class SequenceFeatureConverterTest {

	@Test
	void testSingleLocation() {
//		<signatureSequenceMatch database="PANTHER" id="PTHR11977">
//		<ipr id="IPR007122" name="Villin/Gelsolin"/>
//		<lcn start="49" end="790"/>
//		</signatureSequenceMatch>
		SequenceFeatureBuilder builder = new SequenceFeatureBuilder();
		builder.signatureDbType(SignatureDbType.PANTHER)
		.signatureDbId("PTHR11977")
		.addLocation(new Location(49, 790))
		.interproGroup(
		new InterProGroupBuilder()
		.id("IPR007122")
		.name("Villin/Gelsolin")
		.build());
		verify(builder.build());
	}
	private void verify(SequenceFeature sf) {
		SequenceFeatureConverter converter = new SequenceFeatureConverter();
		SeqFeatureType xmlObj =converter.toXml(sf);
		 System.out.println(UniParcXmlTestHelper.toXmlString(xmlObj, SeqFeatureType.class, "signatureSequenceMatch"));
		 SequenceFeature converted = converter.fromXml(xmlObj);
		   assertEquals(sf, converted);	
		
	}
	@Test
	void testMultiLocations() {
//		<signatureSequenceMatch database="Pfam" id="PF00626">
//		<ipr id="IPR007123" name="Gelsolin-like domain"/>
//		<lcn start="81" end="163"/>
//		<lcn start="202" end="267"/>
//		<lcn start="330" end="398"/>
//		<lcn start="586" end="653"/>
//		<lcn start="692" end="766"/>
//		</signatureSequenceMatch>
		SequenceFeatureBuilder builder = new SequenceFeatureBuilder();
		builder.signatureDbType(SignatureDbType.PFAM)
		.signatureDbId("PF00626")
		.addLocation(new Location(81, 163))
		.addLocation(new Location(202, 267))
		.addLocation(new Location(330, 398))
		.addLocation(new Location(586, 653))
		.addLocation(new Location(692, 766))
		.interproGroup(
		new InterProGroupBuilder()
		.id("IPR007123")
		.name("Gelsolin-like domain")
		.build());
		verify(builder.build());
	}
}

