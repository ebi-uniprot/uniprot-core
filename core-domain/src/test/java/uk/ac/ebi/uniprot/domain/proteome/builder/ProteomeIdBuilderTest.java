package uk.ac.ebi.uniprot.domain.proteome.builder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.proteome.ProteomeId;

class ProteomeIdBuilderTest {

	@Test
	void test() {
		String id = "UP000005640";
		ProteomeId proteomeId = new ProteomeIdBuilder (id).build();
		assertEquals(id, proteomeId.getValue());
		assertTrue(proteomeId.isValidId());
	}
	@Test
	void testInvalid() {
		String id = "UP00000564";
		ProteomeId proteomeId = new ProteomeIdBuilder (id).build();
		assertEquals(id, proteomeId.getValue());
		assertFalse(proteomeId.isValidId());
	}
}
