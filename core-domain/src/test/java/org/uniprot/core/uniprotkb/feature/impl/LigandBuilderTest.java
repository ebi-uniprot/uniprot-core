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

class LigandBuilderTest {

	
	@Test
	void canSetName() {
		Ligand ligand = new LigandBuilder().name("some name").build();
		assertEquals("some name", ligand.getName());
	}
	@Test
	void canSetId() {
		Ligand ligand = new LigandBuilder().id("ChEBI:CHEBI:29180").build();
		assertEquals("ChEBI:CHEBI:29180", ligand.getId());
	}
	@Test
	void canSetLabel() {
		Ligand ligand = new LigandBuilder().label("some label").build();
		assertEquals("some label", ligand.getLabel());
	}
	@Test
	void canSetNote() {
		Ligand ligand = new LigandBuilder().note("some note").build();
		assertEquals("some note", ligand.getNote());
	}
	@Test
    void canCreateBuilderFromInstance() {
        Ligand obj = new LigandBuilder().name("some name").build();
        LigandBuilder builder = LigandBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
    	  Ligand obj = new LigandBuilder().name("some name").build();
    	  Ligand obj2 = new LigandBuilder().name("some name").build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}

