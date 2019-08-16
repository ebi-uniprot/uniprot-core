package org.uniprot.core.uniref.builder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniref.GoTerm;
import org.uniprot.core.uniref.GoTermType;

/**
 *
 * @author jluo
 * @date: 12 Aug 2019
 *
*/

class GoTermBuilderTest {

	@Test
	void testType() {
		GoTermType type = GoTermType.FUNCTION;
		GoTerm goTerm = new GoTermBuilder().type(type).build();
		assertEquals(type, goTerm.getType());
	}

	@Test
	void testTypeId() {
		GoTermType type = GoTermType.COMPONENT;
		String id = "GO:0044444";
		GoTerm goTerm = new GoTermBuilder().type(type).id(id).build();
		assertEquals(type, goTerm.getType());
		assertEquals(id, goTerm.getId());
	}
	@Test
	void testFrom() {
		GoTermType type = GoTermType.COMPONENT;
		String id = "GO:0044444";
		GoTerm goTerm = new GoTermBuilder().type(type).id(id).build();
		
		GoTerm goTerm2 = new GoTermBuilder().from(goTerm).build();
		assertEquals(goTerm, goTerm2);
	}

}

