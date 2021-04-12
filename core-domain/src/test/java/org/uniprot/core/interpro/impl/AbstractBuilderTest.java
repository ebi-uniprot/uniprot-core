package org.uniprot.core.interpro.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.interpro.Abstract;

/**
 *
 * @author jluo
 * @date: 12 Apr 2021
 *
*/

class AbstractBuilderTest {

	@Test
	void testBuild() {
		  String id = "some value";
		  Abstract entryId = new AbstractBuilder(id).build();
	        assertEquals(id, entryId.getValue());

	}

	@Test
	void testFrom() {
		 String id = "some value";
		 Abstract entryId = new AbstractBuilder(id).build();
		 Abstract entryId2= AbstractBuilder.from(entryId).build();
	        assertEquals(entryId, entryId2);
	}

}

