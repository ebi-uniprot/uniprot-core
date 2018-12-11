package uk.ac.ebi.uniprot.domain.taxonomy.impl;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonName;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

class TaxonNameImplTest {

	@Test
	public void testScientificOnly() {
		String scientificName = "Homo sapiens";
		TaxonName organism = new TaxonNameImpl(scientificName, null, null);
		assertEquals(scientificName, organism.getScientificName());
		assertEquals("", organism.getCommonName());
		assertEquals(0, organism.getSynonyms().size());
		assertEquals("Homo sapiens", organism.toString());
		TestHelper.verifyJson(organism);
	}

	@Test
	public void testScientificAndCommon() {
		String scientificName = "Homo sapiens";
		String commonName = "Human";
		TaxonName organism = new TaxonNameImpl(scientificName, commonName, null);
		assertEquals(scientificName, organism.getScientificName());
		assertEquals(commonName, organism.getCommonName());
		assertEquals(0, organism.getSynonyms().size());
		assertEquals("Homo sapiens (Human)", organism.toString());
		TestHelper.verifyJson(organism);
	}

	@Test
	public void testOrganismImplWithSynonym() {

		String scientificName = "Homo sapiens";
		String commonName = "Human";
		List<String> synonyms = Arrays.asList("Name1", "Name2");
		TaxonName organism = new OrganismNameImpl(scientificName, commonName, synonyms);

		assertEquals(scientificName, organism.getScientificName());
		assertEquals(commonName, organism.getCommonName());

		assertEquals("Homo sapiens (Human) (Name1, Name2)", organism.toString());
		TestHelper.verifyJson(organism);
	}
}
