package uk.ac.ebi.uniprot.domain.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.gene.Gene;
import uk.ac.ebi.uniprot.domain.util.json.JsonUtils;

class JsonUtilsTest {

	@Test
	void test() {
		String schema = JsonUtils.getJsonSchema(Gene.class);
		System.out.println(schema);
	}

}
