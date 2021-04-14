package org.uniprot.core.interpro.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 *
 * @author jluo
 * @date: 14 Apr 2021
 *
*/

class InterProMatchContainerImplTest {

	@Test
	void testDefaultConstructor() {
		InterProMatchContainerImpl obj =new InterProMatchContainerImpl();
		assertNotNull(obj);
	}
}

