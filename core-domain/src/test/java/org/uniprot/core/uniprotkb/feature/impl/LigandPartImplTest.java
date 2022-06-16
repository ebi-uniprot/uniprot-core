package org.uniprot.core.uniprotkb.feature.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LigandPartImplTest {

	@Test
	void needDefaultConstructor() {
		LigandPartImpl ligandPart = new LigandPartImpl();
		assertNotNull(ligandPart);
	}
	@Test
	void testFullConstructor() {
		LigandPartImpl ligandPart = new LigandPartImpl("Ca(2+)", "ChEBI:CHEBI:2134", "2", "some note");
		assertEquals("Ca(2+)", ligandPart.getName());
		assertEquals("ChEBI:CHEBI:2134", ligandPart.getId());
		assertEquals( "2", ligandPart.getLabel());
		assertEquals("some note", ligandPart.getNote());
	}
	
	@Test
	void testPartConstructor() {
		LigandPartImpl ligandPart = new LigandPartImpl("Substrate", null, null, null);
		assertEquals("Substrate", ligandPart.getName());
		assertEquals(null, ligandPart.getId());
		assertEquals( null, ligandPart.getLabel());
		assertEquals(null, ligandPart.getNote());
	}
}
