package org.uniprot.core.interpro;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 *
 * @author jluo
 * @date: 9 Apr 2021
 *
*/

class InterProTypeTest {

	@Test
	void testGetDbcodeName() {
		InterProType type = InterProType.ACTIVE_SITE;
		assertEquals("Active site", type.getName());
		assertEquals("A", type.getDbcode());
	}

	@Test
	void testTypeOfDbCode() {
		InterProType type = InterProType.typeOfDbCode("B");
		assertEquals(InterProType.BINDING_SITE, type);
	}

	@Test
	void testTypeOf() {
		InterProType type = InterProType.typeOf("Domain");
		assertEquals(InterProType.DOMAIN, type);
		type = InterProType.typeOf("Family");
		assertEquals(InterProType.FAMILY, type);
	}

}

