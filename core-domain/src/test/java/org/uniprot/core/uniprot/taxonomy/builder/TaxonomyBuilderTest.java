package org.uniprot.core.uniprot.taxonomy.builder;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.taxonomy.Taxonomy;
import org.uniprot.core.uniprot.taxonomy.builder.TaxonomyBuilder;

import java.util.Collections;

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
		Taxonomy taxonomy = getCompleteTaxonomy();
		Taxonomy fromTest = TaxonomyBuilder.newInstance().from(taxonomy).build();
		assertEquals(taxonomy, fromTest);
	}

	@Test
	void testScientificName() {
		Taxonomy taxonomy =TaxonomyBuilder.newInstance().taxonId(9606)
				.scientificName("Homo sapiens").build();
		assertEquals("Homo sapiens", taxonomy.getScientificName());
	}

	@Test
	void testCommonName() {
		Taxonomy taxonomy =TaxonomyBuilder.newInstance().taxonId(9606)
				.commonName("Human")
				.build();
		assertEquals("Human", taxonomy.getCommonName());
	}

	@Test
	void testSynonyms() {
		Taxonomy taxonomy = getCompleteTaxonomy();
		assertEquals(Collections.singletonList("Some name"), taxonomy.getSynonyms());
	}

	private Taxonomy getCompleteTaxonomy() {
		return TaxonomyBuilder.newInstance().taxonId(9606)
					.scientificName("Homo sapiens")
					.commonName("Human")
					.synonyms(Collections.singletonList("Some name"))
					.mnemonic("HUMAN").build();
	}

}
