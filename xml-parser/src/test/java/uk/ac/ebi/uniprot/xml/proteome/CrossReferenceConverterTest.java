package uk.ac.ebi.uniprot.xml.proteome;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.builder.DBCrossReferenceBuilder;
import uk.ac.ebi.uniprot.domain.proteome.ProteomeXReferenceType;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.DbReferenceType;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.ObjectFactory;

class CrossReferenceConverterTest {
	private ObjectFactory xmlFactory =new ObjectFactory();
	CrossReferenceConverter converter = new CrossReferenceConverter();
	@Test
	void testFromXml() {
		DbReferenceType xml = xmlFactory.createDbReferenceType();
		xml.setType("AssemblyId");
		xml.setId("someValue");
		DBCrossReference<ProteomeXReferenceType>  xref = converter.fromXml(xml);
		assertEquals(ProteomeXReferenceType.ASSEMBLY_ID, xref.getDatabaseType());
		assertEquals("someValue", xref.getId());
		assertEquals(0, xref.getProperties().size());
	}

	@Test
	void testToXml() {
		DBCrossReferenceBuilder<ProteomeXReferenceType> builder =
				new DBCrossReferenceBuilder<ProteomeXReferenceType>();
		
		DBCrossReference<ProteomeXReferenceType>  xref =
		builder.databaseType(ProteomeXReferenceType.GENOME_ASSEMBLY)
		.id("AGA21341.1")
		.build();
		DbReferenceType xml =	converter.toXml(xref);
		
		DBCrossReference<ProteomeXReferenceType>  converted = converter.fromXml(xml);
		assertEquals(xref, converted);
				
	}

}
