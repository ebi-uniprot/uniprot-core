package uk.ac.ebi.uniprot.domain.taxonomy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.taxonomy.OrganismName;

class OrganismImplTest {

	@Test
	void testOrganismImpl() {
		String scientificName = "Homo sapiens";
		String commonName = "Human";
		OrganismName name = new OrganismNameImpl(scientificName, commonName, null);

		Organism organism = new OrganismImpl(name, 9606l);
		assertEquals(9606l, organism.getTaxonId());
		assertEquals(name, organism.getName());
		TestHelper.verifyJson(organism);
	}

}
