package uk.ac.ebi.uniprot.domain.taxonomy.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.taxonomy.Taxon;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonName;

class TaxonImplTest {

	@Test
	void test() {
		String scientificName = "Homo sapiens";
		String commonName = "Human";
		TaxonName name = new TaxonNameImpl(scientificName, commonName, null);

		Taxon taxon = new TaxonImpl(name, 9606l);
		assertEquals(9606l, taxon.getTaxonId());
		assertEquals(name, taxon.getName());
		TestHelper.verifyJson(taxon);
	}

}
