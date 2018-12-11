package uk.ac.ebi.uniprot.domain.util;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.gene.Gene;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntry;
import uk.ac.ebi.uniprot.domain.util.json.JsonUtils;

class JsonUtilsTest {

	@Test
	void test() {
		String schema = JsonUtils.getJsonSchema(Gene.class);
		System.out.println(schema);
	}

	@Test
	void test2() {
		String schema = JsonUtils.getJsonSchema(UniProtEntry.class);
		System.out.println(schema);
	}

}
