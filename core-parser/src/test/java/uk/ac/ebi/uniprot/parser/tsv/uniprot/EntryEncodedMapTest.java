package uk.ac.ebi.uniprot.parser.tsv.uniprot;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.uniprot.GeneEncodingType;
import uk.ac.ebi.uniprot.domain.uniprot.GeneLocation;
import uk.ac.ebi.uniprot.domain.uniprot.builder.GeneLocationBuilder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class EntryEncodedMapTest {
	@Test
	void testFields() {
		List<String> fields =  EntryEncodedMap.FIELDS;
		List<String> expected = Collections.singletonList("gene_location");
		assertEquals(expected, fields);
	}
	
	@Test
	void testGetDataEmpty() {
		EntryEncodedMap dl = new EntryEncodedMap(null);
		assertTrue(dl.attributeValues().isEmpty());
	}
	
	@Test
	void testGetData() {
		List<GeneLocation> glocations = new ArrayList<>();
		glocations.add(new GeneLocationBuilder().geneEncodingType(GeneEncodingType.MITOCHONDRION).build());
		glocations.add(new GeneLocationBuilder().geneEncodingType(GeneEncodingType.PLASMID).value("mlp1").build());
		EntryEncodedMap dl = new EntryEncodedMap(glocations);
		Map<String, String> result = dl.attributeValues();
		assertEquals(1, result.size());
		String value = result.get(EntryEncodedMap.FIELDS.get(0));
		assertNotNull(value);
		assertEquals("Mitochondrion; Plasmid mlp1", value);
	}

}
