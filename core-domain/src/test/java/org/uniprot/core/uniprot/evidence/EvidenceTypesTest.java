package org.uniprot.core.uniprot.evidence;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 *
 * @author jluo
 * @date: 30 Aug 2019
 *
*/

public class EvidenceTypesTest {

	@Test
	void testGetType() {
		String name ="EMBL";
		EvidenceTypeDetail emblEvidence =EvidenceTypes.INSTANCE.getType(name);
		assertNotNull(emblEvidence);
	}
}

