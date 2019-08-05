package uk.ac.ebi.uniprot.parser.tsv.uniprot;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.taxonomy.Organism;
import org.uniprot.core.uniprot.taxonomy.builder.OrganismBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntryOrganismMapTest {

	@Test
	void testFields() {
		List<String> fields =  EntryOrganismMap.FIELDS;
		List<String> expected =Arrays.asList("organism", "organism_id", "tax_id");
		assertEquals(expected, fields);
	}
	
	@Test
	void testGetData() {
		Organism organism = new OrganismBuilder().taxonId(9606).scientificName("Homo sapiens").commonName("Human").build();
		EntryOrganismMap dl = new EntryOrganismMap(organism);
		Map<String, String> result = dl.attributeValues();
		assertEquals(3, result.size());
		verify("organism", "Homo sapiens (Human)", result);
		verify("organism_id", "9606", result);
		verify("tax_id", "9606", result);
	}
	
	private void verify(String field, String expected, Map<String, String> result) {
		assertEquals(expected, result.get(field));
	}

}
