package uk.ac.ebi.uniprot.xml.proteome;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.proteome.RedundantProteome;
import uk.ac.ebi.uniprot.domain.proteome.builder.RedundantProteomeBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.ObjectFactory;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.RedundantProteomeType;

class RedundantProteomeConverterTest {
	private ObjectFactory xmlFactory =new ObjectFactory();
	RedundantProteomeConverter converter = new RedundantProteomeConverter();
	@Test
	void testFromXml() {
		RedundantProteomeType xml = xmlFactory.createRedundantProteomeType();
		xml.setUpid("UP001231");
		xml.setSimilarity(0.91f);
		RedundantProteome rProteome = converter.fromXml(xml);
		assertEquals("UP001231",rProteome.getId().getValue());
		assertEquals(0.91f, rProteome.getSimilarity().doubleValue(), 0.000000001);
	}

	@Test
	void testToXml() {
		RedundantProteome rProteome=
				RedundantProteomeBuilder.newInstance()
				.proteomeId("UP0000123")
				.similarity(0.95f)
				.build();
		RedundantProteomeType xml =converter.toXml(rProteome);
		RedundantProteome converted = converter.fromXml(xml);
		assertEquals(rProteome, converted);
	}

}
