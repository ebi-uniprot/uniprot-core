package org.uniprot.core.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 *
 * @author jluo
 * @date: 30 Aug 2019
 *
*/

class UtilsTest {

	@Test
	void testCapitalize() {
		String str ="protein";
		String result = Utils.capitalize(str);
		assertEquals("Protein", result);
		
	}
	@Test
	void testUncapitalize() {
		String str ="Protein";
		String result = Utils.uncapitalize(str);
		assertEquals("protein", result);
		
	}
}

