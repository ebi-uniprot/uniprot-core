package org.uniprot.core.interpro;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 *
 * @author jluo
 * @date: 9 Apr 2021
 *
*/

class StatusTest {

	@Test
	void test() {
		assertEquals("F", Status.FALSE_POSITIVE.getName());
	}

	@Test
	void testTypeOf() {
		Status status = Status.typeOf("?");
		assertEquals(Status.UNKNOWN, status);
		status = Status.typeOf("T");
		assertEquals(Status.TRUE_POSITIVE, status);
	}
}

