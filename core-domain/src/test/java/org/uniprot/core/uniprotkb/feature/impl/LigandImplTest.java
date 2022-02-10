package org.uniprot.core.uniprotkb.feature.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.feature.Ligand;

/**
 *
 * @author jluo
 * @date: 8 Feb 2022
 *
 */

class LigandImplTest {

	@Test
	void testFull() {
		Ligand ligand = new LigandImpl("aname", "aId", "alabel", "anote");
		assertEquals("aname", ligand.getName());
		assertEquals("aId", ligand.getId());
		assertEquals("alabel", ligand.getLabel());
		assertEquals("anote", ligand.getNote());
	}

	@Test
	void testMissing() {
		Ligand ligand = new LigandImpl("aname", "aId", null, null);
		assertEquals("aname", ligand.getName());
		assertEquals("aId", ligand.getId());
		assertEquals(null, ligand.getLabel());
		assertEquals(null, ligand.getNote());
	}

	@Test
	void needDefaultConstructorForJsonDeserialization() {
		Ligand obj = new LigandImpl();
		assertNotNull(obj);
	}

	@Test
	void builderFrom_constructorImp_shouldCreate_equalObject() {
		LigandImpl impl = new LigandImpl("aname", "aId", "alabel", "anote");
		Ligand obj = LigandBuilder.from(impl).build();
		assertTrue(impl.equals(obj) && obj.equals(impl));
		assertEquals(impl.hashCode(), obj.hashCode());
	}
}
