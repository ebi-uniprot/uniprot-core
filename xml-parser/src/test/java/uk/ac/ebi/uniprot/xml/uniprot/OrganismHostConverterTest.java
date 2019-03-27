package uk.ac.ebi.uniprot.xml.uniprot;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.taxonomy.OrganismHost;
import uk.ac.ebi.uniprot.domain.taxonomy.builder.OrganismHostBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.OrganismType;
import uk.ac.ebi.uniprot.xml.uniprot.OrganismHostConverter;

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
