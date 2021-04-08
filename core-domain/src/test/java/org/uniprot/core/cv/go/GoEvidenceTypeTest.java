package org.uniprot.core.cv.go;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 *
 * @author jluo
 * @date: 8 Apr 2021
 *
*/

class GoEvidenceTypeTest {

	@Test
	void testGetName() {
		GoEvidenceType isa = GoEvidenceType.ISA;
		assertEquals("inferred from Sequence Alignment", isa.getName());
	}

	@Test
	void testTypeOf() {	
		GoEvidenceType iba = GoEvidenceType.typeOf("IBA");
		assertEquals(GoEvidenceType.IBA, iba );
	}
	@Test
	void testTypeOfNotExist() {
		 assertThrows(IllegalArgumentException.class, () -> {
			 GoEvidenceType.typeOf("IBB");
			  });
	}
	@Test
	void testTotalType26() {
		assertEquals(26, GoEvidenceType.values().length);
	}
}

