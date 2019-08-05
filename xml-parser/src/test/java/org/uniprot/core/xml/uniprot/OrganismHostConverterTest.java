package org.uniprot.core.xml.uniprot;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.taxonomy.OrganismHost;
import org.uniprot.core.uniprot.taxonomy.builder.OrganismHostBuilder;
import org.uniprot.core.xml.jaxb.uniprot.OrganismType;
import org.uniprot.core.xml.uniprot.OrganismHostConverter;

import static org.junit.Assert.assertEquals;

class OrganismHostConverterTest {

	@Test
	void test() {
		OrganismHost organismHost = createOrganismHost();
		OrganismHostConverter converter = new OrganismHostConverter();
		OrganismType xmlOrganism = converter.toXml(organismHost);
		System.out.println(UniProtXmlTestHelper.toXmlString(xmlOrganism, OrganismType.class, "organism"));
		OrganismHost converted = converter.fromXml(xmlOrganism);
		assertEquals(organismHost, converted);
	}

	private OrganismHost createOrganismHost() {
		OrganismHostBuilder builder = new OrganismHostBuilder();
		return builder.taxonId(29095l)
		.scientificName("Akodon azarae")
		.commonName("Azara's grass mouse")
		.build();
	}
}
