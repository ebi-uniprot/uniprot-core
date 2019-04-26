package uk.ac.ebi.uniprot.domain.uniprot.taxonomy.builder;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.uniprot.taxonomy.Taxonomy;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

class TaxonomyBuilderTest {

	@Test
	void testTaxonId() {
		Taxonomy taxonomy =TaxonomyBuilder.newInstance().taxonId(9606).build();
		assertEquals(9606, taxonomy.getTaxonId());
	}

	@Test
	void testMnemonic() {
		Taxonomy taxonomy =TaxonomyBuilder.newInstance().mnemonic("HUMAN").build();
		assertEquals("HUMAN", taxonomy.getMnemonic());
	}

	@Test
	void testFromTaxonomy() {
		Taxonomy taxonomy =TaxonomyBuilder.newInstance().taxonId(9606)
				.scientificName("Homo sapiens")
				.commonName("Human")
				.mnemonic("HUMAN").build();
		Taxonomy fromTest = TaxonomyBuilder.newInstance().from(taxonomy).build();
		assertEquals(taxonomy, fromTest);
	}

	@Test
	void testScientificName() {
		Taxonomy taxonomy =TaxonomyBuilder.newInstance().taxonId(9606)
				.scientificName("Homo sapiens")
				.commonName("Human")
				.mnemonic("HUMAN").build();
		assertEquals("Homo sapiens", taxonomy.getScientificName());
	}

	@Test
	void testCommonName() {
		Taxonomy taxonomy =TaxonomyBuilder.newInstance().taxonId(9606)
				.scientificName("Homo sapiens")
				.commonName("Human")
				.mnemonic("HUMAN").build();
		assertEquals("Human", taxonomy.getCommonName());
		
	}

	@Test
	void testSynonyms() {
		Taxonomy taxonomy =TaxonomyBuilder.newInstance().taxonId(9606)
				.scientificName("Homo sapiens")
				.commonName("Human")
				.synonyms(Arrays.asList("Some name"))
				.mnemonic("HUMAN").build();
		assertEquals("Human", taxonomy.getCommonName());
		assertEquals(Arrays.asList("Some name"), taxonomy.getSynonyms());
	}

}
