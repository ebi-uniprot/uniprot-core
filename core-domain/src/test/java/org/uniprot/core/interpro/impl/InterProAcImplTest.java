package org.uniprot.core.interpro.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 *
 * @author jluo
 * @date: 9 Apr 2021
 *
*/

class InterProAcImplTest {

	@Test
	void testInterProAcImpl() {
		InterProAcImpl obj = new InterProAcImpl();
	        assertNotNull(obj);
	}

	@Test
	void testIsValidId() {
		String id ="IPR011992";
		InterProAcImpl obj = new InterProAcImpl(id);
		assertEquals(id, obj.getValue());
		assertTrue(obj.isValidId());
	}

	@Test
	void testNotValidId() {
		String id ="IPR01199";
		InterProAcImpl obj = new InterProAcImpl(id);
		assertEquals(id, obj.getValue());
		assertFalse(obj.isValidId());
	}
}

