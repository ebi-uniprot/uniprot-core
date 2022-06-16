package org.uniprot.core.uniprotkb.feature.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LigandImplTest {

	@Test
	void needDefaultConstructor() {
		LigandImpl ligand = new LigandImpl();
		assertNotNull(ligand);
	}
	@Test
	void testFullConstructor() {
		LigandPartImpl ligandPart = new LigandPartImpl("LigandPart name",  "ChEBI:CHEBI:23434", null, null);
		LigandImpl ligand = new LigandImpl("Ca(2+)", "ChEBI:CHEBI:2134", "2", "some note", ligandPart);
		assertEquals("Ca(2+)", ligand.getName());
		assertEquals("ChEBI:CHEBI:2134", ligand.getId());
		assertEquals( "2", ligand.getLabel());
		assertEquals("some note", ligand.getNote());
		assertEquals(ligandPart, ligand.getLigandPart().get());
	}
	
	@Test
	void testPartConstructor() {
		LigandImpl ligand = new LigandImpl("Substrate", null, null, null, null);
		assertEquals("Substrate", ligand.getName());
		assertEquals(null, ligand.getId());
		assertEquals( null, ligand.getLabel());
		assertEquals(null, ligand.getNote());
		assertTrue(ligand.getLigandPart().isEmpty());
	}

}
