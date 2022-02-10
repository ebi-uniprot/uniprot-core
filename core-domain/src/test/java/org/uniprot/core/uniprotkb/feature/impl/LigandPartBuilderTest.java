package org.uniprot.core.uniprotkb.feature.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.feature.LigandPart;

/**
 *
 * @author jluo
 * @date: 9 Feb 2022
 *
*/

class LigandPartBuilderTest {

	@Test
	void canSetName() {
		LigandPart ligandPart = new LigandPartBuilder().name("some name").build();
		assertEquals("some name", ligandPart.getName());
	}
	@Test
	void canSetId() {
		LigandPart ligandPart = new LigandPartBuilder().id("ChEBI:CHEBI:29180").build();
		assertEquals("ChEBI:CHEBI:29180", ligandPart.getId());
	}
	@Test
	void canSetLabel() {
		LigandPart ligandPart = new LigandPartBuilder().label("some label").build();
		assertEquals("some label", ligandPart.getLabel());
	}
	@Test
	void canSetNote() {
		LigandPart ligandPart = new LigandPartBuilder().note("some note").build();
		assertEquals("some note", ligandPart.getNote());
	}
	@Test
    void canCreateBuilderFromInstance() {
        LigandPart obj = new LigandPartBuilder().name("some name").build();
        LigandPartBuilder builder = LigandPartBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
    	  LigandPart obj = new LigandPartBuilder().name("some name").build();
    	  LigandPart obj2 = new LigandPartBuilder().name("some name").build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}

